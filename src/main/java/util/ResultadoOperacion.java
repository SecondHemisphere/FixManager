package util;

/**
 * Representa el resultado de una operación del sistema, indicando si fue
 * exitosa o si ocurrió un error.
 *
 * @author Mendoza Sebastian
 */
public class ResultadoOperacion {

    private final boolean exito;
    private final String mensaje;
    private final String campo;

    private ResultadoOperacion(boolean exito, String mensaje, String campo) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.campo = campo;
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getCampo() {
        return campo;
    }

    public static ResultadoOperacion exito(String mensaje) {
        return new ResultadoOperacion(true, mensaje, null);
    }

    public static ResultadoOperacion error(String mensaje, String campo) {
        return new ResultadoOperacion(false, mensaje, campo);
    }

    public static ResultadoOperacion error(String mensaje) {
        return new ResultadoOperacion(false, mensaje, null);
    }
}
