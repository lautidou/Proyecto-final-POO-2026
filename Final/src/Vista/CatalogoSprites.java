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

        PELEA_ENEMIGO.put(Goblin.class, Rutas.GOBLIN_PELEA);
        // Sin sprite de pelea propio: reutilizan el de goblin por practicidad
        PELEA_ENEMIGO.put(Esqueleto.class, Rutas.GOBLIN_PELEA);
        PELEA_ENEMIGO.put(Ogro.class, Rutas.GOBLIN_PELEA);
        PELEA_ENEMIGO.put(JefeFinal.class, Rutas.BOSS);
    }

    private CatalogoSprites() {
    }

    // Sprite del heroe en el mapa segun su clase
    public static String spriteMapa(Clases clase) {
        return MAPA_HEROE.get(clase);
    }

    public static String spritePelea(Clases clase) {
        return PELEA_HEROE.get(clase);
    }

    //Sprite del enemigo en el mapa segun su tipo.
    public static String spriteMapa(Enemigo enemigo) {
        return MAPA_ENEMIGO.get(enemigo.getClass());
    }

    public static String spritePelea(Enemigo enemigo) {
        return PELEA_ENEMIGO.get(enemigo.getClass());
    }
}


