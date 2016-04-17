package Estructuras;

import Clases.Alquiler;
import Clases.RegistroAlquiler;
import Abstractas.Vehiculo;
import Clases.Cliente;
import Excepciones.ObjetoNoExistenteException;
import Utilidades.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase que representa el histórico de todos los alquileres relizados.
 *
 * @author Kevin
 */
public class HistorialAlquileres {

    private static final String NOMBRE_TABLA = "historialalquileres";

    private final ArrayList<RegistroAlquiler> historial;        //Coleccion de registros
    private final ConexionMySQL conexionMySQL;
    private final ConexionMySQL conexionMySQL2;

    /**
     * Constructor por defecto.
     *
     * @param conexionMySQL la conexion con la base de datos.
     * @param conexionMySQL2 segunda conexion con la base de datos.
     */
    public HistorialAlquileres(ConexionMySQL conexionMySQL, ConexionMySQL conexionMySQL2) {
        historial = new ArrayList<>();
        this.conexionMySQL = conexionMySQL;
        this.conexionMySQL2 = conexionMySQL2;
    }

    /**
     * Anyade un registro dados el alquiler y la cantidad de dias.
     *
     * @param alquiler el alquiler a registrar.
     * @param dias la duración del alquiler.
     * @return el precio del alquiler.
     * @throws java.sql.SQLException si hay un fallo al actualizar l abase de datos.
     */
    public double anyadirRegistro(Alquiler alquiler, int dias) throws SQLException {
        String sentencia = "INSERT INTO " + NOMBRE_TABLA + " (matricula, dni, numdias) VALUES ('"
                + alquiler.getVehiculo().getMatricula() + "', '" + alquiler.getCliente().getDni()
                + "', " + dias + ")";

        conexionMySQL.ejecutaSentencia(sentencia);
        alquiler.getVehiculo().actualizarPrimeraVezEnBD(conexionMySQL, false);
        historial.add(new RegistroAlquiler(alquiler, dias));

        double preciob = alquiler.getVehiculo().alquilerTotal(dias);
        double precio = preciob;

        if (isPrimeraVez(alquiler.getVehiculo().getMatricula())) {
            precio -= preciob * 0.25;
        }
        if (alquiler.getCliente().isVip()) {
            precio -= preciob * 0.15;
        }
        
        return precio;
    }

    /**
     * Devuelve el total de todos los alquileres realizados.
     *
     * @return el total de todos los alquileres realizados
     */
    public double getTotal() {
        double total = 0;
        for (RegistroAlquiler registro : historial) {
            Vehiculo v = registro.getAlquiler().getVehiculo();
            double precio = v.alquilerTotal(registro.getDias());
            Cliente c = registro.getAlquiler().getCliente();

            double descuentoprimera = 0;
            //Comprueba si es la primera vez que se alquilo el vehiculo
            if (isPrimeraVez(v.getMatricula())) {
                descuentoprimera = precio * 0.25;
            }

            double descuentovip = 0;
            //Comprueba si el cliente era VIP
            if (c != null && c.isVip()) {
                descuentovip = precio * 0.15;
            }
            total += precio - descuentoprimera - descuentovip;
        }
        return total;
    }

    /**
     * Devuelve si es la primera vez que se alquila un vehiculo.
     *
     * @param matricula matricula del vehiculo.
     * @return true si el vehiculo ha sido alquilado con anterioridad; false en
     * caso contrario.
     */
    public boolean isPrimeraVez(String matricula) {
        for (RegistroAlquiler registro : historial) {
            if (registro.getAlquiler().getVehiculo().getMatricula().equals(matricula)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Devuelve un array bidimensional con la informacion de la lista. Cada fila
     * contiene un elemento, y las columnas contienen la matricula, DNI,
     * duracion, si era el primer alquiler, si era VIP y precio,
     * respepctivamente.
     *
     * @return un array bidimensional con la informacion de la lista.
     */
    public String[][] obtenerDataArray() {
        String[][] ret = new String[historial.size()][];
        ArrayList<Vehiculo> aparecidos = new ArrayList<>();
        for (int i = 0; i < historial.size(); i++) {
            Vehiculo v = historial.get(i).getAlquiler().getVehiculo();
            Cliente c = historial.get(i).getAlquiler().getCliente();
            int dias = historial.get(i).getDias();
            double precio = historial.get(i).getAlquiler().getVehiculo().alquilerTotal(dias);

            String primerainfo = "\u2717";
            //Comprueba si es la primera vez que se alquilo el vehiculo
            double descuentoprimera = 0;
            if (!aparecidos.contains(historial.get(i).getAlquiler().getVehiculo())) {
                aparecidos.add(historial.get(i).getAlquiler().getVehiculo());
                descuentoprimera = precio * 0.25;

                primerainfo = "\u2713 (" + descuentoprimera + "€)";
            }

            String vipinfo = "\u2717";
            //Comprueba si el cliente era VIP
            double descuentovip = 0;
            if (c != null && c.isVip()) {
                descuentovip = precio * 0.15;
                vipinfo = "\u2713 (" + descuentovip + "€)";
            }

            precio -= descuentoprimera + descuentovip;
            ret[i] = new String[]{v.getMatricula(), c == null ? "Ya no existe" : c.getDni(), "" + dias, primerainfo, vipinfo, "" + precio + "€"};
        }
        return ret;
    }

    /**
     * Carga la informacion del fichero en el programa
     *
     * @param vehiculos coleccion de vehiculos.
     * @param clientes coleccion de clientes.
     */
    public void cargar(ColeccionVehiculos vehiculos, ColeccionClientes clientes) {
        String sentencia = "SELECT * FROM " + NOMBRE_TABLA;
        try {
            ResultSet resultSet = conexionMySQL.ejecutarConsulta(sentencia);

            while (resultSet.next()) {
                String matricula = resultSet.getString("matricula");
                String dni = resultSet.getString("dni");
                int ndias = resultSet.getInt("numdias");

                Vehiculo v = vehiculos.obtenerVechiculo(matricula);
                Cliente c = null;
                if (dni != null) {
                    c = clientes.obtenerCliente(dni);
                }
                Alquiler a = new Alquiler(v, c);
                RegistroAlquiler r = new RegistroAlquiler(a, ndias);

                v.actualizarPrimeraVezEnBD(conexionMySQL2, false);
                historial.add(r);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar el historico de alquileres de la base de datos.\n"
                    + "Error MySQL: " + ex.getMessage(), "Error MySQL", JOptionPane.ERROR_MESSAGE);
        } catch (ObjetoNoExistenteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Objeto no existente", JOptionPane.ERROR_MESSAGE);
        }
    }
}
