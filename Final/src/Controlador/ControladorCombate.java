package Controlador;

import Modelo.ModeloCombate;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Heroe;
import Vista.Ventana;
import Vista.VistaCombate;

import java.beans.PropertyChangeEvent;

public class ControladorCombate {

    private final VistaCombate vista;
    private final ModeloCombate modelo;

    public ControladorCombate(VistaCombate vista, ModeloCombate modelo) {
        if (vista == null || modelo == null) {
            throw new IllegalArgumentException("La vista y el modelo son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;

        // Registro de eventos para los cuatro botones de la botonera
        this.vista.getBotonCombate().addActionListener(evento -> this.ejecutarCombate());
        this.vista.getBotonBusqueda().addActionListener(evento -> this.ejecutarBusqueda());
        this.vista.getBotonInventario().addActionListener(evento -> this.ejecutarInventario());
        this.vista.getBotonEsconderse().addActionListener(evento -> this.ejecutarEsconderse());

        this.modelo.addPropertyChangeListener(this::alCambiarModelo);
    }

    public void iniciarCombate(Heroe heroe, Enemigo enemigo) {
        this.vista.limpiarMensajes();               
        this.modelo.iniciarCombate(heroe, enemigo);  
        Ventana.getInstancia().mostrarPanel(VistaCombate.NOMBRE);
    }

    private void ejecutarCombate() {
        this.modelo.procesarCombate();
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