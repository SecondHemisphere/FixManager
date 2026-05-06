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
import modelo.EstadoFactura;
import modelo.RecepcionEntrega;
import modelo.Reparacion;
import modelo.Factura;
import modelo.MetodoPago;

/**
 * DAO encargado de las operaciones CRUD de la entidad Factura.
 *
 * @author Mendoza Sebastian
 */
public class FacturaDAO {

    /**
     * Lista todas las facturas.
     *
     * @return lista de facturas
     */
    public List<Factura> listar() {
        List<Factura> lista = new ArrayList<>();

        String sql = """
            SELECT f.*, 
                   r.id AS reparacion_id, r.diagnostico,
                   re.id AS recepcion_id,
                   e.id AS equipo_id, e.marca, e.modelo,
                   c.id AS cliente_id, c.nombre
            FROM factura f
            INNER JOIN reparacion r ON f.reparacion_id = r.id
            INNER JOIN recepcion_entrega re ON r.recepcion_id = re.id
            INNER JOIN equipo_movil e ON re.equipo_id = e.id
            INNER JOIN cliente c ON e.cliente_id = c.id
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
                e.setCliente(c);

                RecepcionEntrega re = new RecepcionEntrega();
                re.setId(rs.getInt("recepcion_id"));
                re.setEquipoMovil(e);

                Reparacion r = new Reparacion();
                r.setId(rs.getInt("reparacion_id"));
                r.setDiagnostico(rs.getString("diagnostico"));
                r.setRecepcion(re);

                Factura f = new Factura();
                f.setId(rs.getInt("id"));
                f.setCostoTotal(rs.getDouble("costo_total"));
                f.setFechaEmision(rs.getTimestamp("fecha_emision").toLocalDateTime());
                f.setEstado(EstadoFactura.valueOf(rs.getString("estado")));
                f.setObservaciones(rs.getString("observaciones"));

                if (rs.getString("metodo_pago") != null) {
                    f.setMetodoPago(MetodoPago.valueOf(rs.getString("metodo_pago")));
                }

                f.setReparacion(r);

                lista.add(f);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar facturas", e);
        }

        return lista;
    }

    /**
     * Busca una factura por su ID.
     *
     * @param idFactura ID de la factura
     * @return factura encontrada o null si no existe
     */
    public Factura obtenerPorId(int idFactura) {

        String sql = """
        SELECT f.*, 
               r.id AS reparacion_id, r.diagnostico,
               re.id AS recepcion_id,
               e.id AS equipo_id, e.marca, e.modelo,
               c.id AS cliente_id, c.nombre
        FROM factura f
        INNER JOIN reparacion r ON f.reparacion_id = r.id
        INNER JOIN recepcion_entrega re ON r.recepcion_id = re.id
        INNER JOIN equipo_movil e ON re.equipo_id = e.id
        INNER JOIN cliente c ON e.cliente_id = c.id
        WHERE f.id = ?
    """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idFactura);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {

                    Cliente c = new Cliente();
                    c.setId(rs.getInt("cliente_id"));
                    c.setNombre(rs.getString("nombre"));

                    EquipoMovil e = new EquipoMovil();
                    e.setId(rs.getInt("equipo_id"));
                    e.setMarca(rs.getString("marca"));
                    e.setModelo(rs.getString("modelo"));
                    e.setCliente(c);

                    RecepcionEntrega re = new RecepcionEntrega();
                    re.setId(rs.getInt("recepcion_id"));
                    re.setEquipoMovil(e);

                    Reparacion r = new Reparacion();
                    r.setId(rs.getInt("reparacion_id"));
                    r.setDiagnostico(rs.getString("diagnostico"));
                    r.setRecepcion(re);

                    Factura f = new Factura();
                    f.setId(rs.getInt("id"));
                    f.setCostoTotal(rs.getDouble("costo_total"));
                    f.setFechaEmision(rs.getTimestamp("fecha_emision").toLocalDateTime());
                    f.setEstado(EstadoFactura.valueOf(rs.getString("estado")));
                    f.setObservaciones(rs.getString("observaciones"));

                    if (rs.getString("metodo_pago") != null) {
                        f.setMetodoPago(MetodoPago.valueOf(rs.getString("metodo_pago")));
                    }

                    f.setReparacion(r);

                    return f;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener factura", e);
        }

        return null;
    }

    /**
     * Verifica si ya existe una factura para una reparación.
     *
     * @param reparacionId ID de la reparación
     * @return true si la reparación ya tiene una factura registrada, false en
     * caso contrario
     */
    public boolean existePorReparacion(int reparacionId) {

        String sql = "SELECT COUNT(*) FROM factura WHERE reparacion_id=?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, reparacionId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    /**
     * Guarda una factura en la base de datos.
     *
     * @param f objeto factura
     * @return true si se insertó correctamente
     */
    public boolean guardar(Factura f) {

        if (existePorReparacion(f.getReparacion().getId())) {
            throw new RuntimeException("Ya existe una factura para esta reparación");
        }

        String sql = """
            INSERT INTO factura
            (costo_total, estado, observaciones, metodo_pago, reparacion_id)
            VALUES (?,?,?,?,?)
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, f.getCostoTotal());
            pst.setString(2, f.getEstado().name());
            pst.setString(3, f.getObservaciones());
            pst.setString(4, f.getMetodoPago() != null ? f.getMetodoPago().name() : null);
            pst.setInt(5, f.getReparacion().getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar factura", e);
        }
    }

    /**
     * Actualiza los datos de una factura existente.
     *
     * @param f objeto con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(Factura f) {

        String sql = """
            UPDATE factura
            SET observaciones=?, metodo_pago=?, estado=?
            WHERE id=? AND estado != 'PAGADA'
        """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, f.getObservaciones());
            pst.setString(2, f.getMetodoPago().name());
            pst.setString(3, f.getEstado().name());
            pst.setInt(4, f.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar factura", e);
        }
    }

    /**
     * Filtra facturas por cliente o estado.
     *
     * @param texto texto a buscar
     * @return lista de facturas que coinciden
     */
    public List<Factura> filtrar(String texto) {

        List<Factura> lista = new ArrayList<>();

        String sql = """
        SELECT f.*, 
               r.id AS reparacion_id, r.diagnostico,
               re.id AS recepcion_id,
               e.id AS equipo_id, e.marca, e.modelo,
               c.id AS cliente_id, c.nombre
        FROM factura f
        INNER JOIN reparacion r ON f.reparacion_id = r.id
        INNER JOIN recepcion_entrega re ON r.recepcion_id = re.id
        INNER JOIN equipo_movil e ON re.equipo_id = e.id
        INNER JOIN cliente c ON e.cliente_id = c.id
        WHERE LOWER(c.nombre) LIKE ?
           OR LOWER(f.estado) LIKE ?
    """;

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            String filtro = "%" + texto.toLowerCase() + "%";

            pst.setString(1, filtro);
            pst.setString(2, filtro);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Cliente c = new Cliente();
                c.setId(rs.getInt("cliente_id"));
                c.setNombre(rs.getString("nombre"));

                EquipoMovil e = new EquipoMovil();
                e.setId(rs.getInt("equipo_id"));
                e.setMarca(rs.getString("marca"));
                e.setModelo(rs.getString("modelo"));
                e.setCliente(c);

                RecepcionEntrega re = new RecepcionEntrega();
                re.setId(rs.getInt("recepcion_id"));
                re.setEquipoMovil(e);

                Reparacion r = new Reparacion();
                r.setId(rs.getInt("reparacion_id"));
                r.setDiagnostico(rs.getString("diagnostico"));
                r.setRecepcion(re);

                Factura f = new Factura();
                f.setId(rs.getInt("id"));
                f.setCostoTotal(rs.getDouble("costo_total"));
                f.setEstado(EstadoFactura.valueOf(rs.getString("estado")));
                f.setReparacion(r);

                lista.add(f);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}
