package Vista;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import Vista.combate.BotonImagen;
import Vista.combate.PanelHud;
import Vista.combate.PanelSprite;
import Vista.combate.PanelTablon;

public class VistaCombate extends JPanel {

    public static final String NOMBRE = "Combate";

    // Imagen opcional del paisaje. Si no existe, se muestra un cielo liso.
    private static final String RUTA_FONDO_ESCENA = "Assets/fondoCombate.png";

    // Proporciones de la pantalla
    private static final double PESO_ALTO_ESCENA = 0.62;
    private static final double PESO_ALTO_INFERIOR = 0.38;
    private static final double PESO_ANCHO_BOTONERA = 0.36;
    private static final double PESO_ANCHO_MENSAJES = 0.64;
    private static final int MARGEN_PANTALLA = 8; // + SEPARACION = borde exterior
    private static final int SEPARACION = 6;      // entre tablones: el doble

    private static final Color COLOR_PANTALLA = new Color(214, 206, 194);
    private static final Color COLOR_TEXTO = new Color(240, 230, 210);

    private final PanelHud hudHeroe;
    private final PanelHud hudEnemigo;
    private final PanelSprite spriteHeroe;
    private final PanelSprite spriteEnemigo;

    private final JButton botonCombate;
    private final JButton botonBusqueda;
    private final JButton botonInventario;
    private final JButton botonEsconderse;

    private final JTextArea areaMensajes;

    public VistaCombate() {
        super(new GridBagLayout());
        setBackground(COLOR_PANTALLA);
        setBorder(BorderFactory.createEmptyBorder(MARGEN_PANTALLA, MARGEN_PANTALLA, MARGEN_PANTALLA, MARGEN_PANTALLA));

        this.hudHeroe = new PanelHud();
        this.hudEnemigo = new PanelHud();
        this.spriteHeroe = new PanelSprite(new Color(60, 120, 200));
        this.spriteEnemigo = new PanelSprite(new Color(190, 50, 50));

        this.botonCombate = new BotonImagen("Assets/Pelea/botones/botonCombate.png", "Combate");
        this.botonBusqueda = new BotonImagen("Assets/Pelea/botones/botonBuscar.png", "Búsqueda");
        this.botonInventario = new BotonImagen("Assets/Pelea/botones/botonMochila.png", "Inventario");
        this.botonEsconderse = new BotonImagen("Assets/Pelea/botones/botonEsconderse.png", "Esconderse");

        this.areaMensajes = new JTextArea();

        add(crearTablonEscena(), restricciones(0, 0, 2, 1.0, PESO_ALTO_ESCENA));
        add(crearTablonBotonera(), restricciones(0, 1, 1, PESO_ANCHO_BOTONERA, PESO_ALTO_INFERIOR));
        add(crearTablonMensajes(), restricciones(1, 1, 1, PESO_ANCHO_MENSAJES, PESO_ALTO_INFERIOR));
    }

    // ---------- Construcción ----------

    /** Tablón grande: campo de batalla. */
    private PanelTablon crearTablonEscena() {
        PanelFondo escena = new PanelFondo(RUTA_FONDO_ESCENA);
        escena.setBackground(new Color(120, 180, 230));
        escena.setLayout(new BorderLayout());

        JPanel huds = new JPanel(new GridLayout(1, 2, 140, 0));
        huds.setOpaque(false);
        huds.setBorder(BorderFactory.createEmptyBorder(14, 20, 0, 20));
        huds.add(this.hudHeroe);
        huds.add(this.hudEnemigo);

        JPanel figuras = new JPanel(new GridLayout(1, 2));
        figuras.setOpaque(false);
        figuras.add(this.spriteHeroe);
        figuras.add(this.spriteEnemigo);

        escena.add(huds, BorderLayout.NORTH);
        escena.add(figuras, BorderLayout.CENTER);

        return crearTablon(escena);
    }

    /** Tablón mediano: los cuatro botones, en dos filas de dos (las imágenes son anchas). */
    private PanelTablon crearTablonBotonera() {
        JPanel botonera = new JPanel(new GridLayout(2, 2, 8, 8));
        botonera.setOpaque(false);
        botonera.add(this.botonCombate);
        botonera.add(this.botonBusqueda);
        botonera.add(this.botonInventario);
        botonera.add(this.botonEsconderse);
        return crearTablon(botonera);
    }

    /** Tablón chico: texto de lo que sucede. */
    private PanelTablon crearTablonMensajes() {
        this.areaMensajes.setEditable(false);
        this.areaMensajes.setFocusable(false);
        this.areaMensajes.setLineWrap(true);
        this.areaMensajes.setWrapStyleWord(true);
        this.areaMensajes.setOpaque(false);
        this.areaMensajes.setForeground(COLOR_TEXTO);
        this.areaMensajes.setFont(new Font("SansSerif", Font.BOLD, 20));

        JScrollPane desplazamiento = new JScrollPane(this.areaMensajes);
        desplazamiento.setBorder(null);
        desplazamiento.setOpaque(false);
        desplazamiento.getViewport().setOpaque(false);
        desplazamiento.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE); // evita rastros al ser transparente
        desplazamiento.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return crearTablon(desplazamiento);
    }

    private PanelTablon crearTablon(java.awt.Component contenido) {
        PanelTablon tablon = new PanelTablon();
        tablon.setLayout(new BorderLayout());
        tablon.setPreferredSize(new Dimension(0, 0)); // el reparto lo deciden solo los pesos
        tablon.add(contenido, BorderLayout.CENTER);
        return tablon;
    }

    private GridBagConstraints restricciones(int columna, int fila, int ancho, double pesoX, double pesoY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.weightx = pesoX;
        gbc.weighty = pesoY;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(SEPARACION, SEPARACION, SEPARACION, SEPARACION);
        return gbc;
    }

    // ---------- Datos que entrega el controlador ----------

    public void setHeroe(String nombre, int vida, int vidaMaxima) {
        this.hudHeroe.setDatos(nombre, vida, vidaMaxima);
        this.spriteHeroe.setNombre(nombre);
    }

    public void setEnemigo(String nombre, int vida, int vidaMaxima) {
        this.hudEnemigo.setDatos(nombre, vida, vidaMaxima);
        this.spriteEnemigo.setNombre(nombre);
    }

    public void agregarMensaje(String mensaje) {
        this.areaMensajes.append(mensaje + "\n");
        this.areaMensajes.setCaretPosition(this.areaMensajes.getDocument().getLength());
    }

    public void limpiarMensajes() {
        this.areaMensajes.setText("");
    }

    // ---------- Botones (el controlador les agrega los listeners) ----------

    public JButton getBotonCombate() {
        return this.botonCombate;
    }

    public JButton getBotonBusqueda() {
        return this.botonBusqueda;
    }

    public JButton getBotonInventario() {
        return this.botonInventario;
    }

    public JButton getBotonEsconderse() {
        return this.botonEsconderse;
    }
}