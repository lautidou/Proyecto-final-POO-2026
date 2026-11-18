package Modelo.pjs.enemigos;

import Modelo.pjs.Enemigo;

public class GeneradorEsqueletos extends GeneradorEnemigos {

    @Override
    protected Enemigo crearEnemigo() {
        return new Esqueleto();
    }
}