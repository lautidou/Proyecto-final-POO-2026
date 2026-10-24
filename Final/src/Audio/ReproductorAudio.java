package Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.text.Normalizer;

/**
 * Reproduce todos los sonidos, en bucle de a una para los fondos de los mapas
 * y un sfx para los items y ataques. Los archivos se buscan en Assets/audio
 * Tienen que ser WAV, AIFF, AU.
 */
public final class ReproductorAudio {

    private static final String CARPETA = "Assets/Audio/";
    private static final String EXTENSION = ".wav";

    public static final String MENU = "Menu/";
    public static final String MAPAS = "Mapas/";
    public static final String COMBATE = "Combate/";
    public static final String HABILIDADES = "Habilidades/";
    public static final String CONSUMIBLES = "Consumibles/";
    public static final String ATAQUE_BASICO = "ataque_basico";

    private static Clip musica;
    private static String musicaActual;

    private ReproductorAudio() {
    }

    // Musica del menu principal Assets/Audio/Menu/menu_principal.wav
    public static void musicaDeMenu() {
        reproducirMusica(MENU + "menu_principal");
    }

    // Sfx al apretar un boton del menu Assets/Audio/Menu/boton.wav
    public static void efectoBotonMenu() {
        efecto(MENU + "boton");
    }

    // Aurdio para el mapa Assets/Audio/Mapas/{idMapa}.wav
    public static void musicaDeMapa(String idMapa) {
        reproducirMusica(MAPAS + idMapa);
    }

    // Musica de fondo del combate Assets/Audio/Combate/combate.wav
    public static void musicaDeCombate() {
        reproducirMusica(COMBATE + "combate");
    }

    // Sfx de un ataque o habilidad Assets/Audio/Habilidades/{nombre.wav
    public static void efectoDeHabilidad(String nombre) {
        efecto(HABILIDADES + aArchivo(nombre));
    }

    public static void efectoAtaqueBasico() {
        efecto(HABILIDADES + ATAQUE_BASICO);
    }

    // Sfx de un consumible Assets/Audio/Consumibles/{nombre}.wav
    public static void efectoDeConsumible(String nombre) {
        efecto(CONSUMIBLES + aArchivo(nombre));
    }

    public static synchronized void detenerMusica() {
        if (musica != null) {
            musica.stop();
            musica.close();
            musica = null;
        }
        musicaActual = null;
    }

    private static synchronized void reproducirMusica(String ruta) {
        if (ruta.equals(musicaActual)) {
            return;
        }
        detenerMusica();
        Clip clip = abrir(ruta);
        if (clip == null) {
            return;
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        musica = clip;
        musicaActual = ruta;
    }

    private static void efecto(String ruta) {
        Clip clip = abrir(ruta);
        if (clip == null) {
            return;
        }
        clip.addLineListener(evento -> {
            if (evento.getType() == LineEvent.Type.STOP) {
                clip.close();
            }
        });
        clip.start();
    }

    // Abre el sonido
    private static Clip abrir(String ruta) {
        File archivo = new File(CARPETA + ruta + EXTENSION);
        if (!archivo.isFile() || archivo.length() == 0) {
            return null;
        }
        try (AudioInputStream flujo = AudioSystem.getAudioInputStream(archivo)) {
            Clip clip = AudioSystem.getClip();
            clip.open(flujo);
            return clip;
        } catch (Exception e) {
            System.err.println("No se pudo reproducir '" + archivo.getPath() + "': " + e.getMessage());
            return null;
        }
    }
    // normaliza el nombre porque sino no anda
    private static String aArchivo(String nombre) {
        String sinTildes = Normalizer.normalize(nombre, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase().trim().replaceAll("[^a-z0-9]+", "_");
    }
}
