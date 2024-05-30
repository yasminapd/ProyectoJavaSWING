package controller.com.company;

import model.com.company.ModelAsignaturas;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerAsignaturas {
    private ModelAsignaturas model;
    private DefaultListModel<String> mod;

    public ControllerAsignaturas() {
        model = new ModelAsignaturas();
        mod = new DefaultListModel<>();
    }

    public DefaultListModel<String> listar() throws SQLException {
        ResultSet rs = model.listar();
        mod.removeAllElements();
        while (rs.next()) {
            mod.addElement(
                    rs.getString("id") + " " +
                            rs.getString("nombre") + " " +
                            rs.getInt("creditos") + " " +
                            rs.getString("tipo") + " " +
                            rs.getString("curso") + " " +
                            rs.getString("cuatrimestre") + " " +
                            rs.getString("id_profesor") + " " +
                            rs.getInt("id_grado")
            );
        }
        return mod;
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

    public void close() throws SQLException {
        model.close();
    }
}
