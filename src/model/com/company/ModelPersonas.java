package model.com.company;

import Connecion.ConectionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelPersonas {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public ModelPersonas() {
        this.con = ConectionBD.getConn();
        if (this.con == null) {
            throw new RuntimeException("No se pudo establecer la conexión a la base de datos.");
        }
    }

    public ResultSet listar() throws SQLException {
        Statement st = con.createStatement();
        return st.executeQuery("SELECT * FROM persona");
    }

    public boolean insertar(int id, String nif, String nombre, String apellido1, String apellido2, String ciudad, String direccion, String telefono, String fechaNacimiento, String sexo, String tipo) throws SQLException {
        ps = con.prepareStatement("INSERT INTO persona (id, nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fecha_nacimiento, sexo, tipo) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, nif);
        ps.setString(3, nombre);
        ps.setString(4, apellido1);
        ps.setString(5, apellido2);
        ps.setString(6, ciudad);
        ps.setString(7, direccion);
        ps.setString(8, telefono);
        ps.setString(9, fechaNacimiento);
        ps.setString(10, sexo);
        ps.setString(11, tipo);
        return ps.executeUpdate() > 0;
    }

    public boolean borrar(int id) throws SQLException {
        ps = con.prepareStatement("DELETE FROM persona WHERE id = ?");
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public ResultSet buscarPorId(int id) throws SQLException {
        ps = con.prepareStatement("SELECT * FROM persona WHERE id = ?");
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    public boolean modificar(int id, String nif, String nombre, String apellido1, String apellido2, String ciudad, String direccion, String telefono, String fechaNacimiento, String sexo, String tipo) throws SQLException {
        StringBuilder sqlUpdate = new StringBuilder("UPDATE persona SET ");
        boolean isFirst = true;

        if (!nif.isEmpty()) {
            sqlUpdate.append("nif = ?, ");
            isFirst = false;
        }
        if (!nombre.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "nombre = ?");
            isFirst = false;
        }
        if (!apellido1.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "apellido1 = ?");
            isFirst = false;
        }
        if (!apellido2.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "apellido2 = ?");
            isFirst = false;
        }
        if (!ciudad.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "ciudad = ?");
            isFirst = false;
        }
        if (!direccion.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "direccion = ?");
            isFirst = false;
        }
        if (!telefono.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "telefono = ?");
            isFirst = false;
        }
        if (!fechaNacimiento.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "fecha_nacimiento = ?");
            isFirst = false;
        }
        if (!sexo.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "sexo = ?");
            isFirst = false;
        }
        if (!tipo.isEmpty()) {
            sqlUpdate.append((isFirst ? "" : ", ") + "tipo = ?");
            isFirst = false;
        }

        sqlUpdate.append(" WHERE id = ?");

        ps = con.prepareStatement(sqlUpdate.toString());

        int index = 1;

        if (!nif.isEmpty()) ps.setString(index++, nif);
        if (!nombre.isEmpty()) ps.setString(index++, nombre);
        if (!apellido1.isEmpty()) ps.setString(index++, apellido1);
        if (!apellido2.isEmpty()) ps.setString(index++, apellido2);
        if (!ciudad.isEmpty()) ps.setString(index++, ciudad);
        if (!direccion.isEmpty()) ps.setString(index++, direccion);
        if (!telefono.isEmpty()) ps.setString(index++, telefono);
        if (!fechaNacimiento.isEmpty()) ps.setString(index++, fechaNacimiento);
        if (!sexo.isEmpty()) ps.setString(index++, sexo);
        if (!tipo.isEmpty()) ps.setString(index++, tipo);
        ps.setInt(index, id);

        return ps.executeUpdate() > 0;
    }

    public void close() throws SQLException {
        if (con != null) con.close();
        if (ps != null) ps.close();
        if (rs != null) rs.close();
    }
}
