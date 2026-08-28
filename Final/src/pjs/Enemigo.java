package pjs;
import java.util.List;

public class Enemigo extends Personaje {
    private String tipoEnemigo;
    private int experienciaDada;
    private List<Objeto> recompensa;

    public Enemigo(String nombre, int vida, int nivel, Raza raza, String clase, List<Habilidad> habilidades,
                    int fuerza, int sabiduria, int destreza, int inteligencia, int constitucion, int carisma,
                    String tipoEnemigo, int experienciaDada, List<Objeto> recompensa) {
        super(nombre, vida, nivel, raza, clase, habilidades, fuerza, sabiduria, destreza, inteligencia, constitucion, carisma);
        this.tipoEnemigo = tipoEnemigo;
        this.experienciaDada = experienciaDada;
        this.recompensa = recompensa;
    }
}
