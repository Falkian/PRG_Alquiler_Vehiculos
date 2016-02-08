package Abstractas;

/**
 * Clase abstracta V_Carga, define las propiedades y metodos particulares de los
 * vehiculos de carga.
 *
 * @author Kevin
 */
public abstract class V_Carga extends Vehiculo {

    private double PMA;         //Peso maximo autorizado

    /**
     * Constructor vacio, inicializa el vehiculo de carga como no alquilado.
     */
    public V_Carga() {
        super();
    }

    /**
     * Inicializa el vehiculo de carga como no alquilado con la matricula y PMA
     * dados.
     *
     * @param matricula la matricula del vehiculo.
     * @param PMA el peso maximo autorizado del vehiculo.
     */
    public V_Carga(String matricula, double PMA) {
        super(matricula);
        this.PMA = PMA;
    }

    /**
     * Devuleve el peso maximo autorizado.
     * @return el peso maximo autorizado.
     */
    public double getPMA() {
        return PMA;
    }

    /**
     * Establece el peso maximo autorizado.
     * @param PMA el peso maximo autorizado del vehiculo.
     */
    public void setPMA(double PMA) {
        this.PMA = PMA;
    }

    /**
     * Calcula el precio de alquilar el vechiulo durante los dias dados.
     * @param dias los dias por los que se alquila el vehiculo.
     * @return el pecio de alquilar el vehiculo durante dias dias.
     */
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 20 * PMA;
    }
}
