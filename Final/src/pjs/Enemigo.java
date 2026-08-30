package pjs;
import java.util.List;

public class Enemigo extends Personaje {
    private String tipoEnemigo;
    private int experienciaDada;
    private List<Objeto> recompensa;

    public Enemigo(String nombre, int vida, int nivel, Raza raza, String clase, List<Habilidad> habilidades,
                    Caracteristicas caracteristicas, String tipoEnemigo, int experienciaDada, List<Objeto> recompensa) {
        super(nombre, vida, nivel, raza, clase, habilidades, caracteristicas);
        this.tipoEnemigo = tipoEnemigo;
        this.experienciaDada = experienciaDada;
        this.recompensa = recompensa;
    }
}
