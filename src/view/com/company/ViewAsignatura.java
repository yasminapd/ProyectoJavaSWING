package view.com.company;

import controller.com.company.ControllerAsignaturas;
import Connecion.ConectionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class ViewAsignatura extends JFrame {
    private JTextField textIdAsig;
    private JTextField textNombreAsig;
    private JTextField textCreditosAsig;
    private JTextField textIdProfeAsig;
    private JTextField textCursoAsig;
    private JButton insertarButtonAsig;
    private JButton modificarButtonAsig;
    private JButton borrarButtonAsig;
    private JList<String> tablaAsignatura;
    private JPanel panelAsignatura;
    private JButton consultarButtonAsig;
    private JTextField textCuatrimestreAsig;
    private JTextField textTipoAsig;
    private JTextField textIdGradoAsig;
    private JButton buscarButtonAsig;
    private ControllerAsignaturas controller;

    public ViewAsignatura() {
        controller = new ControllerAsignaturas();

        consultarButtonAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tablaAsignatura.setModel(controller.listar());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        insertarButtonAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controller.insertar(
                            Integer.parseInt(textIdAsig.getText()),
                            textNombreAsig.getText(),
                            Integer.parseInt(textCreditosAsig.getText()),
                            textTipoAsig.getText(),
                            textCursoAsig.getText(),
                            textCuatrimestreAsig.getText(),
                            Integer.parseInt(textIdProfeAsig.getText()),
                            Integer.parseInt(textIdGradoAsig.getText()))) {
                        JOptionPane.showMessageDialog(null, "Se ingresó la información correctamente.");
                        tablaAsignatura.setModel(controller.listar());
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        borrarButtonAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controller.borrar(Integer.parseInt(textIdAsig.getText()))) {
                        JOptionPane.showMessageDialog(null, "Registro borrado correctamente");
                        tablaAsignatura.setModel(controller.listar());
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        modificarButtonAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modificar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ConectionBD.closeConn();
            }
        });
    }

    private void modificar() throws SQLException {
        int id = Integer.parseInt(textIdAsig.getText().trim());

        // Obtener los valores actuales de la asignatura desde la base de datos
        String sqlSelect = "SELECT * FROM asignatura WHERE id = ?";
        PreparedStatement ps = ConectionBD.getConn().prepareStatement(sqlSelect);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String nombre = rs.getString("nombre");
            int creditos = rs.getInt("creditos");
            String tipo = rs.getString("tipo");
            String curso = rs.getString("curso");
            String cuatrimestre = rs.getString("cuatrimestre");
            int idProfesor = rs.getInt("id_profesor");
            int idGrado = rs.getInt("id_grado");

            // Verificar y actualizar solo los campos que no están vacíos
            if (!textNombreAsig.getText().trim().isEmpty()) {
                nombre = textNombreAsig.getText().trim();
            }
            if (!textCreditosAsig.getText().trim().isEmpty()) {
                creditos = Integer.parseInt(textCreditosAsig.getText().trim());
            }
            if (!textTipoAsig.getText().trim().isEmpty()) {
                tipo = textTipoAsig.getText().trim();
            }
            if (!textCursoAsig.getText().trim().isEmpty()) {
                curso = textCursoAsig.getText().trim();
            }
            if (!textCuatrimestreAsig.getText().trim().isEmpty()) {
                cuatrimestre = textCuatrimestreAsig.getText().trim();
            }
            if (!textIdProfeAsig.getText().trim().isEmpty()) {
                idProfesor = Integer.parseInt(textIdProfeAsig.getText().trim());
            }
            if (!textIdGradoAsig.getText().trim().isEmpty()) {
                idGrado = Integer.parseInt(textIdGradoAsig.getText().trim());
            }

            // Actualizar la base de datos con los nuevos valores
            String sqlUpdate = "UPDATE asignatura SET nombre = ?, creditos = ?, tipo = ?, curso = ?, cuatrimestre = ?, id_profesor = ?, id_grado = ? WHERE id = ?";
            ps = ConectionBD.getConn().prepareStatement(sqlUpdate);
            ps.setString(1, nombre);
            ps.setInt(2, creditos);
            ps.setString(3, tipo);
            ps.setString(4, curso);
            ps.setString(5, cuatrimestre);
            ps.setInt(6, idProfesor);
            ps.setInt(7, idGrado);
            ps.setInt(8, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente.");
                tablaAsignatura.setModel(controller.listar());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado.");
        }
    }

    public JPanel getPanelAsignatura() {
        return panelAsignatura;
    }

    public static void main(String[] args) {
        ViewAsignatura f = new ViewAsignatura();
        f.setContentPane(f.getPanelAsignatura());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }
}
