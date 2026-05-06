package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa una factura generada a partir de una reparación.
 *
 * @author Mendoza Sebastian
 */
public class Factura {

    private int id;
    private double costoTotal;
    private LocalDateTime fechaEmision;
    private EstadoFactura estado;
    private String observaciones;
    private MetodoPago metodoPago;

    private Reparacion reparacion;

    public Factura() {
        this.fechaEmision = LocalDateTime.now();
        this.estado = EstadoFactura.PENDIENTE;
    }

    public Factura(int id, double costoTotal, LocalDateTime fechaEmision, EstadoFactura estado, String observaciones, MetodoPago metodoPago, Reparacion reparacion) {
        this.id = id;
        this.costoTotal = costoTotal;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.observaciones = observaciones;
        this.metodoPago = metodoPago;
        this.reparacion = reparacion;
    }

    public Factura(double costoTotal, String observaciones, MetodoPago metodoPago, Reparacion reparacion) {
        this.fechaEmision = LocalDateTime.now();
        this.estado = EstadoFactura.PENDIENTE;
        this.costoTotal = costoTotal;
        this.observaciones = observaciones;
        this.metodoPago = metodoPago;
        this.reparacion = reparacion;
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

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
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
