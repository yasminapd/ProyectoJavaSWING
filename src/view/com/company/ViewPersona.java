package view.com.company;

import Connecion.ConectionBD;
import Controler.com.company.ControllerEntrada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }

    public void listar() throws SQLException {
        ConectionBD.openConn();
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
        ConectionBD.openConn();
        ps = con.prepareStatement("INSERT INTO persona VALUES ('.?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1,);
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
    public static void main(String[] args) {
        ConectionBD.openConn();
        ViewPersona f = new ViewPersona();
        f.setContentPane(new ViewPersona().panelPersona);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();

    }
}
