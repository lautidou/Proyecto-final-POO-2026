package Modelo.pjs.enemigos;

import java.util.ArrayList;

import Modelo.combate.CatalogoObjetos;
import Modelo.combate.ContextoCombate;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;

/** Cobarde y astuto: herido, se esconde para escapar; si no, ataca con su daga. */
public class Goblin extends Enemigo {

    private static final double VIDA_PARA_ESCONDERSE = 0.5;
    private static final double PROB_ESCONDERSE = 0.5;

    public Goblin() {
        super("Goblin", 1, Dados.d12, new ArrayList<>(),
                new Caracteristicas(8, 14, 10, 10, 8, 8), 50, new ArrayList<>());
        equipar(CatalogoObjetos.dagaDeHierro());
        equipar(CatalogoObjetos.armaduraDeCuero());
    }

    @Override
    protected void decidirAccion(ContextoCombate propio) {
        if (intentarEsconderse(propio, VIDA_PARA_ESCONDERSE, PROB_ESCONDERSE)) {
            return;
        }
        atacar(propio);
    }
}
