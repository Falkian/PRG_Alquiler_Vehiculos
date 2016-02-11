package Estructuras;

import Abstractas.Vehiculo;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import java.util.ArrayList;

/**
 * Coleccion de objetos de la clase Objeto.
 *
 * @author Kevin
 */
public class ColeccionVehiculos {

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
     * Devuelve el vechiculo identificado por la amtricula dada.
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
     * Devuelve la posicion del vehiculo identificado por la matricula
     * introducida en la coleccion.
     *
     * @param matricula del vehiculo a buscar.
     * @return la posicion del vehiculo; -1 si no existe en la coleccion.
     */
    public int posicionVehiculo(String matricula) {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getMatricula().equals(matricula)) {
                return i;
            }
        }
        return -1;
    }
}
