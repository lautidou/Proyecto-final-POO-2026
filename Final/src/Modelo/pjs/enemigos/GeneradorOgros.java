package Modelo.pjs.enemigos;

import Modelo.pjs.Enemigo;

public class GeneradorOgros extends GeneradorEnemigos {

    @Override
    protected Enemigo crearEnemigo() {
        return new Ogro();
    }
}