package Vista.combate;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PanelSprite extends JPanel {

    private final Color colorAcento;
    private String inicial = "";

    public PanelSprite(Color colorAcento) {
        this.colorAcento = colorAcento;
        setOpaque(false);
    }

    public void setNombre(String nombre) {
        this.inicial = (nombre == null || nombre.isEmpty()) ? "" : nombre.substring(0, 1).toUpperCase();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int d = (int) (Math.min(getWidth(), getHeight()) * 0.7);
        if (d <= 0) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = (getWidth() - d) / 2;
        int y = (getHeight() - d) / 2;
        g2.setColor(colorAcento);
        g2.fillOval(x, y, d, d);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, d / 2));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(inicial,
                x + (d - fm.stringWidth(inicial)) / 2,
                y + (d + fm.getAscent() - fm.getDescent()) / 2);
        g2.dispose();
    }
}