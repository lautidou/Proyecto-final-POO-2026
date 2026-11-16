package Modelo.pjs.clases;

import Modelo.objetos.*;
import Modelo.pjs.*;
import Recursos.Rutas;
import Modelo.pjs.habilidades.*;

import java.util.List;

public class Barbaro extends Heroe {

    public Barbaro(String nombre, int nivel, Raza raza, Caracteristicas caracteristicas, int experiencia) {
        super(nombre, nivel, raza, Dados.d12, caracteristicas, experiencia,
                Rutas.BARBARO, Rutas.BARBARO_PELEA);
    }

    @Override
    public Arma crearArmaInicial() {
        return new Arma(6, "Hacha de guerra", 3.5, 30, "Un hacha pesada que parte escudos.", Dados.d10);
    }

    @Override
    public Armadura crearArmaduraInicial() {
        return new Armadura(9, "Armadura de pieles", 10.0, 25, "Pieles curtidas de bestias: resistentes y abrigadas.", 3);
    }

    @Override
    public List<Desbloqueo> getDesbloqueos() {
        return List.of(
                new Desbloqueo(1, new GolpePoderoso()),
                new Desbloqueo(3, new TajoBrutal()),
                new Desbloqueo(5, new FuriaDesatada())
        );
    }
}