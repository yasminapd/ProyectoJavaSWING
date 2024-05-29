package view.com.company;

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
    PreparedStatement ps;
    Connection con = ConectionBD.getConn();
    Statement st;
    ResultSet r;
    DefaultListModel<String> mod = new DefaultListModel<>();

    public ViewAsignatura() {
        consultarButtonAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        insertarButtonAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        borrarButtonAsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    borrar();
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

        // Añadir WindowListener para cerrar la conexión a la base de datos al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ConectionBD.closeConn();
            }
        });
    }

    public void listar() throws SQLException {
        tablaAsignatura.setModel(mod);
        st = con.createStatement();
        r = st.executeQuery("SELECT * FROM asignatura");
        mod.removeAllElements();
        while (r.next()) {
            mod.addElement(
                    r.getString("id") + " " +
                            r.getString("nombre") + " " +
                            r.getInt("creditos") + " " +
                            r.getString("tipo") + " " +
                            r.getString("curso") + " " +
                            r.getString("cuatrimestre") + " " +
                            r.getString("id_profesor") + " " +
                            r.getInt("id_grado"));
        }
    }

    public void insertar() throws SQLException {
        int idProfesor = Integer.parseInt(textIdProfeAsig.getText());

        // Verificar si el id_profesor existe en la tabla profesor
        String sqlCheck = "SELECT COUNT(*) FROM profesor WHERE id_profesor = ?";
        ps = con.prepareStatement(sqlCheck);
        ps.setInt(1, idProfesor);
        ResultSet rsCheck = ps.executeQuery();
        rsCheck.next();
        int count = rsCheck.getInt(1);

        if (count == 0) {
            JOptionPane.showMessageDialog(null, "El ID del profesor no existe. Por favor, ingrese un ID de profesor válido.");
            return;
        }

        ps = con.prepareStatement("INSERT INTO asignatura (id, nombre, creditos, tipo, curso, cuatrimestre, id_profesor, id_grado) VALUES (?,?,?,?,?,?,?,?)");
        ps.setInt(1, Integer.parseInt(textIdAsig.getText()));
        ps.setString(2, textNombreAsig.getText());
        ps.setInt(3, Integer.parseInt(textCreditosAsig.getText()));
        ps.setString(4, textTipoAsig.getText());
        ps.setString(5, textCursoAsig.getText());
        ps.setString(6, textCuatrimestreAsig.getText());
        ps.setInt(7, idProfesor);
        ps.setInt(8, Integer.parseInt(textIdGradoAsig.getText()));
        if (ps.executeUpdate() > 0) {
            tablaAsignatura.setModel(mod);
            mod.removeAllElements();
            mod.addElement("Se ingresó la información correctamente.");

            textIdAsig.setText("");
            textNombreAsig.setText("");
            textCreditosAsig.setText("");
            textIdProfeAsig.setText("");
            textCursoAsig.setText("");
            textCuatrimestreAsig.setText("");
            textTipoAsig.setText("");
            textIdGradoAsig.setText("");
        }
    }

    public void borrar() throws SQLException {
        int id = Integer.parseInt(textIdAsig.getText());
        ps = con.prepareStatement("DELETE FROM asignatura WHERE id = ?");
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Registro borrado correctamente");
            listar();  // Actualizar la lista después de borrar
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado");
        }
    }

    public void modificar() throws SQLException {
        int id = Integer.parseInt(textIdAsig.getText());
        String sqlSelect = "SELECT * FROM asignatura WHERE id = ?";
        ps = con.prepareStatement(sqlSelect);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nombre = rs.getString("nombre");
            String creditos = rs.getString("creditos");
            String tipo = rs.getString("tipo");
            String curso = rs.getString("curso");
            String cuatrimestre = rs.getString("cuatrimestre");
            String id_profesor = rs.getString("id_profesor");
            String id_grado = rs.getString("id_grado");

            if (!textNombreAsig.getText().trim().isEmpty()) {
                nombre = textNombreAsig.getText();
            }
            if (!textCreditosAsig.getText().trim().isEmpty()) {
                creditos = textCreditosAsig.getText();
            }
            if (!textTipoAsig.getText().trim().isEmpty()) {
                tipo = textTipoAsig.getText();
            }
            if (!textCursoAsig.getText().trim().isEmpty()) {
                curso = textCursoAsig.getText();
            }
            if (!textCuatrimestreAsig.getText().trim().isEmpty()) {
                cuatrimestre = textCuatrimestreAsig.getText();
            }
            if (!textIdProfeAsig.getText().trim().isEmpty()) {
                id_profesor = textIdProfeAsig.getText();
            }
            if (!textIdGradoAsig.getText().trim().isEmpty()) {
                id_grado = textIdGradoAsig.getText();
            }

            // Verificar si el nuevo id_profesor existe en la tabla profesor
            int idProfesor;
            if (!textIdProfeAsig.getText().trim().isEmpty()) {
                idProfesor = Integer.parseInt(textIdProfeAsig.getText());
                String sqlCheck = "SELECT COUNT(*) FROM profesor WHERE id_profesor = ?";
                ps = con.prepareStatement(sqlCheck);
                ps.setInt(1, idProfesor);
                ResultSet rsCheck = ps.executeQuery();
                rsCheck.next();
                int count = rsCheck.getInt(1);

                if (count == 0) {
                    JOptionPane.showMessageDialog(null, "El ID del profesor no existe. Por favor, ingrese un ID de profesor válido.");
                    return;
                }
            } else {
                idProfesor = Integer.parseInt(id_profesor);
            }

            String sqlUpdate = "UPDATE asignatura SET nombre = ?, creditos = ?, tipo=?, curso=?, cuatrimestre= ?, id_profesor = ?, id_grado=? WHERE id = ?";
            ps = con.prepareStatement(sqlUpdate);
            ps.setString(1, nombre);
            ps.setInt(2, Integer.parseInt(creditos));
            ps.setString(3, tipo);
            ps.setString(4, curso);
            ps.setString(5, cuatrimestre);
            ps.setInt(6, idProfesor);
            ps.setInt(7, Integer.parseInt(id_grado));
            ps.setInt(8, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                listar();  // Actualizar la lista después de modificar
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado");
        }
    }

    // Método necesario para componentes personalizados
    private void createUIComponents() {
        // Inicializa componentes personalizados si es necesario
    }

    public static void main(String[] args) {
        ConectionBD.openConn();
        ViewAsignatura f = new ViewAsignatura();
        f.setContentPane(new ViewAsignatura().panelAsignatura);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }
}
