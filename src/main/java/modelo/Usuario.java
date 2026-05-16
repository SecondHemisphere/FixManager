package modelo;

import java.util.Objects;

/**
 * Representa un usuario del sistema.
 *
 * Contiene información personal, credenciales de acceso, rol dentro del sistema
 * y estado de activación.
 *
 * @author Mendoza Sebastian
 */
public class Usuario {

    /**
     * Enumeración que define los roles disponibles en el sistema.
     */
    public enum Rol {

        ADMIN("Administrador"),
        TECNICO("Técnico"),
        CAJERO("Cajero");

        private final String descripcion;

        /**
         * Constructor del rol con su descripción.
         *
         * @param descripcion nombre legible del rol
         */
        Rol(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    private int id;
    private String nombre;
    private String correo;
    private String contrasena;
    private Rol rol;
    private boolean activo;

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id identificador del usuario
     * @param nombre nombre del usuario
     * @param correo correo electrónico
     * @param contrasena contraseña del usuario
     * @param rol rol asignado al usuario
     * @param activo estado del usuario
     */
    public Usuario(int id, String nombre, String correo, String contrasena, Rol rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = activo;
    }

    /**
     * Constructor sin ID (usado para creación de nuevos usuarios).
     *
     * @param nombre nombre del usuario
     * @param correo correo electrónico
     * @param contrasena contraseña del usuario
     * @param rol rol asignado
     * @param activo estado del usuario
     */
    public Usuario(String nombre, String correo, String contrasena, Rol rol, boolean activo) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = activo;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return nombre + " (" + rol + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
