package model.com.company;

import Connecion.ConectionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelAsignaturas {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public ModelAsignaturas() {
        this.con = ConectionBD.getConn();
        if (this.con == null) {
            throw new RuntimeException("No se pudo establecer la conexión a la base de datos.");
        }
    }

    public ResultSet listar() throws SQLException {
        ps = con.prepareStatement("SELECT * FROM asignatura");
        return ps.executeQuery();
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

    public ResultSet buscar(String nombre, String tipo, String curso, String cuatrimestre, String idProfesor, String idGrado) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM asignatura WHERE 1=1");
        if (!nombre.isEmpty()) query.append(" AND nombre LIKE ?");
        if (!tipo.isEmpty()) query.append(" AND tipo LIKE ?");
        if (!curso.isEmpty()) query.append(" AND curso LIKE ?");
        if (!cuatrimestre.isEmpty()) query.append(" AND cuatrimestre LIKE ?");
        if (!idProfesor.isEmpty()) query.append(" AND id_profesor LIKE ?");
        if (!idGrado.isEmpty()) query.append(" AND id_grado LIKE ?");

        ps = con.prepareStatement(query.toString());
        int index = 1;
        if (!nombre.isEmpty()) ps.setString(index++, "%" + nombre + "%");
        if (!tipo.isEmpty()) ps.setString(index++, "%" + tipo + "%");
        if (!curso.isEmpty()) ps.setString(index++, "%" + curso + "%");
        if (!cuatrimestre.isEmpty()) ps.setString(index++, "%" + cuatrimestre + "%");
        if (!idProfesor.isEmpty()) ps.setString(index++, "%" + idProfesor + "%");
        if (!idGrado.isEmpty()) ps.setString(index++, "%" + idGrado + "%");

        return ps.executeQuery();
    }

    public boolean modificar(int id, String nombre, int creditos, String tipo, String curso, String cuatrimestre, int idProfesor, int idGrado) throws SQLException {
        StringBuilder sqlUpdate = new StringBuilder("UPDATE asignatura SET ");
        boolean isFirst = true;

        if (!nombre.isEmpty()) {
            sqlUpdate.append("nombre = ?");
            isFirst = false;
        }
        if (!tipo.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "tipo = ?");
            isFirst = false;
        }
        if (!curso.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "curso = ?");
            isFirst = false;
        }
        if (!cuatrimestre.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "cuatrimestre = ?");
            isFirst = false;
        }
        if (idProfesor != 0) {
            sqlUpdate.append((isFirst ? "" : ", ") + "id_profesor = ?");
            isFirst = false;
        }
        if (idGrado != 0) {
            sqlUpdate.append((isFirst ? "" : ", ") + "id_grado = ?");
            isFirst = false;
        }

        sqlUpdate.append(" WHERE id = ?");

        ps = con.prepareStatement(sqlUpdate.toString());

        int index = 1;

        if (!nombre.isEmpty()) ps.setString(index++, nombre);
        if (!tipo.isEmpty()) ps.setString(index++, tipo);
        if (!curso.isEmpty()) ps.setString(index++, curso);
        if (!cuatrimestre.isEmpty()) ps.setString(index++, cuatrimestre);
        if (idProfesor != 0) ps.setInt(index++, idProfesor);
        if (idGrado != 0) ps.setInt(index++, idGrado);
        ps.setInt(index, id);

        return ps.executeUpdate() > 0;
    }

    public void close() throws SQLException {
        if (con != null) con.close();
        if (ps != null) ps.close();
        if (rs != null) rs.close();
    }
}
