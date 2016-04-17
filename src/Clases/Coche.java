package Clases;

import Abstractas.V_Transporte;
import Utilidades.ConexionMySQL;
import Utilidades.TiposVehiculos;
import java.sql.SQLException;

/**
 * Clase que representa un objeto de tipo COCHE.
 *
 * @author Kevin
 */
public class Coche extends V_Transporte {

    private static final TiposVehiculos TIPO__VEHICULO = TiposVehiculos.COCHE;  //Tipo de vehiculo
    public static final String NOMBRE_TABLA_COCHES = "coches";      //Tabla de la base de datos

    /**
     * Inicializa el coche con la matricula y el numero de plazas dados.
     *
     * @param matricula la matricula del coche.
     * @param plazas el numero de plazas del coche.
     */
    public Coche(String matricula, int plazas) {
        super(matricula, plazas);
    }

    /**
     * Devuelve el tipo del vehiculo como una cadena.
     *
     * @return el tipo de vehiculo.
     */
    @Override
    public String getNombreTipo() {
        return TIPO__VEHICULO.getTipo();
    }

    /**
     * Guarda un vehiculo en la base de datos.
     *
     * @param conexionMySQL la conexion con la base de datos.
     * @param primeraVez si es la primera vez que se alquila el vehiculo.
     * @param dniAlquilador el dni del cliente que tiene alquilado el vehiculo.
     * NULL si no esta alquilado.
     * @throws SQLException si hay algun fallo al ejecutar la sentencia.
     */
    @Override
    public void guardarEnBD(ConexionMySQL conexionMySQL, boolean primeraVez, String dniAlquilador) throws SQLException {
        super.guardarEnBD(conexionMySQL, primeraVez, dniAlquilador);
        String sentencia = "INSERT INTO " + NOMBRE_TABLA_COCHES + " VALUES ('" + getMatricula() + "')";
        conexionMySQL.ejecutaSentencia(sentencia);
    }

    /**
     * Elimina el vehiculo de la base de datos.
     *
     * @param conexionMySQL conexion con la base de datos,
     * @throws SQLException si hay algun fallo al ejecutar la sentencia.
     */
    @Override
    public void eliminaDeBD(ConexionMySQL conexionMySQL) throws SQLException {
        String sentencia = "DELETE FROM " + NOMBRE_TABLA_COCHES + " WHERE matricula = '" + getMatricula() + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
        super.eliminaDeBD(conexionMySQL);
    }
}
