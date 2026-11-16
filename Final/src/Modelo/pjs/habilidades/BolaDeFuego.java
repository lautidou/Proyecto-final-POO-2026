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
        int danioBase = Dados.d8.rolleo() + Dados.d8.rolleo();
        int modIntel = Reglas.modificador(contexto.getActor().getCaracteristicas().getInteligencia());
        contexto.infligirDanio(danioBase + modIntel);
    }
}