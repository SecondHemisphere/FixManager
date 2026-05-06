package modelo;

/**
 * Representa los estados que puede tener la recepción o la entrega de un equipo
 * móvil.
 *
 * @author Baque Diego
 */
public enum EstadoRecepcionEntrega {

    RECIBIDO("Recibido"),
    LISTO("Listo"),
    ENTREGADO("Entregado");

    private final String descripcion;

    EstadoRecepcionEntrega(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
