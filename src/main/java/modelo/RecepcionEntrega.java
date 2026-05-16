package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa una recepción o entrega de un equipo móvil en el sistema.
 *
 * Contiene información del equipo recibido, problema reportado, estado del
 * proceso, fecha de recepción y el usuario responsable.
 *
 * @author Baque Diego
 */
public class RecepcionEntrega {

    /**
     * Estados posibles de la recepción o entrega.
     */
    public enum Estado {

        RECIBIDO("Recibido"),
        LISTO("Listo"),
        ENTREGADO("Entregado");

        private final String descripcion;

        /**
         * Constructor del estado con su descripción.
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

    private int id;
    private LocalDateTime fechaRecepcion;
    private String problemaReportado;
    private Estado estado;
    private EquipoMovil equipoMovil;
    private Usuario usuario;

    /**
     * Constructor por defecto.
     *
     * Inicializa la recepción con la fecha actual y estado RECIBIDO.
     */
    public RecepcionEntrega() {
        this.fechaRecepcion = LocalDateTime.now();
        this.estado = Estado.RECIBIDO;
    }

    /**
     * Constructor completo.
     *
     * @param id identificador de la recepción
     * @param fechaRecepcion fecha en la que se registró
     * @param problemaReportado problema descrito por el cliente
     * @param estado estado actual de la recepción
     * @param equipoMovil equipo asociado
     * @param usuario usuario que registra la recepción
     */
    public RecepcionEntrega(int id, LocalDateTime fechaRecepcion, String problemaReportado,
            Estado estado, EquipoMovil equipoMovil, Usuario usuario) {

        this.id = id;
        this.fechaRecepcion = fechaRecepcion;
        this.problemaReportado = problemaReportado;
        this.estado = estado;
        this.equipoMovil = equipoMovil;
        this.usuario = usuario;
    }

    /**
     * Constructor para nueva recepción.
     *
     * @param problemaReportado problema del equipo
     * @param equipoMovil equipo asociado
     * @param usuario usuario responsable
     */
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
