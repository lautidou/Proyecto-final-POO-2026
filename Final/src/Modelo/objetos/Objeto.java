package Modelo.objetos;

import Modelo.partida.ObjetoGuardado;
import Modelo.pjs.Personaje;

public abstract class Objeto {
    private String nombre;
    private int idObjeto;
    private double peso;
    private int precio;
    private String descripcion;

    public Objeto(int idObjeto, String nombre, double peso, int precio, String descripcion) {
        if (idObjeto <= 0) {
            throw new IllegalArgumentException("El ID del objeto debe ser mayor que cero.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El objeto debe tener un nombre.");
        }
        if (peso <= 0) {
            throw new IllegalArgumentException("El objeto debe tener peso.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El objeto no puede tener precio negativo.");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("El objeto debe tener descripcion.");
        }
        this.idObjeto = idObjeto;
        this.nombre = nombre;
        this.peso = peso;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDetalle() {
        return descripcion;
    }
    
    public boolean estaEquipadoPor(Personaje p) { 
        return false; 
    }
    
    public boolean debeDesecharse() { 
        return false; 
    }

    public int getIdObjeto() {
        return idObjeto; 
    }

    public int getCantidad() {
        return 1; 
    }

    public double getPeso() {
        return peso;
    }

    public int getPrecio() {
        return precio;
    }

    public abstract void reproducirSonidoUso();

    public abstract boolean esUtilizableEnCombate();

    public abstract void usar(Personaje objetivo);

    public abstract ObjetoGuardado aGuardado(boolean equipado);

    public abstract String getTextoAccion(Personaje p);
}