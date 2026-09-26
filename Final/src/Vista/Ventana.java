package Vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

public class Ventana {

    private static Ventana instancia;

    private final JFrame frame;
    private final JPanel panelContenedor;
    private final CardLayout cardLayout;
    private final Map<String, JPanel> paneles;

    private Ventana() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setUndecorated(true);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setResizable(false);

        this.cardLayout = new CardLayout();
        this.panelContenedor = new JPanel(this.cardLayout);
        this.paneles = new HashMap<>();

        this.frame.setContentPane(this.panelContenedor);
    }

    public static Ventana getInstancia() {
        if (instancia == null) {
            instancia = new Ventana();
        }
        return instancia;
    }

    public void registrarPanel(String nombre, JPanel panel) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del panel no puede ser nulo o vacío.");
        }
        if (panel == null) {
            throw new IllegalArgumentException("El panel no puede ser nulo.");
        }
        if (this.paneles.containsKey(nombre)) {
            return;
        }
        this.paneles.put(nombre, panel);
        this.panelContenedor.add(panel, nombre);
    }

    public void mostrarPanel(String nombre) {
        if (!this.paneles.containsKey(nombre)) {
            throw new IllegalArgumentException("El panel '" + nombre + "' no está registrado en la Ventana.");
        }
        this.cardLayout.show(this.panelContenedor, nombre);
    }

    public void mostrar() {
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}