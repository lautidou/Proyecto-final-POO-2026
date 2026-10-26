package Controlador;

import Audio.ReproductorAudio;
import Modelo.ModeloCombate;
import Modelo.objetos.Consumible;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Habilidad;
import Modelo.pjs.Heroe;
import Vista.Ventana;
import Vista.VistaCombate;

import javax.swing.JButton;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class ControladorCombate {

    private final VistaCombate vista;
    private final ModeloCombate modelo;

    public ControladorCombate(VistaCombate vista, ModeloCombate modelo) {
        if (vista == null || modelo == null) {
            throw new IllegalArgumentException("La vista y el modelo son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;

        // Menú principal
        this.vista.getBotonCombate().addActionListener(evento -> this.ejecutarCombate());
        this.vista.getBotonBusqueda().addActionListener(evento -> this.ejecutarBusqueda());
        this.vista.getBotonInventario().addActionListener(evento -> this.abrirMenuInventario());
        this.vista.getBotonEsconderse().addActionListener(evento -> this.ejecutarEsconderse());

        // Submenú de combate
        this.vista.getBotonBasico().addActionListener(evento -> this.ejecutarBasico());
        this.vista.getBotonHabilidades().addActionListener(evento -> this.abrirMenuHabilidades());
        this.vista.getBotonVolverCombate().addActionListener(evento -> this.vista.mostrarMenuPrincipal());
        this.vista.getBotonInventario().addActionListener(evento -> this.abrirMenuInventario());

        // Submenú de habilidades (los botones de cada habilidad se enganchan al abrirlo)
        this.vista.getBotonVolverHabilidades().addActionListener(evento -> this.vista.mostrarMenuCombate());

        // Submenú de inventario
        this.vista.getBotonVolverInventario().addActionListener(evento -> this.vista.mostrarMenuPrincipal());

        this.modelo.addPropertyChangeListener(this::alCambiarModelo);
    }

    public void iniciarCombate(Heroe heroe, Enemigo enemigo) {
        this.vista.limpiarMensajes();                // el historial de la pelea anterior no se arrastra
        this.vista.mostrarMenuPrincipal();           // ni un submenú que haya quedado abierto
        this.modelo.iniciarCombate(heroe, enemigo);  // el modelo avisa -> alCambiarModelo()
        ReproductorAudio.musicaDeCombate();          // corta la musica del mapa y arranca la del combate
        Ventana.getInstancia().mostrarPanel(VistaCombate.NOMBRE);
    }

    // ---------- Menú principal ----------
    private void ejecutarCombate() {
        this.vista.mostrarMenuCombate();
    }

    private void ejecutarBusqueda() {
        this.modelo.procesarBusqueda();
    }

    private void ejecutarEsconderse() {
        this.modelo.procesarEsconderse();
    }

    // ---------- Submenú de combate ----------
    private void ejecutarBasico() {
        ReproductorAudio.efectoAtaqueBasico();
        this.modelo.procesarAtaqueBasico();
        this.vista.mostrarMenuPrincipal(); // la acción gasta el turno: se vuelve al menú de inicio
    }

    // ---------- Submenú de habilidades ----------
    private void abrirMenuHabilidades() {
        List<Habilidad> habilidades = this.modelo.getHabilidadesDelHeroe();

        List<String> nombres = new ArrayList<>();
        List<String> descripciones = new ArrayList<>();
        for (Habilidad habilidad : habilidades) {
            nombres.add(habilidad.getNombre() + " (" + this.modelo.getUsosRestantesDelHeroe(habilidad)
                    + "/" + habilidad.getUsosTotales() + ")");
            descripciones.add(habilidad.getDescripcion());
        }

        List<JButton> botones = this.vista.setHabilidades(nombres, descripciones);
        for (int i = 0; i < botones.size(); i++) {
            Habilidad habilidad = habilidades.get(i);
            botones.get(i).addActionListener(evento -> this.ejecutarHabilidad(habilidad));
        }
        this.vista.mostrarMenuHabilidades();
    }

    private void ejecutarHabilidad(Habilidad habilidad) {
        if (this.modelo.getUsosRestantesDelHeroe(habilidad) > 0) {
            ReproductorAudio.efectoDeHabilidad(habilidad.getNombre());
        }
        this.modelo.procesarHabilidad(habilidad);
        this.vista.mostrarMenuPrincipal();
    }

    // ---------- Submenú de inventario ----------
    private void abrirMenuInventario() {
        List<Consumible> consumibles = this.modelo.getConsumiblesDelHeroe();

        List<String> nombres = new ArrayList<>();
        List<String> descripciones = new ArrayList<>();
        for (Consumible consumible : consumibles) {
            nombres.add(consumible.getNombre() + " x" + consumible.getCantidad());
            descripciones.add(consumible.getDetalle());
        }

        List<JButton> botones = this.vista.setConsumibles(nombres, descripciones);
        for (int i = 0; i < botones.size(); i++) {
            Consumible consumible = consumibles.get(i);
            botones.get(i).addActionListener(evento -> this.ejecutarConsumible(consumible));
        }
        this.vista.mostrarMenuInventario();
    }

    private void ejecutarConsumible(Consumible consumible) {
        this.modelo.procesarConsumible(consumible);
        this.vista.mostrarMenuPrincipal(); // gasta el turno, como las demás acciones
    }

    // ---------- Cambios del modelo ----------
    private void alCambiarModelo(PropertyChangeEvent evento) {
        switch (evento.getPropertyName()) {
            case ModeloCombate.PROP_ESTADO:
                actualizarCombatientes();
                break;
            case ModeloCombate.PROP_MENSAJE:
                this.vista.agregarMensaje((String) evento.getNewValue());
                break;
            default:
                return;
        }
        this.vista.repaint();
    }

    private void actualizarCombatientes() {
        Heroe heroe = this.modelo.getHeroe();
        Enemigo enemigo = this.modelo.getEnemigo();
        if (heroe == null || enemigo == null) {
            return;
        }
        this.vista.setHeroe(heroe.getNombre(), heroe.getVida(), heroe.getVidaMaxima());
        this.vista.setEnemigo(enemigo.getNombre(), enemigo.getVida(), enemigo.getVidaMaxima());
    }
}