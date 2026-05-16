package controlador;

import dao.UsuarioDAO;
import java.util.List;
import java.util.regex.Pattern;
import modelo.Usuario;
import modelo.Usuario.Rol;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad Usuario.
 *
 * Encargado de aplicar la lógica de negocio antes de interactuar con la capa
 * DAO. Realiza validaciones, control de duplicados y manejo de resultados.
 *
 * @author Mendoza Sebastian
 */
public class UsuarioController {

    private final UsuarioDAO dao = new UsuarioDAO();
    
    private static final Pattern EMAIL = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    public List<Usuario> listarUsuarios() {
        return dao.listar();
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o null si no existe
     */
    public Usuario obtenerUsuario(int id) {
        return dao.obtenerPorId(id);
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param u usuario a guardar
     * @return resultado de la operación
     */
    public ResultadoOperacion guardarUsuario(Usuario u) {

        String error = validar(u);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        if (dao.existeCorreo(u.getCorreo(), null)) {
            return ResultadoOperacion.error("El correo ya está registrado", "correo");
        }

        boolean ok = dao.guardar(u);

        return ok
                ? ResultadoOperacion.exito("Usuario guardado correctamente")
                : ResultadoOperacion.error("No se pudo guardar el usuario");
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param u usuario con datos actualizados
     * @return resultado de la operación
     */
    public ResultadoOperacion actualizarUsuario(Usuario u) {

        String error = validar(u);

        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        if (dao.existeCorreo(u.getCorreo(), u.getId())) {
            return ResultadoOperacion.error("El correo ya está registrado", "correo");
        }

        boolean ok = dao.actualizar(u);

        return ok
                ? ResultadoOperacion.exito("Usuario actualizado correctamente")
                : ResultadoOperacion.error("No se pudo actualizar el usuario");
    }

    /**
     * Desactiva un usuario (baja lógica).
     *
     * @param id identificador del usuario
     * @return resultado de la operación
     */
    public ResultadoOperacion desactivarUsuario(int id) {

        boolean ok = dao.desactivar(id);

        return ok
                ? ResultadoOperacion.exito("Usuario desactivado correctamente")
                : ResultadoOperacion.error("No se pudo desactivar el usuario");
    }

    /**
     * Filtra usuarios por nombre o correo.
     *
     * @param texto texto de búsqueda
     * @return lista de usuarios que coinciden
     */
    public List<Usuario> filtrarUsuarios(String texto) {
        return dao.filtrar(texto);
    }

    /**
     * Realiza el inicio de sesión del usuario.
     *
     * @param correo correo ingresado
     * @param contrasena contraseña ingresada
     * @return usuario autenticado o null si las credenciales son inválidas
     */
    public Usuario login(String correo, String contrasena) {
        return dao.login(correo, contrasena);
    }

    /**
     * Verifica si un usuario está activo.
     *
     * @param u usuario a validar
     * @return true si el usuario está activo, false en caso contrario
     */
    public boolean usuarioActivo(Usuario u) {
        return u != null && u.isActivo();
    }

    /**
     * Valida todos los campos del usuario.
     *
     * @param u objeto usuario a validar
     * @return mensaje de error si existe, null si es válido
     */
    public static String validar(Usuario u) {

        if (u == null) {
            return "El usuario no puede ser nulo";
        }

        String error = validarNombre(u.getNombre());
        if (error != null) {
            return error;
        }

        error = validarCorreo(u.getCorreo());
        if (error != null) {
            return error;
        }

        if (u.getContrasena() != null && !u.getContrasena().isEmpty()) {
            error = validarPassword(u.getContrasena());
            if (error != null) {
                return error;
            }
        }

        error = validarRol(u.getRol());
        if (error != null) {
            return error;
        }

        return null;
    }

    /**
     * Valida el nombre del usuario.
     *
     * @param nombre nombre del usuario a validar
     * @return mensaje de error si es inválido, null si es válido
     */
    private static String validarNombre(String nombre) {

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
     * Valida el correo electrónico del usuario.
     *
     * @param correo correo a validar
     * @return mensaje de error si es inválido, null si es válido
     */
    private static String validarCorreo(String correo) {

        if (correo == null || correo.trim().isEmpty()) {
            return "El correo es obligatorio";
        }

        correo = correo.trim();

        if (!EMAIL.matcher(correo).matches()) {
            return "Correo inválido";
        }

        return null;
    }

    /**
     * Valida la contraseña del usuario.
     *
     * @param password contraseña a validar
     * @return mensaje de error si es inválido, null si es válido
     */
    private static String validarPassword(String password) {

        if (password == null || password.trim().isEmpty()) {
            return "La contraseña es obligatoria";
        }

        password = password.trim();

        if (password.length() < 4) {
            return "La contraseña debe tener al menos 4 caracteres";
        }

        return null;
    }

    /**
     * Valida el rol del usuario.
     *
     * @param rol rol del usuario a validar
     * @return mensaje de error si es inválido, null si es válido
     */
    private static String validarRol(Rol rol) {

        if (rol == null) {
            return "El rol es obligatorio";
        }

        return null;
    }
}
