package Modelo.partida;

public record ObjetoGuardado(
        String tipo,
        int idObjeto,
        String nombre,
        double peso,
        int precio,
        String descripcion,
        int cantidad,
        boolean equipado,
        String dado,         
        int defensa,          
        int puntosRestauracion 
) {
}
