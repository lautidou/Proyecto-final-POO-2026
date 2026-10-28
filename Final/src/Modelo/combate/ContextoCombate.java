package Modelo.combate;

import Modelo.pjs.Habilidad;
import Modelo.pjs.Personaje;
import Modelo.pjs.escondite.EstadoVisibilidad;
import Modelo.pjs.escondite.Visible;
import Modelo.objetos.Consumible;

import java.util.function.Consumer;

public final class ContextoCombate {

    private final Personaje actor;
    private final Personaje oponente;
    private final Consumer<String> salidaMensajes;
    private EstadoVisibilidad estado = new Visible(); // todo combatiente empieza a la vista

    public ContextoCombate(Personaje actor, Personaje oponente, Consumer<String> salidaMensajes) {
        this.actor = actor;
        this.oponente = oponente;
        this.salidaMensajes = salidaMensajes;
    }

    public Personaje getActor() {
        return actor;
    }

    public Personaje getOponente() {
        return oponente;
    }

    // ---- Operaciones que dependen del estado: se delegan en él ----
    public void esconderse() {
        this.estado.esconderse(this);
    }

    public void serBuscado(int tiradaBusqueda) {
        this.estado.serBuscado(this, tiradaBusqueda);
    }

    public boolean esVisible() {
        return this.estado.esVisible();
    }

    public void cambiarEstado(EstadoVisibilidad nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        }
        this.estado = nuevo;
    }

    // ---- Lo que pueden hacer los efectos de las acciones ----
    public void infligirDanio(int puntos) {
        this.oponente.recibirDanio(puntos);
        mensaje(this.oponente.getNombre() + " recibe " + puntos + " de daño.");
        this.estado.atacar(this); // hacer daño es atacar: delata a quien está escondido
    }

    public void curarse(int puntos) {
        int faltante = this.actor.getVidaMaxima() - this.actor.getVida();
        int curado = Math.max(0, Math.min(puntos, faltante));
        this.actor.curar(curado);
        mensaje(this.actor.getNombre() + " recupera " + curado + " PV.");
    }

    // ---- Objetos ----
    public void usarConsumible(Consumible consumible) {
        int vidaAntes = this.actor.getVida();
        consumible.usar(this.actor); // el propio objeto sabe qué efecto tiene
        mensaje(this.actor.getNombre() + " usa " + consumible.getNombre() 
        + " y recupera " + (this.actor.getVida() - vidaAntes) + " PV.");
    }

    // ---- Usos de habilidades (son del personaje y persisten entre peleas) ----
    public int usosRestantes(Habilidad habilidad) {
        return this.actor.getUsosRestantes(habilidad);
    }

    public void registrarUso(Habilidad habilidad) {
        this.actor.registrarUsoHabilidad(habilidad);
    }

    public void mensaje(String texto) {
        this.salidaMensajes.accept(texto);
    }
}