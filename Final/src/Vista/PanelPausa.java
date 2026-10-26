package Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

//Jdialog es una ventana que bloquea la interaccion con el resto del programa
//esto es para crear la ventana emergente

public class PanelPausa extends JDialog{

    private JButton continuar;
    private JButton opciones;
    private JButton salir;

    public PanelPausa(Ventana ventana){
        super(ventana.getFrame(),"Juego Pausado", true);
        setSize(ventana.getFrame().getSize());
        setLocationRelativeTo(ventana.getFrame());
        setUndecorated(true);//es para sacar la barra superior de windows teoricamente

        setBackground(new Color(0,0,0,160));//para oscurecer el juego de fondo
        setLayout(new GridBagLayout());//centramos el pergamino

        //para poner el pergamino
        PanelFondo panelPergamino = new PanelFondo("Assets/panelOpciones.png");
        panelPergamino.setPreferredSize(new Dimension(350, 400));
        panelPergamino.setOpaque(false);
        panelPergamino.setLayout(new GridBagLayout());

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 15));
        panelBotones.setOpaque(false);
        panelBotones.setPreferredSize(new Dimension(200, 150));

        this.continuar = new BotonImagen("Assets/BotonPausa/Continuar.png","Continuar");
        this.opciones = new BotonImagen( "Assets/BotonPausa/Opciones.png", "Opciones");
        this.salir = new BotonImagen( "Assets/BotonPausa/Salir.png","salir");

        panelBotones.add(this.continuar);
        panelBotones.add(this.opciones);
        panelBotones.add(this.salir);

        panelPergamino.add(panelBotones);

        add(panelPergamino);
    }

    public JButton getContinuar(){
        return continuar;
    }
    public JButton getOpciones(){
        return opciones;
    }
    public JButton getSalir(){
        return salir;
    }
}