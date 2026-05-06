package modelo;

/**
 * Representa los estados que puede tener una reparación en el sistema.
 *
 * @author Pluas Kevin
 */
public enum EstadoReparacion {

    PENDIENTE("Pendiente"),
    EN_PROCESO("En proceso"),
    FINALIZADO("Finalizado");

    private final String descripcion;

    EstadoReparacion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
