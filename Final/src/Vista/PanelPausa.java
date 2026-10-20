package Vista;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
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
        setSize(250,300);
        setLocationRelativeTo(ventana.getFrame());
        setUndecorated(true);//es para sacar la barra superior de windows teoricamente

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(3,1,10,10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panelPrincipal.setBackground(Color.DARK_GRAY);

        //creacion de botones
        continuar= new JButton("Continuar");
        opciones= new JButton("Opciones");
        salir= new JButton("Salir");

        panelPrincipal.add(continuar);
        panelPrincipal.add(opciones);
        panelPrincipal.add(salir);

        add(panelPrincipal);
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
