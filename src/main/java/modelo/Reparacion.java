package modelo;

import java.util.Objects;

/**
 * Representa una reparación realizada a un equipo móvil.
 *
 * @author Pluas Kevin
 */
public class Reparacion {

    private int id;
    private String diagnostico;
    private String solucion;
    private double costoRepuestos;
    private String piezasUsadas;
    private EstadoReparacion estado;

    private RecepcionEntrega recepcion;

    public Reparacion() {
        this.estado = EstadoReparacion.PENDIENTE;
    }

    public Reparacion(String diagnostico, String solucion,
            double costoRepuestos, String piezasUsadas,
            RecepcionEntrega recepcion) {
        this.diagnostico = diagnostico;
        this.solucion = solucion;
        this.costoRepuestos = costoRepuestos;
        this.piezasUsadas = piezasUsadas;
        this.estado = EstadoReparacion.PENDIENTE;
        this.recepcion = recepcion;
    }

    public Reparacion(int id, String diagnostico, String solucion,
            double costoRepuestos, String piezasUsadas,
            EstadoReparacion estado, RecepcionEntrega recepcion) {
        this.id = id;
        this.diagnostico = diagnostico;
        this.solucion = solucion;
        this.costoRepuestos = costoRepuestos;
        this.piezasUsadas = piezasUsadas;
        this.estado = estado;
        this.recepcion = recepcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public double getCostoRepuestos() {
        return costoRepuestos;
    }

    public void setCostoRepuestos(double costoRepuestos) {
        this.costoRepuestos = costoRepuestos;
    }

    public String getPiezasUsadas() {
        return piezasUsadas;
    }

    public void setPiezasUsadas(String piezasUsadas) {
        this.piezasUsadas = piezasUsadas;
    }

    public EstadoReparacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoReparacion estado) {
        this.estado = estado;
    }

    public RecepcionEntrega getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(RecepcionEntrega recepcion) {
        this.recepcion = recepcion;
    }

    @Override
    public String toString() {
        return "Reparación #" + id + " - " + estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reparacion)) {
            return false;
        }
        Reparacion that = (Reparacion) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
