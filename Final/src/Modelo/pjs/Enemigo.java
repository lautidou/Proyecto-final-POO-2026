package Modelo.pjs;

import java.util.ArrayList;
import java.util.List;

import Modelo.ModeloCombate;
import Modelo.combate.AtaqueBasico;
import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.combate.ResultadoCombate;
import Modelo.objetos.Objeto;
import Modelo.pjs.habilidades.Habilidad;

public abstract class Enemigo extends Personaje {
    private int experienciaDada;
    private ArrayList<Objeto> recompensa;

    public Enemigo(String nombre, int nivel, Dados dadoGolpe, Caracteristicas caracteristicas,
                      int experienciaDada, ArrayList<Objeto> recompensa,
                      String rutaSprite, String rutaSpritePelea) {
        super(nombre, nivel, dadoGolpe, caracteristicas, rutaSprite, rutaSpritePelea);
        this.experienciaDada = experienciaDada;
        this.recompensa = (recompensa != null) ? new ArrayList<>(recompensa) : new ArrayList<>();
    }

    public abstract void decidirTurno(ContextoCombate propio, ContextoCombate rival);

    @Override
    public void alMorir(ModeloCombate combate) {
        Heroe heroe = combate.getHeroe();
        heroe.ganarExperiencia(this.experienciaDada);
        if (this.recompensa != null && !this.recompensa.isEmpty()) {
            heroe.getInventario().addAll(this.recompensa);
        }
        combate.finalizar(ResultadoCombate.VICTORIA, getNombre() + " ha sido derrotado.");
    }

    // ---- Comportamientos reutilizables para las subclases ----
    public void buscarA(ContextoCombate rival) {
        int tirada = Dados.d20.rolleo() + Reglas.modificador(getCaracteristicas().getSabiduria());
        rival.mensaje(getNombre() + " busca a " + rival.getActor().getNombre() + " (tirada " + tirada + ").");
        rival.serBuscado(tirada);
    }

    public void atacarBasico(ContextoCombate propio) {
        propio.mensaje(getNombre() + " ataca.");
        new AtaqueBasico().aplicar(propio);
    }

    public boolean vidaPorDebajoDe(double fraccion) {
        return getVida() <= getVidaMaxima() * fraccion;
    }

    public Habilidad elegirHabilidadDisponible(ContextoCombate propio, boolean curativa) {
        List<Habilidad> candidatas = new ArrayList<>();
        for (Habilidad habilidad : getHabilidades()) {
            if (habilidad.esCurativa() == curativa && propio.usosRestantes(habilidad) > 0) {
                candidatas.add(habilidad);
            }
        }
        if (candidatas.isEmpty()) {
            return null;
        }
        return candidatas.get((int) (Math.random() * candidatas.size()));
    }

    public int getExperienciaDada() {
        return experienciaDada;
    }

    public List<Objeto> getRecompensa() {
        return recompensa;
    }
}