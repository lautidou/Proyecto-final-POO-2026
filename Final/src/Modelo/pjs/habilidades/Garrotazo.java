package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class Garrotazo extends Habilidad {

    public Garrotazo() {
        super(12, "Garrotazo", "Un mazazo bruto: d10 + Fuerza.", 3, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int mod = Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza());
        contexto.infligirDanio(Reglas.tirada(Dados.d10, mod));
    }
}