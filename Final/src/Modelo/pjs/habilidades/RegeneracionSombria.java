package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class RegeneracionSombria extends Habilidad {

    public RegeneracionSombria() {
        super(15, "Regeneración sombría", "Las sombras cierran sus heridas: recupera 2d8 + Constitución.", 2, true);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int mod = Reglas.modificador(contexto.getActor().getCaracteristicas().getConstitucion());
        int danio = Reglas.tiradaMultiple(2, Dados.d8, mod);
        contexto.curarse(danio);
    }
}