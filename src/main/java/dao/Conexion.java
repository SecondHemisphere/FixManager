package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de establecer la conexión con la base de datos MySQL.
 *
 * Utiliza el driver JDBC para conectarse a la base de datos del sistema y
 * permite que las clases DAO puedan realizar operaciones CRUD.
 *
 * @author Mendoza Sebastian
 */
public class Conexion {

    /**
     * Establece y retorna una conexión con la base de datos.
     *
     * @return objeto Connection si la conexión es exitosa, o null si ocurre un
     * error
     */
    public static Connection conectar() {
        Connection cn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fixmanager",
                    "root",
                    "12345678"
            );

        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver MySQL: " + e);

        } catch (SQLException e) {
            System.out.println("Error en la conexión a la BD: " + e);
        }

        return cn;
    }
}
