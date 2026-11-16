package Modelo.pjs.enemigos;

import Modelo.combate.ContextoCombate;
import Modelo.objetos.Arma;
import Modelo.objetos.Armadura;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Modelo.pjs.habilidades.*;
import Recursos.Rutas;

import java.util.ArrayList;

public class JefeFinal extends Enemigo {

    private static final double VIDA_BAJA = 0.3;
    private static final double PROB_ESCONDERSE_VIDA_BAJA = 0.4;
    private static final double VIDA_PARA_CURARSE = 0.5;
    private static final double PROB_CURARSE = 0.6;
    private static final double PROB_USAR_HABILIDAD = 0.7;

    public JefeFinal() {
        super("Sombra del Vacío", 10, Dados.d12, new Caracteristicas(20, 20, 15, 20, 20, 15),
                1000, new ArrayList<>(), Rutas.BOSS, null);
        equipar(new Arma(7, "Bastón arcano", 2.0, 25, "Un bastón de roble que canaliza la magia.", Dados.d6));
        equipar(new Armadura(10, "Túnica de mago", 1.0, 20, "Tela encantada: casi no pesa.", 1));

        aprenderHabilidad(new LlamaradaDelVacio());
        aprenderHabilidad(new RayoArcano());
        aprenderHabilidad(new DrenajeDelVacio());
        aprenderHabilidad(new RegeneracionSombria());
    }

    @Override
    public void decidirTurno(ContextoCombate propio, ContextoCombate rival) {
        if (!rival.esVisible()) {
            buscarA(rival);
            return;
        }
        if (propio.esVisible() && vidaPorDebajoDe(VIDA_BAJA) && Math.random() < PROB_ESCONDERSE_VIDA_BAJA) {
            propio.esconderse();
            return;
        }
        if (vidaPorDebajoDe(VIDA_PARA_CURARSE) && Math.random() < PROB_CURARSE) {
            Habilidad cura = elegirHabilidadDisponible(propio, true);
            if (cura != null) {
                cura.usar(propio);
                return;
            }
        }
        if (Math.random() < PROB_USAR_HABILIDAD) {
            Habilidad hechizo = elegirHabilidadDisponible(propio, false);
            if (hechizo != null) {
                hechizo.usar(propio);
                return;
            }
        }
        atacarBasico(propio);
    }
}