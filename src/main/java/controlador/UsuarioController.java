package controlador;

import dao.UsuarioDAO;
import java.util.List;
import modelo.Usuario;
import util.ResultadoOperacion;
import validator.UsuarioValidator;

/**
 * Controlador de la entidad Usuario.
 *
 * @author Mendoza Sebastian
 */
public class UsuarioController {

    private final UsuarioDAO dao = new UsuarioDAO();

    public List<Usuario> listarUsuarios() {
        return dao.listar();
    }

    public Usuario obtenerUsuario(int id) {
        return dao.obtenerPorId(id);
    }

    public ResultadoOperacion guardarUsuario(Usuario u) {

        String error = UsuarioValidator.validar(u);
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

        String error = UsuarioValidator.validar(u);
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
}
