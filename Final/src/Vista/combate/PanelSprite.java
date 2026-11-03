package Vista.combate;

import Vista.CargadorImagen;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class PanelSprite extends JPanel {

    private final Color colorAcento;
    private String inicial = "";
    private String rutaActual;
    private BufferedImage imagen;

    public PanelSprite(Color colorAcento) {
        this.colorAcento = colorAcento;
        setOpaque(false);
    }

    public void setDatos(String nombre, String rutaImagen) {
        this.inicial = (nombre == null || nombre.isEmpty()) ? "" : nombre.substring(0, 1).toUpperCase();
        // se llama en cada cambio de estado del modelo: solo se recarga si cambió la ruta
        if (!Objects.equals(rutaImagen, this.rutaActual)) {
            this.rutaActual = rutaImagen;
            this.imagen = (rutaImagen == null) ? null : CargadorImagen.cargar(rutaImagen);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.imagen == null) {
            pintarMarcador(g);
            return;
        }
        double escala = Math.min((double) getWidth() / this.imagen.getWidth(),
                                 (double) getHeight() / this.imagen.getHeight()) * 0.9;
        int w = (int) (this.imagen.getWidth() * escala);
        int h = (int) (this.imagen.getHeight() * escala);
        int x = (getWidth() - w) / 2;
        int y = getHeight() - h; // pies apoyados abajo

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(this.imagen, x, y, w, h, null);
        g2.dispose();
    }

    // círculo  como respaldo
    private void pintarMarcador(Graphics g) {
        int d = (int) (Math.min(getWidth(), getHeight()) * 0.7);
        if (d <= 0) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = (getWidth() - d) / 2;
        int y = (getHeight() - d) / 2;
        g2.setColor(this.colorAcento);
        g2.fillOval(x, y, d, d);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, d / 2));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(this.inicial,
                x + (d - fm.stringWidth(this.inicial)) / 2,
                y + (d + fm.getAscent() - fm.getDescent()) / 2);
        g2.dispose();
    }
}