package Vista;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
    private Image imagenFondo;

    public PanelFondo(String rutaImagen) {
        try {
            File archivoImagen = new File(rutaImagen);
            this.imagenFondo = ImageIO.read(archivoImagen);
        } catch (IOException e) {
            System.err.println("Error: No se pudo cargar la imagen.");
            System.err.println("Java está buscando en esta ruta absoluta: " + new File(rutaImagen).getAbsolutePath());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}