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
        boolean ok = dao.guardar(r);

        return ok
                ? ResultadoOperacion.exito("Reparación registrada correctamente")
                : ResultadoOperacion.error("No se pudo registrar");
    }

    public ResultadoOperacion actualizar(Reparacion r) {
        boolean ok = dao.actualizar(r);

        return ok
                ? ResultadoOperacion.exito("Reparación actualizada")
                : ResultadoOperacion.error("No se pudo actualizar");
    }

    public List<Reparacion> filtrar(String texto) {
        return dao.filtrar(texto);
    }
}
