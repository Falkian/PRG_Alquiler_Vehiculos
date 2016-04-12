package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que gestiona una conexion con una base de datos.
 *
 * @author vesprada
 */
public class ConexionMySQL {

    private final String PATH = "ficheros/conexionmysql.properties";
    
    private final String CONTROLLER;
    private final String NOMBRE_BD;
    private final String ENLACE_BD;
    private final String USUARIO;
    private final String PASSWORD;

    private final Connection connection;
    private final Statement statement;

    /**
     * Crea una conexion con una base de datos cuyas propiedades estan definidas
     * en el archivo pasado como parametro.
     *
     * @throws FileNotFoundException si el archivo no existe.
     * @throws IOException si hay un error de lectura del archivo.
     * @throws ClassNotFoundException si no puede importar.
     * @throws SQLException si hay un error al conectar conla base de datos.
     */
    public ConexionMySQL() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        Properties propiedades = new Properties();
        try (FileInputStream in = new FileInputStream(PATH)) {
            propiedades.load(in);
        }

        CONTROLLER = propiedades.getProperty("controller");
        if (CONTROLLER != null) {
            Class.forName(CONTROLLER);
        }

        NOMBRE_BD = propiedades.getProperty("nombre");
        ENLACE_BD = propiedades.getProperty("direccion");
        USUARIO = propiedades.getProperty("usuario");
        PASSWORD = propiedades.getProperty("contrasenya");

        connection = DriverManager.getConnection(ENLACE_BD + NOMBRE_BD, USUARIO, PASSWORD);
        statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Cierra la conexion con la base de datos.
     *
     * @throws SQLException si hay algun fallo al cerrar la base de datos.
     */
    public void close() throws SQLException {
        statement.close();
        connection.close();
    }

}
