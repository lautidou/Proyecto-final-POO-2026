package Modelo;

import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Dados;
import Modelo.pjs.Heroe;
import Modelo.pjs.Raza;
import Modelo.pjs.clases.Barbaro;
import Modelo.pjs.clases.Mago;
import Modelo.pjs.clases.Monje;

import java.util.List;

public class ModeloCreacionPersonaje {

    private static final List<String> CLASES_DISPONIBLES = List.of("Barbaro", "Mago", "Monje");

    private Heroe heroeCreado;

    public ModeloCreacionPersonaje() {
    }

    public Heroe crearHeroe(String nombre, Raza raza, String clase) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un nombre para el personaje.");
        }
        if (raza == null) {
            throw new IllegalArgumentException("Debe seleccionar una raza.");
        }
        if (clase == null) {
            throw new IllegalArgumentException("Debe seleccionar una clase.");
        }

        Caracteristicas caracteristicasBase = generarCaracteristicasBase(raza);

        this.heroeCreado = instanciarHeroe(clase, nombre.trim(), raza, caracteristicasBase);
        this.heroeCreado.inicializarEquipo();
        return this.heroeCreado;
    }

    private Heroe instanciarHeroe(String clase, String nombre, Raza raza, Caracteristicas caracteristicas) {
        switch (clase) {
            case "Barbaro":
                return new Barbaro(nombre, 1, raza, caracteristicas, 0);
            case "Mago":
                return new Mago(nombre, 1, raza, caracteristicas, 0);
            case "Monje":
                return new Monje(nombre, 1, raza, caracteristicas, 0);
            default:
                throw new IllegalArgumentException("Clase desconocida: " + clase);
        }
    }

    private Caracteristicas generarCaracteristicasBase(Raza raza) {
        int fuerza = tirarCaracteristica() + raza.getFuerza();
        int sabiduria = tirarCaracteristica() + raza.getSabiduria();
        int destreza = tirarCaracteristica() + raza.getDestreza();
        int inteligencia = tirarCaracteristica() + raza.getInteligencia();
        int constitucion = tirarCaracteristica() + raza.getConstitucion();
        int carisma = tirarCaracteristica() + raza.getCarisma();

        return new Caracteristicas(
                limitar(fuerza), limitar(sabiduria), limitar(destreza),
                limitar(inteligencia), limitar(constitucion), limitar(carisma)
        );
    }

    private int tirarCaracteristica() {
        return Dados.d6.rolleo() + Dados.d6.rolleo() + Dados.d6.rolleo();
    }

    private int limitar(int valor) {
        return Math.min(20, Math.max(0, valor));
    }

    public Heroe getHeroeCreado() {
        return this.heroeCreado;
    }

    public List<Raza> getRazas() {
        return List.of(Raza.values());
    }

    public List<String> getClases() {
        return CLASES_DISPONIBLES;
    }
}