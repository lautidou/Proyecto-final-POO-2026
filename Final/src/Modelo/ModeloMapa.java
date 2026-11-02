package Modelo;

import Modelo.mapa.Direccion;
import Modelo.partida.EnemigoDerrotado;
import Modelo.mapa.Mapa;
import Modelo.mapa.Mundo;
import Modelo.mapa.MundoDePrueba;
import Modelo.mapa.Portal;
import Modelo.mapa.Posicion;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Heroe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModeloMapa {

    public static final String PROP_ESCENARIO = "escenario";
    public static final String PROP_PERSONAJE = "personaje";
    public static final String PROP_POSICION = "posicion";
    public static final String PROP_ENCUENTRO = "encuentro";
    public static final String PROP_ENEMIGOS = "enemigos";

    private static final int PASOS_POR_TURNO_ENEMIGO = 3;
    private static final int RADIO_PERSECUCION = 4;

    private final Random azar = new Random();
    private int pasosDesdeMovimientoEnemigos = 0;

    // Cómo se arma un mundo nuevo: así se puede volver a empezar con todos los enemigos vivos
    private final Supplier<Mundo> fabricaMundo;
    private Mundo mundo;
    private Heroe personaje;
    private final Set<EnemigoDerrotado> derrotados = new LinkedHashSet<>();
    private final PropertyChangeSupport soporte = new PropertyChangeSupport(this);

    public ModeloMapa() {
        this(MundoDePrueba::crear);
    }

    public ModeloMapa(Supplier<Mundo> fabricaMundo) {
        if (fabricaMundo == null) {
            throw new IllegalArgumentException("La fábrica de mundos no puede ser nula.");
        }
        this.fabricaMundo = fabricaMundo;
        this.mundo = nuevoMundo();
    }

    private Mundo nuevoMundo() {
        Mundo nuevo = this.fabricaMundo.get();
        if (nuevo == null) {
            throw new IllegalStateException("La fábrica de mundos devolvió null.");
        }
        nuevo.validar();
        nuevo.reiniciar();
        return nuevo;
    }

    // Partida nueva: mundo nuevo, con todos los enemigos vivos
    public void colocarPersonaje(Heroe nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El personaje no puede ser nulo.");
        }
        Mapa mapaAnterior = this.mundo.getMapaActual();
        this.mundo = nuevoMundo();
        this.derrotados.clear();
        instalar(nuevo, mapaAnterior, this.mundo.getPosicionInicial());
    }

    public void eliminarEnemigo(Enemigo enemigo) {
        Mapa actual = this.mundo.getMapaActual();
        Posicion posicion = enemigo.getPosicion();
        if (actual.quitarEnemigo(enemigo)) {
            this.derrotados.add(new EnemigoDerrotado(actual.getId(), posicion));
            this.soporte.firePropertyChange(PROP_ENEMIGOS, null, actual);
        }
    }

    // Para guardar la partida
    public Set<EnemigoDerrotado> getEnemigosDerrotados() {
        return Collections.unmodifiableSet(this.derrotados);
    }

    // Cargar partida: se arma un mundo nuevo y se le quitan los enemigos que ya estaban vencidos
    public boolean restaurar(Heroe heroe, String idEscenario, Posicion posicion,
                             Collection<EnemigoDerrotado> yaDerrotados) {
        if (heroe == null || idEscenario == null || posicion == null || yaDerrotados == null) {
            return false;
        }
        Mundo nuevo = nuevoMundo();   // si algo falla acá, el mundo actual no se toca
        Mapa destino = nuevo.getMapa(idEscenario);
        if (destino == null || !destino.esTransitable(posicion)) {
            return false;
        }
        Mapa mapaAnterior = this.mundo.getMapaActual();
        this.mundo = nuevo;
        this.derrotados.clear();
        for (EnemigoDerrotado muerto : yaDerrotados) {
            Mapa mapa = this.mundo.getMapa(muerto.idMapa());
            Enemigo enemigo = (mapa == null) ? null : mapa.getEnemigoEn(muerto.posicion());
            if (enemigo != null && mapa.quitarEnemigo(enemigo)) {
                this.derrotados.add(muerto);
            }
        }
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

    public boolean moverPersonaje(Direccion direccion) {
        if (this.personaje == null || this.personaje.getPosicion() == null) {
            return false;
        }
        Mapa mapaAnterior = this.mundo.getMapaActual();
        Posicion origen = this.personaje.getPosicion();
        Posicion destino = origen.desplazar(direccion);

        // Chocar con un enemigo es interactuar con él: el héroe no se mueve y se avisa del encuentro.
        Enemigo enemigo = mapaAnterior.getEnemigoEn(destino);
        if (enemigo != null) {
            this.soporte.firePropertyChange(PROP_ENCUENTRO, null, enemigo);
            return false;
        }

        if (!mapaAnterior.esTransitable(destino)) {
            return false;
        }

        // Calcula el estado final antes de avisar
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

        this.pasosDesdeMovimientoEnemigos++;
        if (this.pasosDesdeMovimientoEnemigos >= PASOS_POR_TURNO_ENEMIGO) {
            this.pasosDesdeMovimientoEnemigos = 0;
            moverEnemigos();
        }
        return true;
    }

    // Mueve cada enemigo del mapa una casilla, persigue al héroe si está cerca, si no al azar.
    private void moverEnemigos() {
        Mapa mapa = this.mundo.getMapaActual();
        Posicion posHeroe = this.personaje.getPosicion();
        Enemigo contacto = null;

        for (Enemigo enemigo : mapa.getEnemigos()) {
            Posicion actual = enemigo.getPosicion();
            Direccion dir = (distancia(actual, posHeroe) <= RADIO_PERSECUCION)
                    ? direccionHacia(mapa, enemigo, posHeroe)
                    : direccionAlAzar(mapa, enemigo);
            if (dir == null) {
                continue;
            }
            Posicion destino = actual.desplazar(dir);
            if (!destino.equals(posHeroe)) {
                enemigo.setPosicion(destino);
            }
        }

        // Un enemigo se encuentra con el personaje y arranca el combate.
        for (Enemigo enemigo : mapa.getEnemigos()) {
            if (distancia(enemigo.getPosicion(), posHeroe) == 1) {
                contacto = enemigo;
                break;
            }
        }

        this.soporte.firePropertyChange(PROP_ENEMIGOS, null, mapa);
        if (contacto != null) {
            this.soporte.firePropertyChange(PROP_ENCUENTRO, null, contacto);
        }
    }

    private Direccion direccionHacia(Mapa mapa, Enemigo enemigo, Posicion objetivo) {
        Posicion actual = enemigo.getPosicion();
        Direccion mejor = null;
        int mejorDistancia = distancia(actual, objetivo);
        for (Direccion dir : Direccion.values()) {
            Posicion destino = actual.desplazar(dir);
            if (!destino.equals(objetivo) && !puedeEntrar(mapa, destino)) {
                continue;
            }
            int d = distancia(destino, objetivo);
            if (d < mejorDistancia) {
                mejorDistancia = d;
                mejor = dir;
            }
        }
        return mejor;
    }

    private Direccion direccionAlAzar(Mapa mapa, Enemigo enemigo) {
        List<Direccion> libres = new ArrayList<>();
        for (Direccion dir : Direccion.values()) {
            if (puedeEntrar(mapa, enemigo.getPosicion().desplazar(dir))
                    && !destino(enemigo, dir).equals(this.personaje.getPosicion())) {
                libres.add(dir);
            }
        }
        return libres.isEmpty() ? null : libres.get(this.azar.nextInt(libres.size()));
    }

    private Posicion destino(Enemigo enemigo, Direccion dir) {
        return enemigo.getPosicion().desplazar(dir);
    }

    private boolean puedeEntrar(Mapa mapa, Posicion p) {
        return mapa.esTransitable(p) && mapa.getPortalEn(p) == null && mapa.getEnemigoEn(p) == null;
    }

    private int distancia(Posicion a, Posicion b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
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