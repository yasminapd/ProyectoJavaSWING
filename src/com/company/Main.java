package com.company;

import view.com.company.ViewMenu;

public class Main {
    public static void main(String[] args) {
        // Crear y mostrar la ventana del menú principal
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewMenu();
            }
        });
    }
}
