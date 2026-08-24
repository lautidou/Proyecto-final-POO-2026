public abstract class Objeto {
    private String nombre;
    private double peso;
    private int precio;
    private String descripcion;

    public Objeto(String nombre, double peso, int precio, String descripcion) {
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

