package util;

import modelo.Usuario;

/**
 * Maneja la sesión del usuario autenticado en el sistema. Permite almacenar el
 * usuario en memoria y validar su rol para control de acceso a funcionalidades
 * del sistema.
 *
 * @author Mendoza Sebastian
 */
public class Sesion {

    private static Usuario usuarioActual;

    /**
     * Verifica si el usuario en sesión es administrador.
     *
     * @return true si el usuario es ADMIN, false en caso contrario
     */
    public static boolean esAdmin() {
        return usuarioActual != null && usuarioActual.getRol() == Usuario.Rol.ADMIN;
    }

    /**
     * Verifica si el usuario en sesión es técnico.
     *
     * @return true si el usuario es TECNICO, false en caso contrario
     */
    public static boolean esTecnico() {
        return usuarioActual != null && usuarioActual.getRol() == Usuario.Rol.TECNICO;
    }

    /**
     * Verifica si el usuario en sesión es cajero.
     *
     * @return true si el usuario es CAJERO, false en caso contrario
     */
    public static boolean esCajero() {
        return usuarioActual != null && usuarioActual.getRol() == Usuario.Rol.CAJERO;
    }

    /**
     * Cierra la sesión actual eliminando el usuario en memoria.
     */
    public static void cerrarSesion() {
        usuarioActual = null;
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     *
     * @return usuario en sesión o null si no hay sesión activa
     */
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Establece el usuario autenticado en sesión.
     *
     * @param usuario usuario que inicia sesión
     */
    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }
}
