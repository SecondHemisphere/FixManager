package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa una recepción o una entrega de un equipo móvi en el sistema.
 *
 * @author Baque Diego
 */
public class RecepcionEntrega {

    public enum Estado {

        RECIBIDO("Recibido"),
        LISTO("Listo"),
        ENTREGADO("Entregado");

        private final String descripcion;

        Estado(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    private int id;
    private LocalDateTime fechaRecepcion;
    private String problemaReportado;
    private Estado estado;
    private EquipoMovil equipoMovil;
    private Usuario usuario;

    public RecepcionEntrega() {
        this.fechaRecepcion = LocalDateTime.now();
        this.estado = Estado.RECIBIDO;
    }

    public RecepcionEntrega(int id, LocalDateTime fechaRecepcion, String problemaReportado, Estado estado, EquipoMovil equipoMovil, Usuario usuario) {
        this.id = id;
        this.fechaRecepcion = fechaRecepcion;
        this.problemaReportado = problemaReportado;
        this.estado = estado;
        this.equipoMovil = equipoMovil;
        this.usuario = usuario;
    }

    public RecepcionEntrega(String problemaReportado, EquipoMovil equipoMovil, Usuario usuario) {
        this.fechaRecepcion = LocalDateTime.now();
        this.problemaReportado = problemaReportado;
        this.estado = Estado.RECIBIDO;
        this.equipoMovil = equipoMovil;
        this.usuario = usuario;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public EquipoMovil getEquipoMovil() {
        return equipoMovil;
    }

    public void setEquipoMovil(EquipoMovil equipoMovil) {
        this.equipoMovil = equipoMovil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        String cliente = "N/A";
        String equipo = "Equipo";

        if (equipoMovil != null) {
            equipo = equipoMovil.getMarca() + " " + equipoMovil.getModelo();
            if (equipoMovil.getCliente() != null) {
                cliente = equipoMovil.getCliente().getNombre();
            }
        }

        return String.format("#%d | %s | %s", id, cliente, equipo);
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
