package util;

import modelo.Usuario;

/**
 * Maneja la sesión del usuario autenticado.
 *
 * @author Mendoza Sebastian
 */
public class Sesion {

    private static Usuario usuarioActual;

    public static boolean esAdmin() {
        return usuarioActual != null && usuarioActual.getRol() == Usuario.Rol.ADMIN;
    }

    public static boolean esTecnico() {
        return usuarioActual != null && usuarioActual.getRol() == Usuario.Rol.TECNICO;
    }

    public static boolean esCajero() {
        return usuarioActual != null && usuarioActual.getRol() == Usuario.Rol.CAJERO;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }
}
