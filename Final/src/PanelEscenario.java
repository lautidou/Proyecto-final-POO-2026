import javax.swing.JPanel;
import pjs.Heroe;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class PanelEscenario extends JPanel implements KeyListener{
    private Heroe heroe;

    public PanelEscenario() {
        // Inicializamos al personaje en las coordenadas (100, 100)
        heroe = new Heroe(100, 100);
        // MUY IMPORTANTE: Permite que el panel reciba eventos de teclado
        setFocusable(true); 
        requestFocusInWindow();
        
        // Le decimos al panel que se escuche a sí mismo
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Limpia el panel antes de dibujar
        
        // Dibujar el fondo del escenario
        g.setColor(new Color(34, 139, 34)); // Verde estilo pasto
        g.fillRect(0, 0, getWidth(), getHeight());

        // Dibujar al personaje
        heroe.dibujar(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        // Detectar si presionó W, A, S, D o las flechas
        if (tecla == KeyEvent.VK_W || tecla == KeyEvent.VK_UP) {
            heroe.mover(0, -1); // Arriba (Restamos a Y)
        } 
        else if (tecla == KeyEvent.VK_S || tecla == KeyEvent.VK_DOWN) {
            heroe.mover(0, 1);  // Abajo (Sumamos a Y)
        } 
        else if (tecla == KeyEvent.VK_A || tecla == KeyEvent.VK_LEFT) {
            heroe.mover(-1, 0); // Izquierda (Restamos a X)
        } 
        else if (tecla == KeyEvent.VK_D || tecla == KeyEvent.VK_RIGHT) {
            heroe.mover(1, 0);  // Derecha (Sumamos a X)
        }

        // Vuelve a llamar a paintComponent() para dibujar al personaje en su nueva posición
        repaint(); 
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No lo usamos, pero es obligatorio dejarlo por la interfaz
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No lo usamos, pero es obligatorio dejarlo por la interfaz
    }
}