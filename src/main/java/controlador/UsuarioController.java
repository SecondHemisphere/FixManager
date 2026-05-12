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
 * @author Mendoza Sebastian
 */
public class UsuarioController {

    private final UsuarioDAO dao = new UsuarioDAO();
    private static final Pattern EMAIL = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public List<Usuario> listarUsuarios() {
        return dao.listar();
    }

    public Usuario obtenerUsuario(int id) {
        return dao.obtenerPorId(id);
    }

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

    public ResultadoOperacion eliminarUsuario(int id) {
        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Usuario eliminado correctamente")
                : ResultadoOperacion.error("No se pudo eliminar el usuario");
    }

    public List<Usuario> filtrarUsuarios(String texto) {
        return dao.filtrar(texto);
    }

    public Usuario login(String correo, String password) {
        Usuario u = dao.login(correo, password);

        if (u != null && !u.isActivo()) {
            return null;
        }

        return u;
    }

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
     * Valida el correo electrónico.
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
     * Valida la contraseña.
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
     */
    private static String validarRol(Rol rol) {

        if (rol == null) {
            return "El rol es obligatorio";
        }

        return null;
    }
}
