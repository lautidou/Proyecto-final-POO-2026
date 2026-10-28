package Modelo.pjs.enemigos;

import java.util.ArrayList;

import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Enemigo;

public class Goblin extends Enemigo {

    public Goblin() {
        super("Goblin", 1, Clases.Barbaro, new ArrayList<>(),
        new Caracteristicas(8, 14, 10, 10, 8, 8), 50, new ArrayList<>());
    }
}