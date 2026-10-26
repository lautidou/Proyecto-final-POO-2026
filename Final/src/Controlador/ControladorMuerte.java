package Controlador;

import Audio.ReproductorAudio;
import Modelo.ModeloPartida;
import Vista.Ventana;
import Vista.VistaMapa;
import Vista.VistaMenuMain;
import Vista.VistaMuerte;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ControladorMuerte {

    private final VistaMuerte vista;
    private final ModeloPartida modeloPartida;

    public ControladorMuerte(VistaMuerte vista, ModeloPartida modeloPartida) {
        if (vista == null || modeloPartida == null) {
            throw new IllegalArgumentException("La vista y el modelo de partida son obligatorios");
        }
        this.vista = vista;
        this.modeloPartida = modeloPartida;

        this.vista.getBotonContinuar().addActionListener(evento -> volverAlPuntoDeGuardado());
        this.vista.getBotonMenu().addActionListener(evento -> volverAlMenu());
        this.vista.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                vista.limpiarAviso(); // el aviso de una muerte anterior no se arrastra
            }
        });
    }

    private void volverAlPuntoDeGuardado() {
        if (this.modeloPartida.cargar()) {
            Ventana.getInstancia().mostrarPanel(VistaMapa.NOMBRE);
        } else {
            this.vista.mostrarAviso("No hay una partida guardada a la que volver.");
        }
    }

    private void volverAlMenu() {
        ReproductorAudio.musicaDeMenu();
        Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);
    }
}