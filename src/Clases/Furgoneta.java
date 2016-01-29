package Clases;

import Abstractas.V_Carga;

public class Furgoneta extends V_Carga {

    public static final int PMA_MAX = 10000;
    
    public Furgoneta() {
    }

    public Furgoneta(String matricula, double PMA) {
        super(matricula, PMA);
    }
    
//    @Override
//    public double alquilerTotal(int dias) {
//        return super.alquilerTotal(dias);
//    }
}
