package Clases;

import Abstractas.V_Transporte;

/**
 * Clase que representa un objeto de tipo Microbus.
 * @author Kevin
 */
public class Microbus extends V_Transporte {
    
    public static final int PLAZAS_MAX = 50;    //Plazas maximas para los coches.
    
    /**
     * Inicializa el bus como no alquilado.
     */
    public Microbus() {
        super();
    }
    
    /**
     * Inicializa el bus con la matricula y el numero de plazas dados.
     * @param matricula la matricula del bus.
     * @param plazas el numero de plazas del bus.
     */
    public Microbus(String matricula, int plazas) {
        super(matricula, plazas);
    }
    
    /**
     * Devuelve el precio de alquilar el bus durante los dias dados.
     * @param dias dias por los que se alquila el bus.
     * @return el precio de alquilar el bus por dias dias.
     */
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal( + 2 * super.getPlazas());
    }
}
