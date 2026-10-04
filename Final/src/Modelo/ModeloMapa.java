package Modelo;

import Modelo.mapa.Direccion;
import Modelo.mapa.Mapa;
import Modelo.mapa.Mundo;
import Modelo.mapa.MundoDePrueba;
import Modelo.mapa.Portal;
import Modelo.mapa.Posicion;
import Modelo.pjs.Personaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordina el Mundo con el personaje que lo recorre: decide si un movimiento
 * es posible, lo aplica y cambia de escenario al pisar un portal.
 * Avisa a sus observadores cada vez que su estado cambia, sin conocerlos.
 */
public class ModeloMapa {

    private final Mundo mundo;
    private Personaje personaje;
    private final List<Runnable> observadores = new ArrayList<>();

    public ModeloMapa() {
        this(MundoDePrueba.crear());
    }

    public ModeloMapa(Mundo mundo) {
        if (mundo == null) {
            throw new IllegalArgumentException("El mundo no puede ser nulo.");
        }
        mundo.validar();
        mundo.reiniciar();
        this.mundo = mundo;
    }

    /** Se llama al crear el héroe: lo ubica en el inicio del mundo. */
    public void colocarPersonaje(Personaje personaje) {
        if (personaje == null) {
            throw new IllegalArgumentException("El personaje no puede ser nulo.");
        }
        this.mundo.reiniciar();
        personaje.setPosicion(this.mundo.getPosicionInicial());
        this.personaje = personaje;
        notificarObservadores();
    }

    /** @return true si el personaje se movió, false si el paso estaba bloqueado. */
    public boolean moverPersonaje(Direccion direccion) {
        if (this.personaje == null || this.personaje.getPosicion() == null) {
            return false;
        }
        Mapa actual = this.mundo.getMapaActual();
        Posicion destino = this.personaje.getPosicion().desplazar(direccion);
        if (!actual.esTransitable(destino)) {
            return false;
        }
        this.personaje.setPosicion(destino);

        Portal portal = actual.getPortalEn(destino);
        if (portal != null) {
            atravesar(portal);
        }
        notificarObservadores();
        return true;
    }

    private void atravesar(Portal portal) {
        this.mundo.irA(portal.getIdMapaDestino());
        this.personaje.setPosicion(portal.getPosicionDestino());
    }

    /** Quien se suscribe es avisado cada vez que cambia el escenario o el personaje. */
    public void agregarObservador(Runnable observador) {
        if (observador == null) {
            throw new IllegalArgumentException("El observador no puede ser nulo.");
        }
        this.observadores.add(observador);
    }

    private void notificarObservadores() {
        for (Runnable observador : this.observadores) {
            observador.run();
        }
    }

    public Mapa getMapaActual() {
        return this.mundo.getMapaActual();
    }

    public Personaje getPersonaje() {
        return this.personaje;
    }
}