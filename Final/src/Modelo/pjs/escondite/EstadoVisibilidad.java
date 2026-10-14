package Modelo.pjs.escondite;

import Modelo.combate.ContextoCombate;

public interface EstadoVisibilidad {
    EstadoVisibilidad alEsconderse(ContextoCombate contexto);
    EstadoVisibilidad alAtacar(ContextoCombate contexto);
    EstadoVisibilidad alSerBuscado(ContextoCombate contexto, int tiradaBusqueda);
    boolean esVisible();
}