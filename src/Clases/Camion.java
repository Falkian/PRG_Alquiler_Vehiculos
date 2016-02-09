package Clases;

import Abstractas.V_Carga;

/**
 * Clase que representa un objeto de tipo Camion.
 * @author Kevin
 */
public class Camion extends V_Carga{
    
    public static final int PMA_MAX = 100000;       //PMA maxima para los camiones.

    /**
     * Inicializa el camion como no alquilado.
     */
    public Camion() {
        super();
    }

    /**
     * Inicializa el camion con la matricula y PMA dados.
     * @param matricula la matricula del camion.
     * @param PMA el peso mazimo autorizado del camion.
     */
    public Camion(String matricula, double PMA) {
        super(matricula, PMA);
    }
    
    /**
     * Devuelve el precio de alquilar el camion durante los dias dados.
     * @param dias dias por los que se alquila el camion.
     * @return el precio de alquilar el camion por dias dias.
     */
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 40;
    }

    @Override
    public void mostrarInfoAlquiler(int dias, double alquiler) {
        System.out.println("El vehiculo es un camion con PMA " + getPMA() + "kilos "
                        + "y el alquiler para " + dias + " dias es de " + alquiler + " euros.");
    }
}
