package Modelo.combate;

import Modelo.pjs.Dados;

public class AtaqueBasico implements EfectoCombate {

    private static final Dados DADO = Dados.d6;

    @Override
    public void aplicar(ContextoCombate contexto) {
        int bono = Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza());
        contexto.infligirDanio(Reglas.tirada(DADO, bono));
    }
}