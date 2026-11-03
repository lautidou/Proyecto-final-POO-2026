package Modelo.combate;

import Modelo.objetos.Arma;
import Modelo.objetos.Armadura;
import Modelo.pjs.Dados;
import Modelo.pjs.Personaje;

public final class Reglas {

    public static final Dados DADO_DESARMADO = Dados.d4;

    private Reglas() {
    }

    public static Dados dadoDeArma(Personaje p) {
        Arma arma = p.getArmaEquipada();
        return (arma != null) ? arma.getDado() : DADO_DESARMADO;
    }

    public static int defensa(Personaje p) {
        Armadura armadura = p.getArmaduraEquipada();
        return (armadura != null) ? armadura.getDefensa() : 0;
    }

    // Ataque básico: dado del arma + modificador de Fuerza
    public static int danioBasico(Personaje p) {
        return tirada(dadoDeArma(p), modificador(p.getCaracteristicas().getFuerza()));
    }

    // La armadura resta daño
    public static int danioTrasDefensa(int danio, int defensa) {
        return Math.max(1, danio - defensa);
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