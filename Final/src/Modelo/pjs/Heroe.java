package Modelo.pjs;

import java.util.ArrayList;
import java.util.List;

import Modelo.objetos.Arma;
import Modelo.combate.CatalogoObjetos;
import Modelo.objetos.Armadura;
import Modelo.objetos.Objeto;

// el heroe ahora es abstracto
public abstract class Heroe extends Personaje {

    private int experiencia;
    private List<Objeto> inventario;
    private Raza raza;

    protected Heroe(String nombre, int nivel, Raza raza, Dados dadoGolpe,
                    Caracteristicas caracteristicas, int experiencia, List<Objeto> inventario) {
        super(nombre, nivel, dadoGolpe, new ArrayList<>(), caracteristicas);
        if (raza == null) {
            throw new IllegalArgumentException("La raza no puede ser nula.");
        }
        this.experiencia = experiencia;
        this.inventario = (inventario != null) ? inventario : new ArrayList<>();
        this.raza = raza;
    }

    // ---- Lo que cada clase define por su cuenta ----
    public abstract Clases getClase();

    protected abstract Arma armaInicial();

    protected abstract Armadura armaduraInicial();

    protected abstract List<Desbloqueo> getDesbloqueos();

    // Un heroe de nivel N conoce las habilidades cuyo nivel de desbloqueo es <= N.
    @Override
    protected List<Habilidad> habilidadesDesbloqueadas(int nivel) {
        List<Habilidad> desbloqueadas = new ArrayList<>();
        for (Desbloqueo desbloqueo : getDesbloqueos()) {
            if (desbloqueo.getNivelRequerido() <= nivel) {
                desbloqueadas.add(desbloqueo.getHabilidad());
            }
        }
        return desbloqueadas;
    }

    // Equipa el arma y la armadura de su clase y devuelve el inventario con el que arranca.
    public List<Objeto> crearEquipoInicial() {
        Arma arma = armaInicial();
        Armadura armadura = armaduraInicial();
        arma.usar(this);
        armadura.usar(this);
        List<Objeto> equipo = new ArrayList<>();
        equipo.add(arma);
        equipo.add(armadura);
        equipo.add(CatalogoObjetos.pocionCurativa(5));
        return equipo;
    }

    // Suma experiencia y sube de nivel automáticamente si supera los puntos necesarios
    public void ganarExperiencia(int puntosExp) {
        if (puntosExp <= 0) return;
        this.experiencia += puntosExp;
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
