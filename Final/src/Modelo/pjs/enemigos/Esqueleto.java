package Modelo.pjs.enemigos;

import java.util.ArrayList;
import java.util.List;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Habilidad;

/** Implacable: nunca se esconde ni se cura; golpea siempre y a veces usa su golpe de hueso. */
public class Esqueleto extends Enemigo {

    private static final double PROB_GOLPE_DE_HUESO = 0.4;

    public Esqueleto() {
        super("Esqueleto", 2, Dados.d8, List.of(golpeDeHueso()),
                new Caracteristicas(10, 8, 14, 6, 15, 5), 80, new ArrayList<>());
    }

    @Override
    protected void decidirAccion(ContextoCombate propio) {
        if (azar(PROB_GOLPE_DE_HUESO) && intentarHabilidad(propio, false)) {
            return;
        }
        atacar(propio);
    }

    private static Habilidad golpeDeHueso() {
        return new Habilidad(10, "Golpe de hueso", "Un golpe seco: d8 + Destreza.",
                c -> c.infligirDanio(Reglas.tirada(Dados.d8, c.modDestreza())), 3);
    }
}
