package controlador;

import dao.ReparacionDAO;
import java.util.List;
import modelo.Reparacion;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad Reparacion.
 *
 * @author Pluas Kevin
 */
public class ReparacionController {

    private final ReparacionDAO dao = new ReparacionDAO();

    public List<Reparacion> listar() {
        return dao.listar();
    }

    public ResultadoOperacion guardar(Reparacion r) {
        String error = validar(r);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        boolean ok = dao.guardar(r);

        return ok
                ? ResultadoOperacion.exito("Reparación registrada correctamente")
                : ResultadoOperacion.error("No se pudo registrar");
    }

    public ResultadoOperacion actualizar(Reparacion r) {

        if (r == null || r.getId() == 0) {
            return ResultadoOperacion.error("Reparación inválida");
        }

        Reparacion actual = dao.obtenerPorId(r.getId());

        if (actual == null) {
            return ResultadoOperacion.error("Reparación no encontrada");
        }

        if (dao.tieneFactura(r.getId())) {
            return ResultadoOperacion.error("La reparación no se puede modificar porque tiene una factura generada");
        }

        String error = validar(r);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        boolean ok = dao.actualizar(r);

        return ok
                ? ResultadoOperacion.exito("Reparación actualizada")
                : ResultadoOperacion.error("No se pudo actualizar");
    }

    public ResultadoOperacion eliminar(int id) {
        Reparacion r = dao.obtenerPorId(id);

        if (r == null) {
            return ResultadoOperacion.error("Reparación no encontrada");
        }

        if (r.getEstado() == Reparacion.Estado.EN_PROCESO) {
            return ResultadoOperacion.error("No se puede eliminar una reparación EN PROCESO");
        }

        if (r.getEstado() == Reparacion.Estado.FINALIZADO) {
            return ResultadoOperacion.error("No se puede eliminar una reparación FINALIZADA");
        }

        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Reparación eliminada correctamente")
                : ResultadoOperacion.error("No se pudo eliminar la reparación");
    }

    public List<Reparacion> filtrar(String texto) {
        return dao.filtrar(texto);
    }

    public static String validar(Reparacion r) {

        if (r == null) {
            return "La reparación es nula";
        }

        String error = validarRecepcion(r);

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

        diagnostico = diagnostico.trim();

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

        solucion = solucion.trim();

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

        piezas = piezas.trim();

        if (piezas.length() < 3) {
            return "Las piezas usadas deben tener al menos 3 caracteres";
        }

        if (piezas.length() > 255) {
            return "Las piezas usadas no pueden superar 255 caracteres";
        }

        return null;
    }

    /**
     * Valida el estado de la reparación.
     */
    private static String validarEstado(Reparacion.Estado estado) {

        if (estado == null) {
            return "El estado es obligatorio";
        }

        return null;
    }
}
