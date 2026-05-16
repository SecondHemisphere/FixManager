package modelo;

import java.util.Objects;

/**
 * Representa un equipo móvil registrado en el sistema.
 *
 * Contiene información básica del dispositivo como marca, modelo, IMEI, tipo de
 * equipo, descripción del daño reportado y el cliente propietario.
 *
 * @author Gelves Jonathan
 */
public class EquipoMovil {

    private int id;
    private String marca;
    private String modelo;
    private String imei;
    private String tipo;
    private String descripcionDanio;
    private Cliente cliente;

    /**
     * Constructor vacío.
     */
    public EquipoMovil() {
    }

    /**
     * Constructor completo con ID.
     *
     * @param id identificador del equipo
     * @param marca marca del dispositivo
     * @param modelo modelo del dispositivo
     * @param imei código IMEI del equipo
     * @param tipo tipo de equipo móvil
     * @param descripcionDanio descripción del daño reportado
     * @param cliente cliente propietario del equipo
     */
    public EquipoMovil(int id, String marca, String modelo, String imei,
            String tipo, String descripcionDanio, Cliente cliente) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.imei = imei;
        this.tipo = tipo;
        this.descripcionDanio = descripcionDanio;
        this.cliente = cliente;
    }

    /**
     * Constructor sin ID (para registros nuevos).
     *
     * @param marca marca del dispositivo
     * @param modelo modelo del dispositivo
     * @param imei código IMEI
     * @param tipo tipo de equipo
     * @param descripcionDanio descripción del daño
     * @param cliente cliente propietario
     */
    public EquipoMovil(String marca, String modelo, String imei,
            String tipo, String descripcionDanio, Cliente cliente) {
        this.marca = marca;
        this.modelo = modelo;
        this.imei = imei;
        this.tipo = tipo;
        this.descripcionDanio = descripcionDanio;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcionDanio() {
        return descripcionDanio;
    }

    public void setDescripcionDanio(String descripcionDanio) {
        this.descripcionDanio = descripcionDanio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        String imeiResumido = (imei != null && imei.length() > 4)
                ? imei.substring(imei.length() - 4)
                : "S/N";

        return String.format("%s %s (IMEI: ...%s)", marca, modelo, imeiResumido);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipoMovil)) {
            return false;
        }
        EquipoMovil that = (EquipoMovil) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
