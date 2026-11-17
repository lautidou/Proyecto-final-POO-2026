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

/** Luchador agil: golpes precisos con Destreza y una curacion propia con Sabiduria. */
public class HeroeMonje extends Heroe {

    private final List<Desbloqueo> desbloqueos = List.of(
            new Desbloqueo(1, punoDePalma()),
            new Desbloqueo(3, curacionMenor()),
            new Desbloqueo(5, rafagaDeGolpes()));

    public HeroeMonje(String nombre, int nivel, Raza raza, Caracteristicas caracteristicas,
                      int experiencia, List<Objeto> inventario) {
        super(nombre, nivel, raza, Dados.d8, caracteristicas, experiencia, inventario);
        actualizarHabilidadesDesbloqueadas();
    }

    @Override
    public Clases getClase() {
        return Clases.Monje;
    }

    @Override
    protected Arma armaInicial() {
        return CatalogoObjetos.vendajesDeCombate();
    }

    @Override
    protected Armadura armaduraInicial() {
        return CatalogoObjetos.ropasDeMonje();
    }

    @Override
    protected List<Desbloqueo> getDesbloqueos() {
        return desbloqueos;
    }

    private static Habilidad punoDePalma() {
        return new Habilidad(8, "Puño de palma", "Un golpe preciso: d8 + Destreza.",
                c -> c.infligirDanio(Reglas.tirada(Dados.d8, c.modDestreza())), 5);
    }

    private static Habilidad curacionMenor() {
        return new Habilidad(3, "Curación menor", "Recupera vida: d8 + Sabiduría.",
                c -> c.curarse(Reglas.tirada(Dados.d8, c.modSabiduria())), 2, true);
    }

    private static Habilidad rafagaDeGolpes() {
        return new Habilidad(9, "Ráfaga de golpes", "Una lluvia de golpes: 2d6 + Destreza.",
                c -> c.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6, c.modDestreza())), 1);
    }
}
