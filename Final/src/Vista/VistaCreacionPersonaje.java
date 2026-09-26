package Vista;

import Modelo.pjs.Clases;
import Modelo.pjs.Raza;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Pantalla de creación de personaje. Se muestra al presionar el botón 1
 * del menú principal (VistaMenuMain).
 */
public class VistaCreacionPersonaje extends JPanel {

    /** Nombre con el que esta pantalla se registra en la Ventana (CardLayout). */
    public static final String NOMBRE = "CreacionPersonaje";

    private final JTextField campoNombre;
    private final JComboBox<Raza> comboRaza;
    private final JComboBox<Clases> comboClase;
    private final JButton botonCrear;
    private final JButton botonVolver;

    public VistaCreacionPersonaje() {
        super(new GridBagLayout());
        this.setBackground(new Color(30, 24, 18));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Creación de Personaje");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelFormulario.add(titulo, gbc);
        gbc.gridwidth = 1;

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(labelNombre, gbc);

        this.campoNombre = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelFormulario.add(this.campoNombre, gbc);

        JLabel labelRaza = new JLabel("Raza:");
        labelRaza.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(labelRaza, gbc);

        this.comboRaza = new JComboBox<>(Raza.values());
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelFormulario.add(this.comboRaza, gbc);

        JLabel labelClase = new JLabel("Clase:");
        labelClase.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(labelClase, gbc);

        this.comboClase = new JComboBox<>(Clases.values());
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelFormulario.add(this.comboClase, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setOpaque(false);

        this.botonVolver = new JButton("Volver");
        this.botonCrear = new JButton("Crear Personaje");
        panelBotones.add(this.botonVolver);
        panelBotones.add(this.botonCrear);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        this.add(panelFormulario);
    }

    public String getNombreIngresado() {
        return this.campoNombre.getText();
    }

    public Raza getRazaSeleccionada() {
        return (Raza) this.comboRaza.getSelectedItem();
    }

    public Clases getClaseSeleccionada() {
        return (Clases) this.comboClase.getSelectedItem();
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