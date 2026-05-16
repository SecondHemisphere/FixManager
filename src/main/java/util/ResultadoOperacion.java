package util;

/**
 * Representa el resultado de una operación del sistema.
 *
 * Indica si la operación fue exitosa o fallida, junto con un mensaje
 * descriptivo y opcionalmente el campo relacionado al error.
 *
 * Se utiliza para comunicar resultados entre DAO, controlador y vista.
 *
 * @author Mendoza Sebastian
 */
public class ResultadoOperacion {

    private final boolean exito;
    private final String mensaje;
    private final String campo;

    /**
     * Constructor privado para obligar el uso de métodos estáticos.
     *
     * @param exito indica si la operación fue exitosa
     * @param mensaje mensaje del resultado
     * @param campo campo relacionado con el error (puede ser null)
     */
    private ResultadoOperacion(boolean exito, String mensaje, String campo) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.campo = campo;
    }

    /**
     * Indica si la operación fue exitosa.
     *
     * @return true si fue exitosa, false en caso contrario
     */
    public boolean isExito() {
        return exito;
    }

    /**
     * Crea un resultado exitoso.
     *
     * @param mensaje mensaje de éxito
     * @return instancia de ResultadoOperacion exitosa
     */
    public static ResultadoOperacion exito(String mensaje) {
        return new ResultadoOperacion(true, mensaje, null);
    }

    /**
     * Crea un resultado de error con campo específico.
     *
     * @param mensaje mensaje de error
     * @param campo campo relacionado al error
     * @return instancia de ResultadoOperacion con error
     */
    public static ResultadoOperacion error(String mensaje, String campo) {
        return new ResultadoOperacion(false, mensaje, campo);
    }

    /**
     * Crea un resultado de error general.
     *
     * @param mensaje mensaje de error
     * @return instancia de ResultadoOperacion con error
     */
    public static ResultadoOperacion error(String mensaje) {
        return new ResultadoOperacion(false, mensaje, null);
    }

    /**
     * Obtiene el mensaje del resultado de la operación.
     *
     * @return mensaje descriptivo
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Obtiene el campo relacionado con el error (si existe).
     *
     * @return nombre del campo o null si no aplica
     */
    public String getCampo() {
        return campo;
    }
}
