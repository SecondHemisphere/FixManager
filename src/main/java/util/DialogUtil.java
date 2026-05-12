package util;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Clase para mostrar los mensajes en pantalla del sistema.
 *
 * @author Mendoza Sebastian
 */
public class DialogUtil {

    /**
     * Muestra mensaje de información.
     *
     * @param parent
     * @param mensaje
     */
    public static void mostrarMensajeInformacion(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra mensaje de error.
     *
     * @param parent
     * @param mensaje
     */
    public static void mostrarMensajeError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra mensaje de advertencia.
     *
     * @param parent
     * @param mensaje
     */
    public static void mostrarMensajeAdvertencia(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Confirmación Sí / No.
     *
     * @param parent
     * @param mensaje
     * @return true si el usuario confirma
     */
    public static boolean mostrarDialogoConfirmacion(Component parent, String mensaje) {

        Object[] opciones = {"Sí", "No"};

        int r = JOptionPane.showOptionDialog(
                parent,
                mensaje,
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        return r == 0;
    }
}
