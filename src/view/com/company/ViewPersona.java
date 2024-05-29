package view.com.company;

import Connecion.ConectionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class ViewPersona extends JFrame{
    private JTextField textNIFPer;
    private JTextField textNombrePer;
    private JTextField textApellido1Per;
    private JTextField textApellido2Per;
    private JTextField textCiudadPer;
    private JTextField textDireccionPer;
    private JTextField textTelefonoPer;
    private JTextField textFNacimientoPer;
    private JButton buscarButtonPer;
    private JButton borrarButtonPer;
    private JButton insertarButtonPer;
    private JButton modificarButtonPer;
    private JList tablaPersona;
    private JPanel panelPersona;
    private JButton consultarButton;
    private JTextField textSexoPer;
    private JTextField textTipoPer;
    private JTextField textIdPer;
    PreparedStatement ps;
    Connection con = ConectionBD.getConn();
    Statement st;
    ResultSet r;

    DefaultListModel mod = new DefaultListModel();

    public ViewPersona() {

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        insertarButtonPer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        borrarButtonPer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    borrar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        modificarButtonPer.addActionListener(new ActionListener() {
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

    public void listar() throws SQLException {
        tablaPersona.setModel(mod);
        st = con.createStatement();
        r = st.executeQuery("select * from persona");
        mod.removeAllElements();
        while (r.next()) {
            mod.addElement(r.getString("nif")+" "+ r.getString("nombre")+" "+r.getString("apellido1")+" "+r.getString("apellido2")+" "+
            r.getString("ciudad")+" "+r.getString("direccion")+" "+r.getString("telefono")+" "+r.getString("fecha_nacimiento")+" "+
            r.getString("sexo")+" "+r.getString("tipo"));
        }
    }

    public void insertar() throws SQLException {
        ps = con.prepareStatement("INSERT INTO persona VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, Integer.parseInt(textIdPer.getText()));
        ps.setString(2,textNIFPer.getText());
        ps.setString(3,textNombrePer.getText());
        ps.setString(4,textApellido1Per.getText());
        ps.setString(5,textApellido2Per.getText());
        ps.setString(6,textCiudadPer.getText());
        ps.setString(7,textDireccionPer.getText());
        ps.setString(8,textTelefonoPer.getText());
        ps.setString(9,textFNacimientoPer.getText());
        ps.setString(10,textSexoPer.getText());
        ps.setString(11,textTipoPer.getText());
        if (ps.executeUpdate()>0){
            tablaPersona.setModel(mod);
            mod.removeAllElements();
            mod.addElement("Se ingresó la información correctamente.");

            textIdPer.setText("");
            textNIFPer.setText("");
            textNombrePer.setText("");
            textApellido1Per.setText("");
            textApellido2Per.setText("");
            textCiudadPer.setText("");
            textDireccionPer.setText("");
            textTelefonoPer.setText("");
            textFNacimientoPer.setText("");
            textSexoPer.setText("");
            textTipoPer.setText("");
        }
    }

    public void borrar() throws SQLException{
        ps = con.prepareStatement("DELETE FROM persona WHERE id = ?");
        ps.setInt(1, Integer.parseInt(textIdPer.getText()));
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Registro borrado correctamente");
            listar();  // Actualizar la lista después de borrar
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado");
        }
    }

    public void modificar() throws SQLException {
        int id = Integer.parseInt(textIdPer.getText());
        String sqlSelect = "SELECT * FROM persona WHERE id = ?";
        ps = con.prepareStatement(sqlSelect);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nif = rs.getString("nif");
            String nombre = rs.getString("nombre");
            String apellido1 = rs.getString("apellido1");
            String apellido2 = rs.getString("apellido2");
            String ciudad = rs.getString("ciudad");
            String direccion = rs.getString("direccion");
            String telefono = rs.getString("telefono");
            String fechaNacimiento = rs.getString("fecha_nacimiento");
            String sexo = rs.getString("sexo");
            String tipo = rs.getString("tipo");

            if (!textNIFPer.getText().trim().isEmpty()) {
                nif = textNIFPer.getText();
            }
            if (!textNombrePer.getText().trim().isEmpty()) {
                nombre = textNombrePer.getText();
            }
            if (!textApellido1Per.getText().trim().isEmpty()) {
                apellido1 = textApellido1Per.getText();
            }
            if (!textApellido2Per.getText().trim().isEmpty()) {
                apellido2 = textApellido2Per.getText();
            }
            if (!textCiudadPer.getText().trim().isEmpty()) {
                ciudad = textCiudadPer.getText();
            }
            if (!textDireccionPer.getText().trim().isEmpty()) {
                direccion = textDireccionPer.getText();
            }
            if (!textTelefonoPer.getText().trim().isEmpty()) {
                telefono = textTelefonoPer.getText();
            }
            if (!textFNacimientoPer.getText().trim().isEmpty()) {
                fechaNacimiento = textFNacimientoPer.getText();
            }
            if (!textSexoPer.getText().trim().isEmpty()) {
                sexo = textSexoPer.getText();
            }
            if (!textTipoPer.getText().trim().isEmpty()) {
                tipo = textTipoPer.getText();
            }

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


    public static void main(String[] args) {
        ConectionBD.openConn();
        ViewPersona f = new ViewPersona();
        f.setContentPane(new ViewPersona().panelPersona);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();

    }
}
