import Controlador.ControladorPantallas;
import Vista.Ventana;

import javax.swing.SwingUtilities;

public class Juego {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ventana ventana = Ventana.getInstancia();
            new ControladorPantallas(ventana).iniciar();
        });
    }
}