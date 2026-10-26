package Controlador;

import Audio.ReproductorAudio;
import Modelo.ModeloInventario;
import Modelo.ModeloPausa;
import Modelo.objetos.Consumible;
import Modelo.objetos.Objeto;
import Vista.Inventario.FilaInventario;
import Vista.Inventario.PanelInventario;
import Vista.Ventana;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorInventario {

    private final Ventana ventana;
    private final ModeloInventario modeloInventario;
    private final ModeloPausa modeloPausa;
    private final JPanel panelDeJuego;

    private List<Objeto> objetosMostrados = new ArrayList<>();

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
        refrescar(panel);

        PropertyChangeListener oyente = evento -> refrescar(panel);
        modeloInventario.addPropertyChangeListener(oyente);

        panel.getUsar().addActionListener(e -> {
            int indice = panel.getIndiceSeleccionado();
            if (indice < 0 || indice >= objetosMostrados.size()) {
                return;
            }
            Objeto objeto = objetosMostrados.get(indice);
            if (modeloInventario.usarObjeto(objeto) && objeto instanceof Consumible) {
                ReproductorAudio.efectoDeConsumible(objeto.getNombre());
            }
        });
        panel.getCerrar().addActionListener(e -> {
            modeloInventario.removePropertyChangeListener(oyente);
            panel.dispose();
            modeloPausa.setJuegoPausado(false);
            panelDeJuego.requestFocusInWindow();
        });

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

    private void refrescar(PanelInventario panel) {
        objetosMostrados = modeloInventario.getObjetos();
        List<FilaInventario> filas = objetosMostrados.stream()
                .map(o -> new FilaInventario(
                        o.getNombre(),
                        o.getDetalle(),
                        modeloInventario.esEquipable(o),
                        modeloInventario.estaEquipado(o)))
                .collect(Collectors.toList());
        panel.setFilas(filas);
    }
}