package view.com.company;

import Connecion.ConectionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewMenu extends JFrame {
    private JButton asignaturasButtonMenu;
    private JButton personasButtonMenu;
    private JPanel panelMenu;

    public ViewMenu() {
        // Inicializar los componentes del panel
        panelMenu = new JPanel();
        asignaturasButtonMenu = new JButton("Asignaturas");
        personasButtonMenu = new JButton("Personas");

        // Agregar los botones al panel
        panelMenu.add(asignaturasButtonMenu);
        panelMenu.add(personasButtonMenu);

        // Configuración del botón de Asignaturas
        asignaturasButtonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la conexión a la base de datos
                ConectionBD.openConn();

                // Crear y mostrar la ventana de Asignaturas
                ViewAsignatura viewAsignatura = new ViewAsignatura();
                JFrame frame = new JFrame("Asignaturas");
                frame.setContentPane(viewAsignatura.getPanelAsignatura());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        ConectionBD.closeConn();
                    }
                });
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Configuración del botón de Personas
        personasButtonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la conexión a la base de datos
                ConectionBD.openConn();

                // Crear y mostrar la ventana de Personas
                ViewPersona viewPersona = new ViewPersona();
                JFrame frame = new JFrame("Personas");
                frame.setContentPane(viewPersona.getPanelPersona());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        ConectionBD.closeConn();
                    }
                });
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Configuración del JFrame
        setContentPane(panelMenu);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        // Crear y mostrar la ventana del menú principal
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewMenu();
            }
        });
    }
}
