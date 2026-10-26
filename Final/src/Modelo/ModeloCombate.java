package Modelo;

import Modelo.combate.AtaqueBasico;
import Modelo.combate.ContextoCombate;
import Modelo.combate.EfectoCombate;
import Modelo.combate.Reglas;
import Modelo.pjs.Dados;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Habilidad;
import Modelo.pjs.Heroe;
import Modelo.pjs.Personaje;
import Modelo.objetos.Consumible;
import Modelo.objetos.Objeto;
import java.util.ArrayList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;

public class ModeloCombate {

    public static final String PROP_ESTADO = "estado";
    public static final String PROP_MENSAJE = "mensaje";

    private Heroe heroe;
    private Enemigo enemigo;
    private ContextoCombate contextoHeroe;    // el héroe dentro de esta pelea, con su visibilidad
    private ContextoCombate contextoEnemigo;  // el enemigo dentro de esta pelea, con su visibilidad
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
        // Contextos nuevos en cada pelea: las condiciones de la anterior no se arrastran.
        this.contextoHeroe = new ContextoCombate(heroe, enemigo, this::registrarMensaje);
        this.contextoEnemigo = new ContextoCombate(enemigo, heroe, this::registrarMensaje);
        // TODO: si el combate tiene más estado (turno actual, ronda...), reiniciarlo aquí.
        notificarCambio();
        registrarMensaje("¡" + heroe.getNombre() + " se enfrenta a " + enemigo.getNombre() + "!");
    }

    public void procesarAtaqueBasico() {
        ejecutarAccionDelHeroe(new AtaqueBasico());
    }

    public void procesarHabilidad(Habilidad habilidad) {
        if (habilidad == null) {
            throw new IllegalArgumentException("La habilidad no puede ser nula.");
        }
        if (!this.heroe.getHabilidades().contains(habilidad)) {
            throw new IllegalArgumentException("El héroe no conoce la habilidad " + habilidad.getNombre() + ".");
        }
        ejecutarAccionDelHeroe(habilidad::usar);
    }

    private void ejecutarAccionDelHeroe(EfectoCombate efecto) {
        efecto.aplicar(this.contextoHeroe); // si hace daño, el contexto avisa a su estado (atacar delata)
        turnoEnemigo();
        notificarCambio();
    }

    public void procesarBusqueda() {
        if (this.contextoEnemigo.esVisible()) {
            registrarMensaje(this.enemigo.getNombre() + " no está escondido.");
        } else {
            buscarA(this.contextoEnemigo);
        }
        turnoEnemigo();
        notificarCambio();
    }

    public void procesarConsumible(Consumible consumible) {
        if (consumible == null) {
            throw new IllegalArgumentException("El consumible no puede ser nulo.");
        }
        if (!getConsumiblesDelHeroe().contains(consumible)) {
            throw new IllegalArgumentException("El héroe no tiene " + consumible.getNombre() + " disponible.");
        }
        ejecutarAccionDelHeroe(contexto -> contexto.usarConsumible(consumible));
    }

    public void procesarEsconderse() {
        this.contextoHeroe.esconderse();
        turnoEnemigo();
        notificarCambio();
    }

    private void turnoEnemigo() {
        if (!this.contextoHeroe.esVisible()) {
            buscarA(this.contextoHeroe);
            return;
        }
        // TODO: decisión del enemigo (atacar, esconderse...). Su visibilidad se maneja igual que la del héroe:
    }

    private void buscarA(ContextoCombate contextoDelEscondido) {
        Personaje buscador = contextoDelEscondido.getOponente();
        int tirada = Dados.d20.rolleo() + Reglas.modificador(buscador.getCaracteristicas().getSabiduria());
        registrarMensaje(buscador.getNombre() + " busca a " + contextoDelEscondido.getActor().getNombre()
                + " (tirada " + tirada + ").");
        contextoDelEscondido.serBuscado(tirada);
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

    public List<Consumible> getConsumiblesDelHeroe() {
        List<Consumible> consumibles = new ArrayList<>();
        if (this.heroe == null) {
            return consumibles;
        }
        for (Objeto objeto : this.heroe.getInventario()) {   // <- ajustar si se llama distinto
            if (objeto instanceof Consumible && ((Consumible) objeto).getCantidad() > 0) {
                consumibles.add((Consumible) objeto);
            }
        }
        return consumibles;
    }

    public List<Habilidad> getHabilidadesDelHeroe() {
        return this.heroe == null ? Collections.<Habilidad>emptyList() : this.heroe.getHabilidades();
    }

    public int getUsosRestantesDelHeroe(Habilidad habilidad) {
        return this.contextoHeroe == null ? habilidad.getUsosTotales() : this.contextoHeroe.usosRestantes(habilidad);
    }

    public Heroe getHeroe() {
        return heroe;
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }
}