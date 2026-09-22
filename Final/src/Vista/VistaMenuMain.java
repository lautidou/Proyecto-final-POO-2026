package Vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class VistaMenuMain {

    private final JFrame ventana;
    private final JButton botonUno;
    private final JButton botonDos;
    private final JButton botonTres;

    public VistaMenuMain() {
        this.ventana = new JFrame();
        this.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.ventana.setUndecorated(true);         
        this.ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.ventana.setResizable(false);
        this.ventana.setLayout(new BorderLayout());

        PanelFondo panelFondo = new PanelFondo("Final/src/Assets/fondoInicio.png");
        panelFondo.setLayout(new java.awt.BorderLayout());
        this.ventana.setContentPane(panelFondo);

        JPanel panelContenedorCentral = new JPanel(new java.awt.GridBagLayout());
        panelContenedorCentral.setOpaque(false);

        PanelFondo panelPergamino = new PanelFondo("Final/src/Assets/panelOpciones.png");
        panelPergamino.setOpaque(false);
        panelPergamino.setLayout(new java.awt.GridBagLayout());
        panelPergamino.setPreferredSize(new java.awt.Dimension(350, 350));

        JPanel panelBotones = new JPanel(new java.awt.GridLayout(3, 1, 0, 20));
        panelBotones.setOpaque(false);
        panelBotones.setPreferredSize(new java.awt.Dimension(200, 150));

        this.botonUno = new JButton("Acción 1");
        this.botonDos = new JButton("Acción 2");
        this.botonTres = new JButton("Acción 3");

        panelBotones.add(this.botonUno);
        panelBotones.add(this.botonDos);
        panelBotones.add(this.botonTres);

        panelPergamino.add(panelBotones);

        panelContenedorCentral.add(panelPergamino);

        this.ventana.add(panelContenedorCentral, java.awt.BorderLayout.CENTER);
    }

    public void mostrar() {
        this.ventana.setVisible(true);
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