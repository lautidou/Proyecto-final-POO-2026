package Vista;

import Modelo.pjs.Clases;
import Modelo.pjs.Enemigo;
import Modelo.pjs.enemigos.Esqueleto;
import Modelo.pjs.enemigos.Goblin;
import Modelo.pjs.enemigos.JefeFinal;
import Modelo.pjs.enemigos.Ogro;
import Recursos.Rutas;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Sabe QUE IMAGEN corresponde a cada personaje. Es un dato de presentacion, por eso vive en la
 * vista y no en el modelo: el modelo solo dice QUIEN es (la clase del heroe, el tipo de enemigo)
 * y la vista decide como dibujarlo.
 */
public final class CatalogoSprites {

    private static final Map<Clases, String> MAPA_HEROE = new EnumMap<>(Clases.class);
    private static final Map<Clases, String> PELEA_HEROE = new EnumMap<>(Clases.class);
    private static final Map<Class<? extends Enemigo>, String> MAPA_ENEMIGO = new HashMap<>();
    private static final Map<Class<? extends Enemigo>, String> PELEA_ENEMIGO = new HashMap<>();

    static {
        MAPA_HEROE.put(Clases.Barbaro, Rutas.BARBARO);
        MAPA_HEROE.put(Clases.Mago, Rutas.MAGO);
        MAPA_HEROE.put(Clases.Monje, Rutas.MONJE);

        PELEA_HEROE.put(Clases.Barbaro, Rutas.BARBARO_PELEA);
        PELEA_HEROE.put(Clases.Mago, Rutas.MAGO_PELEA);
        PELEA_HEROE.put(Clases.Monje, Rutas.MONJE_PELEA);

        MAPA_ENEMIGO.put(Goblin.class, Rutas.GOBLIN);
        MAPA_ENEMIGO.put(Esqueleto.class, Rutas.ESQUELETO);
        MAPA_ENEMIGO.put(Ogro.class, Rutas.OGRO);
        MAPA_ENEMIGO.put(JefeFinal.class, Rutas.BOSS);

        // Solo el goblin tiene sprite de pelea propio; los demas usan el de su clase
        PELEA_ENEMIGO.put(Goblin.class, Rutas.GOBLIN_PELEA);
    }

    private CatalogoSprites() {
    }

    /** Sprite del heroe en el mapa segun su clase (null si no hay: la vista dibuja un circulo). */
    public static String spriteMapa(Clases clase) {
        return MAPA_HEROE.get(clase);
    }

    public static String spritePelea(Clases clase) {
        return PELEA_HEROE.get(clase);
    }

    /** Sprite del enemigo en el mapa segun su tipo. */
    public static String spriteMapa(Enemigo enemigo) {
        String ruta = MAPA_ENEMIGO.get(enemigo.getClass());
        return (ruta != null) ? ruta : spriteMapa(enemigo.getClase());
    }

    public static String spritePelea(Enemigo enemigo) {
        String ruta = PELEA_ENEMIGO.get(enemigo.getClass());
        return (ruta != null) ? ruta : spritePelea(enemigo.getClase());
    }
}
