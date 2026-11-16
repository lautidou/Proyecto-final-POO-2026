package Modelo.pjs.enemigos;

import java.util.ArrayList;

import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Enemigo;

public class Esqueleto extends Enemigo {

    public Esqueleto() {
        super("Esqueleto", 2, Clases.Monje, new ArrayList<>(),
        new Caracteristicas(10, 8, 14, 6, 15, 5), 80, new ArrayList<>());
    }
}