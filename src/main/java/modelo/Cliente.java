package modelo;

import java.util.Objects;

/**
 * Representa un cliente del sistema.
 *
 * Contiene la información básica del cliente, quien es el propietario de los
 * equipos móviles registrados en el sistema. Estos datos se utilizan
 * principalmente para el registro de equipos y la emisión de facturas.
 *
 * @author Cagua Derek
 */
public class Cliente {

    private int id;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;

    /**
     * Constructor vacío.
     */
    public Cliente() {
    }

    /**
     * Constructor completo con ID.
     *
     * @param id identificador del cliente
     * @param nombre nombre del cliente
     * @param correo correo electrónico
     * @param telefono número de teléfono
     * @param direccion dirección del cliente
     */
    public Cliente(int id, String nombre, String correo, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Constructor sin ID (para nuevos registros).
     *
     * @param nombre nombre del cliente
     * @param correo correo electrónico
     * @param telefono teléfono del cliente
     * @param direccion dirección del cliente
     */
    public Cliente(String nombre, String correo, String telefono, String direccion) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        return id == cliente.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
