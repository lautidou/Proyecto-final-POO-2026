package Modelo.pjs.heroes;

import java.util.List;

import Modelo.combate.CatalogoObjetos;
import Modelo.combate.Reglas;
import Modelo.objetos.Arma;
import Modelo.objetos.Armadura;
import Modelo.objetos.Objeto;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Dados;
import Modelo.pjs.Desbloqueo;
import Modelo.pjs.Habilidad;
import Modelo.pjs.Heroe;
import Modelo.pjs.Raza;

/** Lanzador de hechizos: poca vida y habilidades que escalan con la Inteligencia. */
public class HeroeMago extends Heroe {

    private final List<Desbloqueo> desbloqueos = List.of(
            new Desbloqueo(1, proyectilMagico()),
            new Desbloqueo(3, bolaDeFuego()),
            new Desbloqueo(5, rayoArcano()));

    public HeroeMago(String nombre, int nivel, Raza raza, Caracteristicas caracteristicas,
                     int experiencia, List<Objeto> inventario) {
        super(nombre, nivel, raza, Dados.d6, caracteristicas, experiencia, inventario);
        actualizarHabilidadesDesbloqueadas();
    }

    @Override
    public Clases getClase() {
        return Clases.Mago;
    }

    @Override
    protected Arma armaInicial() {
        return CatalogoObjetos.bastonArcano();
    }

    @Override
    protected Armadura armaduraInicial() {
        return CatalogoObjetos.tunicaDeMago();
    }

    @Override
    protected List<Desbloqueo> getDesbloqueos() {
        return desbloqueos;
    }

    private static Habilidad proyectilMagico() {
        return new Habilidad(6, "Proyectil mágico", "Un dardo de energía: d6 + Inteligencia.",
                c -> c.infligirDanio(Reglas.tirada(Dados.d6, c.modInteligencia())), 5);
    }

    private static Habilidad bolaDeFuego() {
        return new Habilidad(2, "Bola de fuego", "Una explosión mágica: 2d8 + Inteligencia.",
                c -> c.infligirDanio(Dados.d8.rolleo() + Reglas.tirada(Dados.d8, c.modInteligencia())), 2);
    }

    private static Habilidad rayoArcano() {
        return new Habilidad(7, "Rayo arcano", "Un rayo devastador: 2d10 + Inteligencia.",
                c -> c.infligirDanio(Dados.d10.rolleo() + Reglas.tirada(Dados.d10, c.modInteligencia())), 1);
    }
}
