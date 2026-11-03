package Recursos;

public final class Rutas {

    private Rutas() {
    }

    //carpeta principal
    public static final String RAIZ = "Assets/";

    // Mapas
    public static final String MAPAS = RAIZ + "Mapas/";
    public static final String MAPA_PUEBLO = MAPAS + "Pueblo.png";
    public static final String MAPA_VILLA_VERDE = MAPAS + "VillaVerde.png";
    public static final String MAPA_CUEVA = MAPAS + "Cueva.png";
    public static final String MAPA_ENTRADA_CUEVA = MAPAS + "EntradaCueva.png";

    
    // CSV de Colisiones - Pueblo
    public static final String PUEBLO_COLISIONES = MAPAS + "Pueblo_colisiones.csv";
    public static final String PUEBLO_PAREDES = MAPAS + "Pueblo_Paredes.csv";
    public static final String PUEBLO_TECHOS = MAPAS + "Pueblo_techos.csv";
    public static final String PUEBLO_TECHOS_2 = MAPAS + "Pueblo_techos2.csv";
    public static final String PUEBLO_ESTRUCTURAS = MAPAS + "Pueblo_estructuras.csv";

    // CSV de Colisiones - Villa Verde
    public static final String VV_COLISIONES = MAPAS + "colisiones_VillaVerde_colisiones.csv";
    public static final String VV_VEGETACION = MAPAS + "colisiones_VillaVerde_vegetacion.csv";
    public static final String VV_AGUA = MAPAS + "colisiones_VillaVerde_agua.csv";
    public static final String VV_ELEVACIONES = MAPAS + "colisiones_VillaVerde_elevaciones.csv";
    public static final String VV_ELEVACIONES_2 = MAPAS + "colisiones_VillaVerde_elevaciones_2.csv";

    // CSV de Colisiones - Cueva y Entrada
    public static final String ENTRADA_COLISIONES = MAPAS + "EntradaCueva_colisiones.csv";
    public static final String CUEVA_COLISIONES = MAPAS + "Cueva_colisiones.csv";
    public static final String CUEVA_PAREDES_FOSOS = MAPAS + "Cueva_paredes_fosos.csv";

    //Audio
    public static final String AUDIO = RAIZ + "Audio/";
    public static final String AUDIO_MENU = AUDIO + "Menu/";
    public static final String AUDIO_MAPAS = AUDIO + "Mapas/";

    // --- UI Y MENÚS (Usados en Vistas) ---
    public static final String FONDO_INICIO = RAIZ + "fondoInicio.png";
    public static final String PANEL_OPCIONES = RAIZ + "panelOpciones.png";
    
    // --- COMBATE (Usados en VistaCombate y PanelTablon) ---
    public static final String PELEA = RAIZ + "Pelea/";
    public static final String SPRITES_PELEA = PELEA + "Sprites/";
    public static final String FONDO_BOSQUE = PELEA + "fondoPeleaBosque.png";
    public static final String TABLERO = PELEA + "tablero.png";
    public static final String BOTONES_PELEA = PELEA + "botones/";

    public static final String BARBARO = "Assets/Sprites/SpriteSheetcaveman.png";
    public static final String MONJE = "Assets/Sprites/SpriteSheetmonk.png";
    public static final String MAGO = "Assets/Sprites/Wizard sheet.png";

    public static final String BARBARO_PELEA = SPRITES_PELEA + "barbaroPelea.png";
    public static final String MAGO_PELEA = SPRITES_PELEA + "magoPelea.png";
    public static final String MONJE_PELEA = SPRITES_PELEA + "monjePelea.png";

    public static final String GOBLIN_PELEA = SPRITES_PELEA + "goblinPelea.png";

    public static final String ESQUELETO = "Assets/Sprites/Enemy 04-1.png";
    public static final String GOBLIN = "Assets/Sprites/Enemy 18.png";
    public static final String OGRO = "Assets/Sprites/Enemy 20.png";
    public static final String BOSS = "Assets/Sprites/pipo-boss002.jpg";
}