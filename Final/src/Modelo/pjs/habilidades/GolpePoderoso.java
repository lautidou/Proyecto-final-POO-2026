package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class GolpePoderoso extends Habilidad {
    public GolpePoderoso() {
        super(1, "Golpe poderoso", "Un golpe devastador: d10 + Fuerza.", 3, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modFuerza = Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza());
        contexto.infligirDanio(Reglas.tirada(Dados.d10, modFuerza));
    }
}