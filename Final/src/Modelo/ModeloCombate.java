package Modelo;

import Modelo.combate.AtaqueBasico;
import Modelo.combate.ContextoCombate;
import Modelo.combate.EfectoCombate;
import Modelo.pjs.Enemigo;
import Modelo.pjs.Habilidad;
import Modelo.pjs.Heroe;
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
        this.contextoHeroe.enfrentarA(this.contextoEnemigo);

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
            this.contextoHeroe.buscarAlOponente();
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
            this.enemigo.jugarTurno(this.contextoEnemigo); 
            //cada enemigo decide como juega
        }
        notificarCambio();
        comprobarFin();
    }

    private void comprobarFin() {
        if (this.enemigo.estaMuerto()) {
            this.heroe.ganarExperiencia(this.enemigo.getExperienciaDada());
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
        // Polimorfismo: cada objeto decide si se ofrece como consumible; este modelo no pregunta tipos ni castea.
        for (Objeto objeto : this.heroe.getInventario()) {
            objeto.ofrecerComoConsumible(consumible -> {
                if (!consumible.debeDesecharse()) {
                    consumibles.add(consumible);
                }
            });
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

