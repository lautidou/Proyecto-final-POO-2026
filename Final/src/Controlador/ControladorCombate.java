package Controlador;

import Audio.ReproductorAudio;
import Modelo.ModeloCombate;
import Modelo.objetos.Objeto;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Heroe;
import Recursos.Rutas;
import Modelo.pjs.habilidades.Habilidad;
import Modelo.combate.ResultadoCombate;
import Modelo.ModeloMapa;
import Vista.Ventana;
import Vista.VistaCombate;
import Vista.VistaMapa;
import Vista.VistaMuerte;
import Vista.VistaVictoria;

import javax.swing.Timer;
import javax.swing.JButton;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class ControladorCombate {

    private final VistaCombate vista;
    private final ModeloCombate modelo;
    private static final int PAUSA_FINAL_MS = 1500; // para alcanzar a leer el último mensaje
    private final ModeloMapa modeloMapa;

    public ControladorCombate(VistaCombate vista, ModeloCombate modelo, ModeloMapa modeloMapa) {
        if (vista == null || modelo == null || modeloMapa == null) {
            throw new IllegalArgumentException("La vista, el modelo y el modelo del mapa son obligatorios");
        }
        this.vista = vista;
        this.modelo = modelo;
        this.modeloMapa = modeloMapa;

        // Menú principal
        this.vista.getBotonCombate().addActionListener(evento -> this.ejecutarCombate());
        this.vista.getBotonBusqueda().addActionListener(evento -> this.ejecutarBusqueda());
        this.vista.getBotonInventario().addActionListener(evento -> this.abrirMenuInventario());
        this.vista.getBotonEsconderse().addActionListener(evento -> this.ejecutarEsconderse());

        // Submenú de combate
        this.vista.getBotonBasico().addActionListener(evento -> this.ejecutarBasico());
        this.vista.getBotonHabilidades().addActionListener(evento -> this.abrirMenuHabilidades());
        this.vista.getBotonVolverCombate().addActionListener(evento -> this.vista.mostrarMenuPrincipal());

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
        List<Objeto> objetos = this.modelo.getObjetosCombateDelHeroe();

        List<String> nombres = new ArrayList<>();
        List<String> descripciones = new ArrayList<>();
        for (Objeto objeto : objetos) {
            String sufijo = objeto.getCantidad() > 1 ? " x" + objeto.getCantidad() : "";
            nombres.add(objeto.getNombre() + sufijo);
            descripciones.add(objeto.getDetalle());
        }

        List<JButton> botones = this.vista.setConsumibles(nombres, descripciones);
        for (int i = 0; i < botones.size(); i++) {
            Objeto objeto = objetos.get(i);
            botones.get(i).addActionListener(evento -> this.ejecutarObjeto(objeto));
        }
        this.vista.mostrarMenuInventario();
    }

    private void ejecutarObjeto(Objeto objeto) {
        this.modelo.procesarObjeto(objeto);
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
            case ModeloCombate.PROP_FIN:
                programarSalida((ResultadoCombate) evento.getNewValue());
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
        this.vista.setHeroe(heroe.getNombre(), Rutas.spritePelea(heroe.getClass().getSimpleName()), heroe.getVida(), heroe.getVidaMaxima());
        this.vista.setEnemigo(enemigo.getNombre(), Rutas.spritePelea(enemigo.getClass().getSimpleName()), enemigo.getVida(), enemigo.getVidaMaxima());
    }

    // ---------- Fin del combate ----------
    private void programarSalida(ResultadoCombate resultado) {
        this.vista.mostrarMenuPrincipal();
        Timer pausa = new Timer(PAUSA_FINAL_MS, evento -> {
            Enemigo enemigo = this.modelo.getEnemigo();
            switch (resultado) {
                case VICTORIA:
                    this.modeloMapa.eliminarEnemigo(enemigo);
                    Ventana.getInstancia().mostrarPanel(VistaMapa.NOMBRE);
                    break;
                case VICTORIA_FINAL:
                    this.modeloMapa.eliminarEnemigo(enemigo);
                    ReproductorAudio.detenerMusica();
                    Ventana.getInstancia().mostrarPanel(VistaVictoria.NOMBRE);
                    break;
                default: // DERROTA
                    ReproductorAudio.detenerMusica();
                    Ventana.getInstancia().mostrarPanel(VistaMuerte.NOMBRE);
                    break;
            }
        });
        pausa.setRepeats(false);
        pausa.start();
    }
}