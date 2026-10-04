package Controlador;

import Modelo.ModeloMapa;
import Modelo.mapa.Direccion;
import Modelo.mapa.Mapa;
import Modelo.mapa.Posicion;
import Modelo.mapa.Terreno;
import Modelo.pjs.Personaje;
import Vista.Ventana;
import Vista.VistaMapa;
import Vista.VistaMenuMain;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ControladorMapa {

    private final VistaMapa vista;
    private final ModeloMapa modelo;
    private Mapa escenarioMostrado; // para reconstruir la grilla solo cuando cambia el escenario

    public ControladorMapa(VistaMapa vista, ModeloMapa modelo) {
        if (vista == null || modelo == null) {
            throw new IllegalArgumentException("La vista y el modelo son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;

        registrarTeclas();
        this.modelo.agregarObservador(this::actualizarVista); // el modelo avisa, el controlador actualiza
        actualizarVista();
    }

    /** Copia el estado del modelo a la vista. */
    private void actualizarVista() {
        Mapa mapa = this.modelo.getMapaActual();
        if (mapa != this.escenarioMostrado) {
            this.vista.setEscenario(mapa.getId(), extraerTerrenos(mapa));
            this.escenarioMostrado = mapa;
        }

        Personaje pj = this.modelo.getPersonaje();
        if (pj != null && pj.getPosicion() != null) {
            this.vista.setPersonaje(pj.getNombre(), pj.getPosicion());
        } else {
            this.vista.quitarPersonaje();
        }
        this.vista.repaint();
    }

    private Terreno[][] extraerTerrenos(Mapa mapa) {
        Terreno[][] terrenos = new Terreno[mapa.getFilas()][mapa.getColumnas()];
        for (int y = 0; y < mapa.getFilas(); y++) {
            for (int x = 0; x < mapa.getColumnas(); x++) {
                terrenos[y][x] = mapa.getCelda(new Posicion(x, y)).getTerreno();
            }
        }
        return terrenos;
    }

    /** Traduce las teclas físicas a acciones del juego. */
    private void registrarTeclas() {
        asignarMovimiento(KeyEvent.VK_W, Direccion.ARRIBA);
        asignarMovimiento(KeyEvent.VK_UP, Direccion.ARRIBA);
        asignarMovimiento(KeyEvent.VK_S, Direccion.ABAJO);
        asignarMovimiento(KeyEvent.VK_DOWN, Direccion.ABAJO);
        asignarMovimiento(KeyEvent.VK_A, Direccion.IZQUIERDA);
        asignarMovimiento(KeyEvent.VK_LEFT, Direccion.IZQUIERDA);
        asignarMovimiento(KeyEvent.VK_D, Direccion.DERECHA);
        asignarMovimiento(KeyEvent.VK_RIGHT, Direccion.DERECHA);

        asignarAccion(KeyEvent.VK_ESCAPE, "salir", this::ejecutarSalir);
    }

    private void asignarMovimiento(int tecla, Direccion direccion) {
        asignarAccion(tecla, "mover-" + tecla, () -> this.ejecutarMovimiento(direccion));
    }

    private void asignarAccion(int tecla, String id, Runnable accion) {
        this.vista.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(tecla, 0), id);
        this.vista.getActionMap().put(id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion.run();
            }
        });
    }

    private void ejecutarMovimiento(Direccion direccion) {
        // La vista se actualiza sola: el modelo notifica si hubo cambio.
        this.modelo.moverPersonaje(direccion);
    }

    private void ejecutarSalir() {
        Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);
    }
}