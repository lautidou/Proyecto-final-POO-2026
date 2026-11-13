package Controlador;
 
import Modelo.ModeloPartida;
 
/**
 * Unico lugar por donde el juego se cierra. Primero apaga el hilo guardador (que termina de
 * guardar lo que haya en la cola) y recien despues finaliza la JVM.
 */
public final class SalidaDelJuego {
 
    private SalidaDelJuego() {
    }
 
    public static void salir(ModeloPartida modeloPartida) {
        modeloPartida.apagarGuardador();
        System.exit(0);
    }
}
