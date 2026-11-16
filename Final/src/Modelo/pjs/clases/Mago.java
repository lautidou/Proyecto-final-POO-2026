package Modelo.pjs.clases;

import Modelo.objetos.*;
import Modelo.pjs.*;
import Recursos.Rutas;
import Modelo.pjs.habilidades.*;

import java.util.List;

public class Mago extends Heroe {

    public Mago(String nombre, int nivel, Raza raza, Caracteristicas caracteristicas, int experiencia) {
        super(nombre, nivel, raza, Dados.d6, caracteristicas, experiencia,
                Rutas.MAGO, Rutas.MAGO_PELEA);
    }

    @Override
    public Arma crearArmaInicial() {
        return new Arma(7, "Bastón arcano", 2.0, 25, "Un bastón de roble que canaliza la magia.", Dados.d6);
    }

    @Override
    public Armadura crearArmaduraInicial() {
        return new Armadura(10, "Túnica de mago", 1.0, 20, "Tela encantada: casi no pesa.", 1);
    }

    @Override
    public List<Desbloqueo> getDesbloqueos() {
        return List.of(
                new Desbloqueo(1, new ProyectilMagico()),
                new Desbloqueo(3, new BolaDeFuego()),
                new Desbloqueo(5, new RayoArcano())
        );
    }
}