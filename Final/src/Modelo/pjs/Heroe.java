package Modelo.pjs;

import java.util.ArrayList;
import java.util.List;

import Modelo.ModeloCombate;
import Modelo.combate.ResultadoCombate;
import Modelo.objetos.Arma;
import Modelo.objetos.Armadura;
import Modelo.objetos.Consumible;
import Modelo.objetos.Objeto;

public abstract class Heroe extends Personaje {
    private int experiencia;
    private List<Objeto> inventario;
    private Raza raza;

    public Heroe(String nombre, int nivel, Raza raza, Dados dadoGolpe, Caracteristicas caracteristicas,
                    int experiencia) {
        super(nombre, nivel, dadoGolpe, caracteristicas);
        if (raza == null) {
            throw new IllegalArgumentException("La raza no puede ser nula.");
        }
        this.raza = raza;
        this.experiencia = experiencia;
        this.inventario = new ArrayList<>();
        actualizarHabilidadesDesbloqueadas();
    }

    @Override
    public void alMorir(ModeloCombate combate) {
        combate.finalizar(ResultadoCombate.DERROTA, getNombre() + " ha caído en combate...");
    }

    public void subirNivel() {
        if (getNivel() >= 20) {
            return;
        }
        this.setNivel(this.getNivel() + 1);
        actualizarHabilidadesDesbloqueadas();
    }

    public void inicializarEquipo() {
        Arma arma = crearArmaInicial();
        Armadura armadura = crearArmaduraInicial();

        arma.usar(this);
        armadura.usar(this);

        this.inventario.add(arma);
        this.inventario.add(armadura);
        this.inventario.add(new Consumible(5, "Poción curativa", 0.3, 10, "Recupera 10 puntos de vida.", 5, 10));
    }

    public abstract Arma crearArmaInicial();

    public abstract Armadura crearArmaduraInicial();

    public void actualizarHabilidadesDesbloqueadas() {
        for (Desbloqueo desbloqueo : getDesbloqueos()) {
            if (desbloqueo.getNivelRequerido() <= getNivel()) {
                aprenderHabilidad(desbloqueo.getHabilidad());
            }
        }
    }

    public abstract List<Desbloqueo> getDesbloqueos();

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