package Modelo.mapa;

public enum Terreno {
    PASTO(true),
    PIEDRA(true),
    PUERTA(true),
    PARED(false),
    AGUA(false);

    private final boolean pasable;

    Terreno(boolean pasable) {
        this.pasable = pasable;
    }

    public boolean esPasable() {
        return pasable;
    }
}