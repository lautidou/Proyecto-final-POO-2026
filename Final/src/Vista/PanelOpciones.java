package Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JButton;
import Audio.ReproductorAudio;

public class PanelOpciones extends JDialog {

    private JSlider deslizadorVolumen;
    private JButton botonVolver;

    public PanelOpciones(Ventana ventana) {
        super(ventana.getFrame(), "Opciones", true);
        setSize(ventana.getFrame().getSize());
        setLocationRelativeTo(ventana.getFrame());
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 160)); 
        setLayout(new GridBagLayout());

        PanelFondo marcoMadera = new PanelFondo("Assets/Pelea/tablero.png");
        marcoMadera.setPreferredSize(new Dimension(450, 250));
        marcoMadera.setOpaque(false); 
        marcoMadera.setLayout(new GridBagLayout());

        JPanel panelContenido = new JPanel(new GridLayout(2, 1, 0, 20));
        panelContenido.setOpaque(false);
        panelContenido.setPreferredSize(new Dimension(250, 100));

        deslizadorVolumen = new JSlider(0, 100, 100);
        deslizadorVolumen.setOpaque(false);
        deslizadorVolumen.addChangeListener(e -> {
            float volumen = deslizadorVolumen.getValue() / 100f;
            ReproductorAudio.setVolumen(volumen);
        });

        botonVolver = new BotonImagen("Assets/Pelea/botones/botonVolver.png", "Volver");
        botonVolver.addActionListener(e -> {
            this.dispose();
        });

        panelContenido.add(deslizadorVolumen);
        panelContenido.add(botonVolver);
        
        marcoMadera.add(panelContenido);
        add(marcoMadera);
    }
}
