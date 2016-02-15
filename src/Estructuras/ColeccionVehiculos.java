package Estructuras;

import Abstractas.V_Carga;
import Abstractas.V_Transporte;
import Abstractas.Vehiculo;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import java.io.File;
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

    private static final String PATH = "/ficheros/vehiculos.txt";

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
    //TODO averiguar si el vehiculo ya esta antes de insertar
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

    //TODO Cargar de fichero
    public void cargar() {

    }

    /**
     * Guarda la informacion almacenada en la lista en un fichero.
     */
    //TODO repasar, falla el formato
    public void guardar() {
        File archivo = new File("ficheros/vehiculos.txt");
        try {        
            
            archivo.createNewFile();            
            PrintWriter writer = new PrintWriter(new FileWriter(archivo, false));

            writer.println("Vehiculo\t\tMatricula\t\tNumPlazas / PMA");

            for (Vehiculo vehiculo : vehiculos) {
                String tipo = vehiculo.getClass().getName().split("\\.", 2)[1];
                if (vehiculo instanceof V_Transporte) {
                    V_Transporte v = (V_Transporte) vehiculo;
                    writer.println(tipo + (tipo.length() < 8 ? "\t" : "" ) + "\t\t" + v.getMatricula() + "\t\t\t" + v.getPlazas());
                } else {
                    V_Carga v = (V_Carga) vehiculo;
                    writer.println(tipo + (tipo.length() < 8 ? "\t" : "" ) + "\t\t" + v.getMatricula() + "\t\t\t" + v.getPMA());
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
