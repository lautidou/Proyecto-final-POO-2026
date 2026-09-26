package Controlador;

import Modelo.ModeloCreacionPersonaje;
import Modelo.pjs.Clases;
import Modelo.pjs.Heroe;
import Modelo.pjs.Raza;
import Vista.Ventana;
import Vista.VistaCreacionPersonaje;
import Vista.VistaMenuMain;

import javax.swing.JOptionPane;

public class ControladorCreacionPersonaje {

    private final VistaCreacionPersonaje vista;
    private final ModeloCreacionPersonaje modelo;

    public ControladorCreacionPersonaje(VistaCreacionPersonaje vista, ModeloCreacionPersonaje modelo) {
        if (vista == null || modelo == null) {
            throw new IllegalArgumentException("La vista y el modelo son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;

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

            // TODO: cuando exista la pantalla del juego/escenario, navegar ahí
            // en vez de volver al menú principal.
            Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error al crear personaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarVolver() {
        Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);
    }
}