package Modelo.mapa;

/** Coordenada inmutable dentro del mapa. Desplazarla devuelve una nueva Posicion. */
public final class Posicion {
    private final int x;
    private final int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Posicion desplazar(Direccion direccion) {
        return new Posicion(x + direccion.getDx(), y + direccion.getDy());
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (!(otro instanceof Posicion)) return false;
        Posicion p = (Posicion) otro;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}