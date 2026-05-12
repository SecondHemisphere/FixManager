package validator;

import modelo.Factura;

/**
 * Validador de la entidad Factura.
 *
 * @author Mendoza Sebastian
 */
public class FacturaValidator {

    /**
     * Valida todos los campos de la factura.
     *
     * @param f objeto Factura a validar
     * @return mensaje de error si hay un problema, o null si es válido
     */
    public static String validar(Factura f) {

        if (f == null) {
            return "La factura no puede ser nula";
        }

        String error;

        error = validarReparacion(f);
        if (error != null) {
            return error;
        }

        error = validarCosto(f.getCostoTotal());
        if (error != null) {
            return error;
        }

        error = validarObservaciones(f.getObservaciones());
        if (error != null) {
            return error;
        }

        error = validarEstado(f.getEstado());
        if (error != null) {
            return error;
        }

        return null;
    }

    /**
     * Valida que exista una reparación asociada.
     */
    private static String validarReparacion(Factura f) {
        if (f.getReparacion() == null) {
            return "Debe seleccionar una reparación";
        }
        return null;
    }

    /**
     * Valida el costo total.
     */
    private static String validarCosto(double costo) {

        if (costo <= 0) {
            return "El costo total debe ser mayor a 0";
        }

        return null;
    }

    /**
     * Valida las observaciones.
     */
    private static String validarObservaciones(String obs) {

        if (obs != null && obs.length() > 255) {
            return "Las observaciones no pueden superar 255 caracteres";
        }

        return null;
    }

    /**
     * Valida el estado de la factura.
     */
    private static String validarEstado(Factura.Estado estado) {
        if (estado == null) {
            return "El estado es obligatorio";
        }
        return null;
    }
}
