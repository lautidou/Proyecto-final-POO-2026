package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class CuracionMenor extends Habilidad {
    public CuracionMenor() {
        super(3, "Curación menor", "Recupera vida: d8 + Sabiduría.", 2, true);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modSabiduria = Reglas.modificador(contexto.getActor().getCaracteristicas().getSabiduria());
        contexto.curarse(Reglas.tirada(Dados.d8, modSabiduria));
    }
}