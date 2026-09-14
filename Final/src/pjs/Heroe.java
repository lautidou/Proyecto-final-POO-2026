package pjs;
import java.util.List;

public class Heroe extends Personaje {
    private int experiencia;
    private List<Objeto> inventario;
    private Raza raza;

    public Heroe(String nombre, int vida, int nivel, Raza raza, String clase, List<Habilidad> habilidades,
                Caracteristicas caracteristicas, int experiencia, List<Objeto> inventario) {
        super(nombre, vida, nivel, raza, clase, habilidades, caracteristicas);
        if (raza == null) {
            throw new IllegalArgumentException("La raza no puede ser nula.");
        }
        this.experiencia = experiencia;
        this.inventario = inventario;
        this.raza = raza;
    }
    public Heroe(int x, int y) {
        super(x, y);
        this.experiencia = 0;
        this.inventario = null; // Inicializamos el inventario como null o una lista vacía según tu diseño
    }
}
