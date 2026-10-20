package Modelo.mapa;

import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Enemigo;
import Modelo.pjs.TipoEnemigo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class MundoDePrueba {

    private static final Posicion POSICION_GOBLIN = new Posicion(4, 30);

    public static final String ID_PUEBLO = "Pueblo";
    public static final String ID_VILLA_VERDE = "VillaVerde";
    public static final String ID_ENTRADA_CUEVA = "EntradaCueva";
    public static final String ID_CUEVA = "Cueva";

    private static final String CARPETA = "Assets/Mapas/";

    // ---------------- PUEBLO (40x30) ----------------
    private static final String PUEBLO_COLISIONES = CARPETA + "Pueblo_colisiones.csv";
    private static final String PUEBLO_PAREDES = CARPETA + "Pueblo_Paredes.csv";
    private static final String PUEBLO_TECHOS = CARPETA + "Pueblo_techos.csv";
    private static final String PUEBLO_TECHOS_2 = CARPETA + "Pueblo_techos2.csv";
    // La capa de colisiones no cubre la parte alta de los techos: con esto las casas quedan solidas
    private static final boolean BLOQUEAR_CASAS = true;

    // Inicio: frente a la puerta de la casa del pueblo (columna, fila), no en pixeles
    private static final Posicion INICIO = new Posicion(18, 8);

    // ---------------- VILLAVERDE (60x34) ----------------
    private static final String VV_COLISIONES = CARPETA + "colisiones_VillaVerde_colisiones.csv";
    private static final String VV_VEGETACION = CARPETA + "colisiones_VillaVerde_vegetacion.csv";
    private static final String VV_AGUA = CARPETA + "colisiones_VillaVerde_agua.csv";
    private static final String VV_ELEVACIONES = CARPETA + "colisiones_VillaVerde_elevaciones.csv";
    private static final String VV_ELEVACIONES_2 = CARPETA + "colisiones_VillaVerde_elevaciones_2.csv";

    private static final boolean BLOQUEAR_AGUA = true;
    private static final Set<Integer> TILES_AGUA =
            new HashSet<>(Arrays.asList(145, 185, 189, 210, 211, 163, 164, 125, 229));

    private static final boolean BLOQUEAR_TRONCOS = true;
    private static final Set<Integer> TILES_TRONCO =
            new HashSet<>(Arrays.asList(105, 106, 107, 108));

    private static final boolean BLOQUEAR_ELEVACIONES = true;

    // ---------------- ENTRADA DE LA CUEVA (30x20) ----------------
    private static final String ENTRADA_COLISIONES = CARPETA + "EntradaCueva_colisiones.csv";

    // ---------------- CUEVA (40x30) ----------------
    private static final String CUEVA_COLISIONES = CARPETA + "Cueva_colisiones.csv";
    private static final String CUEVA_PAREDES_FOSOS = CARPETA + "Cueva_paredes_fosos.csv";

    private MundoDePrueba() {
    }

    public static Mundo crear() {
        Mapa pueblo = crearPueblo();
        Mapa villa = crearVillaVerde();
        Mapa entrada = crearEntradaCueva();
        Mapa cueva = crearCueva();

        // ---- Portales (siempre de a pares: ida y vuelta) ----
        // Pueblo (camino del este) <-> VillaVerde (camino del oeste)
        abrirSalida(pueblo, 39, 26, 39, 28, ID_VILLA_VERDE, new Posicion(1, 31));
        abrirSalida(villa, 0, 30, 0, 32, ID_PUEBLO, new Posicion(38, 27));

        // VillaVerde (camino del noreste) <-> EntradaCueva (camino del sur)
        abrirSalida(villa, 59, 1, 59, 3, ID_ENTRADA_CUEVA, new Posicion(14, 18));
        abrirSalida(entrada, 13, 19, 16, 19, ID_VILLA_VERDE, new Posicion(58, 2));

        // EntradaCueva (boca de la cueva) <-> Cueva (salida sur)
        abrirSalida(entrada, 13, 9, 16, 9, ID_CUEVA, new Posicion(20, 27));
        abrirSalida(cueva, 9, 29, 30, 29, ID_ENTRADA_CUEVA, new Posicion(14, 10));

        // ---- Enemigos (despues de los portales, para no taparlos) ----
        villa.agregarEnemigo(crearGoblin(), POSICION_GOBLIN);

        Mundo mundo = new Mundo();
        mundo.agregarMapa(pueblo);
        mundo.agregarMapa(villa);
        mundo.agregarMapa(entrada);
        mundo.agregarMapa(cueva);
        mundo.definirInicio(ID_PUEBLO, INICIO); // lanza error si la celda esta bloqueada
        return mundo;
    }

    // ------------------------------------------------------------------ mapas

    private static Mapa crearPueblo() {
        Mapa mapa = mapaDesdeColisiones(ID_PUEBLO, PUEBLO_COLISIONES);
        if (BLOQUEAR_CASAS) {
            marcarCapaCompleta(mapa, PUEBLO_PAREDES, Terreno.PARED);
            marcarCapaCompleta(mapa, PUEBLO_TECHOS, Terreno.PARED);
            marcarCapaCompleta(mapa, PUEBLO_TECHOS_2, Terreno.PARED);
        }
        return mapa;
    }

    private static Mapa crearVillaVerde() {
        Mapa mapa = mapaDesdeColisiones(ID_VILLA_VERDE, VV_COLISIONES);
        if (BLOQUEAR_TRONCOS) {
            marcarTiles(mapa, VV_VEGETACION, TILES_TRONCO, Terreno.PARED);
        }
        if (BLOQUEAR_AGUA) {
            marcarTiles(mapa, VV_AGUA, TILES_AGUA, Terreno.AGUA);
        }
        if (BLOQUEAR_ELEVACIONES) {
            marcarCapaCompleta(mapa, VV_ELEVACIONES, Terreno.PARED);
            marcarCapaCompleta(mapa, VV_ELEVACIONES_2, Terreno.PARED);
        }
        return mapa;
    }

    private static Mapa crearEntradaCueva() {
        return mapaDesdeColisiones(ID_ENTRADA_CUEVA, ENTRADA_COLISIONES);
    }

    private static Mapa crearCueva() {
        Mapa mapa = mapaDesdeColisiones(ID_CUEVA, CUEVA_COLISIONES);
        marcarCapaCompleta(mapa, CUEVA_PAREDES_FOSOS, Terreno.PARED);
        return mapa;
    }

    // ------------------------------------------------------------------ utilidades

    //Crea un Goblin
    private static Enemigo crearGoblin() {
        return new Enemigo("Goblin", 1, Clases.Barbaro, new ArrayList<>(),
        new Caracteristicas(8, 14, 10, 10, 8, 8), TipoEnemigo.Goblin, 50, new ArrayList<>());
    }

    /** Crea el mapa con el tamano de la capa de colisiones y pone pared en cada celda no vacia. */
    private static Mapa mapaDesdeColisiones(String id, String rutaColisiones) {
        int[][] colisiones = CargarMapa.cargarCapa(rutaColisiones);
        Mapa mapa = new Mapa(id, colisiones.length, colisiones[0].length, Terreno.PASTO);
        for (int y = 0; y < colisiones.length; y++) {
            for (int x = 0; x < colisiones[y].length; x++) {
                if (colisiones[y][x] != CargarMapa.VACIO) {
                    mapa.setTerreno(new Posicion(x, y), Terreno.PARED);
                }
            }
        }
        return mapa;
    }

    /**
     * Convierte el rectangulo de celdas [x0..x1] x [y0..y1] en puerta y le agrega un portal
     * hacia otro mapa. La posicion de llegada debe ser una celda libre que NO sea portal,
     * al lado de la salida del mapa destino, para no volver al mapa anterior sin querer.
     */
    private static void abrirSalida(Mapa origen, int x0, int y0, int x1, int y1,
                                    String idDestino, Posicion llegada) {
        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) {
                Posicion celda = new Posicion(x, y);
                origen.setTerreno(celda, Terreno.PUERTA);
                origen.agregarPortal(celda, new Portal(idDestino, llegada));
            }
        }
    }

    /** Pone 'terreno' en las celdas de la capa cuyo tile este en 'ids' (y que todavia sean pasto). */
    private static void marcarTiles(Mapa mapa, String rutaCapa, Set<Integer> ids, Terreno terreno) {
        int[][] capa = leerCapa(mapa, rutaCapa);
        for (int y = 0; y < capa.length; y++) {
            for (int x = 0; x < capa[y].length; x++) {
                Posicion p = new Posicion(x, y);
                if (ids.contains(capa[y][x]) && mapa.getCelda(p).getTerreno() == Terreno.PASTO) {
                    mapa.setTerreno(p, terreno);
                }
            }
        }
    }

    /** Toda celda no vacia de la capa pasa a ser 'terreno', sin importar el id del tile. */
    private static void marcarCapaCompleta(Mapa mapa, String rutaCapa, Terreno terreno) {
        int[][] capa = leerCapa(mapa, rutaCapa);
        for (int y = 0; y < capa.length; y++) {
            for (int x = 0; x < capa[y].length; x++) {
                Posicion p = new Posicion(x, y);
                if (capa[y][x] != CargarMapa.VACIO && mapa.getCelda(p).getTerreno() == Terreno.PASTO) {
                    mapa.setTerreno(p, terreno);
                }
            }
        }
    }

    private static int[][] leerCapa(Mapa mapa, String rutaCapa) {
        int[][] capa = CargarMapa.cargarCapa(rutaCapa);
        if (capa.length != mapa.getFilas() || capa[0].length != mapa.getColumnas()) {
            throw new IllegalStateException("La capa '" + rutaCapa + "' no tiene el mismo tamano que el mapa '"
                    + mapa.getId() + "' (" + mapa.getColumnas() + "x" + mapa.getFilas() + ").");
        }
        return capa;
    }
}