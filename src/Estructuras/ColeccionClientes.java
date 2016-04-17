package Estructuras;

import Clases.Cliente;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import Utilidades.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JOptionPane;

/**
 * Coleccion de objetos de la clase Cliente.
 *
 * @author Kevin
 */
public class ColeccionClientes {

    private static final String NOMBRE_TABLA = "clientes";  //Tabl aen la base de datos

    private final ArrayList<Cliente> clientes;             //Coleccion de clientes
    private final ConexionMySQL conexionMySQL;             //Conexion con la base de datos

    /**
     * Inicializa la coleccion con un tamanyo determinado.
     *
     * @param conexionMySQL conexion con la base de datos donde esta almacenada
     * la informacion.
     */
    public ColeccionClientes(ConexionMySQL conexionMySQL) {
        clientes = new ArrayList<>();
        this.conexionMySQL = conexionMySQL;
    }

    /**
     * Anayade un cliente a la coleccion y lo guarda en la base de datos.
     *
     * @param dni dni del cliente
     * @param nombre nombre del cliente
     * @param direccion direccion del cliente
     * @param tlf telefono del cliente
     * @param vip si del cliente es vip
     * @throws ObjetoYaExistenteException si el objeto a añadir ya existe.
     * @throws java.sql.SQLException si hay un fallo en la introducicon en la
     * base de datos.
     */
    public void anyadirCliente(String dni, String nombre, String direccion, String tlf, boolean vip) throws ObjetoYaExistenteException, SQLException {
        if (posicionCliente(dni) < 0) {
            Cliente c = new Cliente(dni, nombre, direccion, tlf);
            c.setVip(vip);
            c.guardarEnBD(conexionMySQL);
            clientes.add(c);
            clientes.sort(new ComparadorCliente());
        } else {
            throw new ObjetoYaExistenteException();
        }
    }

    /**
     * Devuelve el cliente identificado por el dni dado.
     *
     * @param dni el dni del cliente a buscar.
     * @return el cliente con el dni dado.
     * @throws ObjetoNoExistenteException si el cliente no existe en la
     * coleccion.
     */
    public Cliente obtenerCliente(String dni) throws ObjetoNoExistenteException {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getDni().equals(dni)) {
                return cliente;
            }
        }
        throw new ObjetoNoExistenteException("El cliente con DNI " + dni + " no existe.");
    }

    /**
     * Modifica el clientes identificado por el DNI dado, asignandole el cliente
     * dado.
     *
     * @param DNI dni del cliente
     * @param nombre nombre del cliente
     * @param direccion direccion del cliente
     * @param tlf telefono del cliente
     * @param vip si del cliente es vip
     * @throws ObjetoNoExistenteException si el vehiculo a modificar no existe.
     * @throws java.sql.SQLException si hay un fallo en la sentencia SQL.
     */
    public void modificarCliente(String DNI, String nombre, String direccion, String tlf, boolean vip) throws ObjetoNoExistenteException, SQLException {
        for (Cliente cliente : clientes) {
            if (cliente.getDni().equals(DNI)) {
                Cliente c = new Cliente(DNI, nombre, direccion, tlf);
                c.actualizaEnBD(conexionMySQL);
                cliente.setNombre(nombre);
                cliente.setDireccion(direccion);
                cliente.setTlf(tlf);
                cliente.setVip(vip);
                return;
            }
        }
        throw new ObjetoNoExistenteException("El cliente con DNI " + DNI + " no esta registrado.");
    }

    /**
     * Elimina el cliente identificado por el DNI dado.
     *
     * @param dni el DNI del cliente.
     * @throws ObjetoNoExistenteException so no existe el cliente en la
     * coleccion.
     * @throws java.sql.SQLException si hay algun fallo con la consulta SQL.
     */
    public void eliminarCliente(String dni) throws ObjetoNoExistenteException, SQLException {
        int index = posicionCliente(dni);
        if (index >= 0) {
            clientes.get(index).eliminaDeBD(conexionMySQL);
            clientes.remove(index);
        } else {
            throw new ObjetoNoExistenteException("El cliente con dni " + dni + " no existe.");
        }
    }

    /**
     * Devuelve un iterador para la coleccion de clientes.
     *
     * @return un iterador para la coleccion de clientes.
     */
    public IteradorClientes getIterador() {
        return new IteradorClientes(clientes);
    }

    /**
     * Devuelve un array bidimensional con la informacion de la lista. Cada fila
     * contiene un elemento, y las columnas contienen el dni, nombre, direccion
     * telefono y vip, respepctivamente.
     *
     * @return un array bidimensional con la informacion de la lista.
     */
    public String[][] obtenerDataArray() {
        String[][] ret = new String[clientes.size()][3];
        for (int i = 0; i < clientes.size(); i++) {
            ret[i] = clientes.get(i).dataToArray();
        }
        return ret;
    }

    /**
     * Devuelve un array con los DNIs de los clientes de la coleccion.
     *
     * @return un array con los DNIs de los clientes de la coleccion.
     */
    public String[] obtenerArrayDnis() {
        String[] dnis = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            dnis[i] = clientes.get(i).getDni();
        }
        return dnis;
    }

    /**
     * Carga la informacion de la base de datos en el programa. *
     */
    public void cargar() {
        String sentencia = "SELECT * FROM " + NOMBRE_TABLA;
        try {
            ResultSet resultSet = conexionMySQL.ejecutarConsulta(sentencia);

            while (resultSet.next()) {
                String DNI = resultSet.getString("dni");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String telefono = resultSet.getString("telefono");
                boolean vip = resultSet.getString("vip").equals("S");

                Cliente c = new Cliente(DNI, nombre, direccion, telefono);
                c.setVip(vip);

                clientes.add(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los clientes de la base de datos.\n"
                    + "Error MySQL: " + e.getMessage(), "Error MySQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Devuelve la posicion del cliente identificado por el dni introducido en
     * la coleccion.
     *
     * @param dni del cliente a buscar.
     * @return la posicion del cliente; -1 si no existe en la coleccion.
     */
    private int posicionCliente(String dni) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni().equals(dni)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Clase para comparar dos clientes y poder ordenarlos por DNI.
     */
    private class ComparadorCliente implements Comparator<Cliente> {

        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c1.getDni().compareTo(c2.getDni());
        }
    }
}
