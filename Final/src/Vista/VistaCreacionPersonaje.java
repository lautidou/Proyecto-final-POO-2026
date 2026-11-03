package Vista;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaCreacionPersonaje extends JPanel {

    public static final String NOMBRE = "CreacionPersonaje";

    private final JTextField campoNombre;
    private final JComboBox<String> comboRaza;
    private final JComboBox<String> comboClase;
    private final JButton botonCrear;
    private final JButton botonVolver;
    private final JLabel imagenPersonaje;

    private static final int ANCHO_IMAGEN = 350;
    private static final int ALTO_IMAGEN = 450;

    public VistaCreacionPersonaje() {
        super(new BorderLayout());

        // FONDO (igual que en VistaMenuMain)
        PanelFondo panelFondo = new PanelFondo("Assets/fondoInicio.png");
        panelFondo.setLayout(new BorderLayout());
        this.add(panelFondo, BorderLayout.CENTER);

        // CENTRO: nombre + imagen + botones
        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        contenedorCentral.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setForeground(Color.WHITE);
        gbc.gridy = 0;
        contenedorCentral.add(labelNombre, gbc);

        this.campoNombre = new JTextField(15);
        gbc.gridy = 1;
        contenedorCentral.add(this.campoNombre, gbc);

        // IMAGEN entre el nombre y los botones
        this.imagenPersonaje = new JLabel();
        this.imagenPersonaje.setHorizontalAlignment(SwingConstants.CENTER);
        this.imagenPersonaje.setPreferredSize(new Dimension(ANCHO_IMAGEN, ALTO_IMAGEN));
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 15, 0);
        contenedorCentral.add(this.imagenPersonaje, gbc);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setOpaque(false);
        this.botonVolver = new JButton("Volver");
        this.botonCrear = new JButton("Crear Personaje");
        panelBotones.add(this.botonVolver);
        panelBotones.add(this.botonCrear);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 5, 0);
        contenedorCentral.add(panelBotones, gbc);

        // El GridBagLayout respeta el tamaño preferido y centra el panel
        JPanel envolturaCentral = new JPanel(new GridBagLayout());
        envolturaCentral.setOpaque(false);
        envolturaCentral.add(contenedorCentral);

        // DERECHA: raza y clase
        JPanel contenedorDerecha = new JPanel(new GridLayout(4, 1, 0, 10));
        contenedorDerecha.setOpaque(false);

        JLabel labelRaza = new JLabel("Raza:");
        labelRaza.setForeground(Color.WHITE);
        contenedorDerecha.add(labelRaza);

        this.comboRaza = new JComboBox<>();
        contenedorDerecha.add(this.comboRaza);

        JLabel labelClase = new JLabel("Clase:");
        labelClase.setForeground(Color.WHITE);
        contenedorDerecha.add(labelClase);

        this.comboClase = new JComboBox<>();
        contenedorDerecha.add(this.comboClase);

        JPanel envolturaDerecha = new JPanel(new GridBagLayout());
        envolturaDerecha.setOpaque(false);
        envolturaDerecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
        envolturaDerecha.add(contenedorDerecha);

        // AGREGA AL PANEL DE FONDO
        panelFondo.add(envolturaCentral, BorderLayout.CENTER);
        panelFondo.add(envolturaDerecha, BorderLayout.EAST);
    }

    public void setImagenPersonaje(String ruta) {
    ImageIcon original = ruta == null ? null : new ImageIcon(ruta);
    if (original == null || original.getIconWidth() <= 0) {
        imagenPersonaje.setIcon(null);
        return;
    }
    Image escalada = original.getImage()
            .getScaledInstance(ANCHO_IMAGEN, ALTO_IMAGEN, Image.SCALE_FAST);
    imagenPersonaje.setIcon(new ImageIcon(escalada));
    }


    public String getNombreIngresado() {
        return this.campoNombre.getText();
    }

    public void setRazas(List<String> nombres)  { 
        cargar(comboRaza, nombres); 
    }
    public void setClases(List<String> nombres) {
        cargar(comboClase, nombres); 
    }

    private void cargar(JComboBox<String> combo, List<String> nombres) {
        combo.setModel(new DefaultComboBoxModel<>(nombres.toArray(new String[0])));
    }

    public int getIndiceRaza()  {   
        return comboRaza.getSelectedIndex(); 
    }

    public int getIndiceClase() { 
        return comboClase.getSelectedIndex(); 
    }

    public void addClaseListener(ActionListener l) { 
        comboClase.addActionListener(l); 
    }

    public void mostrarMensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }

    public void mostrarError(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Error al crear personaje", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarCampos() {
        this.campoNombre.setText("");
        this.comboRaza.setSelectedIndex(0);
        this.comboClase.setSelectedIndex(0);
    }

    public JButton getBotonCrear() {
        return this.botonCrear;
    }

    public JButton getBotonVolver() {
        return this.botonVolver;
    }
}