package Controlador;

import Modelo.ModeloInventario;
import Modelo.ModeloMapa;
import Modelo.ModeloPartida;
import Modelo.mapa.Direccion;
import Modelo.mapa.Mapa;
import Modelo.mapa.Posicion;
import Modelo.mapa.Terreno;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Heroe;
import Modelo.pjs.Personaje;
import Modelo.ModeloPausa;
import Vista.Ventana;
import Vista.VistaMapa;
import Vista.VistaMenuMain;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ControladorMapa {

    private final VistaMapa vista;
    private final ModeloMapa modelo;
    private final ModeloPartida modeloPartida;
    private final BiConsumer<Heroe, Enemigo> iniciarCombate; // lo que pasa cuando el héroe choca con un enemigo
    private final ControladorPantallaPausa controladorPausa;
    private final ModeloPausa modeloPausa;

    public ControladorMapa(VistaMapa vista, ModeloMapa modelo, ModeloPartida modeloPartida,
                           BiConsumer<Heroe, Enemigo> iniciarCombate,ModeloPausa modeloPausa) {
        if (vista == null || modelo == null || modeloPartida == null || iniciarCombate == null) {
            throw new IllegalArgumentException("La vista y los modelos son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;
        this.modeloPartida = modeloPartida;
        this.modeloPausa = modeloPausa;
        this.iniciarCombate = iniciarCombate;

        this.controladorPausa = new ControladorPantallaPausa(Ventana.getInstancia(), this.modeloPausa, this.vista, this);
        this.controladorPausa.configurarTeclas();

        new ControladorInventario(Ventana.getInstancia(), new ModeloInventario(this.modelo), this.modeloPausa, this.vista)
                .configurarTeclas();

        registrarTeclas();
        mostrarEscenario(this.modelo.getMapaActual());
        mostrarEnemigos(this.modelo.getMapaActual());
        mostrarPersonaje();
        this.modelo.addPropertyChangeListener(this::alCambiarModelo);
    }

    private void alCambiarModelo(PropertyChangeEvent evento) {
        switch (evento.getPropertyName()) {
            case ModeloMapa.PROP_ESCENARIO:
                mostrarEscenario((Mapa) evento.getNewValue());
                mostrarEnemigos((Mapa) evento.getNewValue());
                break;
            case ModeloMapa.PROP_PERSONAJE:
            case ModeloMapa.PROP_POSICION:
                mostrarPersonaje();
                break;
            case ModeloMapa.PROP_ENCUENTRO:
                this.iniciarCombate.accept(this.modelo.getPersonaje(), (Enemigo) evento.getNewValue());
                return;
            default:
                return;
        }
        this.vista.repaint();
    }

    private void mostrarEscenario(Mapa mapa) {
        this.vista.setEscenario(mapa.getId(), extraerTerrenos(mapa));
    }

    private void mostrarEnemigos(Mapa mapa) {
        Map<Posicion, String> enemigos = new LinkedHashMap<>();
        for (Enemigo enemigo : mapa.getEnemigos()) {
            enemigos.put(enemigo.getPosicion(), enemigo.getNombre());
        }
        this.vista.setEnemigos(enemigos);
    }

    private void mostrarPersonaje() {
        Personaje pj = this.modelo.getPersonaje();
        if (pj != null && pj.getPosicion() != null) {
            this.vista.setPersonaje(pj.getNombre(), pj.getPosicion());
        } else {
            this.vista.quitarPersonaje();
        }
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

    private void registrarTeclas() {
        asignarMovimiento(KeyEvent.VK_W, Direccion.ARRIBA);
        asignarMovimiento(KeyEvent.VK_UP, Direccion.ARRIBA);
        asignarMovimiento(KeyEvent.VK_S, Direccion.ABAJO);
        asignarMovimiento(KeyEvent.VK_DOWN, Direccion.ABAJO);
        asignarMovimiento(KeyEvent.VK_A, Direccion.IZQUIERDA);
        asignarMovimiento(KeyEvent.VK_LEFT, Direccion.IZQUIERDA);
        asignarMovimiento(KeyEvent.VK_D, Direccion.DERECHA);
        asignarMovimiento(KeyEvent.VK_RIGHT, Direccion.DERECHA);

        //asignarAccion(KeyEvent.VK_ESCAPE, "salir", this::ejecutarSalir);
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
    //pongo un if para que no se mueva si esta en pausa el juego
    private void ejecutarMovimiento(Direccion direccion) {
        if (this.modeloPausa.isJuegoPausado()) {
            return;
        }
        this.modelo.moverPersonaje(direccion);
    }

    public void ejecutarSalir() {
        if (this.modelo.getPersonaje() != null && !this.modeloPartida.guardar()) {
            System.err.println("No se pudo guardar la partida.");
        }
        Ventana.getInstancia().mostrarPanel(VistaMenuMain.NOMBRE);
    }
}