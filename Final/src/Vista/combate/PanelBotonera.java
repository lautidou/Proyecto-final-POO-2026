package Vista.combate;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.List;

import Vista.BotonImagen;

public class PanelBotonera extends JPanel {

    private static final String MENU_PRINCIPAL = "principal";
    private static final String MENU_COMBATE = "combate";
    private static final String MENU_HABILIDADES = "habilidades";
    private static final String MENU_INVENTARIO = "inventario";


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

    // Submenú de listas 
    private final MenuLista menuHabilidades = new MenuLista(RUTA_BOTON_VOLVER, "No conocés ninguna habilidad.");
    private final MenuLista menuInventario = new MenuLista(RUTA_BOTON_VOLVER, "No tenés consumibles.");

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

        add(crearMenuPrincipal(), MENU_PRINCIPAL);
        add(crearMenuCombate(), MENU_COMBATE);
        add(this.menuHabilidades, MENU_HABILIDADES);
        add(this.menuInventario, MENU_INVENTARIO);
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

    public void mostrarMenuInventario() {
        this.tarjetas.show(this, MENU_INVENTARIO);
    }

    // ---------- Habilidades (cambian de un combate a otro) ----------
    public List<JButton> setHabilidades(List<String> nombres, List<String> descripciones) {
        return this.menuHabilidades.setOpciones(nombres, descripciones);
    }

    // ---------- Objetos (cambian de un combate a otro) ----------
    public List<JButton> setConsumibles(List<String> nombres, List<String> descripciones) {
        return this.menuInventario.setOpciones(nombres, descripciones);
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
        return this.menuHabilidades.getBotonVolver();
    }

    public JButton getBotonVolverInventario() {
        return this.menuInventario.getBotonVolver();
    }
}