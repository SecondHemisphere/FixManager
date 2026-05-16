package main;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import vista.FrmLogin;

/**
 * Clase principal del sistema.
 *
 * Inicializa la aplicación y muestra la ventana de inicio de sesión.
 *
 * @author Mendoza Sebastian
 */
public class MainApp {

    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        UIManager.put("Button.focus", new ColorUIResource(new java.awt.Color(0, 0, 0, 0)));

        FrmLogin login = new FrmLogin();
        login.setVisible(true);
    }
}
