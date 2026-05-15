package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario.Rol;
import modelo.Usuario;

/**
 * DAO encargado de las operaciones CRUD de la entidad Usuario.
 *
 * @author Mendoza Sebastian
 */
public class UsuarioDAO {

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return lista de usuarios
     */
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Usuario u = mapear(rs);
                lista.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios", e);
        }

        return lista;
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario
     * @return usuario encontrado o null si no existe
     */
    public Usuario obtenerPorId(int id) {

        String sql = "SELECT * FROM usuario WHERE id=?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario", e);
        }

        return null;
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param u objeto usuario
     * @return true si se insertó correctamente
     */
    public boolean guardar(Usuario u) {

        String sql = """
            INSERT INTO usuario (nombre, correo, contrasena, rol, activo)
            VALUES (?,?,?,?,?)
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, u.getNombre());
            pst.setString(2, u.getCorreo());
            pst.setString(3, u.getContrasena());
            pst.setString(4, u.getRol().name());
            pst.setBoolean(5, u.isActivo());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param u objeto con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(Usuario u) {

        String sql = """
            UPDATE usuario
            SET nombre=?, correo=?, contrasena=?, rol=?, activo=?
            WHERE id=?
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, u.getNombre());
            pst.setString(2, u.getCorreo());
            pst.setString(3, u.getContrasena());
            pst.setString(4, u.getRol().name());
            pst.setBoolean(5, u.isActivo());
            pst.setInt(6, u.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    /**
     * Desactiva un usuario por su ID.
     *
     * @param id ID del usuario
     * @return true si se desactivó correctamente
     */
    public boolean desactivar(int id) {
        String sql = "UPDATE usuario SET activo = FALSE WHERE id = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al desactivar usuario", e);
        }
    }

    /**
     * Verifica si existe un usuario con cierto correo en la base de datos. Si
     * idExcluido es null, valida para INSERT. Si tiene valor, valida para
     * UPDATE.
     *
     * @param correo correo a verificar
     * @param idExcluido ID a excluir (puede ser null)
     * @return true si el correo ya existe
     */
    public boolean existeCorreo(String correo, Integer idExcluido) {

        String sql;

        if (idExcluido == null) {
            sql = "SELECT 1 FROM usuario WHERE correo=? LIMIT 1";
        } else {
            sql = "SELECT 1 FROM usuario WHERE correo=? AND id<>? LIMIT 1";
        }

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, correo);

            if (idExcluido != null) {
                pst.setInt(2, idExcluido);
            }

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar correo", e);
        }
    }

    /**
     * Filtra usuarios por nombre o correo.
     *
     * @param texto texto a buscar
     * @return lista de usuarios que coinciden
     */
    public List<Usuario> filtrar(String texto) {

        List<Usuario> lista = new ArrayList<>();

        String sql = """
        SELECT * FROM usuario
        WHERE LOWER(nombre) LIKE ?
           OR LOWER(correo) LIKE ?
    """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            String filtro = "%" + texto.toLowerCase() + "%";

            pst.setString(1, filtro);
            pst.setString(2, filtro);

            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar usuarios", e);
        }

        return lista;
    }

    /**
     * Realiza el proceso de autenticación de un usuario.
     *
     * @param correo correo ingresado por el usuario
     * @param contrasena contraseña ingresada
     * @return objeto Usuario si las credenciales son correctas null en caso
     * contrario
     */
    public Usuario login(String correo, String contrasena) {
        String sql = "SELECT * FROM usuario WHERE correo = ? AND contrasena = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, correo);
            pst.setString(2, contrasena);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en login", e);
        }
        return null;
    }

    /**
     * Convierte una fila del ResultSet en un objeto Usuario.
     *
     * @param rs resultado de la consulta SQL
     * @return objeto Usuario con los datos de la BD
     * @throws SQLException si ocurre un error al leer datos
     */
    private Usuario mapear(ResultSet rs) throws SQLException {

        Usuario u = new Usuario();

        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setCorreo(rs.getString("correo"));
        u.setContrasena(rs.getString("contrasena"));
        u.setRol(Rol.valueOf(rs.getString("rol")));
        u.setActivo(rs.getBoolean("activo"));

        return u;
    }
}
