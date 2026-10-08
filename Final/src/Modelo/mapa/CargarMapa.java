package Modelo.mapa;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee una capa de Tiled exportada como CSV y la devuelve como matriz [fila][columna].
 * El tamano se toma del propio archivo (no hace falta fijar 60x34 aca).
 */
public final class CargarMapa {

    /** Lo que Tiled escribe en las celdas vacias de una capa. */
    public static final int VACIO = -1;

    private CargarMapa() {
    }

    public static int[][] cargarCapa(String ruta) {
        List<int[]> filas = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta), StandardCharsets.UTF_8)) {
            String linea;
            boolean primera = true;
            while ((linea = br.readLine()) != null) {
                if (primera) {
                    linea = linea.replace("\uFEFF", ""); // por si el archivo trae BOM
                    primera = false;
                }
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                String[] valores = linea.split(",");
                int[] fila = new int[valores.length];
                for (int i = 0; i < valores.length; i++) {
                    fila[i] = Integer.parseInt(valores[i].trim());
                }
                if (!filas.isEmpty() && fila.length != filas.get(0).length) {
                    throw new IllegalStateException("La fila " + (filas.size() + 1) + " de '" + ruta
                            + "' tiene " + fila.length + " columnas y se esperaban " + filas.get(0).length + ".");
                }
                filas.add(fila);
            }
        } catch (IOException | NumberFormatException e) {
            // Si falla, que se note: antes se devolvia una matriz de ceros y el error pasaba desapercibido
            throw new IllegalStateException("No se pudo leer la capa '" + ruta + "' (buscada en "
                    + new File(ruta).getAbsolutePath() + "): " + e.getMessage(), e);
        }
        if (filas.isEmpty()) {
            throw new IllegalStateException("La capa '" + ruta + "' esta vacia.");
        }
        return filas.toArray(new int[0][]);
    }
}