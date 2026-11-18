package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class RayoArcano extends Habilidad {
    public RayoArcano() {
        super(7, "Rayo arcano", "Un rayo devastador: 2d10 + Inteligencia.", 1, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modIntel = Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia());
        int danioBase = Reglas.tiradaMultiple(2, Dados.d10, modIntel);
        contexto.infligirDanio(danioBase);
    }
}