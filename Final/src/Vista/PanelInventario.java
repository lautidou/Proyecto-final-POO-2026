package Vista;

import Modelo.pjs.Objeto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

// Ventana emergente que lista los objetos de la mochila y el detalle del seleccionado
public class PanelInventario extends JDialog {

    private final DefaultListModel<Objeto> modeloLista = new DefaultListModel<>();
    private final JList<Objeto> lista = new JList<>(modeloLista);
    private final JTextArea detalle = new JTextArea();
    private final JButton usar = new JButton("Usar / Equipar");
    private final JButton cerrar = new JButton("Cerrar");

    public PanelInventario(Ventana ventana) {
        super(ventana.getFrame(), "Inventario", true);
        setSize(480, 320);
        setLocationRelativeTo(ventana.getFrame());
        setUndecorated(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.DARK_GRAY);

        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setCellRenderer((jlist, objeto, indice, seleccionado, foco) -> {
            javax.swing.JLabel etiqueta = new javax.swing.JLabel(" " + objeto.getNombre());
            etiqueta.setOpaque(true);
            etiqueta.setBackground(seleccionado ? Color.LIGHT_GRAY : Color.WHITE);
            return etiqueta;
        });
        lista.addListSelectionListener(e -> mostrarDetalle());

        detalle.setEditable(false);
        detalle.setLineWrap(true);
        detalle.setWrapStyleWord(true);

        JPanel centro = new JPanel(new GridLayout(1, 2, 10, 10));
        centro.setOpaque(false);
        centro.add(new JScrollPane(lista));
        centro.add(new JScrollPane(detalle));

        JPanel botones = new JPanel(new GridLayout(1, 2, 10, 10));
        botones.setOpaque(false);
        botones.add(usar);
        botones.add(cerrar);

        panelPrincipal.add(centro, BorderLayout.CENTER);
        panelPrincipal.add(botones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    // Reemplaza el contenido de la lista y conserva la selección si el objeto sigue existiendo
    public void setObjetos(List<Objeto> objetos) {
        Objeto seleccionado = lista.getSelectedValue();
        modeloLista.clear();
        for (Objeto objeto : objetos) {
            modeloLista.addElement(objeto);
        }
        if (seleccionado != null && modeloLista.contains(seleccionado)) {
            lista.setSelectedValue(seleccionado, true);
        } else if (!modeloLista.isEmpty()) {
            lista.setSelectedIndex(0);
        }
        mostrarDetalle();
    }

    private void mostrarDetalle() {
        Objeto objeto = lista.getSelectedValue();
        if (objeto == null) {
            detalle.setText(modeloLista.isEmpty() ? "La mochila está vacía." : "");
        } else {
            detalle.setText(objeto.getNombre() + "\n\n" + objeto.getDetalle());
        }
        usar.setEnabled(objeto != null);
    }

    public Objeto getObjetoSeleccionado() {
        return lista.getSelectedValue();
    }

    public JButton getUsar() {
        return usar;
    }

    public JButton getCerrar() {
        return cerrar;
    }
}
