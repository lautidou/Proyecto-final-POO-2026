package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class PunoDePalma extends Habilidad {
    public PunoDePalma() {
        super(8, "Puño de palma", "Un golpe preciso: d8 + Destreza.", 5, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modDestreza = Reglas.modificador(contexto.getActor().getCaracteristicas().getDestreza());
        contexto.infligirDanio(Reglas.tirada(Dados.d8, modDestreza));
    }
}