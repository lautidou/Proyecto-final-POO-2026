package Controlador;

import Modelo.ModeloCombate;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Habilidad;
import Modelo.pjs.Heroe;
import Vista.Ventana;
import Vista.VistaCombate;

import javax.swing.JButton;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class ControladorCombate {

    private final VistaCombate vista;
    private final ModeloCombate modelo;

    public ControladorCombate(VistaCombate vista, ModeloCombate modelo) {
        if (vista == null || modelo == null) {
            throw new IllegalArgumentException("La vista y el modelo son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;

        // Menú principal
        this.vista.getBotonCombate().addActionListener(evento -> this.ejecutarCombate());
        this.vista.getBotonBusqueda().addActionListener(evento -> this.ejecutarBusqueda());
        this.vista.getBotonInventario().addActionListener(evento -> this.ejecutarInventario());
        this.vista.getBotonEsconderse().addActionListener(evento -> this.ejecutarEsconderse());

        // Submenú de combate
        this.vista.getBotonBasico().addActionListener(evento -> this.ejecutarBasico());
        this.vista.getBotonHabilidades().addActionListener(evento -> this.abrirMenuHabilidades());
        this.vista.getBotonVolverCombate().addActionListener(evento -> this.vista.mostrarMenuPrincipal());

        // Submenú de habilidades (los botones de cada habilidad se enganchan al abrirlo)
        this.vista.getBotonVolverHabilidades().addActionListener(evento -> this.vista.mostrarMenuCombate());

        this.modelo.addPropertyChangeListener(this::alCambiarModelo);
    }

    public void iniciarCombate(Heroe heroe, Enemigo enemigo) {
        this.vista.limpiarMensajes();                // el historial de la pelea anterior no se arrastra
        this.vista.mostrarMenuPrincipal();           // ni un submenú que haya quedado abierto
        this.modelo.iniciarCombate(heroe, enemigo);  // el modelo avisa -> alCambiarModelo()
        Ventana.getInstancia().mostrarPanel(VistaCombate.NOMBRE);
    }

    // ---------- Menú principal ----------

    /** "Combate" no ataca: abre el submenú. */
    private void ejecutarCombate() {
        this.vista.mostrarMenuCombate();
    }

    private void ejecutarBusqueda() {
        this.modelo.procesarBusqueda();
    }

    private void ejecutarInventario() {
        this.modelo.procesarInventario();
    }

    private void ejecutarEsconderse() {
        this.modelo.procesarEsconderse();
    }

    // ---------- Submenú de combate ----------
    private void ejecutarBasico() {
        this.modelo.procesarAtaqueBasico();
        this.vista.mostrarMenuPrincipal(); // la acción gasta el turno: se vuelve al menú de inicio
    }

    // ---------- Submenú de habilidades ----------
    private void abrirMenuHabilidades() {
        List<Habilidad> habilidades = this.modelo.getHabilidadesDelHeroe();

        List<String> nombres = new ArrayList<>();
        List<String> descripciones = new ArrayList<>();
        for (Habilidad habilidad : habilidades) {
            nombres.add(habilidad.getNombre());
            descripciones.add(habilidad.getDescripcion());
        }

        List<JButton> botones = this.vista.setHabilidades(nombres, descripciones);
        for (int i = 0; i < botones.size(); i++) {
            Habilidad habilidad = habilidades.get(i);
            botones.get(i).addActionListener(evento -> this.ejecutarHabilidad(habilidad));
        }
        this.vista.mostrarMenuHabilidades();
    }

    private void ejecutarHabilidad(Habilidad habilidad) {
        this.modelo.procesarHabilidad(habilidad);
        this.vista.mostrarMenuPrincipal();
    }

    // ---------- Cambios del modelo ----------
    private void alCambiarModelo(PropertyChangeEvent evento) {
        switch (evento.getPropertyName()) {
            case ModeloCombate.PROP_ESTADO:
                actualizarCombatientes();
                break;
            case ModeloCombate.PROP_MENSAJE:
                this.vista.agregarMensaje((String) evento.getNewValue());
                break;
            default:
                return;
        }
        this.vista.repaint();
    }

    private void actualizarCombatientes() {
        Heroe heroe = this.modelo.getHeroe();
        Enemigo enemigo = this.modelo.getEnemigo();
        if (heroe == null || enemigo == null) {
            return;
        }
        this.vista.setHeroe(heroe.getNombre(), heroe.getVida(), heroe.getVidaMaxima());
        this.vista.setEnemigo(enemigo.getNombre(), enemigo.getVida(), enemigo.getVidaMaxima());
    }
}