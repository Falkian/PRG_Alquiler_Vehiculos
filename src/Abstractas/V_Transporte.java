package Abstractas;

import Utilidades.ConexionMySQL;
import java.sql.SQLException;

/**
 * Clase abstracta V_Transporte, define las propiedades y metodos particulares
 * de los vehiculos de transporte.
 *
 * @author Kevin
 */
public abstract class V_Transporte extends Vehiculo {
    
    public static final String NOMBRE_TABLA = "transportespersonas";    //Nombre de la tabla en la base de datos

    private int plazas;             //Numero de plazas del vehiculo.

    /**
     * Inicializa el vehiculo de transporte como no alquilado con la matricula y
     * las plazas dados.
     *
     * @param matricula la matricula del vehiculo.
     * @param plazas el numero de plaas del vehiculo.
     */
    public V_Transporte(String matricula, int plazas) {
        super(matricula);
        this.plazas = plazas;
    }

    /**
     * Devuelve el numero de plazas del vehiculo.
     *
     * @return el numero de plazas del vehiculo.
     */
    public int getPlazas() {
        return plazas;
    }

    /**
     * Devuelve el numero de plazas del vehiculo.
     *
     * @return el numero de plazas del vehiculo.
     */
    @Override
    public double getCaracteristica() {
        return plazas;
    }

    /**
     * Establece el numero de plazas.
     *
     * @param plazas el numero de plazas del vehiculo.
     */
    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    /**
     * Devuleve una cadena con informacion sobre el vehiculo.
     *
     * @return una cadena con informacion sobre el vehiculo.
     */
    @Override
    public String obtenerInformacion() {
        return super.obtenerInformacion() + ", Plazas: " + plazas;
    }

    /**
     * Devuelve el precio de alquilar el vehiculo durante los dias dados.
     *
     * @param dias el numero de dias por los que se alquila el vechiculo.
     * @return el precio de alquilar el vechiculo por dias dias.
     */
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 1.5 * plazas * dias;
    }

    /**
     * Devuelve la informacion sobre el vehiculo en forma de array. Orden de la
     * informacion: Matricula, Tipo, PMA
     *
     * @return un array con la informacion del vehiculo
     */
    @Override
    public String[] dataToArray() {
        return new String[]{getMatricula(), getClass().getSimpleName(), "" + plazas};
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
        String sentencia = "INSERT INTO " + NOMBRE_TABLA + " VALUES ('" + getMatricula() + "', " + plazas + ")";
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
        String sentencia = "UPDATE " + NOMBRE_TABLA + " SET numplazas = " + plazas + " WHERE matricula = '" + getMatricula() + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
    }
}
