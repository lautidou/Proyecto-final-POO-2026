package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class DrenajeDelVacio extends Habilidad {

    public DrenajeDelVacio() {
        super(10, "Drenaje del vacio", "Roba la vitalidad del rival: 2d6 + Inteligencia y se cura d6 + Sabiduría.", 2, true);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modIntel = Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia());
        contexto.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6, modIntel)); // Primero el daño...
        int modSab = Reglas.modificador(contexto.getActor().getCaracteristicas().getSabiduria());
        contexto.curarse(Reglas.tirada(Dados.d6, modSab)); // ...y después la curación
    }
}