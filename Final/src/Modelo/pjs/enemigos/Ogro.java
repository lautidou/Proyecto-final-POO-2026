package Modelo.pjs.enemigos;

import java.util.ArrayList;

import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Enemigo;

public class Ogro extends Enemigo {

    public Ogro() {
        super("Ogro", 3, Clases.Barbaro, new ArrayList<>(),
        new Caracteristicas(16, 11, 12, 7, 16, 10), 150, new ArrayList<>());
    }
}