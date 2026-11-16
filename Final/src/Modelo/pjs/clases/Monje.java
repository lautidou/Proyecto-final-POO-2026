package Modelo.pjs.clases;

import Modelo.objetos.*;
import Modelo.pjs.*;
import Recursos.Rutas;
import Modelo.pjs.habilidades.*;

import java.util.List;

public class Monje extends Heroe {

    public Monje(String nombre, int nivel, Raza raza, Caracteristicas caracteristicas, int experiencia) {
        super(nombre, nivel, raza, Dados.d8, caracteristicas, experiencia,
                Rutas.MONJE, Rutas.MONJE_PELEA);
    }

    @Override
    public Arma crearArmaInicial() {
        return new Arma(8, "Vendajes de combate", 0.2, 5, "Vendas reforzadas para golpear sin lastimarse.", Dados.d6);
    }

    @Override
    public Armadura crearArmaduraInicial() {
        return new Armadura(11, "Ropas de monje", 0.8, 10, "Ropas livianas que no limitan el movimiento.", 2);
    }

    @Override
    public List<Desbloqueo> getDesbloqueos() {
        return List.of(
                new Desbloqueo(1, new PunoDePalma()),
                new Desbloqueo(3, new CuracionMenor()),
                new Desbloqueo(5, new RafagaDeGolpes())
        );
    }
}