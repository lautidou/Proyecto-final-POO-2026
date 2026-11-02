package Audio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuracion {
    private static final String ARCHIVO_CONFIG = "config.properties";

    public static float cargarVolumen() {
        try (InputStream input = new FileInputStream(ARCHIVO_CONFIG)) {
            Properties prop = new Properties();
            prop.load(input);
            return Float.parseFloat(prop.getProperty("volumen", "1.0"));
        } catch (IOException | NumberFormatException ex) {
            return 1.0f;
        }
    }

    public static void guardarVolumen(float volumen) {
        try (OutputStream output = new FileOutputStream(ARCHIVO_CONFIG)) {
            Properties prop = new Properties();
            prop.setProperty("volumen", String.valueOf(volumen));
            prop.store(output, "Configuracion Global del Juego");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
