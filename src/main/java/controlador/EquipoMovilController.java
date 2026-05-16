package controlador;

import dao.EquipoMovilDAO;
import dao.RecepcionEntregaDAO;
import java.util.List;
import java.util.regex.Pattern;
import modelo.EquipoMovil;
import util.ResultadoOperacion;

/**
 * Controlador de la entidad EquipoMovil.
 *
 * Encargado de aplicar reglas de negocio antes de interactuar con la base de
 * datos. Valida datos, evita eliminaciones inválidas y coordina con otras
 * entidades como Recepción.
 *
 * @author Gelves Jonathan
 */
public class EquipoMovilController {

    private final EquipoMovilDAO dao = new EquipoMovilDAO();
    private final RecepcionEntregaDAO recepcionDao = new RecepcionEntregaDAO();

    private static final Pattern SOLO_NUMEROS = Pattern.compile("\\d+");
    private static final Pattern IMEI_PATTERN = Pattern.compile("\\d{15}");

    /**
     * Obtiene la lista de equipos móviles registrados.
     *
     * @return lista de equipos móviles
     */
    public List<EquipoMovil> listarEquipos() {
        return dao.listar();
    }

    /**
     * Obtiene un equipo móvil por su ID.
     *
     * @param id identificador del equipo
     * @return equipo encontrado o null si no existe
     */
    public EquipoMovil obtenerEquipo(int id) {
        return dao.obtenerPorId(id);
    }

    /**
     * Guarda un nuevo equipo móvil en la base de datos.
     *
     * @param e objeto equipo móvil a registrar
     * @return ResultadoOperacion con estado de éxito o error
     */
    public ResultadoOperacion guardarEquipo(EquipoMovil e) {

        String error = validar(e);
        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        boolean ok = dao.guardar(e);

        return ok
                ? ResultadoOperacion.exito("Equipo móvil registrado correctamente")
                : ResultadoOperacion.error("No se pudo guardar el equipo móvil");
    }

    /**
     * Actualiza un equipo móvil existente.
     *
     * @param e objeto con datos actualizados
     * @return ResultadoOperacion con estado de la operación
     */
    public ResultadoOperacion actualizarEquipo(EquipoMovil e) {

        String error = validar(e);
        if (error != null) {
            return ResultadoOperacion.error(error);
        }

        boolean ok = dao.actualizar(e);

        return ok
                ? ResultadoOperacion.exito("Equipo móvil actualizado correctamente")
                : ResultadoOperacion.error("No se pudo actualizar el equipo móvil");
    }

    /**
     * Elimina un equipo móvil si no tiene recepciones asociadas.
     *
     * @param id identificador del equipo
     * @return resultado de la operación
     */
    public ResultadoOperacion eliminarEquipo(int id) {
        EquipoMovil e = dao.obtenerPorId(id);

        if (e == null) {
            return ResultadoOperacion.error("Equipo móvil no encontrado");
        }

        if (recepcionDao.existePorEquipo(e.getId())) {
            return ResultadoOperacion.error(
                    "No se puede eliminar el equipo porque tiene recepciones registradas"
            );
        }

        boolean ok = dao.eliminar(id);

        return ok
                ? ResultadoOperacion.exito("Equipo móvil eliminado correctamente")
                : ResultadoOperacion.error("No se pudo eliminar el equipo móvil");
    }

    /**
     * Filtra equipos por marca, modelo o IMEI.
     *
     * @param texto texto de búsqueda
     * @return lista de coincidencias
     */
    public List<EquipoMovil> filtrarEquipos(String texto) {
        return dao.filtrar(texto);
    }

    /**
     * Valida un objeto EquipoMovil antes de guardarlo o actualizarlo.
     *
     * @param e equipo móvil a validar
     * @return mensaje de error o null si es válido
     */
    public static String validar(EquipoMovil e) {

        if (e == null) {
            return "El equipo móvil no puede ser nulo";
        }

        String error = validarMarca(e.getMarca());
        if (error != null) {
            return error;
        }

        error = validarModelo(e.getModelo());
        if (error != null) {
            return error;
        }

        error = validarImei(e.getImei());
        if (error != null) {
            return error;
        }

        error = validarTipo(e.getTipo());
        if (error != null) {
            return error;
        }

        error = validarDescripcionDanio(e.getDescripcionDanio());
        if (error != null) {
            return error;
        }

        error = validarCliente(e);
        if (error != null) {
            return error;
        }

        return null;
    }

    /**
     * Valida la marca del equipo móvil.
     *
     * @param marca nombre de la marca
     * @return error o null si es válida
     */
    public static String validarMarca(String marca) {

        if (marca == null || marca.trim().isEmpty()) {
            return "La marca es obligatoria";
        }

        marca = marca.trim();

        if (marca.length() < 2) {
            return "La marca debe tener al menos 2 caracteres";
        }

        if (marca.length() > 50) {
            return "La marca no puede superar 50 caracteres";
        }

        return null;
    }

    /**
     * Valida el modelo del equipo móvil.
     *
     * @param modelo nombre del modelo
     * @return error o null si es válido
     */
    public static String validarModelo(String modelo) {

        if (modelo == null || modelo.trim().isEmpty()) {
            return "El modelo es obligatorio";
        }

        modelo = modelo.trim();

        if (modelo.length() < 2) {
            return "El modelo debe tener al menos 2 caracteres";
        }

        if (modelo.length() > 50) {
            return "El modelo no puede superar 50 caracteres";
        }

        return null;
    }

    /**
     * Valida el IMEI del equipo móvil.
     *
     * @param imei código IMEI
     * @return error o null si es válido
     */
    public static String validarImei(String imei) {

        if (imei == null || imei.trim().isEmpty()) {
            return "El IMEI es obligatorio";
        }

        imei = imei.trim();

        if (!SOLO_NUMEROS.matcher(imei).matches()) {
            return "El IMEI debe contener solo números";
        }

        if (!IMEI_PATTERN.matcher(imei).matches()) {
            return "El IMEI debe tener exactamente 15 dígitos";
        }

        return null;
    }

    /**
     * Valida el tipo del equipo móvil.
     *
     * @param tipo tipo del equipo
     * @return error o null si es válido
     */
    public static String validarTipo(String tipo) {

        if (tipo == null || tipo.trim().isEmpty()) {
            return "El tipo es obligatorio";
        }

        tipo = tipo.trim();

        if (tipo.length() < 3) {
            return "El tipo debe tener al menos 3 caracteres";
        }

        if (tipo.length() > 30) {
            return "El tipo no puede superar 30 caracteres";
        }

        return null;
    }

    /**
     * Valida la descripción del daño.
     *
     * @param desc descripción del daño
     * @return error o null si es válida
     */
    public static String validarDescripcionDanio(String desc) {

        if (desc == null || desc.trim().isEmpty()) {
            return "La descripción del daño es obligatoria";
        }

        desc = desc.trim();

        if (desc.length() < 5) {
            return "La descripción debe tener al menos 5 caracteres";
        }

        if (desc.length() > 255) {
            return "La descripción no puede superar 255 caracteres";
        }

        return null;
    }

    /**
     * Valida que el equipo tenga cliente asignado.
     *
     * @param e equipo móvil
     * @return error o null si es válido
     */
    public static String validarCliente(EquipoMovil e) {

        if (e.getCliente() == null) {
            return "Debe seleccionar un cliente";
        }

        return null;
    }
}
