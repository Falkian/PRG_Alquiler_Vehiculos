package Clases;

import Abstractas.V_Transporte;

public class Microbus extends V_Transporte {
    
    public static final int PLAZAS_MAX = 50;
    
    public Microbus() {
    }
    
    public Microbus(String matricula, int plazas) {
        super(matricula, plazas);
    }
    
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal( + 2 * super.getPlazas());
    }
}
