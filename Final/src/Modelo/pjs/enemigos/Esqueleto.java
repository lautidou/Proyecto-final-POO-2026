package Modelo.pjs.enemigos;

import Modelo.combate.ContextoCombate;
import Modelo.objetos.Arma;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Modelo.pjs.habilidades.Habilidad;
import Modelo.pjs.habilidades.ReensamblarHuesos;
import Recursos.Rutas;

import java.util.ArrayList;

public class Esqueleto extends Enemigo {

    public Esqueleto() {
        super("Esqueleto", 2, Dados.d8, new Caracteristicas(10, 8, 14, 6, 15, 5),
                80, new ArrayList<>(), Rutas.ESQUELETO, null);
        equipar(new Arma(1, "Espada corta", 2.0, 15, "Una espada ligera y equilibrada.", Dados.d8));
        aprenderHabilidad(new ReensamblarHuesos());
    }

    @Override
    public void decidirTurno(ContextoCombate propio, ContextoCombate rival) {
        if (!rival.esVisible()) {
            buscarA(rival);
            return;
        }
        Habilidad golpe = elegirHabilidadDisponible(propio, false);
        if (golpe != null && Math.random() < 0.5) {
            golpe.usar(propio);
            return;
        }
        atacarBasico(propio);
    }
}