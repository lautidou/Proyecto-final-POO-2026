package Vista.combate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class BotonTexto extends JButton {

    private static final Color COLOR_TEXTO = new Color(240, 230, 210);
    private static final Color COLOR_RESALTE = new Color(255, 210, 90);
    private static final Color COLOR_BORDE = new Color(200, 185, 150);

    public BotonTexto(String texto) {
        super(texto);
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setForeground(COLOR_TEXTO);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        setRolloverEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addChangeListener(evento -> setForeground(getModel().isRollover() ? COLOR_RESALTE : COLOR_TEXTO));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(20, 16, 14, getModel().isPressed() ? 235 : 200));
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        g2.setColor(getModel().isRollover() ? COLOR_RESALTE : COLOR_BORDE);
        g2.setStroke(new BasicStroke(3f));
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
        g2.dispose();
        super.paintComponent(g); // dibuja el texto
    }
}