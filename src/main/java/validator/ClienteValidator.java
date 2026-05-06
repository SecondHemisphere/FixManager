package validator;

import java.util.regex.Pattern;
import modelo.Cliente;

/**
 * Validador de la entidad Cliente.
 *
 * @author Cagua Derek
 */
public class ClienteValidator {

    private static final Pattern SOLO_NUMEROS = Pattern.compile("\\d+");
    private static final Pattern EMAIL = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    /**
     * Valida todos los campos del cliente.
     *
     * @param c objeto Cliente a validar
     * @return mensaje de error si hay un problema, o null si es válido
     */
    public static String validar(Cliente c) {

        if (c == null) {
            return "Cliente no puede ser nulo";
        }

        String error;

        error = validarNombre(c.getNombre());
        if (error != null) {
            return error;
        }

        error = validarCorreo(c.getCorreo());
        if (error != null) {
            return error;
        }

        error = validarTelefono(c.getTelefono());
        if (error != null) {
            return error;
        }

        error = validarDireccion(c.getDireccion());
        if (error != null) {
            return error;
        }

        return null;
    }

    /**
     * Valida el nombre del cliente.
     */
    private static String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre es obligatorio";
        }
        if (nombre.trim().length() < 3) {
            return "El nombre debe tener al menos 3 caracteres";
        }
        if (nombre.trim().length() > 100) {
            return "El nombre no puede superar 100 caracteres";
        }
        return null;
    }

    /**
     * Valida el correo electrónico del cliente.
     */
    private static String validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return "El correo es obligatorio";
        }
        if (!EMAIL.matcher(correo).matches()) {
            return "Correo inválido";
        }
        if (correo.length() > 100) {
            return "El correo no puede superar 100 caracteres";
        }
        return null;
    }

    /**
     * Valida el número de teléfono del cliente.
     */
    private static String validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return "El teléfono es obligatorio";
        }
        if (!SOLO_NUMEROS.matcher(telefono).matches()) {
            return "El teléfono debe contener solo números";
        }
        if (telefono.length() < 7) {
            return "El teléfono debe tener al menos 7 dígitos";
        }
        if (telefono.length() > 20) {
            return "El teléfono no puede superar 20 caracteres";
        }
        return null;
    }

    /**
     * Valida la dirección del cliente (campo opcional).
     */
    private static String validarDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return null;
        }
        if (direccion.trim().length() < 5) {
            return "La dirección debe tener al menos 5 caracteres";
        }
        if (direccion.trim().length() > 150) {
            return "La dirección no puede superar 150 caracteres";
        }

        return null;
    }
}
