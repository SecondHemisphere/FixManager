package modelo;

import java.util.Objects;

/**
 * Representa una reparación realizada a un equipo móvil.
 *
 * Contiene información del diagnóstico, solución, costos, piezas utilizadas,
 * estado de la reparación y relaciones con la recepción del equipo y el usuario
 * responsable.
 *
 * @author Pluas Kevin
 */
public class Reparacion {

    /**
     * Estados posibles de una reparación.
     */
    public enum Estado {

        PENDIENTE("Pendiente"),
        EN_PROCESO("En proceso"),
        FINALIZADO("Finalizado");

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
    private String diagnostico;
    private String solucion;
    private double costoRepuestos;
    private String piezasUsadas;
    private Estado estado;
    private RecepcionEntrega recepcion;
    private Usuario usuario;

    /**
     * Constructor por defecto. Inicializa la reparación en estado PENDIENTE.
     */
    public Reparacion() {
        this.estado = Estado.PENDIENTE;
    }

    /**
     * Constructor para crear una reparación nueva.
     *
     * @param diagnostico diagnóstico del equipo
     * @param solucion solución aplicada
     * @param costoRepuestos costo de repuestos utilizados
     * @param piezasUsadas lista de piezas utilizadas
     * @param recepcion recepción del equipo
     * @param usuario usuario responsable de la reparación
     */
    public Reparacion(String diagnostico, String solucion, double costoRepuestos,
            String piezasUsadas, RecepcionEntrega recepcion, Usuario usuario) {
        this.diagnostico = diagnostico;
        this.solucion = solucion;
        this.costoRepuestos = costoRepuestos;
        this.piezasUsadas = piezasUsadas;
        this.estado = Estado.PENDIENTE;
        this.recepcion = recepcion;
        this.usuario = usuario;
    }

    /**
     * Constructor completo con ID y estado.
     *
     * @param id identificador de la reparación
     * @param diagnostico diagnóstico del equipo
     * @param solucion solución aplicada
     * @param costoRepuestos costo de repuestos
     * @param piezasUsadas piezas utilizadas
     * @param estado estado actual de la reparación
     * @param recepcion recepción asociada
     * @param usuario usuario responsable
     */
    public Reparacion(int id, String diagnostico, String solucion,
            double costoRepuestos, String piezasUsadas,
            Estado estado, RecepcionEntrega recepcion, Usuario usuario) {
        this.id = id;
        this.diagnostico = diagnostico;
        this.solucion = solucion;
        this.costoRepuestos = costoRepuestos;
        this.piezasUsadas = piezasUsadas;
        this.estado = estado;
        this.recepcion = recepcion;
        this.usuario = usuario;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public RecepcionEntrega getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(RecepcionEntrega recepcion) {
        this.recepcion = recepcion;
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
        String modelo = "Equipo";

        if (recepcion != null && recepcion.getEquipoMovil() != null) {
            cliente = recepcion.getEquipoMovil().getCliente().getNombre();
            modelo = recepcion.getEquipoMovil().getModelo();
        }

        return String.format("%s | %s | #%d", cliente, modelo, id);
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
