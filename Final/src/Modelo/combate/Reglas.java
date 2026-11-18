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

    // La armadura absorbe la mitad de su valor
    public static int danioTrasDefensa(int danio, int defensa) {
        if (defensa <= 0) {
            return Math.max(1, danio);
        }
        int reduccion = (defensa + 1) / 2; 
        return Math.max(1, danio - reduccion);
    }

    public static int modificador(int puntuacion) {
        return Math.floorDiv(puntuacion - 10, 2);
    }

    public static int tirada(Dados dado, int bonificador) {
        return Math.max(1, dado.rolleo() + bonificador);
    }

    public static int tiradaMultiple(int cantidadDados, Dados dado, int bonificador) {
        int suma = 0;
        for (int i = 0; i < cantidadDados; i++) {
            suma += dado.rolleo();
        }
        return Math.max(1, suma + bonificador);
    }
}