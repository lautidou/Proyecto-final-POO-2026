package Vista.Inventario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import Vista.Ventana;

public class PanelInventario extends JDialog {

    private final DefaultListModel<FilaInventario> modeloLista = new DefaultListModel<>();
    private final JList<FilaInventario> lista = new JList<>(modeloLista);
    private final JTextArea detalle = new JTextArea();
    private final JButton usar = new JButton("Usar");
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
        lista.setCellRenderer((jlist, fila, indice, seleccionado, foco) -> {
            JLabel etiqueta = new JLabel(" " + fila.nombre() + (fila.equipado() ? "  [Equipado]" : ""));
            etiqueta.setOpaque(true);
            etiqueta.setBackground(seleccionado ? Color.LIGHT_GRAY : Color.WHITE);
            if (fila.equipado()) {
                etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD));
            }
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

    public void setFilas(List<FilaInventario> filas) {
        int indiceAnterior = lista.getSelectedIndex();
        modeloLista.clear();
        for (FilaInventario fila : filas) {
            modeloLista.addElement(fila);
        }
        if (!modeloLista.isEmpty()) {
            lista.setSelectedIndex(Math.max(0, Math.min(indiceAnterior, modeloLista.size() - 1)));
        }
        mostrarDetalle();
    }

    private void mostrarDetalle() {
        FilaInventario fila = lista.getSelectedValue();
        if (fila == null) {
            detalle.setText(modeloLista.isEmpty() ? "La mochila está vacía." : "");
            usar.setText("Usar");
        } else {
            String estado = fila.equipado() ? "\n[EQUIPADO]" : "";
            detalle.setText(fila.nombre() + estado + "\n\n" + fila.detalle());
            usar.setText(fila.textoAccion());
        }
        usar.setEnabled(fila != null);
    }

    public int getIndiceSeleccionado() {
        return lista.getSelectedIndex();
    }

    public JButton getUsar() {
        return usar;
    }

    public JButton getCerrar() {
        return cerrar;
    }
}