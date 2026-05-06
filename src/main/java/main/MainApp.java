package main;

import javax.swing.UIManager;
import vista.FrmLogin;

/**
 *
 * @author Cagua Derek
 */
public class MainApp {

    public static void main(String[] args) {
        UIManager.put("Button.focus", new javax.swing.plaf.ColorUIResource(new java.awt.Color(0, 0, 0, 0)));
        FrmLogin login = new FrmLogin();
        login.setVisible(true);
    }
}
