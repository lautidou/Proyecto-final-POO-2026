package Modelo.pjs.enemigos;

import Modelo.pjs.Enemigo;

public class GeneradorGoblins extends GeneradorEnemigos {

    @Override
    protected Enemigo crearEnemigo() {
        return new Goblin();
    }
}