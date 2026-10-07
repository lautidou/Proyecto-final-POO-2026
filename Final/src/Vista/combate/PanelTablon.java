package Vista.combate;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Vista.CargadorImagen;

public class PanelTablon extends JPanel {

    private static final String RUTA_IMAGEN = "Assets/Pelea/tablero.png";

    private static final int ESQUINA_ANCHO = 78;
    private static final int ESQUINA_ALTO = 108;

    private static final double ESCALA_ESQUINA_DEFECTO = 0.4;

    private static BufferedImage imagen;
    private static boolean cargaIntentada = false;

    private final int esquinaAncho;
    private final int esquinaAlto;

    public PanelTablon() {
        this(ESCALA_ESQUINA_DEFECTO);
    }

    public PanelTablon(double escalaEsquina) {
        if (escalaEsquina <= 0) {
            throw new IllegalArgumentException("La escala debe ser mayor que cero.");
        }
        setOpaque(false);
        this.esquinaAncho = (int) Math.round(ESQUINA_ANCHO * escalaEsquina);
        this.esquinaAlto = (int) Math.round(ESQUINA_ALTO * escalaEsquina);
        setBorder(BorderFactory.createEmptyBorder(esquinaAlto, esquinaAncho, esquinaAlto, esquinaAncho));
    }

    private static synchronized BufferedImage getImagen() {
        if (!cargaIntentada) {
            cargaIntentada = true;
            imagen = CargadorImagen.cargar(RUTA_IMAGEN);
        }
        return imagen;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = getImagen();
        if (img == null) {
            return; 
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); // conserva el estilo pixel art

        int w = getWidth();
        int h = getHeight();
        if (w < 2 * esquinaAncho || h < 2 * esquinaAlto) {
            g2.drawImage(img, 0, 0, w, h, null);
        } else {
            dibujarPorSecciones(g2, img, w, h);
        }
        g2.dispose();
    }

    private void dibujarPorSecciones(Graphics2D g2, BufferedImage img, int w, int h) {
        int iw = img.getWidth();
        int ih = img.getHeight();
        int[] origenX = {0, ESQUINA_ANCHO, iw - ESQUINA_ANCHO, iw};
        int[] origenY = {0, ESQUINA_ALTO, ih - ESQUINA_ALTO, ih};
        int[] destinoX = {0, esquinaAncho, w - esquinaAncho, w};
        int[] destinoY = {0, esquinaAlto, h - esquinaAlto, h};

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                g2.drawImage(img,
                        destinoX[col], destinoY[fila], destinoX[col + 1], destinoY[fila + 1],
                        origenX[col], origenY[fila], origenX[col + 1], origenY[fila + 1],
                        null);
            }
        }
    }
}