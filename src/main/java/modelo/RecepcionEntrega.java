package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa una recepción o una entrega de un equipo móvi en el sistema.
 *
 * @author Baque Diego
 */
public class RecepcionEntrega {

    private int id;
    private LocalDateTime fechaRecepcion;
    private String problemaReportado;
    private EstadoRecepcionEntrega estado;

    private EquipoMovil equipoMovil;

    public RecepcionEntrega() {
        this.fechaRecepcion = LocalDateTime.now();
        this.estado = EstadoRecepcionEntrega.RECIBIDO;
    }

    public RecepcionEntrega(int id, LocalDateTime fechaRecepcion,
            String problemaReportado, EstadoRecepcionEntrega estado,
            EquipoMovil equipoMovil) {
        this.id = id;
        this.fechaRecepcion = fechaRecepcion;
        this.problemaReportado = problemaReportado;
        this.estado = estado;
        this.equipoMovil = equipoMovil;
    }

    public RecepcionEntrega(String problemaReportado, EquipoMovil equipoMovil) {
        this.fechaRecepcion = LocalDateTime.now();
        this.problemaReportado = problemaReportado;
        this.estado = EstadoRecepcionEntrega.RECIBIDO;
        this.equipoMovil = equipoMovil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDateTime fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getProblemaReportado() {
        return problemaReportado;
    }

    public void setProblemaReportado(String problemaReportado) {
        this.problemaReportado = problemaReportado;
    }

    public EstadoRecepcionEntrega getEstado() {
        return estado;
    }

    public void setEstado(EstadoRecepcionEntrega estado) {
        this.estado = estado;
    }

    public EquipoMovil getEquipoMovil() {
        return equipoMovil;
    }

    public void setEquipoMovil(EquipoMovil equipoMovil) {
        this.equipoMovil = equipoMovil;
    }

    @Override
    public String toString() {
        return "#" + id
                + " - " + equipoMovil
                + " - " + estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecepcionEntrega)) {
            return false;
        }
        RecepcionEntrega that = (RecepcionEntrega) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
