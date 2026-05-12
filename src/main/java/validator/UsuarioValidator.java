package validator;

import java.util.regex.Pattern;
import modelo.Usuario.Rol;
import modelo.Usuario;

/**
 * Validador de la entidad Usuario.
 *
 * @author Mendoza Sebastian
 */
public class UsuarioValidator {

    private static final Pattern EMAIL = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    /**
     * Valida todos los campos del usuario.
     *
     * @param u objeto Usuario a validar
     * @return mensaje de error si hay un problema, o null si es válido
     */
    public static String validar(Usuario u) {

        if (u == null) {
            return "El usuario no puede ser nulo";
        }

        String error;

        error = validarNombre(u.getNombre());
        if (error != null) {
            return error;
        }

        error = validarCorreo(u.getCorreo());
        if (error != null) {
            return error;
        }

        if (u.getContrasena() != null && !u.getContrasena().isEmpty()) {
            error = validarPassword(u.getContrasena());
            if (error != null) {
                return error;
            }
        }

        error = validarRol(u.getRol());
        if (error != null) {
            return error;
        }

        return null;
    }

    /**
     * Valida el nombre del usuario.
     */
    private static String validarNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre es obligatorio";
        }

        if (nombre.length() < 3) {
            return "El nombre debe tener al menos 3 caracteres";
        }

        if (nombre.length() > 100) {
            return "El nombre no puede superar 100 caracteres";
        }

        return null;
    }

    /**
     * Valida el correo electrónico.
     */
    private static String validarCorreo(String correo) {

        if (correo == null || correo.trim().isEmpty()) {
            return "El correo es obligatorio";
        }

        if (!EMAIL.matcher(correo).matches()) {
            return "Correo inválido";
        }

        return null;
    }

    /**
     * Valida la contraseña.
     */
    private static String validarPassword(String password) {

        if (password == null || password.trim().isEmpty()) {
            return "La contraseña es obligatoria";
        }

        if (password.length() < 4) {
            return "La contraseña debe tener al menos 4 caracteres";
        }

        return null;
    }

    /**
     * Valida el rol del usuario.
     */
    private static String validarRol(Rol rol) {

        if (rol == null) {
            return "El rol es obligatorio";
        }

        return null;
    }
}
