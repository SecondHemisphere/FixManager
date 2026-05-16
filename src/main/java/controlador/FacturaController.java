package controlador;

import dao.FacturaDAO;
import java.util.List;
import modelo.Factura;
import modelo.Factura.MetodoPago;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad Factura.
 *
 * Encargado de aplicar la lógica de negocio antes de interactuar con la capa
 * DAO. Maneja validaciones, control de estados (PENDIENTE, PAGADA) y
 * operaciones de pago.
 *
 * @author Mendoza Sebastian
 */
public class FacturaController {

    private final FacturaDAO dao = new FacturaDAO();

    /**
     * Lista todas las facturas registradas.
     *
     * @return lista de facturas
     */
    public List<Factura> listarFacturas() {
        return dao.listar();
    }

    /**
     * Obtiene una factura por su ID.
     *
     * @param id identificador de la factura
     * @return objeto Factura o null si no existe
     */
    public Factura obtenerFactura(int id) {
        return dao.obtenerPorId(id);
    }

    /**
     * Registra una nueva factura en el sistema.
     *
     * @param f factura a guardar
     * @return resultado de la operación
     */
    public ResultadoOperacion guardarFactura(Factura f) {

        String error = validar(f);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        try {
            boolean ok = dao.guardar(f);

            return ok
                    ? ResultadoOperacion.exito("Factura registrada correctamente")
                    : ResultadoOperacion.error("No se pudo registrar la factura");

        } catch (RuntimeException e) {
            return ResultadoOperacion.error(e.getMessage());
        }
    }

    /**
     * Actualiza una factura existente.
     *
     * No permite modificar facturas en estado PAGADA.
     *
     * @param f factura con datos actualizados
     * @return resultado de la operación
     */
    public ResultadoOperacion actualizarFactura(Factura f) {

        if (f == null || f.getId() == 0) {
            return ResultadoOperacion.error("Factura inválida");
        }

        Factura actual = dao.obtenerPorId(f.getId());

        if (actual == null) {
            return ResultadoOperacion.error("Factura no encontrada");
        }

        if (actual.getEstado() == Factura.Estado.PAGADA) {
            return ResultadoOperacion.error("No se puede modificar una factura PAGADA");
        }

        String error = validar(f);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        try {
            boolean ok = dao.actualizar(f);

            return ok
                    ? ResultadoOperacion.exito("Factura actualizada correctamente")
                    : ResultadoOperacion.error("No se pudo actualizar la factura");

        } catch (RuntimeException e) {
            return ResultadoOperacion.error(e.getMessage());
        }
    }

    /**
     * Elimina una factura del sistema.
     *
     * No permite eliminar facturas ya pagadas.
     *
     * @param id identificador de la factura
     * @return resultado de la operación
     */
    public ResultadoOperacion eliminarFactura(int id) {

        Factura f = dao.obtenerPorId(id);

        if (f == null) {
            return ResultadoOperacion.error("Factura no encontrada");
        }

        if (f.getEstado() == Factura.Estado.PAGADA) {
            return ResultadoOperacion.error("No se puede borrar una factura PAGADA");
        }

        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Factura borrada correctamente")
                : ResultadoOperacion.error("No se pudo borrar la factura");
    }

    /**
     * Filtra facturas por texto (cliente, estado u otros campos).
     *
     * @param texto texto de búsqueda
     * @return lista de facturas filtradas
     */
    public List<Factura> filtrarFacturas(String texto) {
        return dao.filtrar(texto);
    }

    /**
     * Marca una factura como pagada y asigna el método de pago.
     *
     * @param id identificador de la factura
     * @param metodo método de pago utilizado
     * @return resultado de la operación
     */
    public ResultadoOperacion pagarFactura(int id, MetodoPago metodo) {

        Factura f = dao.obtenerPorId(id);

        if (f == null) {
            return ResultadoOperacion.error("Factura no encontrada");
        }

        if (f.getEstado() != Factura.Estado.PENDIENTE) {
            return ResultadoOperacion.error("Solo se pueden pagar facturas PENDIENTES");
        }

        f.setEstado(Factura.Estado.PAGADA);
        f.setMetodoPago(metodo);

        boolean ok = dao.actualizar(f);

        return ok
                ? ResultadoOperacion.exito("Factura pagada correctamente")
                : ResultadoOperacion.error("No se pudo procesar el pago");
    }

    /**
     * Valida una factura antes de ser guardada o actualizada.
     *
     * @param f factura a validar
     * @return mensaje de error o null si es válida
     */
    public static String validar(Factura f) {

        if (f == null) {
            return "La factura no puede ser nula";
        }

        String error = validarReparacion(f);
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
     * Valida que exista una reparación asociada a la factura.
     *
     * @param f factura a validar
     * @return mensaje de error o null si es válida
     */
    public static String validarReparacion(Factura f) {

        if (f.getReparacion() == null) {
            return "Debe seleccionar una reparación";
        }

        return null;
    }

    /**
     * Valida el costo total de la factura.
     *
     * @param costo valor del costo total
     * @return mensaje de error o null si es válido
     */
    public static String validarCosto(double costo) {

        if (costo <= 0) {
            return "El costo total debe ser mayor a 0";
        }

        return null;
    }

    /**
     * Valida las observaciones de la factura.
     *
     * @param obs texto de observaciones
     * @return mensaje de error o null si es válido
     */
    public static String validarObservaciones(String obs) {

        if (obs != null) {

            obs = obs.trim();

            if (obs.length() > 255) {
                return "Las observaciones no pueden superar 255 caracteres";
            }
        }

        return null;
    }

    /**
     * Valida el estado de la factura.
     *
     * @param estado estado actual de la factura
     * @return mensaje de error o null si es válido
     */
    public static String validarEstado(Factura.Estado estado) {

        if (estado == null) {
            return "El estado es obligatorio";
        }

        return null;
    }
}
