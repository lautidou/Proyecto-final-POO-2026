package Vista.combate;

import Vista.BotonImagen;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class MenuLista extends JPanel {

    private final JPanel lista = new JPanel(new GridLayout(0, 1, 0, 8));
    private final BotonImagen botonVolver;
    private final String textoVacio;

    public MenuLista(String rutaBotonVolver, String textoVacio) {
        super(new BorderLayout());
        this.textoVacio = textoVacio;
        this.botonVolver = new BotonImagen(rutaBotonVolver, "Volver");
        setOpaque(false);

        this.lista.setOpaque(false);
        JPanel arriba = new JPanel(new BorderLayout());
        arriba.setOpaque(false);
        arriba.add(this.lista, BorderLayout.NORTH);

        JScrollPane desplazamiento = new JScrollPane(arriba);
        desplazamiento.setBorder(null);
        desplazamiento.setOpaque(false);
        desplazamiento.getViewport().setOpaque(false);
        desplazamiento.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        desplazamiento.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        desplazamiento.getVerticalScrollBar().setUnitIncrement(16);

        JPanel pie = new JPanel(new BorderLayout());
        pie.setOpaque(false);
        pie.setPreferredSize(new Dimension(0, 50));
        pie.add(this.botonVolver, BorderLayout.CENTER);

        add(desplazamiento, BorderLayout.CENTER);
        add(pie, BorderLayout.SOUTH);
    }

    public List<JButton> setOpciones(List<String> nombres, List<String> descripciones) {
        if (nombres.size() != descripciones.size()) {
            throw new IllegalArgumentException("Debe haber una descripción por cada opción.");
        }
        this.lista.removeAll();
        List<JButton> botones = new ArrayList<>();

        if (nombres.isEmpty()) {
            JLabel vacio = new JLabel(this.textoVacio, SwingConstants.CENTER);
            vacio.setForeground(new Color(240, 230, 210));
            vacio.setFont(new Font("SansSerif", Font.BOLD, 18));
            this.lista.add(vacio);
        }
        for (int i = 0; i < nombres.size(); i++) {
            BotonTexto boton = new BotonTexto(nombres.get(i));
            boton.setToolTipText(descripciones.get(i));
            this.lista.add(boton);
            botones.add(boton);
        }
        this.lista.revalidate();
        this.lista.repaint();
        return botones;
    }

    public JButton getBotonVolver() {
        return this.botonVolver;
    }
}