package Modelo.pjs;
import java.util.List;

public class Enemigo extends Personaje {
    private TipoEnemigo tipoEnemigo;
    private int experienciaDada;
    private List<Objeto> recompensa;

    public Enemigo(String nombre, int nivel, Clases clase, List<Habilidad> habilidades,
        Caracteristicas caracteristicas, TipoEnemigo tipoEnemigo, int experienciaDada, List<Objeto> recompensa) {
        super(nombre, nivel, clase, habilidades, caracteristicas);
        this.tipoEnemigo = tipoEnemigo;
        this.experienciaDada = experienciaDada;
        this.recompensa = recompensa;
    }
}
