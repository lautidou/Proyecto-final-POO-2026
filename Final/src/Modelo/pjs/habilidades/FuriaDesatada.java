package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class FuriaDesatada extends Habilidad {
    public FuriaDesatada() {
        super(5, "Furia desatada", "Una furia incontrolable: 2d10 + Fuerza.", 1, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modFuerza = Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza());
        int danio = Reglas.tiradaMultiple(2, Dados.d10, modFuerza);
        contexto.infligirDanio(danio);
    }
}