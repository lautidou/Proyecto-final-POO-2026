package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class RafagaDeGolpes extends Habilidad {
    public RafagaDeGolpes() {
        super(9, "Ráfaga de golpes", "Una lluvia de golpes: 2d6 + Destreza.", 1, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modDestreza = Reglas.modificador(contexto.getActor().getCaracteristicas().getDestreza());
        contexto.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6, modDestreza));
    }
}