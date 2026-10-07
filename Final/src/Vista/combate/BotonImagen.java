package Vista.combate;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import Vista.CargadorImagen;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class BotonImagen extends JButton {

    private static final int DESPLAZAMIENTO_PRESIONADO = 2;

    private final BufferedImage original; 

    private BufferedImage normal;
    private BufferedImage resaltada;
    private int anchoEscalado = -1;
    private int altoEscalado = -1;

    public BotonImagen(String rutaImagen, String descripcion) {
        this.original = CargadorImagen.cargar(rutaImagen);

        setToolTipText(descripcion);
        getAccessibleContext().setAccessibleName(descripcion);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
        setRolloverEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (original == null || getWidth() <= 0 || getHeight() <= 0) {
            return; // sin imagen no se dibuja nada; el error ya se informó por consola
        }
        double escala = Math.min((double) getWidth() / original.getWidth(),
                (double) getHeight() / original.getHeight());
        int ancho = Math.max(1, (int) Math.round(original.getWidth() * escala));
        int alto = Math.max(1, (int) Math.round(original.getHeight() * escala));
        prepararImagenes(ancho, alto);

        int x = (getWidth() - ancho) / 2;
        int y = (getHeight() - alto) / 2;
        if (getModel().isPressed()) {
            y += DESPLAZAMIENTO_PRESIONADO;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        if (!isEnabled()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2.drawImage(getModel().isRollover() ? resaltada : normal, x, y, null);
        g2.dispose();
    }

    private void prepararImagenes(int ancho, int alto) {
        if (normal != null && ancho == anchoEscalado && alto == altoEscalado) {
            return;
        }
        normal = escalar(original, ancho, alto);
        resaltada = aclarar(normal);
        anchoEscalado = ancho;
        altoEscalado = alto;
    }

    /** Reduce a la mitad varias veces antes del ajuste final: se ve más nítido que un solo salto grande. */
    private static BufferedImage escalar(BufferedImage origen, int ancho, int alto) {
        BufferedImage actual = origen;
        int w = origen.getWidth();
        int h = origen.getHeight();
        while (w > ancho * 2 || h > alto * 2) {
            w = Math.max(ancho, w / 2);
            h = Math.max(alto, h / 2);
            actual = copiaEscalada(actual, w, h);
        }
        return copiaEscalada(actual, ancho, alto);
    }

    private static BufferedImage copiaEscalada(BufferedImage origen, int ancho, int alto) {
        BufferedImage destino = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = destino.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(origen, 0, 0, ancho, alto, null);
        g.dispose();
        return destino;
    }

    private static BufferedImage aclarar(BufferedImage origen) {
        RescaleOp aclarado = new RescaleOp(
                new float[]{1.15f, 1.15f, 1.15f, 1f},  // R, G, B, alfa
                new float[]{12f, 12f, 12f, 0f}, null);
        return aclarado.filter(origen, null);
    }
}