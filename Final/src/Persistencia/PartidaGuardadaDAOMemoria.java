package Persistencia;

import Modelo.partida.PartidaGuardada;
import Modelo.partida.PartidaGuardadaDAO;

public class PartidaGuardadaDAOMemoria implements PartidaGuardadaDAO {

    private PartidaGuardada partida;

    @Override
    public boolean guardar(PartidaGuardada partida) {
        if (partida == null) {
            return false;
        }
        this.partida = partida;
        return true;
    }

    @Override
    public PartidaGuardada cargar() {
        return this.partida;
    }
}