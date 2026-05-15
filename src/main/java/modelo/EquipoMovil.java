package modelo;

import java.util.Objects;

/**
 * Representa un equipo móvil en el sistema.
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

    public EquipoMovil() {
    }

    public EquipoMovil(int id, String marca, String modelo, String imei, String tipo, String descripcionDanio, Cliente cliente) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.imei = imei;
        this.tipo = tipo;
        this.descripcionDanio = descripcionDanio;
        this.cliente = cliente;
    }

    public EquipoMovil(String marca, String modelo, String imei, String tipo, String descripcionDanio, Cliente cliente) {
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
        return String.format("%s %s [%s]", marca, modelo, imei);
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
