package Modelo.pjs.escondite;

import Modelo.combate.ContextoCombate;

public class Escondido implements EstadoVisibilidad {

    private final int resultadoSigilo;

    public Escondido(int resultadoSigilo) {
        this.resultadoSigilo = resultadoSigilo;
    }

    @Override
    public void esconderse(ContextoCombate contexto) {
        contexto.mensaje(contexto.getActor().getNombre() + " ya está escondido.");
    }

    @Override
    public void atacar(ContextoCombate contexto) {
        contexto.mensaje("Al atacar, " + contexto.getActor().getNombre() + " deja de estar escondido.");
        contexto.cambiarEstado(new Visible());
    }

    @Override
    public void serBuscado(ContextoCombate contexto, int tiradaBusqueda) {
        String actor = contexto.getActor().getNombre();
        String oponente = contexto.getOponente().getNombre();
        if (tiradaBusqueda >= resultadoSigilo) {
            contexto.mensaje(oponente + " descubre a " + actor + ".");
            contexto.cambiarEstado(new Visible());
        } else {
            contexto.mensaje(oponente + " no logra encontrar a " + actor + ".");
        }
    }

    @Override
    public boolean esVisible() {
        return false;
    }
}