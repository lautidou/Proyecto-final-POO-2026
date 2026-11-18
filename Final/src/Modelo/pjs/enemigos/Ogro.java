package Modelo.pjs.enemigos;

import Modelo.combate.ContextoCombate;
import Modelo.objetos.Arma;
import Modelo.objetos.Armadura;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Modelo.pjs.habilidades.*;

import java.util.ArrayList;

public class Ogro extends Enemigo {

    private boolean enfurecido = false;
    private boolean furiaAnunciada = false;

    public Ogro() {
        super("Ogro", 3, Dados.d12, new Caracteristicas(16, 11, 12, 7, 16, 10),
                150, new ArrayList<>());
        equipar(new Arma(6, "Hacha de guerra", 3.5, 30, "Un hacha pesada que parte escudos.", Dados.d10));
        equipar(new Armadura(9, "Armadura de pieles", 10.0, 25, "Pieles curtidas de bestias: resistentes y abrigadas.", 3));
        aprenderHabilidad(new Aplastar());
        aprenderHabilidad(new Garrotazo());
    }

    @Override
    public void recibirDanio(int puntosDanio) {
        super.recibirDanio(puntosDanio);
        if (!this.enfurecido && !estaMuerto() && vidaPorDebajoDe(0.5)) {
            this.enfurecido = true;
        }
    }

    @Override
    public void decidirTurno(ContextoCombate propio, ContextoCombate rival) {
        if (!rival.esVisible()) {
            buscarA(rival);
            return;
        }
        if (this.enfurecido) {
            if (!this.furiaAnunciada) {
                propio.mensaje("¡" + getNombre() + " entra en furia por las heridas!");
                this.furiaAnunciada = true;
            }
            Habilidad habilidad = elegirHabilidadDisponible(propio, false);
            if (habilidad != null) {
                habilidad.usar(propio);
                return;
            }
        } else if (Math.random() < 0.35) {
            Habilidad habilidad = elegirHabilidadDisponible(propio, false);
            if (habilidad != null) {
                habilidad.usar(propio);
                return;
            }
        }
        atacarBasico(propio);
    }
}