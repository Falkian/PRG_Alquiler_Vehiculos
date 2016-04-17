package Abstractas;

import Utilidades.ConexionMySQL;
import java.sql.SQLException;

/**
 * Clase abstracta V_Carga, define las propiedades y metodos particulares de los
 * vehiculos de carga.
 *
 * @author Kevin
 */
public abstract class V_Carga extends Vehiculo {

    public static final String NOMBRE_TABLA = "transportescarga";   //Nombre de la tabla en la base de datos

    private double PMA;         //Peso maximo autorizado

    /**
     * Inicializa el vehiculo de carga como no alquilado con la matricula y PMA
     * dados.
     *
     * @param matricula la matricula del vehiculo.
     * @param PMA el peso maximo autorizado del vehiculo.
     */
    public V_Carga(String matricula, double PMA) {
        super(matricula);
        this.PMA = PMA;
    }

    /**
     * Devuleve el peso maximo autorizado.
     *
     * @return el peso maximo autorizado.
     */
    public double getPMA() {
        return PMA;
    }

    /**
     * Devuleve el peso maximo autorizado.
     *
     * @return el peso maximo autorizado.
     */
    @Override
    public double getCaracteristica() {
        return PMA;
    }

    /**
     * Establece el peso maximo autorizado.
     *
     * @param PMA el peso maximo autorizado del vehiculo.
     */
    public void setPMA(double PMA) {
        this.PMA = PMA;
    }

    /**
     * Devuleve una cadena con informacion sobre el vehiculo.
     *
     * @return una cadena con informacion sobre el vehiculo.
     */
    @Override
    public String obtenerInformacion() {
        return super.obtenerInformacion() + ", PMA: " + PMA;
    }

    /**
     * Calcula el precio de alquilar el vechiulo durante los dias dados.
     *
     * @param dias los dias por los que se alquila el vehiculo.
     * @return el pecio de alquilar el vehiculo durante dias dias.
     */
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 20 * PMA;
    }

    /**
     * Devuelve la informacion sobre el vehiculo en forma de array. Orden de la
     * informacion: Matricula, Tipo, PMA
     *
     * @return un array con la informacion del vehiculo
     */
    @Override
    public String[] dataToArray() {
        return new String[]{getMatricula(), getClass().getSimpleName(), "" + PMA};
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
        String sentencia = "INSERT INTO " + NOMBRE_TABLA + " VALUES ('" + getMatricula() + "', " + PMA + ")";
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
        String sentencia = "DELETE FROM " + NOMBRE_TABLA + " WHERE matricula = '" + getMatricula() + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
        super.eliminaDeBD(conexionMySQL);
    }

    /**
     * Actualiza el vehciulo en la base de datos.
     *
     * @param conexionMySQL conexion con la base de datos.
     * @throws SQLException si hay algun fallo al ejecutar la sentencia.
     */
    public void actualizaEnBD(ConexionMySQL conexionMySQL) throws SQLException {
        String sentencia = "UPDATE " + NOMBRE_TABLA + " SET pma = " + PMA + " WHERE matricula = '" + getMatricula() + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
    }
}
