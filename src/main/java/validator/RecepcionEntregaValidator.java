package validator;

import modelo.RecepcionEntrega;

/**
 * Validador de la entidad RecepcionEntrega.
 *
 * @author Baque Diego
 */
public class RecepcionEntregaValidator {

    /**
     * Valida todos los campos de la recepción/entrega.
     *
     * @param r objeto RecepcionEntrega a validar
     * @return mensaje de error si hay un problema, o null si es válido
     */
    public static String validar(RecepcionEntrega r) {

        if (r == null) {
            return "La recepción no puede ser nula";
        }

        String error;

        error = validarEquipo(r);
        if (error != null) {
            return error;
        }

        error = validarProblema(r.getProblemaReportado());
        if (error != null) {
            return error;
        }

        error = validarEstado(r.getEstado());
        if (error != null) {
            return error;
        }

        return null;
    }

    /**
     * Valida el equipo móvil asociado.
     */
    private static String validarEquipo(RecepcionEntrega r) {
        if (r.getEquipoMovil() == null) {
            return "Debe seleccionar un equipo móvil";
        }
        return null;
    }

    /**
     * Valida el problema reportado.
     */
    private static String validarProblema(String problema) {

        if (problema == null || problema.trim().isEmpty()) {
            return "El problema reportado es obligatorio";
        }

        if (problema.length() < 5) {
            return "El problema debe tener al menos 5 caracteres";
        }

        if (problema.length() > 255) {
            return "El problema no puede superar 255 caracteres";
        }

        return null;
    }

    /**
     * Valida el estado de la recepción.
     */
    private static String validarEstado(RecepcionEntrega.Estado estado) {
        if (estado == null) {
            return "El estado es obligatorio";
        }
        return null;
    }
}
