package Modelo.pjs.enemigos;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import Modelo.pjs.Enemigo;

//simple factory
public final class FactoryEnemigos {

    private static final Map<TipoEnemigo, Supplier<Enemigo>> CONSTRUCTORES = new EnumMap<>(TipoEnemigo.class);

    static {
        CONSTRUCTORES.put(TipoEnemigo.GOBLIN, Goblin::new);
        CONSTRUCTORES.put(TipoEnemigo.ESQUELETO, Esqueleto::new);
        CONSTRUCTORES.put(TipoEnemigo.OGRO, Ogro::new);
        CONSTRUCTORES.put(TipoEnemigo.JEFE_FINAL, JefeFinal::new);
        if (CONSTRUCTORES.size() != TipoEnemigo.values().length) {
            throw new ExceptionInInitializerError("Falta registrar algun TipoEnemigo en FactoryEnemigos.");
        }
    }

    private FactoryEnemigos() {
    }

    public static Enemigo crear(TipoEnemigo tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de enemigo no puede ser nulo.");
        }
        return CONSTRUCTORES.get(tipo).get();
    }
}
