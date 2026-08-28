package pjs;

public class Consumible extends Objeto {
    private int cantidad;
    private int puntosRestauracion;

    public Consumible(int idObjeto, String nombre, double peso, int precio, String descripcion, int cantidad, int puntosRestauracion, String efecto) {
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
            System.out.println("Se ha usado un " + super.getNombre() + ". Quedan " + this.cantidad + " unidades.");
        } else {
            System.out.println("No quedan unidades de " + super.getNombre() + " para usar.");
        }
    }

    @Override
    public void usar(Personaje objetivo) {
        System.out.println("Usando " + super.getNombre() + ", " + objetivo.getNombre() + " recupera " + this.puntosRestauracion + " puntos de vida.");
            objetivo.curar(this.puntosRestauracion);
            eliminarObjeto();
    }

    public int getCantidad() {
        return cantidad;
    }
}
