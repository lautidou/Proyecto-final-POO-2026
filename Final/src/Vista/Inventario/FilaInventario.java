package Vista.Inventario;

public record FilaInventario(String nombre, String detalle, boolean equipable, boolean equipado) {

    public String textoAccion() {
        if (!equipable) {
            return "Usar";
        }
        return equipado ? "Desequipar" : "Equipar";
    }
}