package controller.com.company;

import model.com.company.ModelAsignaturas;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerAsignaturas {
    private ModelAsignaturas model;

    public ControllerAsignaturas() {
        model = new ModelAsignaturas();
    }

    public DefaultListModel<String> listar() throws SQLException {
        ResultSet rs = model.listar();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        while (rs.next()) {
            listModel.addElement(rs.getInt("id") + " " + rs.getString("nombre") + " " + rs.getInt("creditos") + " " +
                    rs.getString("tipo") + " " + rs.getString("curso") + " " + rs.getString("cuatrimestre") + " " +
                    rs.getInt("id_profesor") + " " + rs.getInt("id_grado"));
        }
        return listModel;
    }

    public boolean insertar(int id, String nombre, int creditos, String tipo, String curso, String cuatrimestre, int idProfesor, int idGrado) throws SQLException {
        return model.insertar(id, nombre, creditos, tipo, curso, cuatrimestre, idProfesor, idGrado);
    }

    public boolean borrar(int id) throws SQLException {
        return model.borrar(id);
    }

    public boolean modificar(int id, String nombre, int creditos, String tipo, String curso, String cuatrimestre, int idProfesor, int idGrado) throws SQLException {
        return model.modificar(id, nombre, creditos, tipo, curso, cuatrimestre, idProfesor, idGrado);
    }

    public DefaultListModel<String> buscar(String nombre, String tipo, String curso, String cuatrimestre, String idProfesor, String idGrado) throws SQLException {
        ResultSet rs = model.buscar(nombre, tipo, curso, cuatrimestre, idProfesor, idGrado);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        while (rs.next()) {
            listModel.addElement(rs.getInt("id") + " " + rs.getString("nombre") + " " + rs.getInt("creditos") + " " +
                    rs.getString("tipo") + " " + rs.getString("curso") + " " + rs.getString("cuatrimestre") + " " +
                    rs.getInt("id_profesor") + " " + rs.getInt("id_grado"));
        }
        return listModel;
    }

    public void close() throws SQLException {
        model.close();
    }
}
