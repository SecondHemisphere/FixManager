package controlador;

import dao.RecepcionEntregaDAO;
import java.util.List;
import modelo.RecepcionEntrega;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad RecepcionEntrega.
 *
 * Encargado de aplicar la lógica de negocio antes de interactuar con la capa
 * DAO. Valida datos, controla estados del flujo de recepción (LISTO, ENTREGADO,
 * etc.) y gestiona el resultado de las operaciones del sistema.
 *
 * @author Baque Diego
 */
public class RecepcionEntregaController {

    private final RecepcionEntregaDAO dao = new RecepcionEntregaDAO();

    /**
     * Obtiene la lista de todas las recepciones registradas.
     *
     * @return lista de recepciones
     */
    public List<RecepcionEntrega> listarRecepciones() {
        return dao.listar();
    }

    /**
     * Obtiene una recepción por su identificador.
     *
     * @param id identificador de la recepción
     * @return objeto RecepcionEntrega o null si no existe
     */
    public RecepcionEntrega obtenerRecepcion(int id) {
        return dao.obtenerPorId(id);
    }

    /**
     * Registra una nueva recepción en el sistema.
     *
     * Realiza validaciones antes de persistir los datos.
     *
     * @param r objeto RecepcionEntrega a guardar
     * @return resultado de la operación
     */
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

    /**
     * Actualiza una recepción existente.
     *
     * No permite modificaciones si la recepción está en estado LISTO o
     * ENTREGADO.
     *
     * @param r objeto con los datos actualizados
     * @return resultado de la operación
     */
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

    /**
     * Elimina (anula) una recepción del sistema.
     *
     * No permite eliminar recepciones en estado LISTO o ENTREGADO.
     *
     * @param id identificador de la recepción
     * @return resultado de la operación
     */
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

    /**
     * Filtra recepciones por texto (cliente, equipo o problema).
     *
     * @param texto texto de búsqueda
     * @return lista de recepciones que coinciden
     */
    public List<RecepcionEntrega> filtrarRecepciones(String texto) {
        return dao.filtrar(texto);
    }

    /**
     * Valida una recepción antes de ser registrada o actualizada.
     *
     * @param r objeto RecepcionEntrega a validar
     * @return mensaje de error o null si es válida
     */
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
     * Valida que el equipo móvil esté seleccionado.
     *
     * @param r objeto RecepcionEntrega
     * @return mensaje de error o null si es válido
     */
    public static String validarEquipo(RecepcionEntrega r) {

        if (r.getEquipoMovil() == null) {
            return "Debe seleccionar un equipo móvil";
        }

        return null;
    }

    /**
     * Valida el problema reportado por el cliente.
     *
     * @param problema descripción del problema
     * @return mensaje de error o null si es válido
     */
    public static String validarProblema(String problema) {

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
     *
     * @param estado estado de la recepción
     * @return mensaje de error o null si es válido
     */
    public static String validarEstado(RecepcionEntrega.Estado estado) {

        if (estado == null) {
            return "El estado es obligatorio";
        }

        return null;
    }
}
