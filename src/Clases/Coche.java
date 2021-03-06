package Clases;

import Abstractas.V_Transporte;

/**
 * Clase que representa un objeto de tipo Coche.
 *
 * @author Kevin
 */
public class Coche extends V_Transporte {

    public static final int PLAZAS_MAX = 7;     //Plazas maximas para los coches.

    /**
     * Inicializa el coche con la matricula y el numero de plazas dados.
     *
     * @param matricula la matricula del coche.
     * @param plazas el numero de plazas del coche.
     */
    public Coche(String matricula, int plazas) {
        super(matricula, plazas);
    }

    @Override
    public void mostrarInfoAlquiler(int dias, double alquiler) {
        System.out.println("El vehiculo es un coche de " + getPlazas() + " plazas "
                + "y el alquiler para " + dias + " dias es de " + alquiler + " euros.");
    }

}
