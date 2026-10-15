package Modelo.pjs.escondite;

import Modelo.combate.ContextoCombate;

public interface EstadoVisibilidad {
    void esconderse(ContextoCombate contexto);
    void atacar(ContextoCombate contexto);
    void serBuscado(ContextoCombate contexto, int tiradaBusqueda);
    boolean esVisible();
}