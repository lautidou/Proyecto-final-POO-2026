package Modelo.pjs.enemigos;

import Modelo.pjs.Enemigo;

public class GeneradorJefeFinal extends GeneradorEnemigos {

    @Override
    protected Enemigo crearEnemigo() {
        return new JefeFinal();
    }
}