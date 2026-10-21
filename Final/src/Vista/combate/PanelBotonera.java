package Vista.combate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import Vista.BotonImagen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class PanelBotonera extends JPanel {

    private static final String MENU_PRINCIPAL = "principal";
    private static final String MENU_COMBATE = "combate";
    private static final String MENU_HABILIDADES = "habilidades";

    private static final String RUTA_BOTON_COMBATE = "Assets/Pelea/botones/botonCombate.png";
    private static final String RUTA_BOTON_BUSQUEDA = "Assets/Pelea/botones/botonBuscar.png";
    private static final String RUTA_BOTON_INVENTARIO = "Assets/Pelea/botones/botonMochila.png";
    private static final String RUTA_BOTON_ESCONDERSE = "Assets/Pelea/botones/botonEsconderse.png";

    private static final String RUTA_BOTON_BASICO = "Assets/Pelea/botones/botonBasico.png";
    private static final String RUTA_BOTON_HABILIDADES = "Assets/Pelea/botones/botonHabilidades.png";
    private static final String RUTA_BOTON_VOLVER = "Assets/Pelea/botones/botonVolver.png";

    private final CardLayout tarjetas = new CardLayout();

    // Menú principal
    private final BotonImagen botonCombate;
    private final BotonImagen botonBusqueda;
    private final BotonImagen botonInventario;
    private final BotonImagen botonEsconderse;

    // Submenú de combate
    private final BotonImagen botonBasico;
    private final BotonImagen botonHabilidades;
    private final BotonImagen botonVolverCombate;

    // Submenú de habilidades
    private final JPanel listaHabilidades = new JPanel(new GridLayout(0, 1, 0, 8));
    private final BotonImagen botonVolverHabilidades;

    public PanelBotonera() {
        setLayout(this.tarjetas);
        setOpaque(false);

        this.botonCombate = new BotonImagen(RUTA_BOTON_COMBATE, "Combate");
        this.botonBusqueda = new BotonImagen(RUTA_BOTON_BUSQUEDA, "Búsqueda");
        this.botonInventario = new BotonImagen(RUTA_BOTON_INVENTARIO, "Inventario");
        this.botonEsconderse = new BotonImagen(RUTA_BOTON_ESCONDERSE, "Esconderse");

        this.botonBasico = new BotonImagen(RUTA_BOTON_BASICO, "Básico");
        this.botonHabilidades = new BotonImagen(RUTA_BOTON_HABILIDADES, "Habilidades");
        this.botonVolverCombate = new BotonImagen(RUTA_BOTON_VOLVER, "Volver");
        this.botonVolverHabilidades = new BotonImagen(RUTA_BOTON_VOLVER, "Volver");

        add(crearMenuPrincipal(), MENU_PRINCIPAL);
        add(crearMenuCombate(), MENU_COMBATE);
        add(crearMenuHabilidades(), MENU_HABILIDADES);
    }

    // ---------- Construcción ----------

    private JPanel crearMenuPrincipal() {
        JPanel menu = new JPanel(new GridLayout(2, 2, 8, 8));
        menu.setOpaque(false);
        menu.add(this.botonCombate);
        menu.add(this.botonBusqueda);
        menu.add(this.botonInventario);
        menu.add(this.botonEsconderse);
        return menu;
    }

    private JPanel crearMenuCombate() {
        JPanel menu = new JPanel(new GridLayout(2, 2, 8, 8));
        menu.setOpaque(false);
        menu.add(this.botonBasico);
        menu.add(this.botonHabilidades);
        menu.add(this.botonVolverCombate);
        return menu;
    }

    private JPanel crearMenuHabilidades() {
        this.listaHabilidades.setOpaque(false);
        JPanel arriba = new JPanel(new BorderLayout()); 
        arriba.setOpaque(false);
        arriba.add(this.listaHabilidades, BorderLayout.NORTH);

        JScrollPane desplazamiento = new JScrollPane(arriba);
        desplazamiento.setBorder(null);
        desplazamiento.setOpaque(false);
        desplazamiento.getViewport().setOpaque(false);
        desplazamiento.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE); // evita rastros al ser transparente
        desplazamiento.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        desplazamiento.getVerticalScrollBar().setUnitIncrement(16);

        JPanel pie = new JPanel(new BorderLayout());
        pie.setOpaque(false);
        pie.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pie.setPreferredSize(new Dimension(0, 50));
        pie.add(this.botonVolverHabilidades, BorderLayout.CENTER);

        JPanel menu = new JPanel(new BorderLayout());
        menu.setOpaque(false);
        menu.add(desplazamiento, BorderLayout.CENTER);
        menu.add(pie, BorderLayout.SOUTH);
        return menu;
    }

    // ---------- Navegación ----------

    public void mostrarMenuPrincipal() {
        this.tarjetas.show(this, MENU_PRINCIPAL);
    }

    public void mostrarMenuCombate() {
        this.tarjetas.show(this, MENU_COMBATE);
    }

    public void mostrarMenuHabilidades() {
        this.tarjetas.show(this, MENU_HABILIDADES);
    }

    // ---------- Habilidades (cambian de un combate a otro) ----------

    public List<JButton> setHabilidades(List<String> nombres, List<String> descripciones) {
        if (nombres.size() != descripciones.size()) {
            throw new IllegalArgumentException("Debe haber una descripción por cada habilidad.");
        }
        this.listaHabilidades.removeAll();
        List<JButton> botones = new ArrayList<>();

        if (nombres.isEmpty()) {
            JLabel vacio = new JLabel("No conocés ninguna habilidad.", SwingConstants.CENTER);
            vacio.setForeground(new Color(240, 230, 210));
            vacio.setFont(new Font("SansSerif", Font.BOLD, 18));
            this.listaHabilidades.add(vacio);
        }
        for (int i = 0; i < nombres.size(); i++) {
            BotonTexto boton = new BotonTexto(nombres.get(i));
            boton.setToolTipText(descripciones.get(i));
            this.listaHabilidades.add(boton);
            botones.add(boton);
        }
        this.listaHabilidades.revalidate();
        this.listaHabilidades.repaint();
        return botones;
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

    public JButton getBotonBasico() {
        return this.botonBasico;
    }

    public JButton getBotonHabilidades() {
        return this.botonHabilidades;
    }

    public JButton getBotonVolverCombate() {
        return this.botonVolverCombate;
    }

    public JButton getBotonVolverHabilidades() {
        return this.botonVolverHabilidades;
    }
}