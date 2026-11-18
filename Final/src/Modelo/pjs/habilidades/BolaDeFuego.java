package Modelo.pjs.habilidades;

import Modelo.combate.ContextoCombate;
import Modelo.pjs.Dados;
import Modelo.combate.Reglas;

public class BolaDeFuego extends Habilidad {
    public BolaDeFuego() {
        super(2, "Bola de fuego", "Una explosión mágica: 2d8 + Inteligencia.", 2, false);
    }

    @Override
    public void aplicar(ContextoCombate contexto) {
        int modIntel = Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia());
        int danioBase = Reglas.tiradaMultiple(2, Dados.d8, modIntel);
        contexto.infligirDanio(danioBase);
    }
}