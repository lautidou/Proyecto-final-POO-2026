package pjs;
public class Habilidad {
    private int idHabilidad;
    private String nombre;
    private String descripcion;

    public Habilidad(int idHabilidad, String nombre, String descripcion) {
        if (idHabilidad <= 0) {
            throw new IllegalArgumentException("El ID de la habilidad debe ser mayor que cero.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede ser nulo o vacío.");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción de la habilidad no puede ser nula o vacía.");
        }
        this.idHabilidad = idHabilidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
