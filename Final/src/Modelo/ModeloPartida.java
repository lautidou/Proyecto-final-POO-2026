package Modelo;

import Modelo.mapa.Mapa;
import Modelo.partida.PartidaGuardada;
import Modelo.partida.PartidaGuardadaDAO;
import Modelo.pjs.Heroe;

import java.util.ArrayList;

public class ModeloPartida {

    private final PartidaGuardadaDAO partidas;
    private final ModeloMapa modeloMapa;

    public ModeloPartida(PartidaGuardadaDAO partidas, ModeloMapa modeloMapa) {
        if (partidas == null || modeloMapa == null) {
            throw new IllegalArgumentException("El DAO y el modelo del mapa son obligatorios");
        }
        this.partidas = partidas;
        this.modeloMapa = modeloMapa;
    }

    public boolean guardar() {
        Heroe heroe = this.modeloMapa.getPersonaje();
        Mapa escenario = this.modeloMapa.getMapaActual();
        if (heroe == null || heroe.getPosicion() == null) {
            return false;
        }
        try {
            return this.partidas.guardar(new PartidaGuardada(heroe.getNombre(), heroe.getRaza(), heroe.getClase(),
                    heroe.getNivel(), heroe.getExperiencia(), heroe.getVida(), heroe.getCaracteristicas(),
                    escenario.getId(), heroe.getPosicion()));
        } catch (IllegalArgumentException e) {
            return false;
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
            return this.modeloMapa.restaurar(heroe, partida.getEscenario(), partida.getPosicion());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}