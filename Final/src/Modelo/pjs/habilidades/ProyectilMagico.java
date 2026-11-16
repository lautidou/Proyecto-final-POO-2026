package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;

public class ProyectilMagico extends Habilidad {
    public ProyectilMagico() {
        super(6, "Proyectil mágico", "Un dardo de energía: d6 + Inteligencia.", 5, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modIntel = Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia());
        contexto.infligirDanio(Reglas.tirada(Dados.d6, modIntel));
    }
}