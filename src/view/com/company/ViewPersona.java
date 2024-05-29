package view.com.company;

import Connecion.ConectionBD;
import Controler.com.company.ControllerEntrada;

import javax.swing.*;

public class ViewPersona {
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

    public ViewPersona() {

    }

    public static void main(String[] args) {
        ConectionBD.openConn();
        System.out.println("hola");
        ConectionBD.closeConn();

    }
}
