package Controlador;

import Modelo.ModeloCombate;
import Modelo.ModeloCreacionPersonaje;
import Modelo.ModeloMapa;
import Modelo.ModeloMenuMain;
import Modelo.ModeloPartida;
import Modelo.ModeloPausa;
import Modelo.partida.PartidaGuardadaDAO;
import Vista.Ventana;
import Vista.VistaCombate;
import Vista.VistaCreacionPersonaje;
import Vista.VistaMapa;
import Vista.VistaMenuMain;
import Vista.VistaMuerte;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        ModeloPausa modeloPausa =  new ModeloPausa();
        
        ControladorCombate combate = registrarCombate(modeloMapa, modeloPartida);
        
        registrarMenuPrincipal(modeloPartida);
        registrarMapa(modeloMapa, modeloPartida, combate, modeloPausa);
        registrarCreacionPersonaje(modeloMapa);

        registrarSalida(modeloPartida);

        this.ventana.mostrarPanel(VistaMenuMain.NOMBRE);
        this.ventana.mostrar();
    }

        private void registrarSalida(ModeloPartida modeloPartida) {
        // Cerrar la ventana (Alt+F4, etc.)
        this.ventana.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SalidaDelJuego.salir(modeloPartida);
            }
        });
        // Red de seguridad: Ctrl+C, kill, o cualquier System.exit que alguien agregue despues
        Runtime.getRuntime().addShutdownHook(new Thread(modeloPartida::apagarGuardador, "cierre-guardador"));
    }

    private void registrarMenuPrincipal(ModeloPartida modeloPartida) {
        ModeloMenuMain modelo = new ModeloMenuMain();
        VistaMenuMain vista = new VistaMenuMain();

        this.ventana.registrarPanel(VistaMenuMain.NOMBRE, vista);
        new ControladorMenuMain(vista, modelo, modeloPartida);
    }

    private void registrarMapa(ModeloMapa modelo, ModeloPartida modeloPartida, ControladorCombate combate, ModeloPausa modeloPausa) {
        VistaMapa vista = new VistaMapa();

        this.ventana.registrarPanel(VistaMapa.NOMBRE, vista);
        new ControladorMapa(vista, modelo, modeloPartida, combate::iniciarCombate, modeloPausa);
    }

    private void registrarCreacionPersonaje(ModeloMapa modeloMapa) {
        ModeloCreacionPersonaje modelo = new ModeloCreacionPersonaje();
        VistaCreacionPersonaje vista = new VistaCreacionPersonaje();

        this.ventana.registrarPanel(VistaCreacionPersonaje.NOMBRE, vista);
        new ControladorCreacionPersonaje(vista, modelo, modeloMapa);
    }

    private ControladorCombate registrarCombate(ModeloMapa modeloMapa, ModeloPartida modeloPartida) {
        ModeloCombate modelo = new ModeloCombate();
        VistaCombate vista = new VistaCombate();
        VistaMuerte vistaMuerte = new VistaMuerte();

        this.ventana.registrarPanel(VistaCombate.NOMBRE, vista);
        this.ventana.registrarPanel(VistaMuerte.NOMBRE, vistaMuerte);
        new ControladorMuerte(vistaMuerte, modeloPartida);

        return new ControladorCombate(vista, modelo, modeloMapa);
    }
}