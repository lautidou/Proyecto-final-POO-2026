package Modelo;

import Modelo.pjs.Enemigo;
import Modelo.pjs.Heroe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModeloCombate {

    public static final String PROP_ESTADO = "estado";
    public static final String PROP_MENSAJE = "mensaje";

    private Heroe heroe;
    private Enemigo enemigo;
    private final PropertyChangeSupport soporte = new PropertyChangeSupport(this);

    public void iniciarCombate(Heroe heroe, Enemigo enemigo) {
        if (heroe == null) {
            throw new IllegalArgumentException("El héroe no puede ser nulo.");
        }
        if (enemigo == null) {
            throw new IllegalArgumentException("El enemigo no puede ser nulo.");
        }
        this.heroe = heroe;
        this.enemigo = enemigo;
        // TODO: si el combate tiene más estado (turno actual, ronda...), reiniciarlo aquí.
        notificarCambio();
        registrarMensaje("¡" + heroe.getNombre() + " se enfrenta a " + enemigo.getNombre() + "!");
    }

    public void procesarCombate() {
        // TODO
    }

    public void procesarBusqueda() {
        // TODO
    }

    public void procesarInventario() {
        // TODO
    }

    public void procesarEsconderse() {
        // TODO
    }

    public void addPropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.addPropertyChangeListener(oyente);
    }

    public void removePropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.removePropertyChangeListener(oyente);
    }

    private void notificarCambio() {
        this.soporte.firePropertyChange(PROP_ESTADO, null, this);
    }

    private void registrarMensaje(String mensaje) {
        this.soporte.firePropertyChange(PROP_MENSAJE, null, mensaje);
    }

    public Heroe getHeroe() {
        return heroe;
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }
}