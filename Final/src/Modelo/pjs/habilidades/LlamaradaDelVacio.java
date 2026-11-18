package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class LlamaradaDelVacio extends Habilidad {

    public LlamaradaDelVacio() {
        super(14, "Llamarada del vacío", "Fuego oscuro que consume todo: 2d10 + Inteligencia.", 2, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int mod = Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia());
        int danio = Reglas.tiradaMultiple(2, Dados.d10, mod);
        contexto.infligirDanio(danio);
    }
}