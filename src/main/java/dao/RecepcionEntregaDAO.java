package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.EquipoMovil;
import modelo.RecepcionEntrega;
import modelo.Usuario;

/**
 * DAO encargado de las operaciones CRUD de la entidad RecepcionEntrega.
 *
 * @author Baque Diego
 */
public class RecepcionEntregaDAO {

    /**
     * Lista todos las recepciones.
     *
     * @return lista de recepciones
     */
    public List<RecepcionEntrega> listar() {
        List<RecepcionEntrega> lista = new ArrayList<>();

        String sql = """
            SELECT r.*, 
                   e.id AS equipo_id, e.marca, e.modelo, e.imei, e.tipo, e.descripcion_danio, e.estado AS estado_equipo,
                   c.id AS cliente_id, c.nombre,
                   u.id AS usuario_id, u.nombre AS usuario_nombre
            FROM recepcion_entrega r
            INNER JOIN equipo_movil e ON r.equipo_id = e.id
            INNER JOIN cliente c ON e.cliente_id = c.id
            LEFT JOIN usuario u ON r.usuario_id = u.id
        """;

        try (Connection con = Conexion.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Cliente c = new Cliente();
                c.setId(rs.getInt("cliente_id"));
                c.setNombre(rs.getString("nombre"));

                EquipoMovil e = new EquipoMovil();
                e.setId(rs.getInt("equipo_id"));
                e.setMarca(rs.getString("marca"));
                e.setModelo(rs.getString("modelo"));
                e.setImei(rs.getString("imei"));
                e.setTipo(rs.getString("tipo"));
                e.setDescripcionDanio(rs.getString("descripcion_danio"));
                e.setEstado(EquipoMovil.Estado.valueOf(rs.getString("estado_equipo")));
                e.setCliente(c);

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                u.setNombre(rs.getString("usuario_nombre"));

                RecepcionEntrega r = new RecepcionEntrega();
                r.setId(rs.getInt("id"));
                r.setFechaRecepcion(rs.getTimestamp("fecha_recepcion").toLocalDateTime());
                r.setProblemaReportado(rs.getString("problema_reportado"));
                r.setEstado(RecepcionEntrega.Estado.valueOf(rs.getString("estado")));
                r.setEquipoMovil(e);
                r.setUsuario(u);

                lista.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar recepciones", e);
        }

        return lista;
    }

    /**
     * Busca una recepción por su ID.
     *
     * @param idRecepcion ID de la recepción
     * @return recepción encontrada o null si no existe
     */
    public RecepcionEntrega obtenerPorId(int idRecepcion) {
        String sql = """
        SELECT r.*, 
               e.id AS equipo_id, e.marca, e.modelo, e.imei, e.tipo, e.descripcion_danio, e.estado AS estado_equipo,
               c.id AS cliente_id, c.nombre,
               u.id AS usuario_id, u.nombre AS usuario_nombre
        FROM recepcion_entrega r
        INNER JOIN equipo_movil e ON r.equipo_id = e.id
        INNER JOIN cliente c ON e.cliente_id = c.id
        LEFT JOIN usuario u ON r.usuario_id = u.id
        WHERE r.id = ?
    """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idRecepcion);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {

                    Cliente c = new Cliente();
                    c.setId(rs.getInt("cliente_id"));
                    c.setNombre(rs.getString("nombre"));

                    EquipoMovil e = new EquipoMovil();
                    e.setId(rs.getInt("equipo_id"));
                    e.setMarca(rs.getString("marca"));
                    e.setModelo(rs.getString("modelo"));
                    e.setImei(rs.getString("imei"));
                    e.setTipo(rs.getString("tipo"));
                    e.setDescripcionDanio(rs.getString("descripcion_danio"));
                    e.setEstado(EquipoMovil.Estado.valueOf(rs.getString("estado_equipo")));
                    e.setCliente(c);

                    Usuario u = new Usuario();
                    u.setId(rs.getInt("usuario_id"));
                    u.setNombre(rs.getString("usuario_nombre"));

                    RecepcionEntrega r = new RecepcionEntrega();
                    r.setId(rs.getInt("id"));
                    r.setFechaRecepcion(rs.getTimestamp("fecha_recepcion").toLocalDateTime());
                    r.setProblemaReportado(rs.getString("problema_reportado"));
                    r.setEstado(RecepcionEntrega.Estado.valueOf(rs.getString("estado")));
                    r.setEquipoMovil(e);
                    r.setUsuario(u);

                    return r;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener recepción", e);
        }

        return null;
    }

    /**
     * Guarda una recepción en la base de datos.
     *
     * @param r objeto recepción
     * @return true si se insertó correctamente
     */
    public boolean guardar(RecepcionEntrega r) {
        String sql = """
            INSERT INTO recepcion_entrega
            (fecha_recepcion, problema_reportado, equipo_id, usuario_id)
            VALUES (?,?,?,?)
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setTimestamp(1, Timestamp.valueOf(r.getFechaRecepcion()));
            pst.setString(2, r.getProblemaReportado());
            pst.setInt(3, r.getEquipoMovil().getId());
            pst.setInt(4, r.getUsuario().getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar recepción", e);
        }
    }

    /**
     * Actualiza los datos de una recepción existente.
     *
     * @param r objeto con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(RecepcionEntrega r) {
        String sql = """
            UPDATE recepcion_entrega
            SET problema_reportado=?, usuario_id=?
            WHERE id=?
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, r.getProblemaReportado());
            pst.setInt(2, r.getUsuario().getId());
            pst.setInt(3, r.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar recepción", e);
        }
    }

    /**
     * Anula una recepción por su ID.
     *
     * @param idRecepcion ID de la recepción
     * @return true si se anuló correctamente
     */
    public boolean eliminar(int idRecepcion) {
        String sql = """
            UPDATE recepcion_entrega
            SET estado = 'ANULADO'
            WHERE id = ?
            AND estado = 'RECIBIDO'
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idRecepcion);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al anular recepción", e);
        }
    }

    /**
     * Filtra recepciones por cliente o equipo.
     *
     * @param texto texto a buscar
     * @return lista de recepciones que coinciden
     */
    public List<RecepcionEntrega> filtrar(String texto) {
        List<RecepcionEntrega> lista = new ArrayList<>();

        String sql = """
            SELECT r.*, 
                   e.id AS equipo_id, e.marca, e.modelo,
                   c.id AS cliente_id, c.nombre,
                   u.id AS usuario_id, u.nombre AS usuario_nombre
            FROM recepcion_entrega r
            INNER JOIN equipo_movil e ON r.equipo_id = e.id
            INNER JOIN cliente c ON e.cliente_id = c.id
            LEFT JOIN usuario u ON r.usuario_id = u.id
            WHERE LOWER(c.nombre) LIKE ?
               OR LOWER(e.marca) LIKE ?
               OR LOWER(e.modelo) LIKE ?
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            String filtro = "%" + texto.toLowerCase() + "%";

            pst.setString(1, filtro);
            pst.setString(2, filtro);
            pst.setString(3, filtro);

            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    Cliente c = new Cliente();
                    c.setId(rs.getInt("cliente_id"));
                    c.setNombre(rs.getString("nombre"));

                    EquipoMovil e = new EquipoMovil();
                    e.setId(rs.getInt("equipo_id"));
                    e.setMarca(rs.getString("marca"));
                    e.setModelo(rs.getString("modelo"));
                    e.setCliente(c);

                    Usuario u = new Usuario();
                    u.setId(rs.getInt("usuario_id"));
                    u.setNombre(rs.getString("usuario_nombre"));

                    RecepcionEntrega r = new RecepcionEntrega();
                    r.setId(rs.getInt("id"));
                    r.setProblemaReportado(rs.getString("problema_reportado"));
                    r.setEstado(RecepcionEntrega.Estado.valueOf(rs.getString("estado")));
                    r.setEquipoMovil(e);
                    r.setUsuario(u);

                    lista.add(r);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar recepciones", e);
        }

        return lista;
    }
}
