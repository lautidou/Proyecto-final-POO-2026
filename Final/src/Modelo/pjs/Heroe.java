package Modelo.pjs;
import java.util.List;

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
        this.inventario = inventario;
        this.raza = raza;
    }
}
