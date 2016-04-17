package Clases;

import Abstractas.V_Transporte;
import Utilidades.ConexionMySQL;
import Utilidades.TiposVehiculos;
import java.sql.SQLException;

/**
 * Clase que representa un objeto de tipo MICROBUS.
 *
 * @author Kevin
 */
public class Microbus extends V_Transporte {

    public static final TiposVehiculos TIPO_VEHICULO = TiposVehiculos.MICROBUS; //Tipo de vehiculo
    public static final String NOMBRE_TABLA_MICROBUSES = "microbuses";     //Tabla en la base de datos

    /**
     * Inicializa el bus con la matricula y el numero de plazas dados.
     *
     * @param matricula la matricula del bus.
     * @param plazas el numero de plazas del bus.
     */
    public Microbus(String matricula, int plazas) {
        super(matricula, plazas);
    }

    /**
     * Devuelve el precio de alquilar el bus durante los dias dados.
     *
     * @param dias dias por los que se alquila el bus.
     * @return el precio de alquilar el bus por dias dias.
     */
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(+2 * super.getPlazas());
    }

    /**
     * Devuelve el tipo del vehciulo como una cadena.
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
        String sentencia = "INSERT INTO " + NOMBRE_TABLA_MICROBUSES + " VALUES ('" + getMatricula() + "')";
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
        String sentencia = "DELETE FROM " + NOMBRE_TABLA_MICROBUSES + " WHERE matricula = '" + getMatricula() + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
        super.eliminaDeBD(conexionMySQL);
    }
}
