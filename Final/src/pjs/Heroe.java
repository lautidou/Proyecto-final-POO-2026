package pjs;
import java.util.List;

public class Heroe extends Personaje {
    private int experiencia;
    private List<Objeto> inventario;

    public Heroe(String nombre, int vida, int nivel, Raza raza, String clase, List<Habilidad> habilidades,
                 int fuerza, int sabiduria, int destreza, int inteligencia, int constitucion, int carisma,
                 int experiencia, List<Objeto> inventario) {
        super(nombre, vida, nivel, raza, clase, habilidades, fuerza, sabiduria, destreza, inteligencia, constitucion, carisma);
        this.experiencia = experiencia;
        this.inventario = inventario;
    }
    public Heroe(int x, int y) {
        super(x, y);
        this.experiencia = 0;
        this.inventario = null; // Inicializamos el inventario como null o una lista vacía según tu diseño
    }
}
