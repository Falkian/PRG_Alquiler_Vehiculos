package Abstractas;

public abstract class Vehiculo {
    private String matricula;

    public Vehiculo(){
    }
    
    public Vehiculo(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    protected double alquilerTotal(int dias) {
        return 50 * dias;
    }
}
