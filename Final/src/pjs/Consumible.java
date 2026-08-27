public class Consumible extends Objeto {
    private int idObjeto;
    private int cantidad;
    private int puntosRestauracion;
    private String efecto;

    public Consumible(int idObjeto, String nombre, double peso, int precio, String descripcion, int cantidad, int puntosRestauracion, String efecto) {
        super(nombre, peso, precio, descripcion);
        if (idObjeto <= 0) {
            throw new IllegalArgumentException("El ID del objeto debe ser mayor que cero.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (puntosRestauracion < 0) {
            throw new IllegalArgumentException("Los puntos de restauración no pueden ser negativos.");
        }
        if (efecto == null || efecto.isEmpty()) {
            throw new IllegalArgumentException("El efecto no puede ser nulo o vacío.");
        }
        this.idObjeto = idObjeto;
        this.cantidad = cantidad;
        this.puntosRestauracion = puntosRestauracion;
        this.efecto = efecto;
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
        System.out.println("Usando " + super.getNombre() + " en " + objetivo.getNombre());
        // lógica para aplicar efectos
    }

    public int getCantidad() {
        return cantidad;
    }
    
}
