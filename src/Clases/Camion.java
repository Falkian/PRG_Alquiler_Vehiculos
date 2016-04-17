package Clases;

import Abstractas.V_Carga;
import Utilidades.ConexionMySQL;
import Utilidades.TiposVehiculos;
import java.sql.SQLException;

/**
 * Clase que representa un objeto de tipo CAMION.
 *
 * @author Kevin
 */
public class Camion extends V_Carga {

    private static final TiposVehiculos TIPO_VEHICULO = TiposVehiculos.CAMION;  //Tipo de vehiculo
    public static final String NOMBRE_TABLA_CAMIONES = "camiones";

    /**
     * Inicializa el camion con la matricula y PMA dados.
     *
     * @param matricula la matricula del camion.
     * @param PMA el peso mazimo autorizado del camion.
     */
    public Camion(String matricula, double PMA) {
        super(matricula, PMA);
    }

    /**
     * Devuelve el precio de alquilar el camion durante los dias dados.
     *
     * @param dias dias por los que se alquila el camion.
     * @return el precio de alquilar el camion por dias dias.
     */
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 40;
    }

    /**
     * Devuelve el tipo del vehiculo como una cadena.
     *
     * @return el tipo del vehiculo.
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
        String sentencia = "INSERT INTO " + NOMBRE_TABLA_CAMIONES + " VALUES ('" + getMatricula() + "')";
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
        String sentencia = "DELETE FROM " + NOMBRE_TABLA_CAMIONES + " WHERE matricula = '" + getMatricula() + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
        super.eliminaDeBD(conexionMySQL);
    }
}
