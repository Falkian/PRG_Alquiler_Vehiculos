package Estructuras;

import Clases.Alquiler;
import Abstractas.Vehiculo;
import Clases.Cliente;
import Excepciones.AlquilerVehiculoException;
import Excepciones.ObjetoNoExistenteException;
import Utilidades.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 * Coleccion de objetos de la clase Alquiler.
 *
 * @author Kevin
 */
public class ColeccionAlquileres {

    private static final String NOMBRE_TABLA = "vehiculos";

    private final ArrayList<Alquiler> alquileres;       //Coleccion de alquielres
    private final ConexionMySQL conexionMySQL;

    private final ColeccionVehiculos vehiculos;     //Coleccion de vehiculos
    private final ColeccionClientes clientes;       //Coleccion de clientes

    /**
     * Constructor de la coleccion de alquileres.
     *
     * @param vehiculos la coleccion de vehiculos.
     * @param clientes la coleccion de clientes.
     * @param conexionMySQL la conexion con la base de datos.
     */
    public ColeccionAlquileres(ColeccionVehiculos vehiculos, ColeccionClientes clientes, ConexionMySQL conexionMySQL) {
        alquileres = new ArrayList<>();
        this.vehiculos = vehiculos;
        this.clientes = clientes;
        this.conexionMySQL = conexionMySQL;
    }

    /**
     * Dado un dni elimina todos los alquileres que tenga el cliente
     * identidicado por dicho dni.
     *
     * @param matricula la matricula que identifica al vehiculo.
     * @param dni que identifica al cliente.
     * @throws Excepciones.ObjetoNoExistenteException si el objeto no existe en
     * la coleccion.
     * @throws AlquilerVehiculoException si el cliente no tiene vehiculos
     * alquilados.
     * @throws java.sql.SQLException si hay un fallo al actualizar la base de
     * datos.
     */
    public void anyadirAlquiler(String matricula, String dni) throws ObjetoNoExistenteException, AlquilerVehiculoException, SQLException {
        Vehiculo v = vehiculos.obtenerVechiculo(matricula);
        Cliente c = clientes.obtenerCliente(dni);
        v.actualizarAlquiladorEnBD(conexionMySQL, dni);
        v.alquilar();
        c.alquilar();
        alquileres.add(new Alquiler(v, c));
    }

    /**
     * Dada una matricula devuleve el alquiler del vehiculo que representa.
     *
     * @param matricula que identifica al vehiculo.
     * @return el aluiler que involucra al vehiculo.
     * @throws AlquilerVehiculoException si el vehiculo no esta alquilado.
     */
    public Alquiler obtenerAlquilerPorMatricula(String matricula) throws AlquilerVehiculoException {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo().getMatricula().equals(matricula)) {
                return alquiler;
            }
        }
        throw new AlquilerVehiculoException("El vechiculo no esta alquilado.");
    }

    /**
     * Devuleve una coleccion con los vehiculos que tiene alquilados un cliente
     *
     * @param dni que identifica al cliente.
     * @return una coleccion con los vehiculos que tiene alquilados el cliente.
     * @throws AlquilerVehiculoException si el cliente no tiene vehiculos
     * alquilados.
     */
    public ArrayList<Vehiculo> obtenerAlquileresCliente(String dni) throws AlquilerVehiculoException {
        ArrayList<Vehiculo> ac = new ArrayList<>();
        boolean existe = false;
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente().getDni().equals(dni)) {
                ac.add(alquiler.getVehiculo());
                existe = true;
            }
        }
        if (!existe) {
            throw new AlquilerVehiculoException("El cliente no tiene vehiculos alquilados.");
        }
        return ac;
    }

    /**
     * Dada una matricula eliminina un alquiler. El cliente devuelve el vehiculo
     * y finaliza el alquiler.
     *
     * @param matricula que identifica al vehiculo.
     * @throws AlquilerVehiculoException si el vehiculo no esta alquilado.
     * @throws java.sql.SQLException si ahy un fallo al actualizar la base de
     * datos.
     */
    public void eliminarAlquilerPorMatricula(String matricula) throws AlquilerVehiculoException, SQLException {
        Iterator<Alquiler> iter = alquileres.listIterator();
        while (iter.hasNext()) {
            Alquiler alquiler = iter.next();
            if (alquiler.getVehiculo().getMatricula().equals(matricula)) {
                alquiler.getVehiculo().actualizarAlquiladorEnBD(conexionMySQL, null);
                alquiler.getCliente().devolverVehiculo();
                alquiler.getVehiculo().devolver();
                iter.remove();
            }
        }
    }

    /**
     * Dado un dni elimina todos los alquileres que tenga el cliente
     * identidicado por dicho dni.
     *
     * @param dni que identifica al cliente.
     * @throws AlquilerVehiculoException si el cliente no tiene vehiculos
     * alquilados.
     * @throws java.sql.SQLException si ahy un fallo al actualizar la base de
     * datos.
     */
    public void eliminarAlquilerPorDni(String dni) throws AlquilerVehiculoException, SQLException {
        Iterator<Alquiler> i = alquileres.iterator();
        while (i.hasNext()) {
            Alquiler alquiler = i.next();
            if (alquiler.getCliente().getDni().equals(dni)) {
                alquiler.getVehiculo().actualizarAlquiladorEnBD(conexionMySQL, null);
                alquiler.getCliente().devolverVehiculo();
                alquiler.getVehiculo().devolver();
                i.remove();
            }
        }
    }

    /**
     * Devuelve el coste de alquilar un vehiculo durante una cantidad de dias
     * por un cliente determinado.
     *
     * @param matricula identifica al vehiculo
     * @param dni identifica al cliente
     * @param dias que dura el alquiler
     * @return catidad de alquilar el vehiculo durante los dias dados
     * @throws ObjetoNoExistenteException
     */
    public double obtenerPrecioAlquiler(String matricula, String dni, int dias) throws ObjetoNoExistenteException {
        double precio = vehiculos.obtenerVechiculo(matricula).alquilerTotal(dias);
        if (clientes.obtenerCliente(dni).isVip()) {
            precio *= 0.85;
        }
        return precio;
    }

    /**
     * Devuelve un array que contiene las matriculas de los vehiculos
     * alquilados.
     *
     * @return un array que contiene las matriculas de los vehiculos alquilados.
     */
    public String[] obtenerMatriculasAlquilados() {
        ArrayList<String> matriculas = new ArrayList<>();
        for (Alquiler alquiler : alquileres) {
            matriculas.add(alquiler.getVehiculo().getMatricula());
        }
        return matriculas.toArray(new String[matriculas.size()]);
    }

    /**
     * Carga la informacion desde un fichero;
     *
     * @param vehiculos la coleccion en la que se almacenan los vehiculos
     * existentes.
     * @param clientes la coleccion en la que se laquilan los clientes
     * existentes.
     */
    public void cargar(ColeccionVehiculos vehiculos, ColeccionClientes clientes) {
        String sentencia = "SELECT * FROM " + NOMBRE_TABLA;
        try {
            ResultSet resultSet = conexionMySQL.ejecutarConsulta(sentencia);

            while (resultSet.next()) {
                String matricula = resultSet.getString("matricula");
                String dni = resultSet.getString("clientealquilador");

                if (dni != null) {
                    Vehiculo v = vehiculos.obtenerVechiculo(matricula);
                    Cliente c = clientes.obtenerCliente(dni);
                    v.alquilar();
                    c.alquilar();
                    Alquiler a = new Alquiler(v, c);
                    alquileres.add(a);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Fallo al cargar los alquileres de la base de datos.\n"
                    + "Error MySQL: " + ex.getMessage(), "Error MySQL", JOptionPane.ERROR_MESSAGE);
        } catch (ObjetoNoExistenteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Objeto no existente", JOptionPane.ERROR_MESSAGE);
        } catch (AlquilerVehiculoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de alquiler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
