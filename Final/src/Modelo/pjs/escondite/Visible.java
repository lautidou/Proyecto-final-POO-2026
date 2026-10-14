package Modelo.pjs.escondite;

import Modelo.pjs.Dados;
import Modelo.pjs.Personaje;
import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;

public class Visible implements EstadoVisibilidad {

    private static final int BASE_PERCEPCION_PASIVA = 10;

    @Override
    public EstadoVisibilidad alEsconderse(ContextoCombate contexto) {
        Personaje actor = contexto.getActor();
        Personaje oponente = contexto.getOponente();

        int sigilo = Dados.d20.rolleo() + Reglas.modificador(actor.getCaracteristicas().getDestreza());
        int percepcion = BASE_PERCEPCION_PASIVA + Reglas.modificador(oponente.getCaracteristicas().getSabiduria());

        if (sigilo >= percepcion) {
            contexto.mensaje(actor.getNombre() + " se esconde (sigilo " + sigilo + " contra " + percepcion + ").");
            return new Escondido(sigilo);
        }
        contexto.mensaje(actor.getNombre() + " no logra esconderse (sigilo " + sigilo + " contra " + percepcion + ").");
        return this;
    }

    @Override
    public EstadoVisibilidad alAtacar(ContextoCombate contexto) {
        return this; // ya estaba a la vista: nada cambia
    }

    @Override
    public EstadoVisibilidad alSerBuscado(ContextoCombate contexto, int tiradaBusqueda) {
        return this; // no hay nada que encontrar
    }

    @Override
    public boolean esVisible() {
        return true;
    }
}