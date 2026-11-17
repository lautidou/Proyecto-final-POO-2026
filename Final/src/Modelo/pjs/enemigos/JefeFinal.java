package Modelo.pjs.enemigos;

import java.util.ArrayList;
import java.util.List;

import Modelo.combate.ContextoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Habilidad;

/** Jefe final: hechicero que se cura cuando esta herido y, si no, prefiere su magia al golpe comun. */
public class JefeFinal extends Enemigo {

    private static final double VIDA_PARA_CURARSE = 0.4;
    private static final double PROB_CURARSE = 0.6;
    private static final double PROB_MAGIA = 0.6;

    public JefeFinal() {
        super("Sombra del Vacío", 10, Dados.d20, List.of(rayoDelVacio(), absorberSombras()),
                new Caracteristicas(20, 20, 15, 20, 20, 15), 1000, new ArrayList<>());
    }

    @Override
    protected void decidirAccion(ContextoCombate propio) {
        if (vidaAlMenos(VIDA_PARA_CURARSE) && azar(PROB_CURARSE) && intentarHabilidad(propio, true)) {
            return;
        }
        if (azar(PROB_MAGIA) && intentarHabilidad(propio, false)) {
            return;
        }
        atacar(propio);
    }

    private static Habilidad rayoDelVacio() {
        return new Habilidad(12, "Rayo del vacío", "Un rayo de oscuridad: 2d10 + Inteligencia.",
                c -> c.infligirDanio(Dados.d10.rolleo() + Reglas.tirada(Dados.d10, c.modInteligencia())), 4);
    }

    private static Habilidad absorberSombras() {
        return new Habilidad(13, "Absorber sombras", "Recupera vida: 2d8 + Inteligencia.",
                c -> c.curarse(Dados.d8.rolleo() + Reglas.tirada(Dados.d8, c.modInteligencia())), 2, true);
    }
}
