package Controlador;

import Audio.ReproductorAudio;
import Modelo.ModeloPartida;
import Vista.Ventana;
import Vista.VistaMapa;
import Vista.VistaMenuMain;
import Vista.VistaVictoria;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ControladorVictoria {

    private final VistaVictoria vista;
    private final ModeloPartida modeloPartida;

    public ControladorVictoria(VistaVictoria vista, ModeloPartida modeloPartida) {
        if (vista == null || modeloPartida == null) {
            throw new IllegalArgumentException("La vista y el modelo de partida son obligatorios");
        }
        this.vista = vista;
        this.modeloPartida = modeloPartida;

        this.vista.getBotonSeguir().addActionListener(evento -> Ventana.getInstancia().mostrarPanel(VistaMapa.NOMBRE));
        this.vista.getBotonMenu().addActionListener(evento -> volverAlMenu());

        // El mapa tiene el autoguardado frenado mientras no se ve: se guarda el logro al ganar.
        this.vista.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                modeloPartida.guardar();
            }
        });
    }

    private void volverAlMenu() {
        ReproductorAudio.musicaDeMenu();
        Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);
    }
}
