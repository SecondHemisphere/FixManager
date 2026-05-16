package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa una factura generada a partir de una reparación.
 *
 * Contiene información del costo total, fecha de emisión, estado de pago,
 * método de pago, observaciones y relaciones con la reparación y el usuario que
 * la genera.
 *
 * @author Mendoza Sebastian
 */
public class Factura {

    /**
     * Estados posibles de una factura.
     */
    public enum Estado {

        PENDIENTE("Pendiente"),
        PAGADA("Pagada");

        private final String descripcion;

        /**
         * Constructor del estado de la factura.
         *
         * @param descripcion texto legible del estado
         */
        Estado(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    /**
     * Métodos de pago disponibles.
     */
    public enum MetodoPago {

        EFECTIVO("Efectivo"),
        TARJETA("Tarjeta"),
        TRANSFERENCIA("Transferencia");

        private final String descripcion;

        /**
         * Constructor del método de pago.
         *
         * @param descripcion texto legible del método de pago
         */
        MetodoPago(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    private int id;
    private double costoTotal;
    private LocalDateTime fechaEmision;
    private Estado estado;
    private String observaciones;
    private MetodoPago metodoPago;
    private Reparacion reparacion;
    private Usuario usuario;

    /**
     * Constructor por defecto. Inicializa la factura con estado PENDIENTE y
     * fecha actual.
     */
    public Factura() {
        this.fechaEmision = LocalDateTime.now();
        this.estado = Estado.PENDIENTE;
    }

    /**
     * Constructor completo.
     *
     * @param id identificador de la factura
     * @param costoTotal costo total de la reparación
     * @param fechaEmision fecha de emisión
     * @param estado estado de la factura
     * @param observaciones observaciones adicionales
     * @param metodoPago método de pago utilizado
     * @param reparacion reparación asociada
     * @param usuario usuario que generó la factura
     */
    public Factura(int id, double costoTotal, LocalDateTime fechaEmision,
            Estado estado, String observaciones,
            MetodoPago metodoPago, Reparacion reparacion, Usuario usuario) {
        this.id = id;
        this.costoTotal = costoTotal;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.observaciones = observaciones;
        this.metodoPago = metodoPago;
        this.reparacion = reparacion;
        this.usuario = usuario;
    }

    /**
     * Constructor para creación de nuevas facturas.
     *
     * @param costoTotal costo total
     * @param observaciones observaciones
     * @param metodoPago método de pago
     * @param reparacion reparación asociada
     * @param usuario usuario responsable
     */
    public Factura(double costoTotal, String observaciones,
            MetodoPago metodoPago, Reparacion reparacion, Usuario usuario) {
        this.fechaEmision = LocalDateTime.now();
        this.estado = Estado.PENDIENTE;
        this.costoTotal = costoTotal;
        this.observaciones = observaciones;
        this.metodoPago = metodoPago;
        this.reparacion = reparacion;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Reparacion getReparacion() {
        return reparacion;
    }

    public void setReparacion(Reparacion reparacion) {
        this.reparacion = reparacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "#" + id + " | $" + costoTotal + " | " + estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factura)) {
            return false;
        }
        Factura factura = (Factura) o;
        return id == factura.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
