package Clases;

import Abstractas.V_Carga;
import Utilidades.ConexionMySQL;
import Utilidades.TiposVehiculos;
import java.sql.SQLException;

/**
 * Clase que representa un objeto de tipo FURGONETA.
 *
 * @author Kevin
 */
public class Furgoneta extends V_Carga {

    public static final TiposVehiculos TIPO_VEHICULO = TiposVehiculos.FURGONETA;    //Tipo de vehiculo
    public static final String NOMBRE_TABLA_FURGONETAS = "furgonetas";      //Tabla en la base de datos

    /**
     * Inicializa la furgoneta con la matricula y PMA dados.
     *
     * @param matricula la matricula de la furgoneta.
     * @param PMA el peso mazimo autorizado de la furgoneta.
     */
    public Furgoneta(String matricula, double PMA) {
        super(matricula, PMA);
    }

    /**
     * Devuelve el tipo del vehiculo como una cadena.
     *
     * @return el tipo del vehciulo.
     */
    @Override
    public String getNombreTipo() {
        return TIPO_VEHICULO.getTipo();
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
        String sentencia = "INSERT INTO " + NOMBRE_TABLA_FURGONETAS + " VALUES ('" + getMatricula() + "')";
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
        String sentencia = "DELETE FROM " + NOMBRE_TABLA_FURGONETAS + " WHERE matricula = '" + getMatricula() + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
        super.eliminaDeBD(conexionMySQL);
    }
}
