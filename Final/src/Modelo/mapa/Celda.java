package Modelo.mapa;

public class Celda {
    private final Terreno terreno;

    public Celda(Terreno terreno) {
        if (terreno == null) {
            throw new IllegalArgumentException("El terreno no puede ser nulo.");
        }
        this.terreno = terreno;
    }

    public boolean esPasable() {
        return terreno.esPasable();
    }

    public Terreno getTerreno() {
        return terreno;
    }
}