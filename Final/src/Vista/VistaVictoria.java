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

/** Pantalla que se muestra cada vez que se derrota a un enemigo. */
public class VistaVictoria extends JPanel {

    public static final String NOMBRE = "Victoria";

    private final BotonTexto botonSeguir = new BotonTexto("Seguir explorando");
    private final BotonTexto botonMenu = new BotonTexto("Menú principal");
    private final JLabel subtitulo = new JLabel("", SwingConstants.CENTER);

    public VistaVictoria() {
        super(new GridBagLayout());
        setBackground(Color.BLACK);

        JLabel titulo = new JLabel("¡VICTORIA!", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 64));
        titulo.setForeground(new Color(230, 190, 60));

        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitulo.setForeground(new Color(240, 230, 210));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(titulo, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);
        add(subtitulo, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridy = 2;
        add(this.botonSeguir, gbc);
        gbc.gridy = 3;
        add(this.botonMenu, gbc);
    }

    public void setMensaje(String mensaje) {
        this.subtitulo.setText(mensaje);
    }

    public JButton getBotonSeguir() {
        return this.botonSeguir;
    }

    public JButton getBotonMenu() {
        return this.botonMenu;
    }
}


