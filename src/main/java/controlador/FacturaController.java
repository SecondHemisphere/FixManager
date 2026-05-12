package controlador;

import dao.FacturaDAO;
import java.util.List;
import modelo.Factura;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad Factura.
 *
 * @author Mendoza Sebastian
 */
public class FacturaController {

    private final FacturaDAO dao = new FacturaDAO();

    public List<Factura> listarFacturas() {
        return dao.listar();
    }

    public Factura obtenerFactura(int id) {
        return dao.obtenerPorId(id);
    }

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

    public ResultadoOperacion actualizarFactura(Factura f) {
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

    public List<Factura> filtrarFacturas(String texto) {
        return dao.filtrar(texto);
    }

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
     */
    private static String validarEstado(Factura.Estado estado) {

        if (estado == null) {
            return "El estado es obligatorio";
        }

        return null;
    }
}
