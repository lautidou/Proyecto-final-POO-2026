package Controlador;

import Modelo.ModeloCreacionPersonaje;
import Modelo.ModeloMapa;
import Modelo.ModeloMenuMain;
import Vista.Ventana;
import Vista.VistaCreacionPersonaje;
import Vista.VistaMapa;
import Vista.VistaMenuMain;

public class IniciadorPantallas {

    private final Ventana ventana;

    public IniciadorPantallas(Ventana ventana) {
        if (ventana == null) {
            throw new IllegalArgumentException("La ventana es obligatoria");
        }
        this.ventana = ventana;
    }

    public void iniciar() {
        registrarMenuPrincipal();
        ModeloMapa modeloMapa = registrarMapa();
        registrarCreacionPersonaje(modeloMapa);

        this.ventana.mostrarPanel(VistaMenuMain.NOMBRE);
        this.ventana.mostrar();
    }

    private void registrarMenuPrincipal() {
        ModeloMenuMain modelo = new ModeloMenuMain();
        VistaMenuMain vista = new VistaMenuMain();

        this.ventana.registrarPanel(VistaMenuMain.NOMBRE, vista);
        new ControladorMenuMain(vista, modelo);
    }

    private ModeloMapa registrarMapa() {
        ModeloMapa modelo = new ModeloMapa();
        VistaMapa vista = new VistaMapa();

        this.ventana.registrarPanel(VistaMapa.NOMBRE, vista);
        new ControladorMapa(vista, modelo);
        return modelo;
    }

    private void registrarCreacionPersonaje(ModeloMapa modeloMapa) {
        ModeloCreacionPersonaje modelo = new ModeloCreacionPersonaje();
        VistaCreacionPersonaje vista = new VistaCreacionPersonaje();

        this.ventana.registrarPanel(VistaCreacionPersonaje.NOMBRE, vista);
        new ControladorCreacionPersonaje(vista, modelo, modeloMapa);
    }
}