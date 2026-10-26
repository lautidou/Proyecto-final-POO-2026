package Modelo.combate;

import Modelo.pjs.Enemigo;
import Modelo.pjs.Heroe;

public interface SalidaCombate {
    void alGanar(Heroe heroe, Enemigo enemigo);

    void alPerder(Heroe heroe, Enemigo enemigo);
}