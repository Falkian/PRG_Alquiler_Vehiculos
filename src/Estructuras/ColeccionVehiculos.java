package Estructuras;

import Abstractas.V_Carga;
import Abstractas.V_Transporte;
import Abstractas.Vehiculo;
import Clases.Camion;
import Clases.Coche;
import Clases.Furgoneta;
import Clases.Microbus;
import Excepciones.FormatoIncorrectoException;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import Utilidades.ConexionMySQL;
import Utilidades.TiposVehiculos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JOptionPane;

/**
 * Coleccion de objetos de la clase Objeto.
 *
 * @author Kevin
 */
public class ColeccionVehiculos {

    private final ArrayList<Vehiculo> vehiculos;        //Coleccion de vehiculos.
    private final ConexionMySQL conexionMySQL;          //Conexion con la base de datos
    private final ConexionMySQL conexionMySQL2;         //Segunda conexion con la base de datos, utilizada para poder mantener dos resultados simultaneamente

    /**
     * Inicializa la coleccion con un tamanyo determinado.
     *
     * @param conexionMySQL la conexion con la base de datos
     * @param conexionMySQL2 segunda conexion con la base de datos.
     */
    public ColeccionVehiculos(ConexionMySQL conexionMySQL, ConexionMySQL conexionMySQL2) {
        vehiculos = new ArrayList<>();
        this.conexionMySQL = conexionMySQL;
        this.conexionMySQL2 = conexionMySQL2;
    }

    /**
     * Anyade un vehiculo a la colccion dando sus caracteristicas.
     *
     * @param tipo el tipo de vehiculo
     * @param matricula la matricula del vehiculo
     * @param caract las plazas o PMA
     * @throws ObjetoYaExistenteException si ya existe un vehiculo con la misma
     * matricula
     * @throws FormatoIncorrectoException si las plazas o PMA no tienen un valor
     * valido
     * @throws java.sql.SQLException si hay un fallo en la introduccion en la
     * base de datos.
     */
    public void anyadirVehiculo(TiposVehiculos tipo, String matricula, double caract) throws ObjetoYaExistenteException, FormatoIncorrectoException, SQLException {
        //Si el vehiculo no existe en la coleccion lo añade, si ya existe lanza una excepcion
        if (posicionVehiculo(matricula) < 0) {
            switch (tipo) {
                case COCHE:
                    double min = (int) TiposVehiculos.COCHE.getCaractMin();
                    double max = (int) TiposVehiculos.COCHE.getCaractMax();
                    if (caract < min || caract > max) {
                        throw new FormatoIncorrectoException("Las plazas de un coche deben estar entre " + (int) min + " y " + (int) max);
                    } else {
                        Coche c = new Coche(matricula, (int) caract);
                        c.guardarEnBD(conexionMySQL, true, null);
                        vehiculos.add(c);
                    }
                    break;
                case MICROBUS:
                    min = (int) TiposVehiculos.MICROBUS.getCaractMin();
                    max = (int) TiposVehiculos.MICROBUS.getCaractMax();
                    if (caract < min || caract > max) {
                        throw new FormatoIncorrectoException("Las plazas de un microbus deben estar entre " + (int) min + " y " + (int) max);
                    } else {
                        Microbus m = new Microbus(matricula, (int) caract);
                        m.guardarEnBD(conexionMySQL, true, null);
                        vehiculos.add(m);
                    }
                    break;
                case FURGONETA:
                    min = TiposVehiculos.FURGONETA.getCaractMin();
                    max = TiposVehiculos.FURGONETA.getCaractMax();
                    if (caract < min || caract > max) {
                        throw new FormatoIncorrectoException("El PMA de una furgoneta debe estar entre " + min + " y " + max);
                    } else {
                        Furgoneta f = new Furgoneta(matricula, caract);
                        f.guardarEnBD(conexionMySQL, true, null);
                        vehiculos.add(f);
                    }
                    break;
                case CAMION:
                    min = TiposVehiculos.CAMION.getCaractMin();
                    max = TiposVehiculos.CAMION.getCaractMax();
                    if (caract < min || caract > max) {
                        throw new FormatoIncorrectoException("El PMA de un camion debe estar entre " + min + " y " + max);
                    } else {
                        Camion c = new Camion(matricula, caract);
                        c.guardarEnBD(conexionMySQL, true, null);
                        vehiculos.add(c);
                    }
                    break;
            }
            vehiculos.sort(new ComparadorVehiculo());
        } else {
            throw new ObjetoYaExistenteException();
        }
    }

    /**
     * Devuelve el vechiculo identificado por la matricula dada.
     *
     * @param matricula la matricula del vehiculo a buscar.
     * @return el vehiculo con laa matricula dada.
     * @throws ObjetoNoExistenteException si no existe el vehiculo en la
     * coleccion.
     */
    public Vehiculo obtenerVechiculo(String matricula) throws ObjetoNoExistenteException {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equals(matricula)) {
                return vehiculo;
            }
        }
        throw new ObjetoNoExistenteException("El vehiculo con matricula " + matricula + " no existe en el almacen.");
    }

    /**
     * Modifica el vehiculo identificado por la matricula dada, asignandole las
     * caracteristicas dadas.
     *
     * @param matricula la matricula del vehiculo a modificar.
     * @param tipo el tipo de vehiculo
     * @param caract las plazas o PMA del vehiculo
     * @throws ObjetoNoExistenteException si el vehiculo a modificar no existe.
     * @throws FormatoIncorrectoException si los datos no tienen el formato
     * adecuado
     * @throws java.sql.SQLException si ahy un fallo al actualizar la base de
     * datos.
     */
    public void modificarVehiculo(TiposVehiculos tipo, String matricula, double caract) throws ObjetoNoExistenteException, FormatoIncorrectoException, SQLException {
        int posicionVehiculo = posicionVehiculo(matricula);
        if (posicionVehiculo >= 0) {
            switch (tipo) {
                case COCHE:
                    double min = (int) TiposVehiculos.COCHE.getCaractMin();
                    double max = (int) TiposVehiculos.COCHE.getCaractMax();
                    if (caract < min || caract > max) {
                        throw new FormatoIncorrectoException("Las plazas de un coche deben estar entre " + (int) min + " y " + (int) max);
                    } else {
                        Coche c = new Coche(matricula, (int) caract);
                        c.actualizaEnBD(conexionMySQL);
                        vehiculos.set(posicionVehiculo, c);
                    }
                    break;
                case MICROBUS:
                    min = (int) TiposVehiculos.MICROBUS.getCaractMin();
                    max = (int) TiposVehiculos.MICROBUS.getCaractMax();
                    if (caract < min || caract > max) {
                        throw new FormatoIncorrectoException("Las plazas de un microbus deben estar entre " + (int) min + " y " + (int) max);
                    } else {
                        Microbus f = new Microbus(matricula, (int) caract);
                        f.actualizaEnBD(conexionMySQL);
                        vehiculos.set(posicionVehiculo, f);
                    }
                    break;
                case FURGONETA:
                    min = TiposVehiculos.FURGONETA.getCaractMin();
                    max = TiposVehiculos.FURGONETA.getCaractMax();
                    if (caract < min || caract > max) {
                        throw new FormatoIncorrectoException("El PMA de una furgoneta debe estar entre " + min + " y " + max);
                    } else {
                        Furgoneta f = new Furgoneta(matricula, caract);
                        f.actualizaEnBD(conexionMySQL);
                        vehiculos.set(posicionVehiculo, f);
                    }
                    break;
                case CAMION:
                    min = TiposVehiculos.CAMION.getCaractMin();
                    max = TiposVehiculos.CAMION.getCaractMax();
                    if (caract < min || caract > max) {
                        throw new FormatoIncorrectoException("El PMA de un camion debe estar entre " + min + " y " + max);
                    } else {
                        Camion c = new Camion(matricula, caract);
                        c.actualizaEnBD(conexionMySQL);
                        vehiculos.set(posicionVehiculo, c);
                    }
                    break;
            }
        } else {
            throw new ObjetoNoExistenteException();
        }
    }

    /**
     * Elimina el vehiculo identificado por la matricula dada.
     *
     * @param matricula la matricula del vehiculo.
     * @throws ObjetoNoExistenteException so no existe el vehiculo en la
     * coleccion.
     * @throws java.sql.SQLException si ahy un fallo al eliminar de la base de
     * datos.
     */
    public void eliminarVehiculo(String matricula) throws ObjetoNoExistenteException, SQLException {
        int index = posicionVehiculo(matricula);
        if (index >= 0) {
            vehiculos.get(index).eliminaDeBD(conexionMySQL);
            vehiculos.remove(index);
        } else {
            throw new ObjetoNoExistenteException("El vehiculo con matricula " + matricula + " no existe en el almacen.");
        }
    }

    /**
     * Devuelve un iterador para la coleccion de vehiculos.
     *
     * @return un iterador para la coleccion de vehiculos.
     */
    public IteradorVehiculos getIterador() {
        return new IteradorVehiculos(vehiculos);
    }

    /**
     * Devuelve un array bidimensional con la informacion de la lista. Cada fila
     * contiene un elemento, y las columnas contienen la matricula, tipo y
     * caracteristica (plazas/PMA), respepctivamente.
     *
     * @return un array bidimensional con la informacion de la lista.
     */
    public String[][] obtenerDataArray() {
        String[][] ret = new String[vehiculos.size()][3];
        for (int i = 0; i < vehiculos.size(); i++) {
            ret[i] = vehiculos.get(i).dataToArray();
        }
        return ret;
    }

    /**
     * Devuelve un array con las matriculas de los vehiculos de la coleccion.
     *
     * @return un array con las matriculas de los vehiculos de la coleccion.
     */
    public String[] obtenerArrayMatriculas() {
        String[] matriculas = new String[vehiculos.size()];
        for (int i = 0; i < vehiculos.size(); i++) {
            matriculas[i] = vehiculos.get(i).getMatricula();
        }
        return matriculas;
    }

    /**
     * Devuelve un array con las matriculas de los vehiculos que no estan
     * alquilados.
     *
     * @return un array con las matriculas de los vehiculos no alquilados.
     */
    public String[] obtenerArrayMatriculasLibres() {
        ArrayList<String> matriculas = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            if (!vehiculo.isAlquilado()) {
                matriculas.add(vehiculo.getMatricula());
            }
        }
        return matriculas.toArray(new String[matriculas.size()]);
    }

    /**
     * Carga la informacion del fichero en el programa
     */
    public void cargar() {
        try {
            cargarCoches();
            cargarMicrobuses();
            cargarFurgonetas();
            cargarCamiones();

            vehiculos.sort(new ComparadorVehiculo());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los vehiculos de la base de datos.\n"
                    + "Error MySQL: " + e.getMessage(), "Error MySQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga los coches de la base de datos.
     *
     * @throws SQLException si hay un fallo en la consulta.
     */
    private void cargarCoches() throws SQLException {
        String sentenciacoches = "SELECT * FROM " + Coche.NOMBRE_TABLA_COCHES;
        ResultSet resultcoches = conexionMySQL.ejecutarConsulta(sentenciacoches);

        while (resultcoches.next()) {
            String matricula = resultcoches.getString("matricula");

            String sentenciaPersonas = "SELECT * FROM " + V_Transporte.NOMBRE_TABLA + " WHERE matricula = '" + matricula + "'";
            ResultSet resultTransporte = conexionMySQL2.ejecutarConsulta(sentenciaPersonas);

            resultTransporte.next();
            int plazas = resultTransporte.getInt("numplazas");

            Coche coche = new Coche(matricula, plazas);
            vehiculos.add(coche);
        }
    }

    /**
     * Carga los microbuses de la base de datos.
     *
     * @throws SQLException si hay un fallo en la consulta.
     */
    private void cargarMicrobuses() throws SQLException {
        String sentenciabuses = "SELECT * FROM " + Microbus.NOMBRE_TABLA_MICROBUSES;
        ResultSet resultbuses = conexionMySQL.ejecutarConsulta(sentenciabuses);

        while (resultbuses.next()) {
            String matricula = resultbuses.getString("matricula");

            String sentenciaPersonas = "SELECT * FROM " + V_Transporte.NOMBRE_TABLA + " WHERE matricula = '" + matricula + "'";
            ResultSet resultTransporte = conexionMySQL2.ejecutarConsulta(sentenciaPersonas);

            resultTransporte.next();
            int plazas = resultTransporte.getInt("numplazas");

            Microbus microbus = new Microbus(matricula, plazas);
            vehiculos.add(microbus);
        }
    }

    /**
     * Carga las furgonetas de la base de datos.
     *
     * @throws SQLException si hay un fallo en la consulta.
     */
    private void cargarFurgonetas() throws SQLException {
        String sentenciafurgonetas = "SELECT * FROM " + Furgoneta.NOMBRE_TABLA_FURGONETAS;
        ResultSet resultfurgonetas = conexionMySQL.ejecutarConsulta(sentenciafurgonetas);

        while (resultfurgonetas.next()) {
            String matricula = resultfurgonetas.getString("matricula");

            String sentenciaPersonas = "SELECT * FROM " + V_Carga.NOMBRE_TABLA + " WHERE matricula = '" + matricula + "'";
            ResultSet resultTransporte = conexionMySQL2.ejecutarConsulta(sentenciaPersonas);

            resultTransporte.next();
            double pma = resultTransporte.getDouble("pma");

            Furgoneta furgoneta = new Furgoneta(matricula, pma);
            vehiculos.add(furgoneta);
        }
    }

    /**
     * Carga los camiones de la base de datos.
     *
     * @throws SQLException si hay un fallo en la consulta.
     */
    private void cargarCamiones() throws SQLException {
        String sentenciacamiones = "SELECT * FROM " + Camion.NOMBRE_TABLA_CAMIONES;
        ResultSet resultcamiones = conexionMySQL.ejecutarConsulta(sentenciacamiones);

        while (resultcamiones.next()) {
            String matricula = resultcamiones.getString("matricula");

            String sentenciaPersonas = "SELECT * FROM " + V_Carga.NOMBRE_TABLA + " WHERE matricula = '" + matricula + "'";
            ResultSet resultTransporte = conexionMySQL2.ejecutarConsulta(sentenciaPersonas);

            resultTransporte.next();
            double pma = resultTransporte.getDouble("pma");

            Camion camion = new Camion(matricula, pma);
            vehiculos.add(camion);
        }
    }

    /**
     * Devuelve la posicion del vehiculo identificado por la matricula
     * introducida en la coleccion.
     *
     * @param matricula del vehiculo a buscar.
     * @return la posicion del vehiculo; -1 si no existe en la coleccion.
     */
    private int posicionVehiculo(String matricula) {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getMatricula().equals(matricula)) {
                return i;
            }
        }
        return -1;
    }

    private class ComparadorVehiculo implements Comparator<Vehiculo> {

        @Override
        public int compare(Vehiculo v1, Vehiculo v2) {
            return v1.getMatricula().compareTo(v2.getMatricula());
        }
    }
}
