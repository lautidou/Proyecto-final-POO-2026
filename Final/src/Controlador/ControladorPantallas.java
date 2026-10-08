package Controlador;

//import java.util.ArrayList;

import Modelo.ModeloCombate;
import Modelo.ModeloCreacionPersonaje;
import Modelo.ModeloMapa;
import Modelo.ModeloMenuMain;
import Vista.Ventana;
import Vista.VistaCombate;
import Vista.VistaCreacionPersonaje;
import Vista.VistaMapa;
import Vista.VistaMenuMain;


/*import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Heroe;
import Modelo.pjs.Raza;
import Modelo.pjs.TipoEnemigo;*/

public class ControladorPantallas {

    private final Ventana ventana;

    public ControladorPantallas(Ventana ventana) {
        if (ventana == null) {
            throw new IllegalArgumentException("La ventana es obligatoria");
        }
        this.ventana = ventana;
    }

    //probar luego
    //new ModeloMapa(MundoDePrueba.crear())

  /*   public void iniciar() {
    registrarMenuPrincipal();
    ModeloMapa modeloMapa = registrarMapa();
    registrarCreacionPersonaje(modeloMapa);
    ControladorCombate combate = registrarCombate();

    this.ventana.mostrarPanel(VistaMenuMain.NOMBRE);

    // --- PRUEBA TEMPORAL: borrar cuando el mapa dispare los combates ---
    combate.iniciarCombate(crearHeroePrueba(), crearEnemigoPrueba());

    this.ventana.mostrar();
}
private Heroe crearHeroePrueba() {
    return new Heroe("Thorin", 1, Raza.Enano, Clases.Barbaro, new ArrayList<>(),
            new Caracteristicas(15, 10, 12, 8, 14, 8), 0, new ArrayList<>());
}

private Enemigo crearEnemigoPrueba() {
    return new Enemigo("Goblin", 1, Clases.Barbaro, new ArrayList<>(),
            new Caracteristicas(8, 14, 10, 10, 8, 8), TipoEnemigo.Goblin, 50, new ArrayList<>());
}
*/

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

    private ControladorCombate registrarCombate() {
        ModeloCombate modelo = new ModeloCombate();
        VistaCombate vista = new VistaCombate();

        this.ventana.registrarPanel(VistaCombate.NOMBRE, vista);
        return new ControladorCombate(vista, modelo);
    }
}