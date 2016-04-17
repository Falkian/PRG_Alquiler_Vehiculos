package Clases;

import Excepciones.AlquilerVehiculoException;
import Utilidades.ConexionMySQL;
import java.sql.SQLException;

/**
 * Clase que representa a un cliente.
 *
 * @author Kevin
 */
public class Cliente {

    private static final String NOMBRE_TABLA = "clientes";      //Tabla de la base de datos en la que se almacenan los clientes

    private final String dni;           //DNI del cliente
    private String nombre;              //Nombre del cliente
    private String direccion;           //Direccion del cliente
    private String tlf;                 //Telefono del cliente
    private boolean vip;                //Verdadero si el cliente es VIP
    private int vehiculosAlquilados;    //Cantidad de vehiculos que tiene alquilados

    /**
     * Crea un cliente con el dni dado como parametro.
     *
     * @param dni que tendra el cliente.
     */
    public Cliente(String dni) {
        this.dni = dni;
    }

    /**
     * Crea un cliente con toda la informacion.
     *
     * @param dni dni del cliente.
     * @param nombre nombre del cliente.
     * @param direccion direccion del cliente.
     * @param tlf telefono del cliente.
     */
    public Cliente(String dni, String nombre, String direccion, String tlf) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tlf = tlf;
        vehiculosAlquilados = 0;
    }

    /**
     * Devuleve el DNI del cliente.
     *
     * @return del DNI del cliente.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Devuleve el nombre del cliente.
     *
     * @return el nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre el nombre a asignarle al cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la direccion del cliente.
     *
     * @return la direccion del cliente:
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la direccion del cliente.
     *
     * @param direccion la direccion a asignarle al cliente.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devulelve el telefono del cliente.
     *
     * @return el telefono del cliente.
     */
    public String getTlf() {
        return tlf;
    }

    /**
     * Establece el telefono del cliente.
     *
     * @param tlf el telefono a asignarle al cliente.
     */
    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    /**
     * Devuelve si el cliente es VIP.
     *
     * @return true si el cliente es VIP; false en caso contrario.
     */
    public boolean isVip() {
        return vip;
    }

    /**
     * Establece si el cliente es VIP.
     *
     * @param vip true si el cliente sera VIP; false si no lo sera.
     */
    public void setVip(boolean vip) {
        this.vip = vip;
    }

    /**
     * Indica si el cliente tiene vehiculos alquilados.
     *
     * @return ture si tiene vehiculos alquildos; false en caso contrario.
     */
    public boolean isAlquilado() {
        return vehiculosAlquilados > 0;
    }

    /**
     * Aumenta en uno la cnatidad de vehiculos alquilados.
     */
    public void alquilar() {
        vehiculosAlquilados++;
    }

    /**
     * Disminuye en uno la cantidad de vehiculos alquilados.
     *
     * @throws AlquilerVehiculoException si el cliente no tiene vehiculos
     * alquilados.
     */
    public void devolverVehiculo() throws AlquilerVehiculoException {
        if (vehiculosAlquilados > 0) {
            vehiculosAlquilados--;
        } else {
            throw new AlquilerVehiculoException("El cliente no tiene ningun vehiculo alquilado.");
        }
    }

    /**
     * Guarda el cliente en la base de datos.
     *
     * @param conexionMySQL la conexion con la base de datos.
     * @throws SQLException si hay un fallo al ejecutar la introduccion.
     */
    public void guardarEnBD(ConexionMySQL conexionMySQL) throws SQLException {
        String sentencia = "INSERT INTO " + NOMBRE_TABLA + " VALUES('" + dni + "', '"
                + nombre + "', '" + direccion + "', '" + tlf + "', '"
                + (vip ? "S" : "N") + "')";
        conexionMySQL.ejecutaSentencia(sentencia);
    }

    /**
     * Elimina el cliente de la base de datos.
     *
     * @param conexionMySQL la conexiono con la base de datos.
     * @throws SQLException si hay algun fallo al ejecutar la sentencia de
     * eliminacion.
     */
    public void eliminaDeBD(ConexionMySQL conexionMySQL) throws SQLException {
        String sentencia = "DELETE FROM " + NOMBRE_TABLA + " WHERE dni = '" + dni + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
    }

    /**
     * Actualiza los datos del cliente en la base de datos,
     *
     * @param conexionMySQL conexion con la base de datos.
     * @throws SQLException si ahy algun fallo al ejecutar la instruccion.
     */
    public void actualizaEnBD(ConexionMySQL conexionMySQL) throws SQLException {
        String sentencia = "UPDATE " + NOMBRE_TABLA + " SET nombre = '" + nombre + "', direccion = '"
                + direccion + "', telefono = '" + tlf + "', vip = '" + (vip ? "S" : "N") + "' WHERE dni = '" + dni + "'";
        conexionMySQL.ejecutaSentencia(sentencia);
    }

    /**
     * Devuelve una cadena con la informacion del cliente.
     *
     * @return una cadena con la informacion del cliente.
     */
    public String obtenerInformacion() {
        String info = "Nombre: " + nombre + ", DNI: " + dni + ", "
                + "Direccion: " + direccion + ", Telefono: " + tlf;
        info += vip ? "Es un cliente VIP" : "";
        return info;
    }

    /**
     * Devuelve la informacion sobre el cliente en forma de array. Orden de la
     * informacion: DNI, nombre, direccion, telefono, vip
     *
     * @return un array con la informacion del cliente
     */
    public String[] dataToArray() {
        return new String[]{dni, nombre, direccion, tlf, vip ? "\u2713" : "\u2717"};
    }
}
