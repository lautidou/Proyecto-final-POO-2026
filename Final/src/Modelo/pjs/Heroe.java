package Modelo.pjs;

import java.util.ArrayList;
import java.util.List;

import Modelo.objetos.Objeto;

public class Heroe extends Personaje {
    private int experiencia;
    private List<Objeto> inventario;
    private Raza raza;

    public Heroe(String nombre, int nivel, Raza raza, Clases clase, List<Habilidad> habilidades,
                Caracteristicas caracteristicas, int experiencia, List<Objeto> inventario) {
        super(nombre, nivel, clase, habilidades, caracteristicas);
        if (raza == null) {
            throw new IllegalArgumentException("La raza no puede ser nula.");
        }
        this.experiencia = experiencia;
        this.inventario = (inventario != null) ? inventario : new ArrayList<>();
        this.raza = raza;
    }

    // Suma experiencia y sube de nivel automáticamente si supera los puntos necesarios
    public void ganarExperiencia(int puntosExp) {
        if (puntosExp <= 0) return;
        this.experiencia += puntosExp;
        System.out.println(getNombre() + " ha ganado " + puntosExp + " puntos de experiencia (Total: " + this.experiencia + " XP).");
        while (getNivel() < 20 && experienciaRequeridaParaSiguienteNivel() <= this.experiencia) {
            subirNivel();
        }
    }

    public int experienciaRequeridaParaSiguienteNivel() {
        return getNivel() * 100; 
    }

    public int getExperiencia() {
        return experiencia;
    }

    public List<Objeto> getInventario() {
        return inventario;
    }

    public Raza getRaza() {
        return raza;
    }
}