package Modelo;

import Modelo.mapa.Mapa;
import Modelo.objetos.Arma;
import Modelo.objetos.Armadura;
import Modelo.objetos.Consumible;
import Modelo.objetos.Objeto;
import Modelo.partida.ObjetoGuardado;
import Modelo.partida.PartidaGuardada;
import Modelo.partida.PartidaGuardadaDAO;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Heroe;
import Modelo.pjs.Raza;
import Modelo.pjs.clases.Barbaro;
import Modelo.pjs.clases.Mago;
import Modelo.pjs.clases.Monje;
import Persistencia.GuardadorDePartidas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ModeloPartida {

    private final PartidaGuardadaDAO partidas;
    private final ModeloMapa modeloMapa;

    private final BlockingQueue<PartidaGuardada> pendientes;
    private final Thread hiloGuardador;

    public ModeloPartida(PartidaGuardadaDAO partidas, ModeloMapa modeloMapa) {
        if (partidas == null || modeloMapa == null) {
            throw new IllegalArgumentException("El DAO y el modelo del mapa son obligatorios");
        }
        this.partidas = partidas;
        this.modeloMapa = modeloMapa;

        this.pendientes = new LinkedBlockingQueue<>();
        GuardadorDePartidas guardador = new GuardadorDePartidas(this.pendientes, this.partidas);
        this.hiloGuardador = new Thread(guardador);
        this.hiloGuardador.start();
    }

    public boolean guardar() {
        Heroe heroe = this.modeloMapa.getPersonaje();
        Mapa escenario = this.modeloMapa.getMapaActual();
        if (heroe == null || heroe.getPosicion() == null) {
            return false;
        }
        try {
            List<ObjetoGuardado> inventario = new ArrayList<>();
            for (Objeto o : heroe.getInventario()) {
                if (o.getCantidad() <= 0) continue;
                inventario.add(o.aGuardado(o.estaEquipadoPor(heroe)));
            }
            PartidaGuardada nuevaPartida = new PartidaGuardada(
                    heroe.getNombre(), heroe.getRaza(), heroe.getClass().getSimpleName(),
                    heroe.getNivel(), heroe.getExperiencia(), heroe.getVida(), heroe.getCaracteristicas(),
                    escenario.getId(), heroe.getPosicion(), inventario, heroe.getUsosGastados(),
                    new ArrayList<>(modeloMapa.getEnemigosDerrotados())
            );
            this.pendientes.put(nuevaPartida);
            return true;
        } catch (IllegalArgumentException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }

    public void apagarGuardador() {
        try {
            this.pendientes.put(GuardadorDePartidas.FIN);
            this.hiloGuardador.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean cargar() {
        PartidaGuardada partida = this.partidas.cargar();
        if (partida == null) {
            return false;
        }
        try {
            Heroe heroe = instanciarHeroe(partida);
            heroe.setVida(partida.getVida());
            for (ObjetoGuardado og : partida.getInventario()) {
                Objeto objeto = reconstruirObjeto(og);
                heroe.getInventario().add(objeto);
                if (og.equipado()) {
                    objeto.usar(heroe);
                }
            }
            heroe.restaurarUsosGastados(partida.getUsosHabilidades());
            return this.modeloMapa.restaurar(heroe, partida.getEscenario(), partida.getPosicion(),
                    partida.getEnemigosDerrotados());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private Objeto reconstruirObjeto(ObjetoGuardado og) {
        switch (og.tipo()) {
        case "ARMA":
            return new Arma(og.idObjeto(), og.nombre(), og.peso(), og.precio(),
                    og.descripcion(), Dados.valueOf(og.dado()));
        case "ARMADURA":
            return new Armadura(og.idObjeto(), og.nombre(), og.peso(), og.precio(),
                    og.descripcion(), og.defensa());
        case "CONSUMIBLE":
            return new Consumible(og.idObjeto(), og.nombre(), og.peso(), og.precio(),
                    og.descripcion(), og.cantidad(), og.puntosRestauracion());
        default:
            throw new IllegalArgumentException("Tipo de objeto desconocido en guardado: " + og.tipo());
        }
    }

    private Heroe instanciarHeroe(PartidaGuardada partida) {
        String nombre = partida.getNombre();
        int nivel = partida.getNivel();
        Raza raza = partida.getRaza();
        Caracteristicas caracteristicas = partida.getCaracteristicas();
        int experiencia = partida.getExperiencia();

        switch (partida.getClase()) {
            case "Barbaro":
                return new Barbaro(nombre, nivel, raza, caracteristicas, experiencia);
            case "Mago":
                return new Mago(nombre, nivel, raza, caracteristicas, experiencia);
            case "Monje":
                return new Monje(nombre, nivel, raza, caracteristicas, experiencia);
            default:
                throw new IllegalArgumentException("Clase desconocida en partida guardada: " + partida.getClase());
        }
    }
}