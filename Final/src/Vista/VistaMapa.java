package Vista;

import Modelo.mapa.Posicion;
import Modelo.mapa.Terreno;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Pantalla del mapa. No conoce al modelo: dibuja lo que el controlador le
 * entrega mediante setEscenario / setPersonaje. La entrada de teclado también
 * la registra el controlador.
 */
public class VistaMapa extends JPanel {

    public static final String NOMBRE = "Mapa";

    private String nombreEscenario;
    private Terreno[][] terrenos;        // [fila][columna]
    private String nombrePersonaje;
    private Posicion posicionPersonaje;

    public VistaMapa() {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (terrenos == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int filas = terrenos.length;
        int cols = terrenos[0].length;
        int tam = Math.min(getWidth() / cols, getHeight() / filas); // celdas cuadradas
        int offX = (getWidth() - tam * cols) / 2;                    // mapa centrado
        int offY = (getHeight() - tam * filas) / 2;

        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < cols; x++) {
                g2.setColor(colorTerreno(terrenos[y][x]));
                g2.fillRect(offX + x * tam, offY + y * tam, tam, tam);
                g2.setColor(new Color(0, 0, 0, 40));
                g2.drawRect(offX + x * tam, offY + y * tam, tam, tam);
            }
        }

        if (posicionPersonaje != null && nombrePersonaje != null) {
            int px = offX + posicionPersonaje.getX() * tam;
            int py = offY + posicionPersonaje.getY() * tam;
            int margen = tam / 8;
            g2.setColor(new Color(200, 40, 40));
            g2.fillOval(px + margen, py + margen, tam - 2 * margen, tam - 2 * margen);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, tam / 2));
            FontMetrics fm = g2.getFontMetrics();
            String inicial = nombrePersonaje.substring(0, 1).toUpperCase();
            g2.drawString(inicial,
                    px + (tam - fm.stringWidth(inicial)) / 2,
                    py + (tam + fm.getAscent() - fm.getDescent()) / 2);
        }

        // Nombre del escenario actual
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        FontMetrics fmTitulo = g2.getFontMetrics();
        int anchoTitulo = fmTitulo.stringWidth(nombreEscenario);
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(10, 10, anchoTitulo + 20, 30, 10, 10);
        g2.setColor(Color.WHITE);
        g2.drawString(nombreEscenario, 20, 31);

        g2.dispose();
    }

    private Color colorTerreno(Terreno terreno) {
        switch (terreno) {
            case PASTO:  return new Color(96, 160, 80);
            case PIEDRA: return new Color(130, 125, 120);
            case PARED:  return new Color(70, 70, 80);
            case AGUA:   return new Color(60, 110, 190);
            case PUERTA: return new Color(190, 140, 50);
            default:     return Color.MAGENTA;
        }
    }
}