package Modelo.combate;

import Modelo.pjs.Arma;
import Modelo.pjs.Armadura;
import Modelo.pjs.Consumible;
import Modelo.pjs.Dados;

public final class CatalogoObjetos {

    private CatalogoObjetos() {
    }
    // ---- Armas ----
    public static Arma espadaCorta() {
        return new Arma(1, "Espada corta", 2.0, 15, "Una espada ligera y equilibrada.", Dados.d8);
    }

    public static Arma dagaDeHierro() {
        return new Arma(2, "Daga de hierro", 0.5, 8, "Pequeña y rápida, hace poco daño.", Dados.d4);
    }

    public static Arma hachaDeGuerra() {
        return new Arma(6, "Hacha de guerra", 3.5, 30, "Un hacha pesada que parte escudos.", Dados.d10);
    }

    public static Arma bastonArcano() {
        return new Arma(7, "Bastón arcano", 2.0, 25, "Un bastón de roble que canaliza la magia.", Dados.d6);
    }

    public static Arma vendajesDeCombate() {
        return new Arma(8, "Vendajes de combate", 0.2, 5, "Vendas reforzadas para golpear sin lastimarse.", Dados.d6);
    }
    // ---- Armaduras ----
    public static Armadura armaduraDeCuero() {
        return new Armadura(3, "Armadura de cuero", 8.0, 20, "Protección ligera que no estorba al moverse.", 2);
    }

    public static Armadura armaduraDeMalla() {
        return new Armadura(4, "Armadura de malla", 20.0, 60, "Anillos de metal entrelazados: pesada pero resistente.", 4);
    }

    public static Armadura armaduraDePieles() {
        return new Armadura(9, "Armadura de pieles", 10.0, 25, "Pieles curtidas de bestias: resistentes y abrigadas.", 3);
    }

    public static Armadura tunicaDeMago() {
        return new Armadura(10, "Túnica de mago", 1.0, 20, "Tela encantada: casi no pesa.", 1);
    }

    public static Armadura ropasDeMonje() {
        return new Armadura(11, "Ropas de monje", 0.8, 10, "Ropas livianas que no limitan el movimiento.", 2);
    }
    // ---- Consumibles ----
    public static Consumible pocionCurativa(int cantidad) {
        return new Consumible(5, "Poción curativa", 0.3, 10, "Recupera 10 puntos de vida.", cantidad, 10);
    }
}