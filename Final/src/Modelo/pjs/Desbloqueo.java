package Modelo.pjs;

public final class Desbloqueo {

    private final int nivelRequerido;
    private final Habilidad habilidad;

    public Desbloqueo(int nivelRequerido, Habilidad habilidad) {
        if (nivelRequerido <= 0 || nivelRequerido > 20) {
            throw new IllegalArgumentException("El nivel de desbloqueo debe estar entre 1 y 20.");
        }
        if (habilidad == null) {
            throw new IllegalArgumentException("La habilidad no puede ser nula.");
        }
        this.nivelRequerido = nivelRequerido;
        this.habilidad = habilidad;
    }

    public int getNivelRequerido() {
        return nivelRequerido;
    }

    public Habilidad getHabilidad() {
        return habilidad;
    }
}