package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;

/**
 * DAO encargado de las operaciones CRUD de la entidad Cliente.
 *
 * @author Cagua Derek
 */
public class ClienteDAO {

    /**
     * Obtiene todos los clientes registrados en la base de datos.
     *
     * @return lista de clientes
     */
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection con = Conexion.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getString("telefono"));
                c.setDireccion(rs.getString("direccion"));
                lista.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes", e);
        }

        return lista;
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param idCliente ID del cliente
     * @return cliente encontrado o null si no existe
     */
    public Cliente obtenerPorId(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE id=?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idCliente);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNombre(rs.getString("nombre"));
                    c.setCorreo(rs.getString("correo"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setDireccion(rs.getString("direccion"));
                    return c;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener cliente por ID", e);
        }

        return null;
    }

    /**
     * Guarda un nuevo cliente en la base de datos.
     *
     * @param cliente objeto cliente
     * @return true si se insertó correctamente
     */
    public boolean guardar(Cliente cliente) {
        String sql = "INSERT INTO cliente(nombre, correo, telefono, direccion) VALUES (?,?,?,?)";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, cliente.getNombre());
            pst.setString(2, cliente.getCorreo());
            pst.setString(3, cliente.getTelefono());
            pst.setString(4, cliente.getDireccion());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cliente", e);
        }
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param cliente objeto con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre=?, correo=?, telefono=?, direccion=? WHERE id=?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, cliente.getNombre());
            pst.setString(2, cliente.getCorreo());
            pst.setString(3, cliente.getTelefono());
            pst.setString(4, cliente.getDireccion());
            pst.setInt(5, cliente.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cliente", e);
        }
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param idCliente ID del cliente
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM cliente WHERE id=?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idCliente);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }

    /**
     * Filtra clientes por nombre usando coincidencia parcial.
     *
     * @param texto texto a buscar en el nombre
     * @return lista de clientes que coinciden con el filtro
     */
    public List<Cliente> filtrarPorNombre(String texto) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE LOWER(nombre) LIKE ?";

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + texto.toLowerCase() + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNombre(rs.getString("nombre"));
                    c.setCorreo(rs.getString("correo"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setDireccion(rs.getString("direccion"));
                    lista.add(c);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar clientes", e);
        }

        return lista;
    }

    /**
     * Verifica si existe un cliente con cierto correo en la base de datos. Si
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
            sql = "SELECT 1 FROM cliente WHERE correo=? LIMIT 1";
        } else {
            sql = "SELECT 1 FROM cliente WHERE correo=? AND id<>? LIMIT 1";
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
     * Verifica si existe un cliente con cierto teléfono en la base de datos. Si
     * idExcluido es null, valida para INSERT. Si tiene valor, valida para
     * UPDATE.
     *
     * @param telefono teléfono a verificar
     * @param idExcluido ID a excluir (puede ser null)
     * @return true si el teléfono ya existe
     */
    public boolean existeTelefono(String telefono, Integer idExcluido) {

        String sql;

        if (idExcluido == null) {
            sql = "SELECT 1 FROM cliente WHERE telefono=? LIMIT 1";
        } else {
            sql = "SELECT 1 FROM cliente WHERE telefono=? AND id<>? LIMIT 1";
        }

        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, telefono);

            if (idExcluido != null) {
                pst.setInt(2, idExcluido);
            }

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar teléfono", e);
        }
    }

}
