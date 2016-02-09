package Estructuras;

import Abstractas.Vehiculo;
import Excepciones.AlmacenVehiculosLlenoException;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;

/**
 * Coleccion de objetos de la clase Objeto.
 *
 * @author Kevin
 */
public class ColeccionVehiculos {

    private Vehiculo[] vehiculos;           //Array de objetos Vehiculo.
    private int primeraPosicionLibre;       //Indice de la primera posicion vacia en el array.

    /**
     * Inicializa la coleccion con un tamanyo determinado.
     *
     * @param tamanyo el tamanyo de la coleccion.
     */
    public ColeccionVehiculos(int tamanyo) {
        vehiculos = new Vehiculo[tamanyo];
        primeraPosicionLibre = 0;
    }

    /**
     * Anayade un vehiculo a la coleccion.
     *
     * @param v el vechiulo a anyadir.
     * @throws AlmacenVehiculosLlenoException si la coleccion ya esta llena.
     * @throws ObjetoYaExistenteException si el objeto ya existe en la coleccion.
     */
    //TODO excepcion "existente"; Replantear ambas listas como una de object??
    public void anyadirVehiculo(Vehiculo v) throws AlmacenVehiculosLlenoException, ObjetoYaExistenteException {
        if (primeraPosicionLibre < vehiculos.length) {
            for (Vehiculo vehiculo : vehiculos) {
                if (vehiculo != null && vehiculo.getMatricula().equals(v.getMatricula())) {
                    throw new ObjetoYaExistenteException("Ya existe un vehiculo con esa matricula.");
                }
            }
            vehiculos[primeraPosicionLibre++] = v;
            System.out.println("Vehiculo anyadido.");
        } else {
            throw new AlmacenVehiculosLlenoException();
        }
    }

    /**
     * Devuelve el vechiculo identificado por la amtricula dada.
     *
     * @param matricula la matricula del vehiculo a buscar.
     * @return el vehiculo con laa matricula dada.
     * @throws ObjetoNoExistenteException si no existe el vehiculo en la
     * coleccion.
     */
    public Vehiculo obtenerVechiculo(String matricula) throws ObjetoNoExistenteException {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null && vehiculo.getMatricula().equals(matricula)) {
                return vehiculo;
            }
        }
        throw new ObjetoNoExistenteException("El vehiculo no existe en el almacen.");
    }
}
