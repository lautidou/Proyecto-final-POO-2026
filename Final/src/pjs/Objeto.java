package pjs;
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
        this.nombre = nombre;
        this.peso = peso;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void usar(Personaje objetivo); 
}

