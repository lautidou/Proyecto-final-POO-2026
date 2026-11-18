package Modelo.pjs.enemigos;

import Modelo.mapa.Mapa;
import Modelo.mapa.Posicion;
import Modelo.pjs.Enemigo;

public abstract class GeneradorEnemigos {

    protected abstract Enemigo crearEnemigo();

    public Enemigo colocarEn(Mapa mapa, Posicion posicion) {
        if (mapa == null) {
            throw new IllegalArgumentException("El mapa no puede ser nulo.");
        }
        Enemigo enemigo = crearEnemigo();
        mapa.agregarEnemigo(enemigo, posicion);
        return enemigo;
    }
}