package modelo;

/**
 * Representa los roles para los usuarios del sistema.
 *
 * @author Mendoza Sebastian
 */
public enum Rol {
    ADMIN("Administrador"),
    TECNICO("Técnico"),
    CAJERO("Cajero");

    private final String descripcion;

    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
