package view.com.company;

import Controler.com.company.ControllerPersonas;
import Connecion.ConectionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class ViewPersona extends JFrame {
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
    private JList<String> tablaPersona;
    private JPanel panelPersona;
    private JButton consultarButton;
    private JTextField textSexoPer;
    private JTextField textTipoPer;
    private JTextField textIdPer;
    private ControllerPersonas controller;

    public ViewPersona() {
        controller = new ControllerPersonas();

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tablaPersona.setModel(controller.listar());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        insertarButtonPer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controller.insertar(
                            Integer.parseInt(textIdPer.getText()),
                            textNIFPer.getText(),
                            textNombrePer.getText(),
                            textApellido1Per.getText(),
                            textApellido2Per.getText(),
                            textCiudadPer.getText(),
                            textDireccionPer.getText(),
                            textTelefonoPer.getText(),
                            textFNacimientoPer.getText(),
                            textSexoPer.getText(),
                            textTipoPer.getText())) {
                        JOptionPane.showMessageDialog(null, "Se ingresó la información correctamente.");
                        tablaPersona.setModel(controller.listar());
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        borrarButtonPer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controller.borrar(Integer.parseInt(textIdPer.getText()))) {
                        JOptionPane.showMessageDialog(null, "Registro borrado correctamente");
                        tablaPersona.setModel(controller.listar());
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        modificarButtonPer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controller.modificar(
                            Integer.parseInt(textIdPer.getText()),
                            textNIFPer.getText(),
                            textNombrePer.getText(),
                            textApellido1Per.getText(),
                            textApellido2Per.getText(),
                            textCiudadPer.getText(),
                            textDireccionPer.getText(),
                            textTelefonoPer.getText(),
                            textFNacimientoPer.getText(),
                            textSexoPer.getText(),
                            textTipoPer.getText())) {
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                        tablaPersona.setModel(controller.listar());
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buscarButtonPer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nif = textNIFPer.getText().trim();
                    String nombre = textNombrePer.getText().trim();
                    String apellido1 = textApellido1Per.getText().trim();
                    String apellido2 = textApellido2Per.getText().trim();
                    String ciudad = textCiudadPer.getText().trim();
                    String direccion = textDireccionPer.getText().trim();
                    String telefono = textTelefonoPer.getText().trim();
                    String fechaNacimiento = textFNacimientoPer.getText().trim();
                    String sexo = textSexoPer.getText().trim();
                    String tipo = textTipoPer.getText().trim();

                    tablaPersona.setModel(controller.buscar(nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fechaNacimiento, sexo, tipo));
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

    public JPanel getPanelPersona() {
        return panelPersona;
    }

    public static void main(String[] args) {
        ViewPersona f = new ViewPersona();
        f.setContentPane(f.getPanelPersona());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }
}
