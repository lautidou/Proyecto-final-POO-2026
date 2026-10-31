package Modelo.mapa;

import Modelo.pjs.enemigos.FactoryEnemigos;
import Recursos.Rutas;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class MundoDePrueba {

    private static final Posicion POSICION_GOBLIN = new Posicion(4, 30);

    public static final String ID_PUEBLO = "Pueblo";
    public static final String ID_VILLA_VERDE = "VillaVerde";
    public static final String ID_ENTRADA_CUEVA = "EntradaCueva";
    public static final String ID_CUEVA = "Cueva";

    private static final String PUEBLO_COLISIONES = Rutas.PUEBLO_COLISIONES;
    private static final String PUEBLO_PAREDES = Rutas.PUEBLO_PAREDES;
    private static final String PUEBLO_TECHOS = Rutas.PUEBLO_TECHOS;
    private static final String PUEBLO_TECHOS_2 = Rutas.PUEBLO_TECHOS_2;
    private static final String PUEBLO_ESTRUCTURAS = Rutas.PUEBLO_ESTRUCTURAS;
    private static final boolean BLOQUEAR_CASAS = true;

    // Inicio: frente a la puerta de la casa del pueblo
    private static final Posicion INICIO = new Posicion(18, 8);

    private static final String VV_COLISIONES = Rutas.VV_COLISIONES;
    private static final String VV_VEGETACION = Rutas.VV_VEGETACION;
    private static final String VV_AGUA = Rutas.VV_AGUA;
    private static final String VV_ELEVACIONES = Rutas.VV_ELEVACIONES;
    private static final String VV_ELEVACIONES_2 = Rutas.VV_ELEVACIONES_2;

    private static final boolean BLOQUEAR_AGUA = true;
    private static final Set<Integer> TILES_AGUA =
            new HashSet<>(Arrays.asList(145, 185, 189, 210, 211, 163, 164, 125, 229));

    private static final boolean BLOQUEAR_TRONCOS = true;
    private static final Set<Integer> TILES_TRONCO =
            new HashSet<>(Arrays.asList(105, 106, 107, 108));

    private static final boolean BLOQUEAR_ELEVACIONES = true;

    private static final String ENTRADA_COLISIONES = Rutas.ENTRADA_COLISIONES;

    private static final String CUEVA_COLISIONES = Rutas.CUEVA_COLISIONES;
    private static final String CUEVA_PAREDES_FOSOS = Rutas.CUEVA_PAREDES_FOSOS;

    private MundoDePrueba() {
    }

    public static Mundo crear() {
        Mapa pueblo = crearPueblo();
        Mapa villa = crearVillaVerde();
        Mapa entrada = crearEntradaCueva();
        Mapa cueva = crearCueva();

        //Portales (siempre de a pares: ida y vuelta)

        abrirSalida(pueblo, 39, 26, 39, 28, ID_VILLA_VERDE, new Posicion(1, 31));
        abrirSalida(villa, 0, 30, 0, 32, ID_PUEBLO, new Posicion(38, 27));

        abrirSalida(villa, 59, 1, 59, 3, ID_ENTRADA_CUEVA, new Posicion(14, 18));
        abrirSalida(entrada, 13, 19, 16, 19, ID_VILLA_VERDE, new Posicion(58, 2));

        abrirSalida(entrada, 13, 9, 16, 9, ID_CUEVA, new Posicion(20, 27));
        abrirSalida(cueva, 9, 29, 30, 29, ID_ENTRADA_CUEVA, new Posicion(14, 10));

        // ---- Enemigos (despues de los portales, para no taparlos) ----
        villa.agregarEnemigo(FactoryEnemigos.crear("Goblin"), POSICION_GOBLIN);

        Mundo mundo = new Mundo();
        mundo.agregarMapa(pueblo);
        mundo.agregarMapa(villa);
        mundo.agregarMapa(entrada);
        mundo.agregarMapa(cueva);
        mundo.definirInicio(ID_PUEBLO, INICIO); // lanza error si la celda esta bloqueada
        return mundo;
    }

    private static Mapa crearPueblo() {
        Mapa mapa = mapaDesdeColisiones(ID_PUEBLO, PUEBLO_COLISIONES);
        if (BLOQUEAR_CASAS) {
            marcarCapaCompleta(mapa, PUEBLO_PAREDES, Terreno.PARED);
            marcarCapaCompleta(mapa, PUEBLO_TECHOS, Terreno.PARED);
            marcarCapaCompleta(mapa, PUEBLO_TECHOS_2, Terreno.PARED);
            marcarCapaCompleta(mapa, PUEBLO_ESTRUCTURAS, Terreno.PARED);
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