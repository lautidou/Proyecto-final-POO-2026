package Controlador;

import Modelo.ModeloCombate;
import Modelo.ModeloCreacionPersonaje;
import Modelo.ModeloMapa;
import Modelo.ModeloMenuMain;
import Modelo.ModeloPartida;
import Modelo.partida.PartidaGuardadaDAO;
import Vista.Ventana;
import Vista.VistaCombate;
import Vista.VistaCreacionPersonaje;
import Vista.VistaMapa;
import Vista.VistaMenuMain;

public class ControladorPantallas {

    private final Ventana ventana;
    private final PartidaGuardadaDAO partidas;

    public ControladorPantallas(Ventana ventana, PartidaGuardadaDAO partidas) {
        if (ventana == null || partidas == null) {
            throw new IllegalArgumentException("La ventana y el DAO de partidas son obligatorios");
        }
        this.ventana = ventana;
        this.partidas = partidas;
    }

    public void iniciar() {
        ModeloMapa modeloMapa = new ModeloMapa();
        ModeloPartida modeloPartida = new ModeloPartida(this.partidas, modeloMapa);

        registrarMenuPrincipal(modeloPartida);
        registrarMapa(modeloMapa, modeloPartida);
        registrarCreacionPersonaje(modeloMapa);
        registrarCombate();

        this.ventana.mostrarPanel(VistaMenuMain.NOMBRE);
        this.ventana.mostrar();
    }

    private void registrarMenuPrincipal(ModeloPartida modeloPartida) {
        ModeloMenuMain modelo = new ModeloMenuMain();
        VistaMenuMain vista = new VistaMenuMain();

        this.ventana.registrarPanel(VistaMenuMain.NOMBRE, vista);
        new ControladorMenuMain(vista, modelo, modeloPartida);
    }

    private void registrarMapa(ModeloMapa modelo, ModeloPartida modeloPartida) {
        VistaMapa vista = new VistaMapa();

        this.ventana.registrarPanel(VistaMapa.NOMBRE, vista);
        new ControladorMapa(vista, modelo, modeloPartida);
    }

    private void registrarCreacionPersonaje(ModeloMapa modeloMapa) {
        ModeloCreacionPersonaje modelo = new ModeloCreacionPersonaje();
        VistaCreacionPersonaje vista = new VistaCreacionPersonaje();

        this.ventana.registrarPanel(VistaCreacionPersonaje.NOMBRE, vista);
        new ControladorCreacionPersonaje(vista, modelo, modeloMapa);
    }

    private ControladorCombate registrarCombate() {
        ModeloCombate modelo = new ModeloCombate();
        VistaCombate vista = new VistaCombate();

        this.ventana.registrarPanel(VistaCombate.NOMBRE, vista);
        return new ControladorCombate(vista, modelo);
    }
}