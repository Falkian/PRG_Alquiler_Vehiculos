package Estructuras;

import Abstractas.Iterador;
import java.util.ArrayList;
import Abstractas.Vehiculo;

/**
 * Iterador para recorrer los elementos de una coleccion de vehiculos.
 *
 * @author Kevin
 */
public class IteradorVehiculos extends Iterador<Vehiculo> {

    /**
     * Crea un iterador sobre la lista dada.
     *
     * @param lista la lista sobre la que se iterara.
     */
    public IteradorVehiculos(ArrayList<Vehiculo> lista) {
        super(lista);
    }

    /**
     * Posiciona el iterador sobre el elemento identificado por la matricula
     * dada. Si no se encuentra se posiciona en la ultima posicion.
     *
     * @param matricula la matricula que identifica al elemento.
     */
    @Override
    public void seleccionar(String matricula) {
        posicion = 0;
        for (Vehiculo vehiculo : lista) {
            if (vehiculo.getMatricula().equals(matricula)) {
                return;
            }
            posicion++;
        }
    }
}
