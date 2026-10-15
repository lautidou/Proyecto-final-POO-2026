package Modelo.pjs.escondite;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;
import Modelo.pjs.Personaje;

public class Visible implements EstadoVisibilidad {

    private static final int BASE_PERCEPCION_PASIVA = 10;

    @Override
    public void esconderse(ContextoCombate contexto) {
        Personaje actor = contexto.getActor();
        Personaje oponente = contexto.getOponente();

        int sigilo = Dados.d20.rolleo() + Reglas.modificador(actor.getCaracteristicas().getDestreza());
        int percepcion = BASE_PERCEPCION_PASIVA + Reglas.modificador(oponente.getCaracteristicas().getSabiduria());

        if (sigilo >= percepcion) {
            contexto.mensaje(actor.getNombre() + " se esconde (sigilo " + sigilo + " contra " + percepcion + ").");
            contexto.cambiarEstado(new Escondido(sigilo));
        } else {
            contexto.mensaje(actor.getNombre() + " no logra esconderse (sigilo " + sigilo + " contra " + percepcion + ").");
        }
    }

    @Override
    public void atacar(ContextoCombate contexto) {
        // ya estaba a la vista: nada cambia
    }

    @Override
    public void serBuscado(ContextoCombate contexto, int tiradaBusqueda) {
        // no hay nada que encontrar
    }

    @Override
    public boolean esVisible() {
        return true;
    }
}