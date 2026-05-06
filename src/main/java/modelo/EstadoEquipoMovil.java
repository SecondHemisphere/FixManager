package modelo;

/**
 * Representa los estados que puede tener un equipo móvil en el sistema.
 *
 * @author Gelves Jonathan
 */
public enum EstadoEquipoMovil {

    REGISTRADO("Registrado"),
    RECEPCIONADO("Recepcionado"),
    ENTREGADO("Entregado");

    private final String descripcion;

    EstadoEquipoMovil(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
