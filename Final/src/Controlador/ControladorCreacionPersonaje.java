package Controlador;

import Modelo.ModeloCreacionPersonaje;
import Modelo.ModeloMapa;
import Modelo.pjs.Clases;
import Modelo.pjs.Heroe;
import Modelo.pjs.Raza;
import Vista.Ventana;
import Vista.VistaCreacionPersonaje;
import Vista.VistaMapa;
import Vista.VistaMenuMain;

import java.util.List;
import java.util.stream.Collectors;

public class ControladorCreacionPersonaje {

    private final VistaCreacionPersonaje vista;
    private final ModeloCreacionPersonaje modelo;
    private final ModeloMapa modeloMapa;

    private static final String RUTA_IMAGENES = "Assets/MenuSeleccion/";
    private final List<Raza> razas;
    private final List<Clases> clases;

    public ControladorCreacionPersonaje(VistaCreacionPersonaje vista, ModeloCreacionPersonaje modelo,
                                        ModeloMapa modeloMapa) {
        if (vista == null || modelo == null || modeloMapa == null) {
            throw new IllegalArgumentException("La vista y los modelos son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;
        this.modeloMapa = modeloMapa;

        this.razas = modelo.getRazas();
        this.clases = modelo.getClases();
        this.vista.setRazas(this.razas.stream().map(Enum::name).collect(Collectors.toList()));
        this.vista.setClases(this.clases.stream().map(Enum::name).collect(Collectors.toList()));

        this.vista.addClaseListener(evento -> this.actualizarImagen());
        this.actualizarImagen();

        this.vista.getBotonCrear().addActionListener(evento -> this.ejecutarCrearPersonaje());
        this.vista.getBotonVolver().addActionListener(evento -> this.ejecutarVolver());
    }

    private void actualizarImagen() {
        Clases clase = elegido(this.clases, this.vista.getIndiceClase());
        this.vista.setImagenPersonaje(clase == null ? null: RUTA_IMAGENES + clase.name().toLowerCase() + "Seleccion.png");
    }

    private void ejecutarCrearPersonaje() {
        try {
            Raza raza = elegido(this.razas, this.vista.getIndiceRaza());
            Clases clase = elegido(this.clases, this.vista.getIndiceClase());

            Heroe heroe = this.modelo.crearHeroe(this.vista.getNombreIngresado(), raza, clase);

            this.vista.mostrarMensaje("Personaje '" + heroe.getNombre() + "' creado con éxito.");
            this.vista.limpiarCampos();

            this.modeloMapa.colocarPersonaje(heroe);
            Ventana.getInstancia().mostrarPanel(VistaMapa.NOMBRE);
        } catch (IllegalArgumentException e) {
            this.vista.mostrarError(e.getMessage());
        }
    }

    private static <T> T elegido(List<T> lista, int indice) {
        return (indice >= 0 && indice < lista.size()) ? lista.get(indice) : null;
    }

    private void ejecutarVolver() {
        Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);
    }
}