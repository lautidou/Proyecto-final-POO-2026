package Modelo.mapa;

import Modelo.pjs.Enemigo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapa {
    private final String id;
    private final Celda[][] cuadricula;
    private final int filas;
    private final int columnas;
    private final Map<Posicion, Portal> portales = new HashMap<>();
    private final List<Enemigo> enemigos = new ArrayList<>();

    public Mapa(String id, int filas, int columnas, Terreno base) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El mapa necesita un id.");
        }
        if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("El mapa debe tener al menos 1 fila y 1 columna.");
        }
        this.id = id;
        this.filas = filas;
        this.columnas = columnas;
        this.cuadricula = new Celda[filas][columnas];
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                this.cuadricula[y][x] = new Celda(base);
            }
        }
    }

    public String getId() { return id; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }

    public boolean estaDentro(Posicion p) {
        return p.getX() >= 0 && p.getX() < columnas && p.getY() >= 0 && p.getY() < filas;
    }

    public boolean esTransitable(Posicion p) {
        return estaDentro(p) && cuadricula[p.getY()][p.getX()].esPasable();
    }

    public Celda getCelda(Posicion p) {
        validarDentro(p);
        return cuadricula[p.getY()][p.getX()];
    }

    public void setTerreno(Posicion p, Terreno terreno) {
        validarDentro(p);
        cuadricula[p.getY()][p.getX()] = new Celda(terreno);
    }

    public void agregarPortal(Posicion origen, Portal portal) {
        validarDentro(origen);
        if (portal == null) {
            throw new IllegalArgumentException("El portal no puede ser nulo.");
        }
        portales.put(origen, portal);
    }

    public Portal getPortalEn(Posicion p) {
        return portales.get(p);
    }

    public Map<Posicion, Portal> getPortales() {
        return Collections.unmodifiableMap(portales);
    }

    public void agregarEnemigo(Enemigo enemigo, Posicion posicion) {
        if (enemigo == null) {
            throw new IllegalArgumentException("El enemigo no puede ser nulo.");
        }
        validarDentro(posicion);
        if (!esTransitable(posicion) || portales.containsKey(posicion) || getEnemigoEn(posicion) != null) {
            throw new IllegalArgumentException("No se puede colocar un enemigo en " + posicion + " del mapa '" + id + "'.");
        }
        enemigo.setPosicion(posicion);
        enemigos.add(enemigo);
    }

    public boolean quitarEnemigo(Enemigo enemigo) {
        return enemigos.remove(enemigo);
    }

    public Enemigo getEnemigoEn(Posicion p) {
        for (Enemigo enemigo : enemigos) {
            if (p.equals(enemigo.getPosicion())) {
                return enemigo;
            }
        }
        return null;
    }

    public List<Enemigo> getEnemigos() {
        return Collections.unmodifiableList(enemigos);
    }

    private void validarDentro(Posicion p) {
        if (!estaDentro(p)) {
            throw new IllegalArgumentException("Posición fuera del mapa '" + id + "': " + p);
        }
    }
}