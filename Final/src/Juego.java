import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Juego extends JFrame {
    public Juego() {
        setTitle("Juego RPG");
        setSize(800, 600); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Agregamos el escenario a la ventana
        add(new PanelEscenario());
        
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            Juego ventana = new Juego();
            ventana.setVisible(true);
        });
    }
}