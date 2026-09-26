package Controlador;

import Modelo.ModeloMenuMain;
import Vista.Ventana;
import Vista.VistaCreacionPersonaje;
import Vista.VistaMenuMain;

public class ControladorMenuMain {

    private final VistaMenuMain vista;
    private final ModeloMenuMain modelo;

    public ControladorMenuMain(VistaMenuMain vista, ModeloMenuMain modelo) {
        if (vista == null || modelo == null) {
            throw new IllegalArgumentException("La vista y el modelo son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;

        // Registro de eventos para los tres botones del panel central
        this.vista.getBotonUno().addActionListener(evento -> this.ejecutarBotonUno());
        this.vista.getBotonDos().addActionListener(evento -> this.ejecutarBotonDos());
        this.vista.getBotonTres().addActionListener(evento -> this.ejecutarBotonTres());
    }

    private void ejecutarBotonUno() {
        System.out.println("Se presionó el Botón 1");
        this.modelo.procesarAccionUno();
        // Navega a la pantalla de creación de personaje
        Ventana.getInstancia().mostrarPanel(VistaCreacionPersonaje.NOMBRE);
    }

    private void ejecutarBotonDos() {
        System.out.println("Se presionó el Botón 2");
        this.modelo.procesarAccionDos();
    }

    private void ejecutarBotonTres() {
        System.out.println("Se presionó el Botón 3");
        this.modelo.procesarAccionTres();
    }
}