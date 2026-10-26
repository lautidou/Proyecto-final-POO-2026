package Vista;

import Vista.combate.BotonTexto;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class VistaMuerte extends JPanel {

    public static final String NOMBRE = "Muerte";

    private final BotonTexto botonContinuar = new BotonTexto("Volver al punto de guardado");
    private final BotonTexto botonMenu = new BotonTexto("Menú principal");
    private final JLabel aviso = new JLabel(" ", SwingConstants.CENTER);

    public VistaMuerte() {
        super(new GridBagLayout());
        setBackground(Color.BLACK);

        JLabel titulo = new JLabel("HAS MUERTO", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 64));
        titulo.setForeground(new Color(170, 30, 30));

        this.aviso.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.aviso.setForeground(new Color(240, 230, 210));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        add(titulo, gbc);
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridy = 1;
        add(this.botonContinuar, gbc);
        gbc.gridy = 2;
        add(this.botonMenu, gbc);
        gbc.gridy = 3;
        add(this.aviso, gbc);
    }

    public void mostrarAviso(String texto) {
        this.aviso.setText(texto);
    }

    public void limpiarAviso() {
        this.aviso.setText(" ");
    }

    public JButton getBotonContinuar() {
        return this.botonContinuar;
    }

    public JButton getBotonMenu() {
        return this.botonMenu;
    }
}