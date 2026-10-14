package Modelo.combate;

import Modelo.pjs.Dados;

public final class Reglas {

    private Reglas() {
    }

    //Modificador de una característica (puntuación - 10) / 2, redondeando hacia abajo.
    public static int modificador(int puntuacion) {
        return Math.floorDiv(puntuacion - 10, 2);
    }

    //Tirada de un dado más un bonificador. Nunca baja de 1.
    public static int tirada(Dados dado, int bonificador) {
        return Math.max(1, dado.rolleo() + bonificador);
    }
}