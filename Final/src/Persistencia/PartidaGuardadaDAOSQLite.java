package Persistencia;

import Modelo.mapa.Posicion;
import Modelo.partida.PartidaGuardada;
import Modelo.partida.PartidaGuardadaDAO;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Clases;
import Modelo.pjs.Raza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PartidaGuardadaDAOSQLite implements PartidaGuardadaDAO {

    private final Connection conexion;

    public PartidaGuardadaDAOSQLite(Connection conexion) throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexion es obligatoria");
        }
        this.conexion = conexion;
        try (Statement sentencia = conexion.createStatement()) {
            sentencia.execute("CREATE TABLE IF NOT EXISTS partida_guardada ("
                    + "id INTEGER PRIMARY KEY CHECK (id = 1), "
                    + "nombre TEXT NOT NULL, raza TEXT NOT NULL, clase TEXT NOT NULL, "
                    + "nivel INTEGER NOT NULL, experiencia INTEGER NOT NULL, vida INTEGER NOT NULL, "
                    + "fuerza INTEGER NOT NULL, sabiduria INTEGER NOT NULL, destreza INTEGER NOT NULL, "
                    + "inteligencia INTEGER NOT NULL, constitucion INTEGER NOT NULL, carisma INTEGER NOT NULL, "
                    + "escenario TEXT NOT NULL, pos_x INTEGER NOT NULL, pos_y INTEGER NOT NULL)");
        }
    }

    @Override
    public boolean guardar(PartidaGuardada partida) {
        if (partida == null) {
            return false;
        }
        String sql = "INSERT OR REPLACE INTO partida_guardada (id, nombre, raza, clase, nivel, experiencia, vida, "
                + "fuerza, sabiduria, destreza, inteligencia, constitucion, carisma, escenario, pos_x, pos_y) "
                + "VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = this.conexion.prepareStatement(sql)) {
            Caracteristicas c = partida.getCaracteristicas();
            sentencia.setString(1, partida.getNombre());
            sentencia.setString(2, partida.getRaza().name());
            sentencia.setString(3, partida.getClase().name());
            sentencia.setInt(4, partida.getNivel());
            sentencia.setInt(5, partida.getExperiencia());
            sentencia.setInt(6, partida.getVida());
            sentencia.setInt(7, c.getFuerza());
            sentencia.setInt(8, c.getSabiduria());
            sentencia.setInt(9, c.getDestreza());
            sentencia.setInt(10, c.getInteligencia());
            sentencia.setInt(11, c.getConstitucion());
            sentencia.setInt(12, c.getCarisma());
            sentencia.setString(13, partida.getEscenario());
            sentencia.setInt(14, partida.getPosicion().getX());
            sentencia.setInt(15, partida.getPosicion().getY());
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public PartidaGuardada cargar() {
        String sql = "SELECT nombre, raza, clase, nivel, experiencia, vida, fuerza, sabiduria, destreza, "
                + "inteligencia, constitucion, carisma, escenario, pos_x, pos_y FROM partida_guardada WHERE id = 1";
        try (PreparedStatement sentencia = this.conexion.prepareStatement(sql)) {
            try (ResultSet filas = sentencia.executeQuery()) {
                if (!filas.next()) {
                    return null;
                }
                Caracteristicas caracteristicas = new Caracteristicas(filas.getInt("fuerza"),
                        filas.getInt("sabiduria"), filas.getInt("destreza"), filas.getInt("inteligencia"),
                        filas.getInt("constitucion"), filas.getInt("carisma"));
                return new PartidaGuardada(filas.getString("nombre"),
                        Raza.valueOf(filas.getString("raza")), Clases.valueOf(filas.getString("clase")),
                        filas.getInt("nivel"), filas.getInt("experiencia"), filas.getInt("vida"),
                        caracteristicas, filas.getString("escenario"),
                        new Posicion(filas.getInt("pos_x"), filas.getInt("pos_y")));
            }
        } catch (SQLException | IllegalArgumentException e) {
            return null; // fila ilegible o con datos inválidos: se trata como "no hay partida"
        }
    }
}