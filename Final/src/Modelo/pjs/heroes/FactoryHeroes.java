package Modelo.pjs.heroes;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import Modelo.objetos.Objeto;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Heroe;
import Modelo.pjs.Raza;

// Simple Factory: arma el Heroe concreto que corresponde a una clase. La usan la creacion de personaje y la carga de partida, que solo conocen el tag Clases (no las subclases).
public final class FactoryHeroes {

    @FunctionalInterface
    private interface Constructor {
        Heroe crear(String nombre, int nivel, Raza raza, Caracteristicas caracteristicas,
                    int experiencia, List<Objeto> inventario);
    }

    private static final Map<Clases, Constructor> CONSTRUCTORES = new EnumMap<>(Clases.class);

    static {
        CONSTRUCTORES.put(Clases.Barbaro, HeroeBarbaro::new);
        CONSTRUCTORES.put(Clases.Mago, HeroeMago::new);
        CONSTRUCTORES.put(Clases.Monje, HeroeMonje::new);
    }

    private FactoryHeroes() {
    }

    public static Heroe crear(Clases clase, String nombre, int nivel, Raza raza,
                              Caracteristicas caracteristicas, int experiencia, List<Objeto> inventario) {
        Constructor constructor = (clase == null) ? null : CONSTRUCTORES.get(clase);
        if (constructor == null) {
            throw new IllegalArgumentException("Clase de heroe desconocida: " + clase);
        }
        return constructor.crear(nombre, nivel, raza, caracteristicas, experiencia, inventario);
    }
}
