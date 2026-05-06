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
}
