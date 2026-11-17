package Modelo.pjs;

import java.util.List;

import Modelo.combate.AtaqueBasico;
import Modelo.combate.ContextoCombate;
import Modelo.objetos.Objeto;

/**
 * Un enemigo. Cada subclase decide POR SU CUENTA como juega su turno (decidirAccion): el goblin
 * es cobarde, el esqueleto implacable, el ogro bruto y el jefe usa su magia. El resto del turno
 * (buscar al heroe si esta escondido) es comun y esta aca (Template Method).
 */
public abstract class Enemigo extends Personaje {

    private int experienciaDada;
    private List<Objeto> recompensa;

    protected Enemigo(String nombre, int nivel, Dados dadoGolpe, List<Habilidad> habilidades,
        Caracteristicas caracteristicas, int experienciaDada, List<Objeto> recompensa) {
        super(nombre, nivel, dadoGolpe, habilidades, caracteristicas);
        this.experienciaDada = experienciaDada;
        this.recompensa = recompensa;
    }

    public final void jugarTurno(ContextoCombate propio) {
        if (estaMuerto() || propio.getOponente().estaMuerto()) {
            return;
        }
        // Si el heroe esta escondido, el turno se gasta buscandolo.
        if (!propio.getContextoOponente().esVisible()) {
            propio.buscarAlOponente();
            return;
        }
        decidirAccion(propio);
    }

    // Lo que hace este enemigo cuando el heroe esta a la vista: 
    // cada subclase lo hereda y define que hacer
    protected abstract void decidirAccion(ContextoCombate propio);

    protected final void atacar(ContextoCombate propio) {
        propio.mensaje(getNombre() + " ataca.");
        new AtaqueBasico().aplicar(propio);
    }

    protected final boolean vidaAlMenos(double fraccion) {
        return getVida() <= getVidaMaxima() * fraccion;
    }

    protected final boolean azar(double probabilidad) {
        return Math.random() < probabilidad;
    }

    // Esconderse con la misma regla que el heroe (el contexto delega en su estado de visibilidad).
    protected final boolean intentarEsconderse(ContextoCombate propio, double vidaMaxFraccion, double probabilidad) {
        if (propio.esVisible() && vidaAlMenos(vidaMaxFraccion) && azar(probabilidad)) {
            propio.esconderse();
            return true;
        }
        return false;
    }

    // Usa una habilidad (curativa u ofensiva) con usos disponibles; false si no tiene ninguna.
    protected final boolean intentarHabilidad(ContextoCombate propio, boolean curativa) {
        List<Habilidad> candidatas = new java.util.ArrayList<>();
        for (Habilidad habilidad : getHabilidades()) {
            if (habilidad.esCurativa() == curativa && propio.usosRestantes(habilidad) > 0) {
                candidatas.add(habilidad);
            }
        }
        if (candidatas.isEmpty()) {
            return false;
        }
        candidatas.get((int) (Math.random() * candidatas.size())).usar(propio);
        return true;
    }

    public int getExperienciaDada() {
        return experienciaDada;
    }

    public List<Objeto> getRecompensa() {
        return recompensa;
    }
}
