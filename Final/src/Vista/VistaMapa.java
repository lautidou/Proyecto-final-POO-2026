package Vista;

import Modelo.mapa.Posicion;
import Modelo.mapa.Terreno;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class VistaMapa extends JPanel {

    public static final String NOMBRE = "Mapa";

    // true = muestra la grilla y las celdas bloqueadas (en rojo) para ajustar colisiones
    private static final boolean MODO_DEPURACION = false;
    private static final Map<String, String> RUTAS_FONDO = new HashMap<>();
    static {
        RUTAS_FONDO.put("VillaVerde", "Assets/Mapas/VillaVerde.png");
        // Cada mapa nuevo: una linea mas aca
    }

    private final Map<String, Image> imagenes = new HashMap<>();

    private String nombreEscenario;
    private Terreno[][] terrenos;        // [fila][columna]
    private String nombrePersonaje;
    private Posicion posicionPersonaje;

    public VistaMapa() {
        super();
        setBackground(Color.BLACK);
    }

    public void setEscenario(String nombre, Terreno[][] terrenos) {
        this.nombreEscenario = nombre;
        this.terrenos = terrenos;
    }

    public void setPersonaje(String nombre, Posicion posicion) {
        this.nombrePersonaje = nombre;
        this.posicionPersonaje = posicion;
    }

    public void quitarPersonaje() {
        this.nombrePersonaje = null;
        this.posicionPersonaje = null;
    }

    private Image imagenDe(String idEscenario) {
        if (idEscenario == null || !RUTAS_FONDO.containsKey(idEscenario)) {
            return null;
        }
        if (!imagenes.containsKey(idEscenario)) {
            Image imagen = null;
            String ruta = RUTAS_FONDO.get(idEscenario);
            try {
                imagen = ImageIO.read(new File(ruta));
            } catch (IOException e) {
                System.err.println("No se pudo cargar el fondo '" + ruta + "' (buscado en "
                        + new File(ruta).getAbsolutePath() + ")");
            }
            imagenes.put(idEscenario, imagen); 
        }
        return imagenes.get(idEscenario);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (terrenos == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int filas = terrenos.length;
        int cols = terrenos[0].length;

        int mapaX = 0, mapaY = 0, mapaAncho = getWidth(), mapaAlto = getHeight();
        Image fondo = imagenDe(nombreEscenario);
        if (fondo != null) {
            double escala = Math.min(getWidth() / (double) fondo.getWidth(null),
                                     getHeight() / (double) fondo.getHeight(null));
            mapaAncho = (int) Math.round(fondo.getWidth(null) * escala);
            mapaAlto = (int) Math.round(fondo.getHeight(null) * escala);
            mapaX = (getWidth() - mapaAncho) / 2;
            mapaY = (getHeight() - mapaAlto) / 2;
            g2.drawImage(fondo, mapaX, mapaY, mapaAncho, mapaAlto, this);
        } else {
            g2.setColor(new Color(40, 38, 45));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        double celdaAncho = mapaAncho / (double) cols;
        double celdaAlto = mapaAlto / (double) filas;

        if (MODO_DEPURACION) {
            for (int y = 0; y < filas; y++) {
                for (int x = 0; x < cols; x++) {
                    int x0 = mapaX + (int) Math.round(x * celdaAncho);
                    int y0 = mapaY + (int) Math.round(y * celdaAlto);
                    int x1 = mapaX + (int) Math.round((x + 1) * celdaAncho);
                    int y1 = mapaY + (int) Math.round((y + 1) * celdaAlto);
                    if (!terrenos[y][x].esPasable()) {
                        g2.setColor(new Color(255, 0, 0, 90));
                        g2.fillRect(x0, y0, x1 - x0, y1 - y0);
                    }
                    g2.setColor(new Color(255, 255, 255, 45));
                    g2.drawRect(x0, y0, x1 - x0, y1 - y0);
                }
            }
        }

        if (posicionPersonaje != null && nombrePersonaje != null) {
            int px = mapaX + (int) Math.round(posicionPersonaje.getX() * celdaAncho);
            int py = mapaY + (int) Math.round(posicionPersonaje.getY() * celdaAlto);
            int w = (int) Math.round(celdaAncho);
            int h = (int) Math.round(celdaAlto);
            int tam = Math.min(w, h);
            int margen = tam / 8;
            g2.setColor(new Color(200, 40, 40));
            g2.fillOval(px + margen, py + margen, w - 2 * margen, h - 2 * margen);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(10, tam / 2)));
            FontMetrics fm = g2.getFontMetrics();
            String inicial = nombrePersonaje.substring(0, 1).toUpperCase();
            g2.drawString(inicial,
                    px + (w - fm.stringWidth(inicial)) / 2,
                    py + (h + fm.getAscent() - fm.getDescent()) / 2);
        }

        //muestra el nombre del escenario
        if (nombreEscenario != null) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 18));
            FontMetrics fmTitulo = g2.getFontMetrics();
            int anchoTitulo = fmTitulo.stringWidth(nombreEscenario);
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRoundRect(10, 10, anchoTitulo + 20, 30, 10, 10);
            g2.setColor(Color.WHITE);
            g2.drawString(nombreEscenario, 20, 31);
        }

        g2.dispose();
    }
}