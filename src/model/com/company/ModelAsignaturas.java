package model.com.company;

import Connecion.ConectionBD;
import java.sql.*;

public class ModelAsignaturas {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public ModelAsignaturas() {
        this.con = ConectionBD.getConn();
    }

    public ResultSet listar() throws SQLException {
        Statement st = con.createStatement();
        return st.executeQuery("SELECT * FROM asignatura");
    }

    public boolean insertar(int id, String nombre, int creditos, String tipo, String curso, String cuatrimestre, int idProfesor, int idGrado) throws SQLException {
        ps = con.prepareStatement("INSERT INTO asignatura (id, nombre, creditos, tipo, curso, cuatrimestre, id_profesor, id_grado) VALUES (?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, nombre);
        ps.setInt(3, creditos);
        ps.setString(4, tipo);
        ps.setString(5, curso);
        ps.setString(6, cuatrimestre);
        ps.setInt(7, idProfesor);
        ps.setInt(8, idGrado);
        return ps.executeUpdate() > 0;
    }

    public boolean borrar(int id) throws SQLException {
        ps = con.prepareStatement("DELETE FROM asignatura WHERE id = ?");
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public ResultSet buscarPorId(int id) throws SQLException {
        ps = con.prepareStatement("SELECT * FROM asignatura WHERE id = ?");
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    public boolean modificar(int id, String nombre, int creditos, String tipo, String curso, String cuatrimestre, int idProfesor, int idGrado) throws SQLException {
        String sqlUpdate = "UPDATE asignatura SET nombre = ?, creditos = ?, tipo = ?, curso = ?, cuatrimestre = ?, id_profesor = ?, id_grado = ? WHERE id = ?";
        ps = con.prepareStatement(sqlUpdate);
        ps.setString(1, nombre);
        ps.setInt(2, creditos);
        ps.setString(3, tipo);
        ps.setString(4, curso);
        ps.setString(5, cuatrimestre);
        ps.setInt(6, idProfesor);
        ps.setInt(7, idGrado);
        ps.setInt(8, id);
        return ps.executeUpdate() > 0;
    }

    public void close() throws SQLException {
        if (con != null) con.close();
        if (ps != null) ps.close();
        if (rs != null) rs.close();
    }
}
