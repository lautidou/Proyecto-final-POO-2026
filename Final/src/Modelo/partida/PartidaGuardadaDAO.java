package Modelo.partida;

public interface PartidaGuardadaDAO {
    boolean guardar(PartidaGuardada partida);
    PartidaGuardada cargar();
}