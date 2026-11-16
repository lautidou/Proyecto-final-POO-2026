package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class ReensamblarHuesos extends Habilidad {

    public ReensamblarHuesos() {
        super(11, "Reensamblar huesos", "Los huesos vuelven a su lugar: recupera d8 + Constitución.", 1, true);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int mod = Reglas.modificador(contexto.getActor().getCaracteristicas().getConstitucion());
        contexto.curarse(Reglas.tirada(Dados.d8, mod));
    }
}