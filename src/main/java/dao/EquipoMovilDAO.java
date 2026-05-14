package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.EquipoMovil;

/**
 * DAO encargado de las operaciones CRUD de la entidad EquipoMovil.
 *
 * @author Gelves Jonathan
 */
public class EquipoMovilDAO {

    /**
     * Lista todos los equipos móviles registrados.
     *
     * @return lista de equipos móviles
     */
    public List<EquipoMovil> listar() {
        List<EquipoMovil> lista = new ArrayList<>();

        String sql = """
            SELECT e.*, c.id AS cliente_id, c.nombre, c.correo, c.telefono, c.direccion
            FROM equipo_movil e
            INNER JOIN cliente c ON e.cliente_id = c.id
        """;

        try (Connection con = Conexion.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Cliente c = new Cliente();
                c.setId(rs.getInt("cliente_id"));
                c.setNombre(rs.getString("nombre"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getString("telefono"));
                c.setDireccion(rs.getString("direccion"));

                EquipoMovil e = new EquipoMovil();
                e.setId(rs.getInt("id"));
                e.setMarca(rs.getString("marca"));
                e.setModelo(rs.getString("modelo"));
                e.setImei(rs.getString("imei"));
                e.setTipo(rs.getString("tipo"));
                e.setDescripcionDanio(rs.getString("descripcion_danio"));

                e.setEstado(EquipoMovil.Estado.valueOf(rs.getString("estado")));
                e.setCliente(c);

                lista.add(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar equipos móviles", e);
        }

        return lista;
    }

    /**
     * Busca un equipo móvil por su ID.
     *
     * @param idEquipo ID del equipo móvil
     * @return equipo móvil encontrado o null si no existe
     */
    public EquipoMovil obtenerPorId(int idEquipo) {

        String sql = """
            SELECT e.*, c.id AS cliente_id, c.nombre, c.correo, c.telefono, c.direccion
            FROM equipo_movil e
            INNER JOIN cliente c ON e.cliente_id = c.id
            WHERE e.id = ?
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idEquipo);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {

                    Cliente c = new Cliente();
                    c.setId(rs.getInt("cliente_id"));
                    c.setNombre(rs.getString("nombre"));
                    c.setCorreo(rs.getString("correo"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setDireccion(rs.getString("direccion"));

                    EquipoMovil e = new EquipoMovil();
                    e.setId(rs.getInt("id"));
                    e.setMarca(rs.getString("marca"));
                    e.setModelo(rs.getString("modelo"));
                    e.setImei(rs.getString("imei"));
                    e.setTipo(rs.getString("tipo"));
                    e.setDescripcionDanio(rs.getString("descripcion_danio"));
                    e.setEstado(EquipoMovil.Estado.valueOf(rs.getString("estado")));
                    e.setCliente(c);

                    return e;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener equipo móvil", e);
        }

        return null;
    }

    /**
     * Guarda un equipo móvil en la base de datos.
     *
     * @param e objeto equipo móvil
     * @return true si se insertó correctamente
     */
    public boolean guardar(EquipoMovil e) {

        String sql = """
            INSERT INTO equipo_movil
            (marca, modelo, imei, tipo, descripcion_danio, cliente_id)
            VALUES (?,?,?,?,?,?)
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, e.getMarca());
            pst.setString(2, e.getModelo());
            pst.setString(3, e.getImei());
            pst.setString(4, e.getTipo());
            pst.setString(5, e.getDescripcionDanio());
            pst.setInt(6, e.getCliente().getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException ex) {
            throw new RuntimeException("Error al guardar equipo móvil", ex);
        }
    }

    /**
     * Actualiza los datos de un equipo móvil existente.
     *
     * @param e objeto con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(EquipoMovil e) {
        String sql = """
            UPDATE equipo_movil
            SET marca=?, modelo=?, imei=?, tipo=?, descripcion_danio=?, cliente_id=?
            WHERE id=?
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, e.getMarca());
            pst.setString(2, e.getModelo());
            pst.setString(3, e.getImei());
            pst.setString(4, e.getTipo());
            pst.setString(5, e.getDescripcionDanio());
            pst.setInt(6, e.getCliente().getId());
            pst.setInt(7, e.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException ex) {
            throw new RuntimeException("Error al actualizar equipo móvil", ex);
        }
    }

    /**
     * Elimina un equipo móvil por su ID.
     *
     * @param idEquipo ID del equipo móvil
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int idEquipo) {

        String sql = "DELETE FROM equipo_movil WHERE id=?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idEquipo);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar equipo móvil", e);
        }
    }

    /**
     * Filtra equipos móviles por IMEI o nombre del cliente.
     *
     * @param texto texto a buscar
     * @return lista de equipos que coinciden
     */
    public List<EquipoMovil> filtrar(String texto) {
        List<EquipoMovil> lista = new ArrayList<>();

        String sql = """
        SELECT e.*, c.id AS cliente_id, c.nombre, c.correo, c.telefono, c.direccion
        FROM equipo_movil e
        INNER JOIN cliente c ON e.cliente_id = c.id
        WHERE LOWER(e.imei) LIKE ?
           OR LOWER(c.nombre) LIKE ?
    """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            String filtro = "%" + texto.toLowerCase() + "%";

            pst.setString(1, filtro);
            pst.setString(2, filtro);

            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    Cliente c = new Cliente();
                    c.setId(rs.getInt("cliente_id"));
                    c.setNombre(rs.getString("nombre"));
                    c.setCorreo(rs.getString("correo"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setDireccion(rs.getString("direccion"));

                    EquipoMovil e = new EquipoMovil();
                    e.setId(rs.getInt("id"));
                    e.setMarca(rs.getString("marca"));
                    e.setModelo(rs.getString("modelo"));
                    e.setImei(rs.getString("imei"));
                    e.setTipo(rs.getString("tipo"));
                    e.setDescripcionDanio(rs.getString("descripcion_danio"));
                    e.setEstado(EquipoMovil.Estado.valueOf(rs.getString("estado")));
                    e.setCliente(c);

                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar equipos móviles", e);
        }

        return lista;
    }

    /**
     * Verifica si un cliente tiene equipos móviles registrados.
     *
     * @param idCliente ID del cliente
     * @return true si el cliente tiene al menos un equipo registrado
     */
    public boolean existePorCliente(int idCliente) {

        String sql = """
            SELECT 1
            FROM equipo_movil
            WHERE cliente_id = ?
            LIMIT 1
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idCliente);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar equipos del cliente", e);
        }
    }
}
