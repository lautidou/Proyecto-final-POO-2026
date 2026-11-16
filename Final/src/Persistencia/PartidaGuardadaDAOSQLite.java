package Persistencia;

import Modelo.mapa.Posicion;
import Modelo.partida.EnemigoDerrotado;
import Modelo.partida.ObjetoGuardado;
import Modelo.partida.PartidaGuardada;
import Modelo.partida.PartidaGuardadaDAO;
import Modelo.pjs.Caracteristicas;
import Modelo.pjs.Raza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            sentencia.execute("CREATE TABLE IF NOT EXISTS partida_objeto ("
                + "tipo TEXT NOT NULL, id_objeto INTEGER NOT NULL, nombre TEXT NOT NULL, "
                + "peso REAL NOT NULL, precio INTEGER NOT NULL, descripcion TEXT NOT NULL, "
                + "cantidad INTEGER NOT NULL, equipado INTEGER NOT NULL, "
                + "dado TEXT, defensa INTEGER NOT NULL, puntos_restauracion INTEGER NOT NULL)");
            sentencia.execute("CREATE TABLE IF NOT EXISTS partida_habilidad_uso (id_habilidad INTEGER PRIMARY KEY, usos_gastados INTEGER)");
            sentencia.execute("CREATE TABLE IF NOT EXISTS partida_enemigo_derrotado (escenario TEXT NOT NULL, pos_x INTEGER, pos_y INTEGER)");
        }
    }
    

    @Override
    public boolean guardar(PartidaGuardada partida) {
        if (partida == null) return false;
        boolean autoCommit = true;
        try {
            autoCommit = conexion.getAutoCommit();
            conexion.setAutoCommit(false);
        
            guardarDatosBasicos(partida);
            reemplazarObjetos(partida);
            reemplazarUsos(partida);
            reemplazarDerrotados(partida);
        
            conexion.commit();
            return true;
        } catch (SQLException e) {
            try { conexion.rollback(); } catch (SQLException ignorado) { }
            System.err.println("No se pudo guardar la partida: " + e.getMessage());
            return false;
        } finally {
            try { conexion.setAutoCommit(autoCommit); } catch (SQLException ignorado) { }
        }
    }

    private void guardarDatosBasicos(PartidaGuardada partida) throws SQLException {
        String sql = "INSERT OR REPLACE INTO partida_guardada (id, nombre, raza, clase, nivel, experiencia, vida, "
            + "fuerza, sabiduria, destreza, inteligencia, constitucion, carisma, escenario, pos_x, pos_y) "
            + "VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = this.conexion.prepareStatement(sql)) {
            Caracteristicas c = partida.getCaracteristicas();
            sentencia.setString(1, partida.getNombre());
            sentencia.setString(2, partida.getRaza().name());
            sentencia.setString(3, partida.getClase());
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
        sentencia.executeUpdate(); 
        }
    }

    private void reemplazarObjetos(PartidaGuardada p) throws SQLException {
        try (Statement s = conexion.createStatement()) {
            s.executeUpdate("DELETE FROM partida_objeto");
        }
        String sql = "INSERT INTO partida_objeto (tipo, id_objeto, nombre, peso, precio, descripcion, "
            + "cantidad, equipado, dado, defensa, puntos_restauracion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (ObjetoGuardado o : p.getInventario()) {
                ps.setString(1, o.tipo());
                ps.setInt(2, o.idObjeto());
                ps.setString(3, o.nombre());
                ps.setDouble(4, o.peso());
                ps.setInt(5, o.precio());
                ps.setString(6, o.descripcion());
                ps.setInt(7, o.cantidad());
                ps.setInt(8, o.equipado() ? 1 : 0);
                ps.setString(9, o.dado());
                ps.setInt(10, o.defensa());
                ps.setInt(11, o.puntosRestauracion());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void reemplazarUsos(PartidaGuardada p) throws SQLException {
        try (Statement s = conexion.createStatement()) {
            s.executeUpdate("DELETE FROM partida_habilidad_uso");
        }
        Map<Integer, Integer> usos = p.getUsosHabilidades();
        if (usos == null || usos.isEmpty()) return;

        String sql = "INSERT INTO partida_habilidad_uso (id_habilidad, usos_gastados) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (Map.Entry<Integer, Integer> entry : usos.entrySet()) {
                ps.setInt(1, entry.getKey());   // id_habilidad
                ps.setInt(2, entry.getValue()); // usos_gastados
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void reemplazarDerrotados(PartidaGuardada p) throws SQLException {
        try (Statement s = conexion.createStatement()) {
            s.executeUpdate("DELETE FROM partida_enemigo_derrotado");
        }
        List<EnemigoDerrotado> derrotados = p.getEnemigosDerrotados();
        if (derrotados == null || derrotados.isEmpty()) return;

        String sql = "INSERT INTO partida_enemigo_derrotado (escenario, pos_x, pos_y) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (EnemigoDerrotado e : derrotados) {
                ps.setString(1, e.idMapa());
                ps.setInt(2, e.posicion().getX());
                ps.setInt(3, e.posicion().getY());
                ps.addBatch();
            }
            ps.executeBatch();
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
            // 1. Instanciar colecciones vacías
                List<ObjetoGuardado> inventario = new ArrayList<>();
                Map<Integer, Integer> usosHabilidades = new HashMap<>();
                List<EnemigoDerrotado> enemigosDerrotados = new ArrayList<>();
            // 2. Cargar el inventario
                try (Statement sObjetos = conexion.createStatement();
                    ResultSet rsObjetos = sObjetos.executeQuery("SELECT tipo, id_objeto, nombre, peso, precio, descripcion, cantidad, equipado, dado, defensa, puntos_restauracion FROM partida_objeto")) {
                    while (rsObjetos.next()) {
                        inventario.add(new ObjetoGuardado(
                        rsObjetos.getString("tipo"),
                        rsObjetos.getInt("id_objeto"),
                        rsObjetos.getString("nombre"),
                        rsObjetos.getDouble("peso"),
                        rsObjetos.getInt("precio"),
                        rsObjetos.getString("descripcion"),
                        rsObjetos.getInt("cantidad"),
                        rsObjetos.getInt("equipado") == 1,
                        rsObjetos.getString("dado"),
                        rsObjetos.getInt("defensa"),
                        rsObjetos.getInt("puntos_restauracion")
                        ));
                    }
                }
            // 3. Cargar los usos de habilidades
            try (Statement sUsos = conexion.createStatement(); 
                 ResultSet rsUsos = sUsos.executeQuery("SELECT id_habilidad, usos_gastados FROM partida_habilidad_uso")) {
                while (rsUsos.next()) {
                    usosHabilidades.put(rsUsos.getInt("id_habilidad"), rsUsos.getInt("usos_gastados"));
                }
            }
            // 4. Cargar los enemigos derrotados
            try (Statement sEnemigos = conexion.createStatement(); 
                 ResultSet rsEnemigos = sEnemigos.executeQuery("SELECT escenario, pos_x, pos_y FROM partida_enemigo_derrotado")) {
                while (rsEnemigos.next()) {
                    enemigosDerrotados.add(new EnemigoDerrotado(
                            rsEnemigos.getString("escenario"), 
                            new Posicion(rsEnemigos.getInt("pos_x"), rsEnemigos.getInt("pos_y"))
                    ));
                }
            }
            // 5. Retornar la partida con las tres nuevas colecciones
                return new PartidaGuardada(
                filas.getString("nombre"), Raza.valueOf(filas.getString("raza")), filas.getString("clase"),
                filas.getInt("nivel"), filas.getInt("experiencia"), filas.getInt("vida"),
                caracteristicas, filas.getString("escenario"), new Posicion(filas.getInt("pos_x"), filas.getInt("pos_y")),
                inventario, usosHabilidades, enemigosDerrotados);
            }
        } catch (SQLException | IllegalArgumentException e) {
            return null;
        }
    }
}