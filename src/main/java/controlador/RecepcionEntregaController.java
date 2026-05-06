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
        boolean ok = dao.guardar(r);

        return ok
                ? ResultadoOperacion.exito("Recepción registrada correctamente")
                : ResultadoOperacion.error("No se pudo registrar la recepción");
    }

    public ResultadoOperacion actualizarRecepcion(RecepcionEntrega r) {
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
}
