package Modelo.partida;

import Modelo.mapa.Posicion;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Raza;

public class PartidaGuardada {

    private final String nombre;
    private final Raza raza;
    private final Clases clase;
    private final int nivel;
    private final int experiencia;
    private final int vida;
    private final Caracteristicas caracteristicas;
    private final String escenario;
    private final Posicion posicion;

    public PartidaGuardada(String nombre, Raza raza, Clases clase, int nivel, int experiencia, int vida,
                           Caracteristicas caracteristicas, String escenario, Posicion posicion) {
        if (nombre == null || nombre.trim().isEmpty() || raza == null || clase == null
                || nivel <= 0 || nivel > 20 || experiencia < 0 || vida < 0
                || caracteristicas == null || escenario == null || escenario.trim().isEmpty()
                || posicion == null) {
            throw new IllegalArgumentException("Los datos de la partida guardada son obligatorios y deben ser válidos");
        }
        this.nombre = nombre;
        this.raza = raza;
        this.clase = clase;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.vida = vida;
        this.caracteristicas = caracteristicas;
        this.escenario = escenario;
        this.posicion = posicion;
    }

    public String getNombre() { return this.nombre; }
    public Raza getRaza() { return this.raza; }
    public Clases getClase() { return this.clase; }
    public int getNivel() { return this.nivel; }
    public int getExperiencia() { return this.experiencia; }
    public int getVida() { return this.vida; }
    public Caracteristicas getCaracteristicas() { return this.caracteristicas; }
    public String getEscenario() { return this.escenario; }
    public Posicion getPosicion() { return this.posicion; }
}