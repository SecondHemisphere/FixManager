package modelo;

/**
 * Representa los estados que puede tener una factura en el sistema.
 *
 * @author Mendoza Sebastian
 */
public enum EstadoFactura {

    PENDIENTE("Pendiente"),
    PAGADA("Pagada");

    private final String descripcion;

    EstadoFactura(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
