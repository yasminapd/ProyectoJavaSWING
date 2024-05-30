package model.com.company;

import Connecion.ConectionBD;
import java.sql.*;

public class ModelPersonas {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public ModelPersonas() {
        this.con = ConectionBD.getConn();
    }

    public ResultSet listar() throws SQLException {
        Statement st = con.createStatement();
        return st.executeQuery("SELECT * FROM persona");
    }

    public boolean insertar(int id, String nif, String nombre, String apellido1, String apellido2, String ciudad, String direccion, String telefono, String fechaNacimiento, String sexo, String tipo) throws SQLException {
        ps = con.prepareStatement("INSERT INTO persona VALUES (?,?,?,?,?,?,?,?,?,?,?)");
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
        String sqlUpdate = "UPDATE persona SET nif = ?, nombre = ?, apellido1 = ?, apellido2 = ?, ciudad = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, sexo = ?, tipo = ? WHERE id = ?";
        ps = con.prepareStatement(sqlUpdate);
        ps.setString(1, nif);
        ps.setString(2, nombre);
        ps.setString(3, apellido1);
        ps.setString(4, apellido2);
        ps.setString(5, ciudad);
        ps.setString(6, direccion);
        ps.setString(7, telefono);
        ps.setString(8, fechaNacimiento);
        ps.setString(9, sexo);
        ps.setString(10, tipo);
        ps.setInt(11, id);
        return ps.executeUpdate() > 0;
    }

    public void close() throws SQLException {
        if (con != null) con.close();
        if (ps != null) ps.close();
        if (rs != null) rs.close();
    }
}
