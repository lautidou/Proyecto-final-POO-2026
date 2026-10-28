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
import Modelo.combate.ResultadoCombate;

public class ModeloCombate {

    public static final String PROP_ESTADO = "estado";
    public static final String PROP_MENSAJE = "mensaje";
    public static final String PROP_FIN = "fin";

    private boolean enCurso;
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

        this.enCurso = true;
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
        if (!this.enCurso) {
            return;
        }
        efecto.aplicar(this.contextoHeroe);
        cerrarTurno();
    }

    public void procesarBusqueda() {
        if (!this.enCurso) {
            return;
        }
        if (this.contextoEnemigo.esVisible()) {
            registrarMensaje(this.enemigo.getNombre() + " no está escondido.");
        } else {
            buscarA(this.contextoEnemigo);
        }
        cerrarTurno();
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
        if (!this.enCurso) {
            return;
        }
        this.contextoHeroe.esconderse();
        cerrarTurno();
    }

    private void cerrarTurno() {
        if (!this.enemigo.estaMuerto()) {
            turnoEnemigo();
        }
        notificarCambio();
        comprobarFin();
    }

    private void comprobarFin() {
        if (this.enemigo.estaMuerto()) {
            finalizar(ResultadoCombate.VICTORIA, this.enemigo.getNombre() + " ha sido derrotado.");
        } else if (this.heroe.estaMuerto()) {
            finalizar(ResultadoCombate.DERROTA, this.heroe.getNombre() + " ha caído en combate...");
        }
    }

    private void finalizar(ResultadoCombate resultado, String mensaje) {
        this.enCurso = false;
        registrarMensaje(mensaje);
        this.soporte.firePropertyChange(PROP_FIN, null, resultado);
    }

    // ---------- Turno del enemigo ----------
    private static final double VIDA_BAJA = 0.3;                  // con este % de vida o menos intenta esconderse
    private static final double PROB_ESCONDERSE_VIDA_BAJA = 0.4;
    private static final double VIDA_PARA_CURARSE = 0.5;          // con este % de vida o menos considera curarse
    private static final double PROB_CURARSE = 0.6;
    private static final double PROB_USAR_HABILIDAD = 0.5;        // si no, ataque básico

    private void turnoEnemigo() {
        if (this.enemigo.estaMuerto() || this.heroe.estaMuerto()) {
            return;
        }
        // 1) Si el héroe está escondido, el enemigo gasta su turno buscándolo.
        if (!this.contextoHeroe.esVisible()) {
            buscarA(this.contextoHeroe);
            return;
        }
        // 2) Si está herido y a la vista, a veces intenta esconderse (misma regla que el héroe).
        if (this.contextoEnemigo.esVisible() && vidaEnemigoAlMenos(VIDA_BAJA)
                && Math.random() < PROB_ESCONDERSE_VIDA_BAJA) {
            this.contextoEnemigo.esconderse();
            return;
        }
        // 3) Si está bastante herido y tiene una habilidad curativa con usos, a veces la usa.
        if (vidaEnemigoAlMenos(VIDA_PARA_CURARSE) && Math.random() < PROB_CURARSE) {
            Habilidad cura = elegirHabilidadDelEnemigo(true);
            if (cura != null) {
                cura.usar(this.contextoEnemigo);
                return;
            }
        }
        // 4) A veces usa una habilidad ofensiva con usos disponibles.
        if (Math.random() < PROB_USAR_HABILIDAD) {
            Habilidad ataque = elegirHabilidadDelEnemigo(false);
            if (ataque != null) {
                ataque.usar(this.contextoEnemigo);
                return;
            }
        }
        // 5) Ataque básico. Si estaba escondido, el daño lo delata (Escondido.atacar).
        registrarMensaje(this.enemigo.getNombre() + " ataca.");
        new AtaqueBasico().aplicar(this.contextoEnemigo);
    }

    private boolean vidaEnemigoAlMenos(double fraccion) {
        return this.enemigo.getVida() <= this.enemigo.getVidaMaxima() * fraccion;
    }

    private Habilidad elegirHabilidadDelEnemigo(boolean curativa) {
        List<Habilidad> candidatas = new ArrayList<>();
        for (Habilidad habilidad : this.enemigo.getHabilidades()) {
            if (habilidad.esCurativa() == curativa && this.contextoEnemigo.usosRestantes(habilidad) > 0) {
                candidatas.add(habilidad);
            }
        }
        if (candidatas.isEmpty()) {
            return null;
        }
        return candidatas.get((int) (Math.random() * candidatas.size()));
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