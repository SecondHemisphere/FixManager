package controlador;

import dao.ClienteDAO;
import java.util.List;
import modelo.Cliente;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad Cliente.
 *
 * @author Cagua Derek
 */
public class ClienteController {

    private final ClienteDAO dao = new ClienteDAO();

    public List<Cliente> listarClientes() {
        return dao.listar();
    }

    public Cliente obtenerCliente(int id) {
        return dao.obtenerPorId(id);
    }

    public ResultadoOperacion guardarCliente(Cliente c) {

        if (dao.existeCorreo(c.getCorreo(), null)) {
            return ResultadoOperacion.error("El correo ya está registrado", "correo");
        }

        if (dao.existeTelefono(c.getTelefono(), null)) {
            return ResultadoOperacion.error("El teléfono ya está registrado", "telefono");
        }

        boolean ok = dao.guardar(c);

        return ok
                ? ResultadoOperacion.exito("Cliente guardado correctamente")
                : ResultadoOperacion.error("No se pudo guardar el cliente");
    }

    public ResultadoOperacion actualizarCliente(Cliente c) {

        if (dao.existeCorreo(c.getCorreo(), c.getId())) {
            return ResultadoOperacion.error("El correo ya está registrado", "correo");
        }

        if (dao.existeTelefono(c.getTelefono(), c.getId())) {
            return ResultadoOperacion.error("El teléfono ya está registrado", "telefono");
        }

        boolean ok = dao.actualizar(c);

        return ok
                ? ResultadoOperacion.exito("Cliente actualizado correctamente")
                : ResultadoOperacion.error("No se pudo actualizar el cliente");
    }

    public ResultadoOperacion eliminarCliente(int id) {
        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Cliente eliminado correctamente")
                : ResultadoOperacion.error("No se pudo eliminar el cliente");
    }

    public List<Cliente> filtrarClientes(String texto) {
        return dao.filtrarPorNombre(texto);
    }
}
