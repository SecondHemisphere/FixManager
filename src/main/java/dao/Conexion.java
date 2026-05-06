package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Cagua Derek
 */
public class Conexion {

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
