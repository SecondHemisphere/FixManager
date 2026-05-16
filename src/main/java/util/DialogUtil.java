package util;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Clase utilitaria para mostrar mensajes en pantalla dentro del sistema.
 *
 * Proporciona métodos centralizados para información, errores, advertencias y
 * diálogos de confirmación usando JOptionPane.
 *
 * @author Mendoza Sebastian
 */
public class DialogUtil {

    /**
     * Muestra un mensaje informativo en pantalla.
     *
     * @param parent componente padre del diálogo
     * @param mensaje texto del mensaje a mostrar
     */
    public static void mostrarMensajeInformacion(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error en pantalla.
     *
     * @param parent componente padre del diálogo
     * @param mensaje texto del mensaje de error
     */
    public static void mostrarMensajeError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de advertencia en pantalla.
     *
     * @param parent componente padre del diálogo
     * @param mensaje texto del mensaje de advertencia
     */
    public static void mostrarMensajeAdvertencia(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un diálogo de confirmación con opciones Sí y No.
     *
     * @param parent componente padre del diálogo
     * @param mensaje mensaje a confirmar
     * @return true si el usuario selecciona "Sí", false en caso contrario
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
