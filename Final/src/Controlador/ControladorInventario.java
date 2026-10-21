package Controlador;

import Modelo.ModeloInventario;
import Modelo.ModeloPausa;
import Vista.PanelInventario;
import Vista.Ventana;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

public class ControladorInventario {

    private final Ventana ventana;
    private final ModeloInventario modeloInventario;
    private final ModeloPausa modeloPausa;
    private final JPanel panelDeJuego;

    public ControladorInventario(Ventana ventana, ModeloInventario modeloInventario, ModeloPausa modeloPausa,
                                 JPanel panelDeJuego) {
        this.ventana = ventana;
        this.modeloInventario = modeloInventario;
        this.modeloPausa = modeloPausa;
        this.panelDeJuego = panelDeJuego;
    }

    public void configurarTeclas() {
        panelDeJuego.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "inventario");
        panelDeJuego.getActionMap().put("inventario", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!modeloPausa.isJuegoPausado()) {
                    abrirInventario();
                }
            }
        });
    }

    private void abrirInventario() {
        modeloPausa.setJuegoPausado(true); // mientras está abierto el héroe no se mueve

        PanelInventario panel = new PanelInventario(ventana);
        panel.setObjetos(modeloInventario.getObjetos());

        PropertyChangeListener oyente = evento -> panel.setObjetos(modeloInventario.getObjetos());
        modeloInventario.addPropertyChangeListener(oyente);

        panel.getUsar().addActionListener(e -> modeloInventario.usarObjeto(panel.getObjetoSeleccionado()));
        panel.getCerrar().addActionListener(e -> {
            modeloInventario.removePropertyChangeListener(oyente);
            panel.dispose();
            modeloPausa.setJuegoPausado(false);
            panelDeJuego.requestFocusInWindow();
        });

        // La misma tecla (o Esc) cierra el inventario
        panel.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "cerrar");
        panel.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrar");
        panel.getRootPane().getActionMap().put("cerrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.getCerrar().doClick();
            }
        });

        panel.setVisible(true);
    }
}
