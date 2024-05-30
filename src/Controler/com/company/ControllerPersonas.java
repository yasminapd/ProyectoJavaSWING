package Controler.com.company;

import model.com.company.ModelPersonas;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerPersonas {
    private ModelPersonas model;

    public ControllerPersonas() {
        model = new ModelPersonas();
    }

    public DefaultListModel<String> listar() throws SQLException {
        ResultSet rs = model.listar();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        while (rs.next()) {
            listModel.addElement(rs.getInt("id") + " " + rs.getString("nif") + " " + rs.getString("nombre") + " " +
                    rs.getString("apellido1") + " " + rs.getString("apellido2") + " " + rs.getString("ciudad") + " " +
                    rs.getString("direccion") + " " + rs.getString("telefono") + " " + rs.getString("fecha_nacimiento") +
                    " " + rs.getString("sexo") + " " + rs.getString("tipo"));
        }
        return listModel;
    }

    public boolean insertar(int id, String nif, String nombre, String apellido1, String apellido2, String ciudad, String direccion, String telefono, String fechaNacimiento, String sexo, String tipo) throws SQLException {
        return model.insertar(id, nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fechaNacimiento, sexo, tipo);
    }

    public boolean borrar(int id) throws SQLException {
        return model.borrar(id);
    }

    public boolean modificar(int id, String nif, String nombre, String apellido1, String apellido2, String ciudad, String direccion, String telefono, String fechaNacimiento, String sexo, String tipo) throws SQLException {
        return model.modificar(id, nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fechaNacimiento, sexo, tipo);
    }

    public DefaultListModel<String> buscar(String nif, String nombre, String apellido1, String apellido2, String ciudad, String direccion, String telefono, String fechaNacimiento, String sexo, String tipo) throws SQLException {
        ResultSet rs = model.buscar(nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fechaNacimiento, sexo, tipo);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        while (rs.next()) {
            listModel.addElement(rs.getInt("id") + " " + rs.getString("nif") + " " + rs.getString("nombre") + " " +
                    rs.getString("apellido1") + " " + rs.getString("apellido2") + " " + rs.getString("ciudad") + " " +
                    rs.getString("direccion") + " " + rs.getString("telefono") + " " + rs.getString("fecha_nacimiento") +
                    " " + rs.getString("sexo") + " " + rs.getString("tipo"));
        }
        return listModel;
    }


    public void close() throws SQLException {
        model.close();
    }
}
