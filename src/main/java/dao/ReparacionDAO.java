package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.RecepcionEntrega;
import modelo.Reparacion;

/**
 * DAO encargado de las operaciones CRUD de la entidad Reparacion.
 *
 * @author Pluas Kevin
 */
public class ReparacionDAO {

    /**
     * Lista todos las reparaciones.
     *
     * @return lista de reparaciones
     */
    public List<Reparacion> listar() {
        List<Reparacion> lista = new ArrayList<>();

        String sql = """
        SELECT r.*, 
               re.id AS recepcion_id,
               c.nombre AS cliente,
               e.marca, e.modelo,
               u.nombre AS usuario
        FROM reparacion r
        INNER JOIN recepcion_entrega re ON r.recepcion_id = re.id
        INNER JOIN equipo_movil e ON re.equipo_id = e.id
        INNER JOIN cliente c ON e.cliente_id = c.id
        INNER JOIN usuario u ON r.usuario_id = u.id
    """;

        try (Connection con = Conexion.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                modelo.Cliente c = new modelo.Cliente();
                c.setNombre(rs.getString("cliente"));

                modelo.EquipoMovil e = new modelo.EquipoMovil();
                e.setMarca(rs.getString("marca"));
                e.setModelo(rs.getString("modelo"));
                e.setCliente(c);

                modelo.Usuario u = new modelo.Usuario();
                u.setNombre(rs.getString("usuario"));

                RecepcionEntrega re = new RecepcionEntrega();
                re.setId(rs.getInt("recepcion_id"));
                re.setEquipoMovil(e);

                Reparacion r = new Reparacion();
                r.setId(rs.getInt("id"));
                r.setDiagnostico(rs.getString("diagnostico"));
                r.setSolucion(rs.getString("solucion"));
                r.setCostoRepuestos(rs.getDouble("costo_repuestos"));
                r.setPiezasUsadas(rs.getString("piezas_usadas"));
                r.setEstado(Reparacion.Estado.valueOf(rs.getString("estado")));
                r.setRecepcion(re);
                r.setUsuario(u);

                lista.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar reparaciones", e);
        }

        return lista;
    }

    /**
     * Verifica si ya existe una reparación asociada a una recepción específica.
     *
     * @param recepcionId ID de la recepción
     * @return true si la recepción ya tiene una reparación registrada, false en
     * caso contrario
     */
    public boolean existePorRecepcion(int recepcionId) {
        String sql = "SELECT COUNT(*) FROM reparacion WHERE recepcion_id=?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, recepcionId);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar reparación", e);
        }
    }

    /**
     * Verifica si la reparación tiene una factura válida asociada.
     *
     * @param reparacionId ID de la reparación
     * @return true si existe una factura válida
     */
    public boolean tieneFactura(int reparacionId) {
        String sql = """
            SELECT COUNT(*)
            FROM factura
            WHERE reparacion_id = ?
              AND estado <> 'ANULADA'
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, reparacionId);

            try (ResultSet rs = pst.executeQuery()) {

                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar factura", e);
        }
    }

    /**
     * Guarda una reparación en la base de datos.
     *
     * @param r objeto reparación
     * @return true si se insertó correctamente
     */
    public boolean guardar(Reparacion r) {

        if (r.getRecepcion() == null) {
            throw new RuntimeException("Recepción no puede ser null");
        }

        if (r.getUsuario() == null) {
            throw new RuntimeException("Usuario no puede ser null");
        }

        if (existePorRecepcion(r.getRecepcion().getId())) {
            throw new RuntimeException("Ya existe una reparación para esta recepción");
        }

        String sql = """
            INSERT INTO reparacion
            (diagnostico, solucion, costo_repuestos, piezas_usadas, estado, recepcion_id, usuario_id)
            VALUES (?,?,?,?,?,?,?)
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, r.getDiagnostico());
            pst.setString(2, r.getSolucion());
            pst.setDouble(3, r.getCostoRepuestos());
            pst.setString(4, r.getPiezasUsadas());
            pst.setString(5, r.getEstado().name());
            pst.setInt(6, r.getRecepcion().getId());
            pst.setInt(7, r.getUsuario().getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar reparación", e);
        }
    }

    /**
     * Actualiza los datos de una reparación existente.
     *
     * @param r objeto con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(Reparacion r) {
        String sql = """
            UPDATE reparacion
            SET diagnostico=?, solucion=?, costo_repuestos=?, piezas_usadas=?, estado=?, usuario_id=?
            WHERE id=?
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, r.getDiagnostico());
            pst.setString(2, r.getSolucion());
            pst.setDouble(3, r.getCostoRepuestos());
            pst.setString(4, r.getPiezasUsadas());
            pst.setString(5, r.getEstado().name());
            pst.setInt(6, r.getUsuario().getId());
            pst.setInt(7, r.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar reparación", e);
        }
    }

    /**
     * Filtra reparaciones por nombre del cliente o estado.
     *
     * @param texto texto a buscar
     * @return lista de reparaciones que coinciden
     */
    public List<Reparacion> filtrar(String texto) {

        List<Reparacion> lista = new ArrayList<>();

        String sql = """
        SELECT r.*, 
               re.id AS recepcion_id,
               c.nombre AS cliente,
               e.marca, e.modelo,
               u.nombre AS usuario
        FROM reparacion r
        INNER JOIN recepcion_entrega re ON r.recepcion_id = re.id
        INNER JOIN equipo_movil e ON re.equipo_id = e.id
        INNER JOIN cliente c ON e.cliente_id = c.id
        INNER JOIN usuario u ON r.usuario_id = u.id
        WHERE LOWER(c.nombre) LIKE ?
           OR LOWER(r.estado) LIKE ?
    """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            String filtro = "%" + texto.toLowerCase() + "%";

            pst.setString(1, filtro);
            pst.setString(2, filtro);

            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    modelo.Cliente c = new modelo.Cliente();
                    c.setNombre(rs.getString("cliente"));

                    modelo.EquipoMovil e = new modelo.EquipoMovil();
                    e.setMarca(rs.getString("marca"));
                    e.setModelo(rs.getString("modelo"));
                    e.setCliente(c);

                    modelo.Usuario u = new modelo.Usuario();
                    u.setNombre(rs.getString("usuario"));

                    RecepcionEntrega re = new RecepcionEntrega();
                    re.setId(rs.getInt("recepcion_id"));
                    re.setEquipoMovil(e);

                    Reparacion r = new Reparacion();
                    r.setId(rs.getInt("id"));
                    r.setDiagnostico(rs.getString("diagnostico"));
                    r.setSolucion(rs.getString("solucion"));
                    r.setCostoRepuestos(rs.getDouble("costo_repuestos"));
                    r.setPiezasUsadas(rs.getString("piezas_usadas"));
                    r.setEstado(Reparacion.Estado.valueOf(rs.getString("estado")));
                    r.setRecepcion(re);
                    r.setUsuario(u);

                    lista.add(r);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar reparaciones", e);
        }

        return lista;
    }

    /**
     * Elimina una reparación por su ID.
     *
     * @param idReparacion ID de la reparación
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int idReparacion) {
        String sql = "DELETE FROM reparacion WHERE id = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idReparacion);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar reparación", e);
        }
    }

    /**
     * Obtiene una reparación por su ID.
     *
     * @param id ID de la reparación
     * @return reparación encontrada o null
     */
    public Reparacion obtenerPorId(int id) {
        String sql = """
            SELECT *
            FROM reparacion
            WHERE id = ?
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {

                    Reparacion r = new Reparacion();

                    r.setId(rs.getInt("id"));
                    r.setDiagnostico(rs.getString("diagnostico"));
                    r.setSolucion(rs.getString("solucion"));
                    r.setCostoRepuestos(rs.getDouble("costo_repuestos"));
                    r.setPiezasUsadas(rs.getString("piezas_usadas"));
                    r.setEstado(Reparacion.Estado.valueOf(rs.getString("estado")));

                    return r;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener reparación", e);
        }

        return null;
    }
}
