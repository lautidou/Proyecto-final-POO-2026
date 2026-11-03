package Modelo.pjs.enemigos;

import java.util.ArrayList;

import Modelo.combate.CatalogoObjetos;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Enemigo;

public class Goblin extends Enemigo {

    public Goblin() {
        super("Goblin", 1, Clases.Barbaro, new ArrayList<>(),
        new Caracteristicas(8, 14, 10, 10, 8, 8), 50, new ArrayList<>());
         setRutaSprite(Recursos.Rutas.GOBLIN);
         setRutaSpritePelea(Recursos.Rutas.GOBLIN_PELEA); 
         equipar(CatalogoObjetos.dagaDeHierro());
         equipar(CatalogoObjetos.armaduraDeCuero());
    }
}