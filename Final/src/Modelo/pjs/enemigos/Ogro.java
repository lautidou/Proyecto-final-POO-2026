package Modelo.pjs.enemigos;

import java.util.ArrayList;
import java.util.List;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Habilidad;

/** Bruto: ataca sin pensar; cuando esta herido se enfurece y usa su garrotazo en cada turno. */
public class Ogro extends Enemigo {

    private static final double VIDA_DE_FURIA = 0.5;
    private static final double PROB_GARROTAZO = 0.3;

    public Ogro() {
        super("Ogro", 3, Dados.d12, List.of(garrotazo()),
                new Caracteristicas(16, 11, 12, 7, 16, 10), 150, new ArrayList<>());
    }

    @Override
    protected void decidirAccion(ContextoCombate propio) {
        boolean enfurecido = vidaAlMenos(VIDA_DE_FURIA);
        if ((enfurecido || azar(PROB_GARROTAZO)) && intentarHabilidad(propio, false)) {
            return;
        }
        atacar(propio);
    }

    private static Habilidad garrotazo() {
        return new Habilidad(11, "Garrotazo", "Un golpe brutal: 2d6 + Fuerza.",
                c -> c.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6, c.modFuerza())), 3);
    }
}
