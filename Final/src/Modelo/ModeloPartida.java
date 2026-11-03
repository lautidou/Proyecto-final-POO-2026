package Modelo;

import Modelo.combate.CatalogoObjetos;
import Modelo.mapa.Mapa;
import Modelo.objetos.Objeto;
import Modelo.partida.ObjetoGuardado;
import Modelo.partida.PartidaGuardada;
import Modelo.partida.PartidaGuardadaDAO;
import Modelo.pjs.Heroe;
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

        // Se inicializa la cola y el hilo consumidor
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
                inventario.add(new ObjetoGuardado(o.getIdObjeto(), o.getCantidad(), o.estaEquipadoPor(heroe))); 
            }
            
            PartidaGuardada nuevaPartida = new PartidaGuardada(
                    heroe.getNombre(), heroe.getRaza(), heroe.getClase(),
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

    // Método para cerrar ordenadamente al salir del juego
    public void apagarGuardador() {
        try {
            this.pendientes.put(GuardadorDePartidas.FIN); // avisa al consumidor que termine
            this.hiloGuardador.join(); // espera a que se guarde lo pendiente antes de cerrar
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
            Heroe heroe = new Heroe(partida.getNombre(), partida.getNivel(), partida.getRaza(), partida.getClase(),
            new ArrayList<>(), partida.getCaracteristicas(), partida.getExperiencia(), new ArrayList<>());
            heroe.setVida(partida.getVida());
            for (ObjetoGuardado og : partida.getInventario()) {
                Objeto objeto = CatalogoObjetos.crear(og.idObjeto(), og.cantidad());
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
}