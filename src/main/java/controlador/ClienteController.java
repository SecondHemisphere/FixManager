package controlador;

import dao.ClienteDAO;
import dao.EquipoMovilDAO;
import java.util.List;
import java.util.regex.Pattern;
import modelo.Cliente;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad Cliente.
 *
 * Encargado de aplicar validaciones y reglas de negocio antes de acceder a la
 * capa DAO. También controla restricciones de eliminación cuando el cliente
 * tiene equipos asociados.
 *
 * @author Cagua Derek
 */
public class ClienteController {

    private final ClienteDAO dao = new ClienteDAO();
    private final EquipoMovilDAO equipoDao = new EquipoMovilDAO();

    private static final Pattern SOLO_NUMEROS = Pattern.compile("\\d+");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Obtiene la lista de todos los clientes.
     *
     * @return lista de clientes registrados
     */
    public List<Cliente> listarClientes() {
        return dao.listar();
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param id identificador del cliente
     * @return cliente encontrado o null si no existe
     */
    public Cliente obtenerCliente(int id) {
        return dao.obtenerPorId(id);
    }

    /**
     * Guarda un nuevo cliente.
     *
     * @param c cliente a registrar
     * @return resultado de la operación
     */
    public ResultadoOperacion guardarCliente(Cliente c) {
        String error = validar(c);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

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

    /**
     * Actualiza un cliente existente.
     *
     * @param c cliente con datos actualizados
     * @return resultado de la operación
     */
    public ResultadoOperacion actualizarCliente(Cliente c) {
        String error = validar(c);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

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

    /**
     * Elimina un cliente si no tiene equipos móviles asociados.
     *
     * @param id identificador del cliente
     * @return resultado de la operación
     */
    public ResultadoOperacion eliminarCliente(int id) {
        Cliente c = dao.obtenerPorId(id);

        if (c == null) {
            return ResultadoOperacion.error("Cliente no encontrado");
        }

        if (equipoDao.existePorCliente(id)) {
            return ResultadoOperacion.error(
                    "No se puede eliminar un cliente con equipos móviles registrados"
            );
        }

        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Cliente eliminado correctamente")
                : ResultadoOperacion.error("No se pudo eliminar el cliente");
    }

    /**
     * Filtra clientes por nombre.
     *
     * @param texto texto de búsqueda
     * @return lista de clientes que coinciden
     */
    public List<Cliente> filtrarClientes(String texto) {
        return dao.filtrarPorNombre(texto);
    }

    /**
     * Valida un cliente antes de guardarlo o actualizarlo.
     *
     * @param c cliente a validar
     * @return mensaje de error o null si es válido
     */
    public static String validar(Cliente c) {

        if (c == null) {
            return "Cliente no puede ser nulo";
        }

        String error = validarNombre(c.getNombre());
        if (error != null) {
            return error;
        }

        error = validarCorreo(c.getCorreo());
        if (error != null) {
            return error;
        }

        error = validarTelefono(c.getTelefono());
        if (error != null) {
            return error;
        }

        error = validarDireccion(c.getDireccion());
        if (error != null) {
            return error;
        }

        return null;
    }

    /**
     * Valida el nombre del cliente.
     *
     * @param nombre nombre del cliente
     * @return error o null si es válido
     */
    public static String validarNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre es obligatorio";
        }

        nombre = nombre.trim();

        if (nombre.length() < 3) {
            return "El nombre debe tener al menos 3 caracteres";
        }

        if (nombre.length() > 100) {
            return "El nombre no puede superar 100 caracteres";
        }

        return null;
    }

    /**
     * Valida el correo electrónico del cliente.
     *
     * @param correo correo del cliente
     * @return error o null si es válido
     */
    public static String validarCorreo(String correo) {

        if (correo == null || correo.trim().isEmpty()) {
            return "El correo es obligatorio";
        }

        correo = correo.trim();

        if (!EMAIL.matcher(correo).matches()) {
            return "Correo inválido";
        }

        if (correo.length() > 100) {
            return "El correo no puede superar 100 caracteres";
        }

        return null;
    }

    /**
     * Valida el teléfono del cliente.
     *
     * @param telefono número telefónico
     * @return error o null si es válido
     */
    public static String validarTelefono(String telefono) {

        if (telefono == null || telefono.trim().isEmpty()) {
            return "El teléfono es obligatorio";
        }

        telefono = telefono.trim();

        if (!SOLO_NUMEROS.matcher(telefono).matches()) {
            return "El teléfono debe contener solo números";
        }

        if (telefono.length() < 7) {
            return "El teléfono debe tener al menos 7 dígitos";
        }

        if (telefono.length() > 20) {
            return "El teléfono no puede superar 20 caracteres";
        }

        return null;
    }

    /**
     * Valida la dirección del cliente.
     *
     * @param direccion dirección del cliente
     * @return error o null si es válida
     */
    public static String validarDireccion(String direccion) {

        if (direccion == null || direccion.trim().isEmpty()) {
            return null;
        }

        direccion = direccion.trim();

        if (direccion.length() < 5) {
            return "La dirección debe tener al menos 5 caracteres";
        }

        if (direccion.length() > 150) {
            return "La dirección no puede superar 150 caracteres";
        }

        return null;
    }
}
