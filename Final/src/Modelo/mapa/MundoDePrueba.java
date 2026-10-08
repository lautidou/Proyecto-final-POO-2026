package Modelo.mapa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class MundoDePrueba {

    public static final String ID_VILLA_VERDE = "VillaVerde";

    private static final String CARPETA = "Assets/Mapas/";
    private static final String CAPA_COLISIONES = CARPETA + "/colisiones_VillaVerde_colisiones.csv";
    private static final String CAPA_VEGETACION = CARPETA + "/colisiones_VillaVerde_vegetacion.csv";
    private static final String CAPA_AGUA = CARPETA + "/colisiones_VillaVerde_caminos_y_agua.csv";

    //aca no se por que por poner false funciona xd
    private static final boolean BLOQUEAR_AGUA = false;
    private static final Set<Integer> TILES_AGUA =
            new HashSet<>(Arrays.asList(255, 142, 138, 140, 165, 161, 118, 187));

    private static final boolean BLOQUEAR_TRONCOS = true;
    private static final Set<Integer> TILES_TRONCO =
            new HashSet<>(Arrays.asList(96, 97, 98, 99, 156, 157, 158, 159));

    //cambiar despues las coordenadas donde empieza
    private static final Posicion INICIO = new Posicion(20, 20);

    private MundoDePrueba() {
    }

    public static Mundo crear() {
        int[][] colisiones = CargarMapa.cargarCapa(CAPA_COLISIONES);
        int filas = colisiones.length;
        int columnas = colisiones[0].length;

        Mapa villa = new Mapa(ID_VILLA_VERDE, filas, columnas, Terreno.PASTO);

        
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                if (colisiones[y][x] != CargarMapa.VACIO) {
                    villa.setTerreno(new Posicion(x, y), Terreno.PARED);
                }
            }
        }

        if (BLOQUEAR_TRONCOS) {
            marcarTiles(villa, CAPA_VEGETACION, TILES_TRONCO, Terreno.PARED);
        }
        if (BLOQUEAR_AGUA) {
            marcarTiles(villa, CAPA_AGUA, TILES_AGUA, Terreno.AGUA);
        }

        Mundo mundo = new Mundo();
        mundo.agregarMapa(villa);
        mundo.definirInicio(ID_VILLA_VERDE, INICIO); // lanza error si la celda esta bloqueada

        // Cuando tengas otro mapa, se conectan con portales (siempre de a pares):
        // villa.agregarPortal(new Posicion(59, 3), new Portal("Pueblo", new Posicion(1, 20)));
        // pueblo.agregarPortal(new Posicion(0, 20), new Portal("VillaVerde", new Posicion(58, 3)));

        return mundo;
    }

    private static void marcarTiles(Mapa mapa, String rutaCapa, Set<Integer> ids, Terreno terreno) {
        int[][] capa = CargarMapa.cargarCapa(rutaCapa);
        if (capa.length != mapa.getFilas() || capa[0].length != mapa.getColumnas()) {
            throw new IllegalStateException("La capa '" + rutaCapa + "' no tiene el mismo tamano que el mapa ("
                    + mapa.getColumnas() + "x" + mapa.getFilas() + ").");
        }
        for (int y = 0; y < capa.length; y++) {
            for (int x = 0; x < capa[y].length; x++) {
                Posicion p = new Posicion(x, y);
                if (ids.contains(capa[y][x]) && mapa.getCelda(p).getTerreno() == Terreno.PASTO) {
                    mapa.setTerreno(p, terreno);
                }
            }
        }
    }
}