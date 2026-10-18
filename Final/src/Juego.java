import Controlador.ControladorPantallas;
import Modelo.partida.PartidaGuardadaDAO;
import Persistencia.Conexion;
import Persistencia.PartidaGuardadaDAOMemoria;
import Persistencia.PartidaGuardadaDAOSQLite;
import Vista.Ventana;

import java.sql.SQLException;

public class Juego {

    public static void main(String[] args) {
        PartidaGuardadaDAO partidas;
        try {
            partidas = new PartidaGuardadaDAOSQLite(Conexion.getInstancia().get());
        } catch (SQLException e) {
            System.err.println("No se pudo abrir la base de datos (" + e.getMessage() + "). Se juega sin guardado.");
            partidas = new PartidaGuardadaDAOMemoria();
        }
        new ControladorPantallas(Ventana.getInstancia(), partidas).iniciar();
    }
}