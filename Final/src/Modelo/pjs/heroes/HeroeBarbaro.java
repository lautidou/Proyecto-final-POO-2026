package Modelo.pjs.heroes;

import java.util.List;

import Modelo.combate.CatalogoObjetos;
import Modelo.objetos.Arma;
import Modelo.objetos.Armadura;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Dados;
import Modelo.pjs.Desbloqueo;
import Modelo.pjs.Habilidad;
import Modelo.pjs.Heroe;
import Modelo.pjs.Raza;
import Modelo.combate.Reglas;
import Modelo.objetos.Objeto;

/** Guerrero bruto: mucha vida y habilidades que escalan con la Fuerza. */
public class HeroeBarbaro extends Heroe {

    private final List<Desbloqueo> desbloqueos = List.of(
            new Desbloqueo(1, golpePoderoso()),
            new Desbloqueo(3, tajoBrutal()),
            new Desbloqueo(5, furiaDesatada()));

    public HeroeBarbaro(String nombre, int nivel, Raza raza, Caracteristicas caracteristicas,
                        int experiencia, List<Objeto> inventario) {
        super(nombre, nivel, raza, Dados.d12, caracteristicas, experiencia, inventario);
        actualizarHabilidadesDesbloqueadas(); // los campos de esta clase ya estan inicializados
    }

    @Override
    public Clases getClase() {
        return Clases.Barbaro;
    }

    @Override
    protected Arma armaInicial() {
        return CatalogoObjetos.hachaDeGuerra();
    }

    @Override
    protected Armadura armaduraInicial() {
        return CatalogoObjetos.armaduraDePieles();
    }

    @Override
    protected List<Desbloqueo> getDesbloqueos() {
        return desbloqueos;
    }

    private static Habilidad golpePoderoso() {
        return new Habilidad(1, "Golpe poderoso", "Un golpe devastador: d10 + Fuerza.",
                c -> c.infligirDanio(Reglas.tirada(Dados.d10, c.modFuerza())), 3);
    }

    private static Habilidad tajoBrutal() {
        return new Habilidad(4, "Tajo brutal", "Un tajo con todo el peso del arma: 2d6 + Fuerza.",
                c -> c.infligirDanio(Dados.d6.rolleo() + Reglas.tirada(Dados.d6, c.modFuerza())), 2);
    }

    private static Habilidad furiaDesatada() {
        return new Habilidad(5, "Furia desatada", "Una furia incontrolable: 2d10 + Fuerza.",
                c -> c.infligirDanio(Dados.d10.rolleo() + Reglas.tirada(Dados.d10, c.modFuerza())), 1);
    }
}
