package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class Aplastar extends Habilidad {

    public Aplastar() {
        super(13, "Aplastar", "Se deja caer con todo su peso: 2d8 + Fuerza.", 1, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int mod = Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza());
        contexto.infligirDanio(Dados.d8.rolleo() + Reglas.tirada(Dados.d8, mod));
    }
}