package Clases;

import Abstractas.V_Transporte;

public class Coche extends V_Transporte {
    
    public static final int PLAZAS_MAX = 7;

    public Coche() {
    }
    
    public Coche(String matricula, int plazas) {
        super(matricula, plazas);
    }
}
