package view.com.company;

import controller.com.company.ControllerPersonas;
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
