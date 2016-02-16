package Estructuras;

import Abstractas.V_Carga;
import Abstractas.V_Transporte;
import Abstractas.Vehiculo;
import Clases.Camion;
import Clases.Coche;
import Clases.Furgoneta;
import Clases.Microbus;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Coleccion de objetos de la clase Objeto.
 *
 * @author Kevin
 */
public class ColeccionVehiculos {

    private static final String PATH = "ficheros/vehiculos.txt";

    private final ArrayList<Vehiculo> vehiculos;      //Coleccion de vehiculos.

    /**
     * Inicializa la coleccion con un tamanyo determinado.
     */
    public ColeccionVehiculos() {
        vehiculos = new ArrayList<>();
    }

    /**
     * Anayade un vehiculo a la coleccion.
     *
     * @param v el vechiulo a anyadir.
     * @throws ObjetoYaExistenteException si el objeto ya existe en la
     * coleccion.
     */
    public void anyadirVehiculo(Vehiculo v) throws ObjetoYaExistenteException {
        if (posicionVehiculo(v.getMatricula()) < 0) {
            vehiculos.add(v);
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
        throw new ObjetoNoExistenteException("El vehiculo no existe en el almacen.");
    }

    /**
     * Elimina el vehiculo identificado por la matricula dada.
     *
     * @param matricula la matricula del vehiculo.
     * @throws ObjetoNoExistenteException so no existe el vehiculo en la
     * coleccion,
     */
    public void eliminarVehiculo(String matricula) throws ObjetoNoExistenteException {
        int index = posicionVehiculo(matricula);
        if (index >= 0) {
            vehiculos.remove(index);
        } else {
            throw new ObjetoNoExistenteException("El vehiculo no existe en el almacen.");
        }
    }

    /**
     * Carga la informacion del fichero en el programa
     */
    public void cargar() {
        File archivo = new File(PATH);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(archivo));

            //Lee el encabezado del archivo e informa si esta vacio.
            String str = reader.readLine();
            if (str == null) {
                System.out.println("Archivo de vehiclos en blanco.");
                return;
            } else {
                //Lee la primera linea del archivo e informa si no contiene datos.
                str = reader.readLine();
                if (str == null) {
                    System.out.println("El archivo de vechiculos no contiene informacion.");
                }
                while (str != null) {
                    String[] datos = str.split("\\t+");
                    String tipo = datos[0].trim();
                    String matricula = datos[1];
                    switch (tipo) {
                        case "Coche":
                            vehiculos.add(new Coche(matricula, Integer.parseInt(datos[2])));
                            break;
                        case "Microbus":
                            vehiculos.add(new Microbus(matricula, Integer.parseInt(datos[2])));
                            break;
                        case "Furgoneta":
                            vehiculos.add(new Furgoneta(matricula, Double.parseDouble(datos[2])));
                            break;
                        case "Camion":
                            vehiculos.add(new Camion(matricula, Double.parseDouble(datos[2])));
                            break;
                    }
                    str = reader.readLine();
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Fin de la carga de vehiculos.");
        }
    }

    /**
     * Guarda la informacion almacenada en la lista en un fichero.
     */
    public void guardar() {
        File archivo = new File(PATH);
        PrintWriter writer = null;
        try {
            archivo.createNewFile();
            writer = new PrintWriter(new FileWriter(archivo, false));

            writer.println("Vehiculo\t\tMatricula\t\tNumPlazas / PMA");

            for (Vehiculo vehiculo : vehiculos) {
                String tipo = vehiculo.getClass().getName().split("\\.", 2)[1];
                writer.printf("%-9s\t\t%-9s\t\t", tipo, vehiculo.getMatricula());
                if (vehiculo instanceof V_Transporte) {
                    V_Transporte v = (V_Transporte) vehiculo;
                    writer.println(v.getPlazas());
                } else {
                    V_Carga v = (V_Carga) vehiculo;
                    writer.println(v.getPMA());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
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

}
