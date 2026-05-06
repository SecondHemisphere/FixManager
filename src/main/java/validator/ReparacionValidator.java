package validator;

import modelo.EstadoReparacion;
import modelo.Reparacion;

/**
 * Validador de la entidad Reparacion.
 *
 * @author Pluas Kevin
 */
public class ReparacionValidator {

    /**
     * Valida todos los campos de la reparación.
     *
     * @param r objeto Reparacion a validar
     * @return mensaje de error si hay un problema, o null si es válido
     */
    public static String validar(Reparacion r) {

        if (r == null) {
            return "La reparación es nula";
        }

        String error;

        error = validarRecepcion(r);
        if (error != null) {
            return error;
        }

        error = validarDiagnostico(r.getDiagnostico());
        if (error != null) {
            return error;
        }

        error = validarSolucion(r.getSolucion());
        if (error != null) {
            return error;
        }

        error = validarCosto(r.getCostoRepuestos());
        if (error != null) {
            return error;
        }

        error = validarPiezas(r.getPiezasUsadas());
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
     * Valida la recepción asociada.
     */
    private static String validarRecepcion(Reparacion r) {
        if (r.getRecepcion() == null) {
            return "Debe seleccionar una recepción";
        }
        return null;
    }

    /**
     * Valida el diagnóstico.
     */
    private static String validarDiagnostico(String diagnostico) {

        if (diagnostico == null || diagnostico.trim().isEmpty()) {
            return "El diagnóstico es obligatorio";
        }

        if (diagnostico.length() < 5) {
            return "El diagnóstico debe tener al menos 5 caracteres";
        }

        if (diagnostico.length() > 255) {
            return "El diagnóstico no puede superar 255 caracteres";
        }

        return null;
    }

    /**
     * Valida la solución.
     */
    private static String validarSolucion(String solucion) {

        if (solucion == null || solucion.trim().isEmpty()) {
            return "La solución es obligatoria";
        }

        if (solucion.length() < 5) {
            return "La solución debe tener al menos 5 caracteres";
        }

        if (solucion.length() > 255) {
            return "La solución no puede superar 255 caracteres";
        }

        return null;
    }

    /**
     * Valida el costo de repuestos.
     */
    private static String validarCosto(double costo) {

        if (costo < 0) {
            return "El costo debe ser un valor positivo";
        }

        return null;
    }

    /**
     * Valida las piezas usadas.
     */
    private static String validarPiezas(String piezas) {

        if (piezas == null || piezas.trim().isEmpty()) {
            return "Debe especificar las piezas usadas";
        }

        return null;
    }

    /**
     * Valida el estado de la reparación.
     */
    private static String validarEstado(EstadoReparacion estado) {
        if (estado == null) {
            return "El estado es obligatorio";
        }
        return null;
    }
}
