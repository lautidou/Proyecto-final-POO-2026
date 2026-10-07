package Vista.combate;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PanelHud extends JPanel {

    private static final Color COLOR_TEXTO = new Color(240, 230, 210);

    private final JLabel etiquetaNombre;
    private final JProgressBar barraVida;

    public PanelHud() {
        super(new BorderLayout(0, 4));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 16, 10, 16));

        this.etiquetaNombre = new JLabel("");
        this.etiquetaNombre.setForeground(COLOR_TEXTO);
        this.etiquetaNombre.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel etiquetaPv = new JLabel("PV");
        etiquetaPv.setForeground(Color.WHITE);
        etiquetaPv.setFont(new Font("SansSerif", Font.BOLD, 20));

        this.barraVida = new JProgressBar(0, 1);
        this.barraVida.setStringPainted(true);
        this.barraVida.setForeground(new Color(200, 40, 40));
        this.barraVida.setBackground(new Color(45, 20, 20));
        this.barraVida.setBorder(BorderFactory.createLineBorder(new Color(15, 10, 10), 2));
        this.barraVida.setPreferredSize(new Dimension(0, 26));

        JPanel fila = new JPanel(new BorderLayout(10, 0));
        fila.setOpaque(false);
        fila.add(etiquetaPv, BorderLayout.WEST);
        fila.add(this.barraVida, BorderLayout.CENTER);

        add(this.etiquetaNombre, BorderLayout.NORTH);
        add(fila, BorderLayout.CENTER);
    }

    public void setDatos(String nombre, int vida, int vidaMaxima) {
        this.etiquetaNombre.setText(nombre);
        this.barraVida.setMaximum(Math.max(1, vidaMaxima));
        this.barraVida.setValue(vida);
        this.barraVida.setString(vida + " / " + vidaMaxima);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(20, 16, 14, 220));
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        g2.setColor(new Color(200, 185, 150));
        g2.setStroke(new BasicStroke(3f));
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
        g2.dispose();
        super.paintComponent(g);
    }
}