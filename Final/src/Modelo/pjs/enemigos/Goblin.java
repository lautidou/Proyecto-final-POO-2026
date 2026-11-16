package Modelo.pjs.enemigos;

import Modelo.combate.ContextoCombate;
import Modelo.objetos.Arma;
import Modelo.objetos.Armadura;
import Modelo.objetos.Consumible;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Recursos.Rutas;

import java.util.List;
import java.util.ArrayList;

public class Goblin extends Enemigo {

    public Goblin() {
        super("Goblin", 1, Dados.d6, new Caracteristicas(8, 14, 10, 10, 8, 8),
                50, new ArrayList<>(List.of(new Consumible(5, "Poción curativa", 0.3, 10, "Recupera 10 puntos de vida.", 1, 10))),
                Rutas.GOBLIN, Rutas.GOBLIN_PELEA);
        equipar(new Arma(2, "Daga de hierro", 0.5, 8, "Pequeña y rápida, hace poco daño.", Dados.d4));
        equipar(new Armadura(3, "Armadura de cuero", 8.0, 20, "Protección ligera que no estorba al moverse.", 2));
    }

    @Override
    public void decidirTurno(ContextoCombate propio, ContextoCombate rival) {
        if (!rival.esVisible()) {
            buscarA(rival);
            return;
        }
        if (propio.esVisible() && vidaPorDebajoDe(0.5) && Math.random() < 0.6) {
            propio.esconderse();
            return;
        }
        atacarBasico(propio);
    }
}