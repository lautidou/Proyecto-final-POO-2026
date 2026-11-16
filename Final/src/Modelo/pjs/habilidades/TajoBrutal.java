package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class TajoBrutal extends Habilidad {
    public TajoBrutal() {
        super(4, "Tajo brutal", "Un tajo con todo el peso del arma: 2d6 + Fuerza.", 2, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modFuerza = Reglas.modificador(contexto.getActor().getCaracteristicas().getFuerza());
        contexto.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6, modFuerza));
    }
}