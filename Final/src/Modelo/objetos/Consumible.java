package Modelo.objetos;

import Modelo.pjs.Personaje;

public class Consumible extends Objeto {
    private int cantidad;
    private int puntosRestauracion;

    public Consumible(int idObjeto, String nombre, double peso, int precio, String descripcion, int cantidad, int puntosRestauracion) {
        super(idObjeto, nombre, peso, precio, descripcion);
        if (idObjeto <= 0) {
            throw new IllegalArgumentException("El ID del objeto debe ser mayor que cero.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (puntosRestauracion < 0) {
            throw new IllegalArgumentException("Los puntos de restauración no pueden ser negativos.");
        }
        this.cantidad = cantidad;
        this.puntosRestauracion = puntosRestauracion;
    }

    public void eliminarObjeto() {
        if (this.cantidad > 0) {
            this.cantidad--;
        } else {
            throw new IllegalStateException("No hay más consumibles para eliminar.");
        }
    }

    @Override
    public void usar(Personaje objetivo) {
            objetivo.curar(this.puntosRestauracion);
            eliminarObjeto();
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String getDetalle() {
        return super.getDetalle() + "\nCantidad: " + this.cantidad + "\nRestaura: " + this.puntosRestauracion + " PV";
    }
}
