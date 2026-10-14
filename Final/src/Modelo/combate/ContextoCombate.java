package Modelo.combate;

import Modelo.pjs.Personaje;

import java.util.function.Consumer;

public final class ContextoCombate {

    private final Personaje actor;
    private final Personaje oponente;
    private final Consumer<String> salidaMensajes;
    private boolean huboAtaque = false;

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

    public void mensaje(String texto) {
        this.salidaMensajes.accept(texto);
    }

    // El actor daña a su oponente. Todo el daño del combate pasa por aquí.
    public void infligirDanio(int puntos) {
        this.huboAtaque = true;
        this.oponente.recibirDanio(puntos);
        mensaje(this.oponente.getNombre() + " recibe " + puntos + " de daño.");
    }

    // Si el actor hizo daño con esta acción (es lo que cuenta como atacar).
    public boolean huboAtaque() {
        return this.huboAtaque;
    }

    // El actor recupera vida, sin pasar de su máximo.
    public void curarse(int puntos) {
        int faltante = this.actor.getVidaMaxima() - this.actor.getVida();
        int curado = Math.max(0, Math.min(puntos, faltante));
        this.actor.curar(curado);
        mensaje(this.actor.getNombre() + " recupera " + curado + " PV.");
    }
}