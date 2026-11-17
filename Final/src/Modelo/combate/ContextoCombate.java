package Modelo.combate;

import Modelo.pjs.Dados;
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
    private ContextoCombate contextoOponente;         // el del otro combatiente (visibilidad, busqueda)

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

    // Une los dos contextos de una misma pelea para que cada uno pueda ver el estado del otro.
    public void enfrentarA(ContextoCombate otro) {
        this.contextoOponente = otro;
        otro.contextoOponente = this;
    }

    public ContextoCombate getContextoOponente() {
        return contextoOponente;
    }

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

    public void infligirDanio(int puntos) {
        int danio = Reglas.danioTrasDefensa(puntos, Reglas.defensa(this.oponente));
        this.oponente.recibirDanio(danio);

        int absorbido = puntos - danio;
        String texto = this.oponente.getNombre() + " recibe " + danio + " de daño";
        mensaje(absorbido > 0 ? texto + " (la armadura absorbe " + absorbido + ")." : texto + ".");

        this.estado.atacar(this); // hacer daño es atacar: delata a quien está escondido
    }

    public void curarse(int puntos) {
        int faltante = this.actor.getVidaMaxima() - this.actor.getVida();
        int curado = Math.max(0, Math.min(puntos, faltante));
        this.actor.curar(curado);
        mensaje(this.actor.getNombre() + " recupera " + curado + " PV.");
    }

    // El actor gasta su turno buscando al oponente escondido (tirada de d20 + Sabiduria).
    public void buscarAlOponente() {
        int tirada = Dados.d20.rolleo() + modSabiduria();
        mensaje(this.actor.getNombre() + " busca a " + this.oponente.getNombre() + " (tirada " + tirada + ").");
        this.contextoOponente.serBuscado(tirada);
    }

    //modificaciones de cada personaje
    public int modFuerza() {
        return Reglas.modificador(this.actor.getCaracteristicas().getFuerza());
    }

    public int modDestreza() {
        return Reglas.modificador(this.actor.getCaracteristicas().getDestreza());
    }

    public int modInteligencia() {
        return Reglas.modificador(this.actor.getCaracteristicas().getInteligencia());
    }

    public int modSabiduria() {
        return Reglas.modificador(this.actor.getCaracteristicas().getSabiduria());
    }

    // ---- Objetos ----
    public void usarConsumible(Consumible consumible) {
        int vidaAntes = this.actor.getVida();
        consumible.usar(this.actor); // el propio objeto sabe qué efecto tiene
        mensaje(this.actor.getNombre() + " usa " + consumible.getNombre() 
        + " y recupera " + (this.actor.getVida() - vidaAntes) + " PV.");
    }
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

