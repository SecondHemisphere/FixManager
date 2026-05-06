package controlador;

import dao.EquipoMovilDAO;
import java.util.List;
import modelo.EquipoMovil;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad EquipoMovil.
 *
 * @author Gelves Jonathan
 */
public class EquipoMovilController {

    private final EquipoMovilDAO dao = new EquipoMovilDAO();

    public List<EquipoMovil> listarEquipos() {
        return dao.listar();
    }

    public EquipoMovil obtenerEquipo(int id) {
        return dao.obtenerPorId(id);
    }

    public ResultadoOperacion guardarEquipo(EquipoMovil e) {
        boolean ok = dao.guardar(e);

        return ok
                ? ResultadoOperacion.exito("Equipo móvil registrado correctamente")
                : ResultadoOperacion.error("No se pudo guardar el equipo móvil");
    }

    public ResultadoOperacion actualizarEquipo(EquipoMovil e) {
        boolean ok = dao.actualizar(e);

        return ok
                ? ResultadoOperacion.exito("Equipo móvil actualizado correctamente")
                : ResultadoOperacion.error("No se pudo actualizar el equipo móvil");
    }

    public ResultadoOperacion eliminarEquipo(int id) {
        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Equipo móvil eliminado correctamente")
                : ResultadoOperacion.error("No se pudo eliminar el equipo móvil");
    }

    public List<EquipoMovil> filtrarEquipos(String texto) {
        return dao.filtrar(texto);
    }
}
