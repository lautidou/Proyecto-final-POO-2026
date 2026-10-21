package Vista;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class VistaMenuMain extends JPanel {

    public static final String NOMBRE = "MenuMain";

    private final JButton botonUno;
    private final JButton botonDos;
    private final JButton botonTres;

    public VistaMenuMain() {
        super(new BorderLayout());

        PanelFondo panelFondo = new PanelFondo("Assets/fondoInicio.png");
        panelFondo.setLayout(new BorderLayout());
        this.add(panelFondo, BorderLayout.CENTER);

        JPanel panelContenedorCentral = new JPanel(new GridBagLayout());
        panelContenedorCentral.setOpaque(false);

        PanelFondo panelPergamino = new PanelFondo("Assets/panelOpciones.png");
        panelPergamino.setOpaque(false);
        panelPergamino.setLayout(new GridBagLayout());
        panelPergamino.setPreferredSize(new Dimension(350, 350));

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 20));
        panelBotones.setOpaque(false);
        panelBotones.setPreferredSize(new Dimension(200, 150));//ver despues tamaño de las letras

        this.botonUno = new BotonImagen("Assets/MenuMain/botonCrearPartida.png", "Crear partida");
        this.botonDos = new BotonImagen("Assets/MenuMain/botonCargarPartida.png", "Cargar partida");
        this.botonTres = new BotonImagen("Assets/MenuMain/botonSalir.png", "Salir");

        panelBotones.add(this.botonUno);
        panelBotones.add(this.botonDos);
        panelBotones.add(this.botonTres);

        panelPergamino.add(panelBotones);
        panelContenedorCentral.add(panelPergamino);

        panelFondo.add(panelContenedorCentral, BorderLayout.CENTER);
    }

    public JButton getBotonUno() {
        return this.botonUno;
    }

    public JButton getBotonDos() {
        return this.botonDos;
    }

    public JButton getBotonTres() {
        return this.botonTres;
    }
}