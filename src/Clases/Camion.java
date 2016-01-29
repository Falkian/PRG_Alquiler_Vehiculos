package Clases;

import Abstractas.V_Carga;

public class Camion extends V_Carga{
    
    public static final int PMA_MAX = 100000;

    public Camion() {
    }

    public Camion(String matricula, double PMA) {
        super(matricula, PMA);
    }
    
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 40;
    }
}
