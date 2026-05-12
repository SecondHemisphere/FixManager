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
        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Recepción eliminada correctamente")
                : ResultadoOperacion.error("No se pudo eliminar la recepción");
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
