package Abstractas;

import Excepciones.AlquilerVehiculoException;
import Utilidades.ConexionMySQL;
import java.sql.SQLException;

/**
 * Clase abstracta Vechiculo, define las propiedades comunes a todos los tipos
 * de vehiculo.
 *
 * @author Kevin
 */
public abstract class Vehiculo {

    private static final String NOMBRE_TABLA = "vehiculos";

    private final String matricula;           //Maricula del vehiculo
    private boolean alquilado;          //Define si esta alquilado o no

    /**
     * Inicializa el vehiculo con la matricula dad como no alquilado.
     *
     * @param matricula matricula del vehiculo.
     */
    public Vehiculo(String matricula) {
        alquilado = false;
        this.matricula = matricula;
    }

    /**
     * Devuelve la matricula del vehiculo.
     *
     * @return la matricula del vehiculo.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Devuelve la cacracteristica propia del vehiculo, bien sea un numero de
     * plazas o PMA
     *
     * @return las plazas o PMA del vehiculo
     */
    public abstract double getCaracteristica();

    /**
     * Devuelve si el vechiculo esta alquilado o no.
     *
     * @return true si el vechiulo esta alquilado; false en caso contrario.
     */
    public boolean isAlquilado() {
        return alquilado;
    }

    /**
     * Alquila el vehiculo al cliente pasado.
     *
     * @throws AlquilerVehiculoException si el vehiculo ya esta alquilado.
     */
    public void alquilar() throws AlquilerVehiculoException {
        if (alquilado) {
            throw new AlquilerVehiculoException("El vehiculo ya esta alquilado.");
        } else {
            alquilado = true;
        }
    }

    /**
     * Devuelve el vehiculo al almacen.
     *
     * @throws AlquilerVehiculoException si el vehiculo no esta alquilado.
     */
    public void devolver() throws AlquilerVehiculoException {
        if (alquilado) {
            alquilado = false;
        } else {
            throw new AlquilerVehiculoException("El vechiculo no esta alquilado.");
        }
    }

    /**
     * Devuelve el precio base de alquilar un vehiculo para los dias dados.
     *
     * @param dias los dias por los que va a estar alquilado el vehiculo.
     * @return el precio de alquilar el vehiculo durante dias dias.
     */
    public double alquilerTotal(int dias) {
        return 50 * dias;
    }

    /**
     * Devuleve una cadena con informacion sobre el vehiculo.
     *
     * @return una cadena con informacion sobre el vehiculo.
     */
    public String obtenerInformacion() {
        String info = "Matricula: " + matricula
                + ", Tipo: " + this.getClass().getSimpleName();
        return info;
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
    public void guardarEnBD(ConexionMySQL conexionMySQL, boolean primeraVez, String dniAlquilador) throws SQLException {
        String sentencia = "INSERT INTO " + NOMBRE_TABLA + " VALUES ('" + matricula + "', '" + (primeraVez ? "N" : "S")
                + "', " + (dniAlquilador != null ? "'" + dniAlquilador + "'" : "NULL") + ")";
        conexionMySQL.ejecutaSentencia(sentencia);
    }

    /**
     * Elimina el vehiculo de la base de datos.
     *
     * @param conexionMySQL conexion con la base de datos,
     * @throws SQLException si hay algun fallo al ejecutar la sentencia.
     */
    public void eliminaDeBD(ConexionMySQL conexionMySQL) throws SQLException {
        String sentencia = "DELETE FROM " + NOMBRE_TABLA + " WHERE matricula = '" + matricula + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
    }

    /**
     * Actualiza el cliente alquilador en la base de datos.
     *
     * @param conexionMySQL la conexion con la base de datos.
     * @param dni el dni del cliente que alquila el vehiculo.
     * @throws SQLException si hay algun fallo al ejecutar la sentencia.
     */
    public void actualizarAlquiladorEnBD(ConexionMySQL conexionMySQL, String dni) throws SQLException {
        String sentencia;
        if (dni != null) {
            sentencia = "UPDATE " + NOMBRE_TABLA + " SET clientealquilador = '" + dni + "' WHERE matricula = '" + matricula + "'";
        } else {
            sentencia = "UPDATE " + NOMBRE_TABLA + " SET clientealquilador = NULL WHERE matricula = '" + matricula + "'";
        }
        conexionMySQL.ejecutaSentencia(sentencia);
    }

    /**
     * Actualiza si es la primera vez que se ha alquilado el vehiculo en la base
     * de datos.
     *
     * @param conexionMySQL la conexion con la base de datos-
     * @param primera si se ha alquilado alguna vez.
     * @throws SQLException si hay un fallo al ejecutar la sentencia.
     */
    public void actualizarPrimeraVezEnBD(ConexionMySQL conexionMySQL, boolean primera) throws SQLException {
        String sentencia = "UPDATE " + NOMBRE_TABLA + " SET alquiladoalgunavez = '" + (primera ? "N" : "S") + "' WHERE matricula = '"
                + matricula + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
    }

    /**
     * Muestra incoformacion sobre el precio de alquilar el vechiulo durante una
     * cantidad de dias.
     *
     * @param dias los dias por los que se alquilara.
     * @param alquiler el precio del alquiler del vechiculo.
     */
    public abstract void mostrarInfoAlquiler(int dias, double alquiler);

    /**
     * Devuelve la informacion sobre el vehiculo en forma de array. Orden de la
     * informacion: Matricula, Tipo, PMA
     *
     * @return un array con la informacion del vehiculo
     */
    public abstract String[] dataToArray();

    /**
     * Devuelve el tipo de vehiculo como cadena.
     *
     * @return el tipo de vehiculo como cadena.
     */
    public abstract String getNombreTipo();
}
