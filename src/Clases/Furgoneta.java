package Clases;

import Abstractas.V_Carga;

/**
 * Clase que representa un objeto de tipo Furgoneta.
 * @author Kevin
 */
public class Furgoneta extends V_Carga {

    public static final int PMA_MAX = 10000;    //PMA maxima para las furgonetas.
    
    /**
     * Inicializa la furgoneta como no alquilada.
     */
    public Furgoneta() {
        super();
    }

    /**
     * Inicializa la furgoneta con la matricula y PMA dados.
     * @param matricula la matricula de la furgoneta.
     * @param PMA el peso mazimo autorizado de la furgoneta.
     */
    public Furgoneta(String matricula, double PMA) {
        super(matricula, PMA);
    }
}
