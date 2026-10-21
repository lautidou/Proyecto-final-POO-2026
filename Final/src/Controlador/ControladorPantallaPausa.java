package Controlador;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import Modelo.ModeloPausa;
import Vista.PanelPausa;
import Vista.Ventana;

public class ControladorPantallaPausa {

    private Ventana ventana;
    private final ModeloPausa modeloPausa;
    private JPanel panelDeJuego;
    private ControladorMapa controladorMapa;

    public ControladorPantallaPausa(Ventana ventana, ModeloPausa modeloPausa, JPanel panelDeJuego,ControladorMapa controladorMapa) {
    this.ventana = ventana;
    this.modeloPausa = modeloPausa;
    this.panelDeJuego = panelDeJuego; 
    this.controladorMapa = controladorMapa;
    }

    public void configurarTeclas(){
        panelDeJuego.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "pausar");
        panelDeJuego.getActionMap().put("pausar", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e){
            if (!modeloPausa.isJuegoPausado()) {
                pausarJuego();
            }
           } 
        });
    }

    private void pausarJuego(){
        modeloPausa.setJuegoPausado(true);

        PanelPausa menuPausa = new PanelPausa(ventana);
        menuPausa.getContinuar().addActionListener(e->{
            menuPausa.dispose();//cierra el menu
            modeloPausa.setJuegoPausado(false);//reanuda el juego
            panelDeJuego.requestFocusInWindow();// vuelve el foco al juego pausado
        });

        /*menuPausa.getOpciones().addActionListener(e->{
            //hay que poner opciones jeje
        });*/

        menuPausa.getSalir().addActionListener(e->{
            menuPausa.dispose();
            modeloPausa.setJuegoPausado(false);
            controladorMapa.ejecutarSalir();
        });

        //para un poco mas de comodidad aplicamos el doble esc para salir de menu de pausa
        //simulamos que el usuario apreto continuar para reanudar
        menuPausa.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
        put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),"reanudar");
        menuPausa.getRootPane().getActionMap().put("reanudar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPausa.getContinuar().doClick();
            }
        });

        menuPausa.setVisible(true);
    }
    
}
