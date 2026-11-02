package Vista;

import Recursos.Rutas;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Modelo.mapa.Direccion;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VistaMapa extends JPanel {

    public static final String NOMBRE = "Mapa";

    private static boolean MODO_DEPURACION = false;
    private static final Map<String, String> RUTAS_FONDO = new HashMap<>();
    static {
        RUTAS_FONDO.put("VillaVerde", Rutas.MAPA_VILLA_VERDE);
        RUTAS_FONDO.put("Pueblo", Rutas.MAPA_PUEBLO);
        RUTAS_FONDO.put("Cueva", Rutas.MAPA_CUEVA); 
        RUTAS_FONDO.put("EntradaCueva", Rutas.MAPA_ENTRADA_CUEVA);
    }

    private final Map<String, Image> imagenes = new HashMap<>();

    private String nombreEscenario;
    private boolean[][] obstaculos;
    private int filas;
    private int columnas;

    private String nombrePersonaje;
    private String rutaSpritePersonaje;
    private Direccion orientacionPersonaje = Direccion.ABAJO;
    private int frameAnimacionPersonaje = 0;
    private java.awt.Point posicionPersonaje;
    private Map<java.awt.Point, DatosEnemigo> enemigos = new HashMap<>();
    private final Map<String, BufferedImage> cacheSprites = new HashMap<>();
    private float visualX = -1f;
    private float visualY = -1f;
    private javax.swing.Timer timerAnimacion;

    public VistaMapa() {
        super();
        setBackground(Color.BLACK);
    }

    public void setEscenario(String nombre, int filas, int columnas, boolean[][] obstaculos) {
        this.nombreEscenario = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.obstaculos = obstaculos;
        repaint();
    }

   public void setPersonaje(String nombre, int x, int y, String rutaSprite, Direccion dir, int frame) {
        this.nombrePersonaje = nombre;
        this.posicionPersonaje = new java.awt.Point(x,y);
        this.rutaSpritePersonaje = rutaSprite;
        this.orientacionPersonaje = dir != null ? dir : Direccion.ABAJO;
        this.frameAnimacionPersonaje = frame;
        
        if (visualX == -1f) {
            // Primera vez que entra al mapa: aparece instantáneamente
            visualX = x;
            visualY = y;
        } else {
            // Deslizamiento fluido hacia la nueva celda
            if (timerAnimacion != null && timerAnimacion.isRunning()) {
                timerAnimacion.stop();
            }
            timerAnimacion = new javax.swing.Timer(16, e -> {
                // Se acerca un 25% de la distancia restante por cada frame (16ms)
                visualX += (x - visualX) * 0.25f;
                visualY += (y - visualY) * 0.25f;
                
                // Si está lo suficientemente cerca, lo encajamos exacto y paramos el timer
                if (Math.abs(x - visualX) < 0.02f && Math.abs(y - visualY) < 0.02f) {
                    visualX = x;
                    visualY = y;
                    timerAnimacion.stop();
                }
                repaint();
            });
            timerAnimacion.start();
        }
        repaint();
    }

    public void setEnemigos(Map<java.awt.Point, DatosEnemigo> enemigos) {
        this.enemigos = new HashMap<>(enemigos);
    }

    public static class DatosEnemigo {
        public String nombre;
        public String rutaSprite;
        public Modelo.mapa.Direccion orientacion;
        public int frame;
        
        public DatosEnemigo(String n, String r, Modelo.mapa.Direccion o, int f) {
            this.nombre = n; this.rutaSprite = r; this.orientacion = o; this.frame = f;
        }
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
        if (obstaculos == null || filas == 0 || columnas == 0) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

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

        double celdaAncho = mapaAncho / (double) columnas;
        double celdaAlto = mapaAlto / (double) filas;

        if (MODO_DEPURACION) {
            for (int y = 0; y < filas; y++) {
                for (int x = 0; x < columnas; x++) {
                    int x0 = mapaX + (int) Math.round(x * celdaAncho);
                    int y0 = mapaY + (int) Math.round(y * celdaAlto);
                    int x1 = mapaX + (int) Math.round((x + 1) * celdaAncho);
                    int y1 = mapaY + (int) Math.round((y + 1) * celdaAlto);
                    if (!obstaculos[y][x]) {
                        g2.setColor(new Color(255, 0, 0, 90));
                        g2.fillRect(x0, y0, x1 - x0, y1 - y0);
                    }
                    g2.setColor(new Color(255, 255, 255, 45));
                    g2.drawRect(x0, y0, x1 - x0, y1 - y0);
                }
            }
        }

        for (Map.Entry<java.awt.Point, DatosEnemigo> entrada : enemigos.entrySet()) {
            DatosEnemigo enemigo = entrada.getValue();
            dibujarFicha(g2, mapaX, mapaY, celdaAncho, celdaAlto, 
                    entrada.getKey().x, entrada.getKey().y, enemigo.nombre,
                    new Color(130, 60, 170), enemigo.rutaSprite, enemigo.orientacion, enemigo.frame);
        }
        if (posicionPersonaje != null && nombrePersonaje != null) {
            dibujarFicha(g2, mapaX, mapaY, celdaAncho, celdaAlto, 
                    visualX, visualY, nombrePersonaje,
                    new Color(200, 40, 40), rutaSpritePersonaje, orientacionPersonaje, frameAnimacionPersonaje);
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

    private void dibujarFicha(Graphics2D g2, int mapaX, int mapaY, double celdaAncho, double celdaAlto,
                              float posX, float posY, String nombre, Color color, String rutaSprite, 
                              Modelo.mapa.Direccion orientacion, int frameAnimacion) {
        
        int px = mapaX + (int) Math.round(posX * celdaAncho);
        int py = mapaY + (int) Math.round(posY * celdaAlto);
        int w = (int) Math.round(celdaAncho);
        int h = (int) Math.round(celdaAlto);

        if (rutaSprite != null && !rutaSprite.isEmpty()) {

            BufferedImage hojaSprites = cacheSprites.get(rutaSprite);
            if (hojaSprites == null) {
                hojaSprites = CargadorImagen.cargarSinRecorte(rutaSprite);
                if (hojaSprites != null) {
                    cacheSprites.put(rutaSprite, hojaSprites);
                }
            }

            if (hojaSprites != null) {
                int columnasSprite = 4; 
                int filasSprite = 4;
                int filaCaminata = 0;
                
                if (rutaSprite.contains("boss")) {
                    columnasSprite = 1;
                    filasSprite = 1;
                } else if (rutaSprite.contains("Wizard")) {
                    filasSprite = 18; 
                    filaCaminata = 4; 
                } else if (rutaSprite.contains("Enemy")) {
                    columnasSprite = 3;
                }

                int anchoFrame = hojaSprites.getWidth() / columnasSprite;
                int altoFrame = hojaSprites.getHeight() / filasSprite;
                
                int col = 0;
                int fila = 0;
                
                if (rutaSprite.contains("boss")) {
                    col = 0;
                    fila = 0;
                } else if (rutaSprite.contains("Enemy")) {
                    col = frameAnimacion % columnasSprite; 
                    if (orientacion != null) {
                        switch (orientacion) {
                            case ABAJO:     fila = 0; break;
                            case IZQUIERDA: fila = 1; break;
                            case DERECHA:   fila = 2; break;
                            case ARRIBA:    fila = 3; break;
                        }
                    }
                } else {
                    fila = frameAnimacion % filasSprite;
                    if (orientacion != null) {
                        switch (orientacion) {
                            case ABAJO:     col = 0; break;
                            case ARRIBA:    col = 1; break;
                            case IZQUIERDA: col = 2; break;
                            case DERECHA:   col = 3; break;
                        }
                    }
                    fila = fila + filaCaminata; 
                }
                
                int recorteX = col * anchoFrame;
                int recorteY = fila * altoFrame;
                
                BufferedImage frameActual = hojaSprites.getSubimage(recorteX, recorteY, anchoFrame, altoFrame);
                g2.drawImage(frameActual, px, py, w, h, null);
                return; 
        }

        int tam = Math.min(w, h);
        int margen = tam / 8;
        g2.setColor(color);
        g2.fillOval(px + margen, py + margen, w - 2 * margen, h - 2 * margen);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(10, tam / 2)));
        FontMetrics fm = g2.getFontMetrics();
        String inicial = nombre.substring(0, 1).toUpperCase();
        g2.drawString(inicial,
                px + (w - fm.stringWidth(inicial)) / 2,
                py + (h + fm.getAscent() - fm.getDescent()) / 2);
    }
    }
}