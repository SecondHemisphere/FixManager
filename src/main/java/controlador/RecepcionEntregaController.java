package controlador;

import dao.RecepcionEntregaDAO;
import java.util.List;
import modelo.RecepcionEntrega;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad RecepcionEntrega.
 *
 * @author Baque Diego
 */
public class RecepcionEntregaController {

    private final RecepcionEntregaDAO dao = new RecepcionEntregaDAO();

    public List<RecepcionEntrega> listarRecepciones() {
        return dao.listar();
    }

    public RecepcionEntrega obtenerRecepcion(int id) {
        return dao.obtenerPorId(id);
    }

    public ResultadoOperacion guardarRecepcion(RecepcionEntrega r) {
        String error = validar(r);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        boolean ok = dao.guardar(r);

        return ok
                ? ResultadoOperacion.exito("Recepción registrada correctamente")
                : ResultadoOperacion.error("No se pudo registrar la recepción");
    }

    public ResultadoOperacion actualizarRecepcion(RecepcionEntrega r) {

        if (r == null || r.getId() == 0) {
            return ResultadoOperacion.error("Recepción inválida");
        }

        RecepcionEntrega actual = dao.obtenerPorId(r.getId());

        if (actual == null) {
            return ResultadoOperacion.error("Recepción no encontrada");
        }

        if (actual.getEstado() == RecepcionEntrega.Estado.LISTO) {
            return ResultadoOperacion.error("No se puede modificar una recepción LISTA");
        }

        if (actual.getEstado() == RecepcionEntrega.Estado.ENTREGADO) {
            return ResultadoOperacion.error("No se puede modificar una recepción ENTREGADA");
        }

        String error = validar(r);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        boolean ok = dao.actualizar(r);

        return ok
                ? ResultadoOperacion.exito("Recepción actualizada correctamente")
                : ResultadoOperacion.error("No se pudo actualizar la recepción");
    }

    public ResultadoOperacion eliminarRecepcion(int id) {
        RecepcionEntrega r = dao.obtenerPorId(id);

        if (r == null) {
            return ResultadoOperacion.error("Recepción no encontrada");
        }

        if (r.getEstado() == RecepcionEntrega.Estado.ENTREGADO) {
            return ResultadoOperacion.error("No se puede anular una recepción ENTREGADA");
        }

        if (r.getEstado() == RecepcionEntrega.Estado.LISTO) {
            return ResultadoOperacion.error("No se puede anular una recepción LISTA");
        }

        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Recepción anulada correctamente")
                : ResultadoOperacion.error("No se pudo anular la recepción");
    }

    public List<RecepcionEntrega> filtrarRecepciones(String texto) {
        return dao.filtrar(texto);
    }

    public static String validar(RecepcionEntrega r) {

        if (r == null) {
            return "La recepción no puede ser nula";
        }

        String error = validarEquipo(r);

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

        problema = problema.trim();

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
