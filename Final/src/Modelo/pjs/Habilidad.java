package Modelo.pjs;

import Modelo.combate.ContextoCombate;
import Modelo.combate.EfectoCombate;

public class Habilidad {
    private final int idHabilidad;
    private final String nombre;
    private final String descripcion;
    private final EfectoCombate efecto;
    private final int usosTotales;
    private int usosRestantes;

    public Habilidad(int idHabilidad, String nombre, String descripcion, EfectoCombate efecto, int usosTotales) {
        if (idHabilidad <= 0) {
            throw new IllegalArgumentException("El ID de la habilidad debe ser mayor que cero.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede ser nulo o vacío.");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción de la habilidad no puede ser nula o vacía.");
        }
        if (efecto == null) {
            throw new IllegalArgumentException("La habilidad necesita un efecto.");
        }
        this.idHabilidad = idHabilidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.efecto = efecto;
        this.usosTotales = usosTotales;
        this.usosRestantes = usosTotales;
    }

    public void usar(ContextoCombate contexto) {
        if (usosRestantes <= 0) {
            contexto.mensaje(contexto.getActor().getNombre() + " intenta usar " + nombre + ", pero ya no tiene usos disponibles.");
            return;
        }
        contexto.mensaje(contexto.getActor().getNombre() + " usa " + nombre + ".");
        efecto.aplicar(contexto);
        usosRestantes--;
    }

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getUsosTotales() {
        return usosTotales;
    }

    public int getUsosRestantes() {
        return usosRestantes;
    }
}