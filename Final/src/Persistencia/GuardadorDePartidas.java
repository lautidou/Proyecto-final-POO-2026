package Persistencia;

import Modelo.partida.PartidaGuardada;
import Modelo.partida.PartidaGuardadaDAO;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Raza;
import Modelo.mapa.Posicion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class GuardadorDePartidas implements Runnable {
    
    public static final PartidaGuardada FIN = new PartidaGuardada(
            "FIN", Raza.Enano, Clases.Barbaro, 1, 0, 0, 
            new Caracteristicas(0,0,0,0,0,0), "FIN", new Posicion(0,0), 
            new ArrayList<>(), new HashMap<>(), new ArrayList<>());

    private final BlockingQueue<PartidaGuardada> pendientes;
    private final PartidaGuardadaDAO partidasDAO;

    public GuardadorDePartidas(BlockingQueue<PartidaGuardada> pendientes, PartidaGuardadaDAO partidasDAO) {
        this.pendientes = pendientes;
        this.partidasDAO = partidasDAO;
    }

    @Override
    public void run() {
        try {
            while (true) {
                PartidaGuardada partida = this.pendientes.take(); 
                if (partida == FIN) { 
                    return;
                }
                guardarSinMorir(partida);
            }
        } catch (InterruptedException e) {
            PartidaGuardada partida;
            while ((partida = this.pendientes.poll()) != null) {
                if (partida != FIN) {
                    guardarSinMorir(partida);
                }
            }
            Thread.currentThread().interrupt();
        }
    }

    private void guardarSinMorir(PartidaGuardada partida) {
        try {
            this.partidasDAO.guardar(partida);
        } catch (RuntimeException e) {
            System.err.println("No se pudo guardar la partida: " + e.getMessage());
        }
    }
    // si el dao falla, el hulo sigue vivo para las siguientes
}