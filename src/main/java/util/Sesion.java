package util;

import modelo.Usuario;

/**
 * Maneja la sesión del usuario autenticado.
 *
 * @author Mendoza Sebastian
 */
public class Sesion {

    private static Usuario usuarioActual;

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
