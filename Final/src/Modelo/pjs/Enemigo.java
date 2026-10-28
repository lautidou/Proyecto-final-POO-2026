package Modelo.pjs;

import java.util.List;

import Modelo.objetos.Objeto;

public abstract class Enemigo extends Personaje {
    private int experienciaDada;
    private List<Objeto> recompensa;

    protected Enemigo(String nombre, int nivel, Clases clase, List<Habilidad> habilidades,
        Caracteristicas caracteristicas, int experienciaDada, List<Objeto> recompensa) {
        super(nombre, nivel, clase, habilidades, caracteristicas);
        this.experienciaDada = experienciaDada;
        this.recompensa = recompensa;
    }

    public int getExperienciaDada() {
        return experienciaDada;
    }

    public List<Objeto> getRecompensa() {
        return recompensa;
    }
}