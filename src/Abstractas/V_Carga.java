package Abstractas;

public class V_Carga extends Vehiculo {
    private double PMA;

    public V_Carga(){
    }
    
    public V_Carga(String matricula, double PMA) {
        super(matricula);
        this.PMA = PMA;
    }

    public double getPMA() {
        return PMA;
    }

    public void setPMA(double PMA) {
        this.PMA = PMA;
    }
    
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 20 * PMA;
    }
}
