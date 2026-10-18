package Modelo;

import Modelo.mapa.Direccion;
import Modelo.mapa.Mapa;
import Modelo.mapa.Mundo;
import Modelo.mapa.MundoDePrueba;
import Modelo.mapa.Portal;
import Modelo.mapa.Posicion;
import Modelo.pjs.Heroe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModeloMapa {

    public static final String PROP_ESCENARIO = "escenario";
    public static final String PROP_PERSONAJE = "personaje";
    public static final String PROP_POSICION = "posicion";

    private final Mundo mundo;
    private Heroe personaje;
    private final PropertyChangeSupport soporte = new PropertyChangeSupport(this);

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

    public void colocarPersonaje(Heroe nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El personaje no puede ser nulo.");
        }
        Mapa mapaAnterior = this.mundo.getMapaActual();
        this.mundo.reiniciar();
        instalar(nuevo, mapaAnterior, this.mundo.getPosicionInicial());
    }

    public boolean restaurar(Heroe heroe, String idEscenario, Posicion posicion) {
        if (heroe == null || idEscenario == null || posicion == null) {
            return false;
        }
        Mapa destino = this.mundo.getMapa(idEscenario);
        if (destino == null || !destino.esTransitable(posicion)) {
            return false;
        }
        Mapa mapaAnterior = this.mundo.getMapaActual();
        this.mundo.irA(idEscenario);
        instalar(heroe, mapaAnterior, posicion);
        return true;
    }

    private void instalar(Heroe nuevo, Mapa mapaAnterior, Posicion posicion) {
        Heroe anterior = this.personaje;
        Posicion posicionAnterior = (anterior == null) ? null : anterior.getPosicion();

        nuevo.setPosicion(posicion);
        this.personaje = nuevo;

        this.soporte.firePropertyChange(PROP_ESCENARIO, mapaAnterior, this.mundo.getMapaActual());
        this.soporte.firePropertyChange(PROP_PERSONAJE, anterior, nuevo);
        this.soporte.firePropertyChange(PROP_POSICION, posicionAnterior, nuevo.getPosicion());
    }

    // @return true si el personaje se movió, false si el paso estaba bloqueado
    public boolean moverPersonaje(Direccion direccion) {
        if (this.personaje == null || this.personaje.getPosicion() == null) {
            return false;
        }
        Mapa mapaAnterior = this.mundo.getMapaActual();
        Posicion origen = this.personaje.getPosicion();
        Posicion destino = origen.desplazar(direccion);
        if (!mapaAnterior.esTransitable(destino)) {
            return false;
        }

        // Se calcula el estado final antes de avisar, para no notificar estados intermedios.
        Posicion posicionFinal = destino;
        Portal portal = mapaAnterior.getPortalEn(destino);
        if (portal != null) {
            this.mundo.irA(portal.getIdMapaDestino());
            posicionFinal = portal.getPosicionDestino();
        }
        this.personaje.setPosicion(posicionFinal);

        if (portal != null) {
            this.soporte.firePropertyChange(PROP_ESCENARIO, mapaAnterior, this.mundo.getMapaActual());
        }
        this.soporte.firePropertyChange(PROP_POSICION, origen, posicionFinal);
        return true;
    }

    public void addPropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.addPropertyChangeListener(oyente);
    }

    public void removePropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.removePropertyChangeListener(oyente);
    }

    public Mapa getMapaActual() {
        return this.mundo.getMapaActual();
    }

    public Heroe getPersonaje() {
        return this.personaje;
    }
}