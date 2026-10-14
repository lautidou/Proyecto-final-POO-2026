package Modelo;

import Modelo.combate.AtaqueBasico;
import Modelo.combate.ContextoCombate;
import Modelo.combate.EfectoCombate;
import Modelo.pjs.escondite.EstadoVisibilidad;
import Modelo.combate.Reglas;
import Modelo.pjs.escondite.Visible;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Habilidad;
import Modelo.pjs.Heroe;
import Modelo.pjs.Personaje;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;

public class ModeloCombate {

    public static final String PROP_ESTADO = "estado";
    public static final String PROP_MENSAJE = "mensaje";

    private Heroe heroe;
    private Enemigo enemigo;
    private EstadoVisibilidad visibilidadHeroe = new Visible();
    private EstadoVisibilidad visibilidadEnemigo = new Visible();
    private final PropertyChangeSupport soporte = new PropertyChangeSupport(this);

    public void iniciarCombate(Heroe heroe, Enemigo enemigo) {
        if (heroe == null) {
            throw new IllegalArgumentException("El héroe no puede ser nulo.");
        }
        if (enemigo == null) {
            throw new IllegalArgumentException("El enemigo no puede ser nulo.");
        }
        this.heroe = heroe;
        this.enemigo = enemigo;
        this.visibilidadHeroe = new Visible();   // las condiciones de la pelea anterior no se arrastran
        this.visibilidadEnemigo = new Visible();
        // TODO: si el combate tiene más estado (turno actual, ronda...), reiniciarlo aquí.
        notificarCambio();
        registrarMensaje("¡" + heroe.getNombre() + " se enfrenta a " + enemigo.getNombre() + "!");
    }

    /** El héroe ataca con su golpe básico. */
    public void procesarAtaqueBasico() {
        ejecutarAccionDelHeroe(new AtaqueBasico());
    }

    /** El héroe usa una de las habilidades que conoce. */
    public void procesarHabilidad(Habilidad habilidad) {
        if (habilidad == null) {
            throw new IllegalArgumentException("La habilidad no puede ser nula.");
        }
        if (!this.heroe.getHabilidades().contains(habilidad)) {
            throw new IllegalArgumentException("El héroe no conoce la habilidad " + habilidad.getNombre() + ".");
        }
        ejecutarAccionDelHeroe(habilidad::usar);
    }

    /** Resuelve una acción del héroe: aplica su efecto, actualiza su visibilidad y da paso al turno enemigo. */
    private void ejecutarAccionDelHeroe(EfectoCombate efecto) {
        ContextoCombate contexto = contextoHeroe();
        efecto.aplicar(contexto);
        if (contexto.huboAtaque()) {
            this.visibilidadHeroe = this.visibilidadHeroe.alAtacar(contexto); // atacar delata al héroe
        }
        turnoEnemigo();
        notificarCambio();
    }

    public void procesarBusqueda() {
        if (this.visibilidadEnemigo.esVisible()) {
            registrarMensaje(this.enemigo.getNombre() + " no está escondido.");
        } else {
            this.visibilidadEnemigo = buscarA(this.visibilidadEnemigo, contextoEnemigo());
        }
        turnoEnemigo();
        notificarCambio();
    }

    public void procesarInventario() {
        // TODO
    }

    public void procesarEsconderse() {
        this.visibilidadHeroe = this.visibilidadHeroe.alEsconderse(contextoHeroe());
        turnoEnemigo();
        notificarCambio();
    }

    private void turnoEnemigo() {
        if (!this.visibilidadHeroe.esVisible()) {
            this.visibilidadHeroe = buscarA(this.visibilidadHeroe, contextoHeroe());
            return;
        }
        // TODO: decisión del enemigo (atacar, esconderse...). El State del enemigo se usa igual:
        //   this.visibilidadEnemigo = this.visibilidadEnemigo.alEsconderse(contextoEnemigo());
        //   this.visibilidadEnemigo = this.visibilidadEnemigo.alAtacar(contextoEnemigo());
    }

    private EstadoVisibilidad buscarA(EstadoVisibilidad estadoEscondido, ContextoCombate contextoDelEscondido) {
        Personaje buscador = contextoDelEscondido.getOponente();
        int tirada = Dados.d20.rolleo() + Reglas.modificador(buscador.getCaracteristicas().getSabiduria());
        registrarMensaje(buscador.getNombre() + " busca a " + contextoDelEscondido.getActor().getNombre()
                + " (tirada " + tirada + ").");
        return estadoEscondido.alSerBuscado(contextoDelEscondido, tirada);
    }

    private ContextoCombate contextoHeroe() {
        return new ContextoCombate(this.heroe, this.enemigo, this::registrarMensaje);
    }

    private ContextoCombate contextoEnemigo() {
        return new ContextoCombate(this.enemigo, this.heroe, this::registrarMensaje);
    }

    public void addPropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.addPropertyChangeListener(oyente);
    }

    public void removePropertyChangeListener(PropertyChangeListener oyente) {
        this.soporte.removePropertyChangeListener(oyente);
    }

    private void notificarCambio() {
        this.soporte.firePropertyChange(PROP_ESTADO, null, this);
    }

    private void registrarMensaje(String mensaje) {
        this.soporte.firePropertyChange(PROP_MENSAJE, null, mensaje);
    }

    /** Habilidades que el héroe conoce (vacía si todavía no hay pelea). */
    public List<Habilidad> getHabilidadesDelHeroe() {
        return this.heroe == null ? Collections.<Habilidad>emptyList() : this.heroe.getHabilidades();
    }

    public Heroe getHeroe() {
        return heroe;
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }
}