package Abstractas;

public abstract class V_Transporte extends Vehiculo {
    private int plazas;

    public V_Transporte() {
    }
    
    public V_Transporte(String matricula, int plazas) {
        super(matricula);
        this.plazas = plazas;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
    
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 1.5 * plazas * dias;
    }
}
