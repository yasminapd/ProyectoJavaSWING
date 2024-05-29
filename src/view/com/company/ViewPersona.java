package view.com.company;

import Connecion.ConectionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void modificar() throws SQLException{
        ps = con.prepareStatement("UPDATE persona SET nif = ?, nombre = ?, apellido1 = ?, apellido2 = ?, ciudad = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, sexo = ?, tipo = ? WHERE id = ?");
        ps.setString(1, textNIFPer.getText());
        ps.setString(2, textNombrePer.getText());
        ps.setString(3, textApellido1Per.getText());
        ps.setString(4, textApellido2Per.getText());
        ps.setString(5, textCiudadPer.getText());
        ps.setString(6, textDireccionPer.getText());
        ps.setString(7, textTelefonoPer.getText());
        ps.setString(8, textFNacimientoPer.getText());
        ps.setString(9, textSexoPer.getText());
        ps.setString(10, textTipoPer.getText());
        ps.setInt(11, Integer.parseInt(textIdPer.getText()));

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
            listar();  // Actualizar la lista después de modificar
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
