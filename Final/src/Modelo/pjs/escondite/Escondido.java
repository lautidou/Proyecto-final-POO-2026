package Modelo.pjs.escondite;

import Modelo.combate.ContextoCombate;

public class Escondido implements EstadoVisibilidad {

    private final int resultadoSigilo;

    public Escondido(int resultadoSigilo) {
        this.resultadoSigilo = resultadoSigilo;
    }

    @Override
    public EstadoVisibilidad alEsconderse(ContextoCombate contexto) {
        contexto.mensaje(contexto.getActor().getNombre() + " ya está escondido.");
        return this;
    }

    @Override
    public EstadoVisibilidad alAtacar(ContextoCombate contexto) {
        contexto.mensaje("Al atacar, " + contexto.getActor().getNombre() + " deja de estar escondido.");
        return new Visible();
    }

    @Override
    public EstadoVisibilidad alSerBuscado(ContextoCombate contexto, int tiradaBusqueda) {
        String actor = contexto.getActor().getNombre();
        String oponente = contexto.getOponente().getNombre();
        if (tiradaBusqueda >= resultadoSigilo) {
            contexto.mensaje(oponente + " descubre a " + actor + ".");
            return new Visible();
        }
        contexto.mensaje(oponente + " no logra encontrar a " + actor + ".");
        return this;
    }

    @Override
    public boolean esVisible() {
        return false;
    }
}