package view.com.company;

import controller.com.company.ControllerAsignaturas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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
                    if (controller.modificar(
                            Integer.parseInt(textIdAsig.getText()),
                            textNombreAsig.getText(),
                            Integer.parseInt(textCreditosAsig.getText()),
                            textTipoAsig.getText(),
                            textCursoAsig.getText(),
                            textCuatrimestreAsig.getText(),
                            Integer.parseInt(textIdProfeAsig.getText()),
                            Integer.parseInt(textIdGradoAsig.getText()))) {
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente.");
                        tablaAsignatura.setModel(controller.listar());
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID especificado.");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    controller.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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
