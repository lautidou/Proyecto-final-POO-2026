package Controlador;

import Audio.ReproductorAudio;
import Modelo.ModeloMenuMain;
import Modelo.ModeloPartida;
import Vista.Ventana;
import Vista.VistaCreacionPersonaje;
import Vista.VistaMapa;
import Vista.VistaMenuMain;

import javax.swing.JOptionPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ControladorMenuMain {

    private final VistaMenuMain vista;
    private final ModeloMenuMain modelo;
    private final ModeloPartida modeloPartida;

    public ControladorMenuMain(VistaMenuMain vista, ModeloMenuMain modelo, ModeloPartida modeloPartida) {
        if (vista == null || modelo == null || modeloPartida == null) {
            throw new IllegalArgumentException("La vista y los modelos son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;
        this.modeloPartida = modeloPartida;

        // La musica del menu suena apenas arranca y cada vez que se muestre el panel
        ReproductorAudio.musicaDeMenu();
        this.vista.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                ReproductorAudio.musicaDeMenu();
            }
        });

        // Registro de eventos para los tres botones del panel central
        this.vista.getBotonUno().addActionListener(evento -> this.ejecutarBotonUno());
        this.vista.getBotonDos().addActionListener(evento -> this.ejecutarBotonDos());
        this.vista.getBotonTres().addActionListener(evento -> this.ejecutarBotonTres());
    }

    private void ejecutarBotonUno() {
        ReproductorAudio.efectoBotonMenu();
        this.modelo.procesarAccionUno();
        // Navega a la pantalla de creación de personaje
        Ventana.getInstancia().mostrarPanel(VistaCreacionPersonaje.NOMBRE);
    }

    private void ejecutarBotonDos() {
        ReproductorAudio.efectoBotonMenu();
        if (this.modeloPartida.cargar()) {
            Ventana.getInstancia().mostrarPanel(VistaMapa.NOMBRE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay una partida guardada para cargar.",
                    "Cargar partida", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void ejecutarBotonTres() {
        ReproductorAudio.efectoBotonMenu();
        System.exit(0);
    }
}