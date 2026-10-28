package Modelo.pjs.enemigos;

import Modelo.pjs.Enemigo;

public final class FactoryEnemigos {

    private FactoryEnemigos() {
    }

    public static Enemigo crear(String tipoEnemigo) {
        if (tipoEnemigo == null) {
            throw new IllegalArgumentException("El tipo de enemigo no puede ser nulo.");
        }
        switch (tipoEnemigo) {
            case "Goblin":    return new Goblin();
            case "Esqueleto": return new Esqueleto();
            case "Ogro":      return new Ogro();
            default:
                throw new IllegalArgumentException("Tipo de enemigo desconocido: " + tipoEnemigo);
        }
    }
}