package Vista;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class CargadorImagen {

    private static final int UMBRAL_ALFA = 8;

    private CargadorImagen() {
    }

    public static BufferedImage cargar(String ruta) {
        try {
            BufferedImage leida = ImageIO.read(new File(ruta));
            if (leida == null) {
                throw new IOException("Formato de imagen no soportado.");
            }
            return recortarTransparencia(leida);
        } catch (IOException e) {
            System.err.println("Error: No se pudo cargar la imagen.");
            System.err.println("Java está buscando en esta ruta absoluta: " + new File(ruta).getAbsolutePath());
            return null;
        }
    }

    private static BufferedImage recortarTransparencia(BufferedImage imagen) {
        int w = imagen.getWidth();
        int h = imagen.getHeight();
        int[] pixeles = imagen.getRGB(0, 0, w, h, null, 0, w);
        int minX = w;
        int minY = h;
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if ((pixeles[y * w + x] >>> 24) > UMBRAL_ALFA) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }
        if (maxX < 0) {
            return imagen;
        }
        return imagen.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }
}