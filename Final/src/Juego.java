import Controlador.ControladorMenuMain;
import Modelo.ModeloMenuMain;
import Vista.VistaMenuMain;

import javax.swing.SwingUtilities;

public class Juego {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeloMenuMain modelo = new ModeloMenuMain();
            VistaMenuMain vista = new VistaMenuMain();
            ControladorMenuMain controlador = new ControladorMenuMain(vista, modelo);
            
            vista.mostrar();
        });
    }
}