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

import javax.swing.JOptionPane;

public class ControladorCreacionPersonaje {

    private final VistaCreacionPersonaje vista;
    private final ModeloCreacionPersonaje modelo;
    private final ModeloMapa modeloMapa;

    public ControladorCreacionPersonaje(VistaCreacionPersonaje vista, ModeloCreacionPersonaje modelo,
                                        ModeloMapa modeloMapa) {
        if (vista == null || modelo == null || modeloMapa == null) {
            throw new IllegalArgumentException("La vista y los modelos son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;
        this.modeloMapa = modeloMapa;

        this.vista.getBotonCrear().addActionListener(evento -> this.ejecutarCrearPersonaje());
        this.vista.getBotonVolver().addActionListener(evento -> this.ejecutarVolver());
    }

    private void ejecutarCrearPersonaje() {
        try {
            String nombre = this.vista.getNombreIngresado();
            Raza raza = this.vista.getRazaSeleccionada();
            Clases clase = this.vista.getClaseSeleccionada();

            Heroe heroe = this.modelo.crearHeroe(nombre, raza, clase);

            JOptionPane.showMessageDialog(null,
                    "Personaje '" + heroe.getNombre() + "' creado con éxito.");

            this.vista.limpiarCampos();

            // El héroe recién creado entra al mapa y se muestra la pantalla del juego
            this.modeloMapa.colocarPersonaje(heroe);
            Ventana.getInstancia().mostrarPanel(VistaMapa.NOMBRE);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error al crear personaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarVolver() {
        Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);
    }
}