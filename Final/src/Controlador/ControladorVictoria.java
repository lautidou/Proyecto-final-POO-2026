package Controlador;

import Audio.ReproductorAudio;
import Vista.Ventana;
import Vista.VistaMenuMain;
import Vista.VistaVictoria;

public class ControladorVictoria {

    private final VistaVictoria vista;

    public ControladorVictoria(VistaVictoria vista) {
        if (vista == null) {
            throw new IllegalArgumentException("La vista de victoria es obligatoria");
        }
        this.vista = vista;
        this.vista.getBotonMenu().addActionListener(evento -> volverAlMenu());
    }

    private void volverAlMenu() {
        ReproductorAudio.musicaDeMenu();
        Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);
    }
}