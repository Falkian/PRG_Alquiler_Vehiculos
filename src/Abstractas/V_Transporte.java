package Abstractas;

/**
 * Clase abstracta V_Transporte, define las propiedades y metodos particulares
 * de los vehiculos de transporte.
 *
 * @author Kevin
 */
public abstract class V_Transporte extends Vehiculo {

    private int plazas;             //Numero de plazas del vehiculo.

    /**
     * Inicializa el vehiculo de transporte como no alquilado con la matricula y
     * las plazas dados.
     *
     * @param matricula la matricula del vehiculo.
     * @param plazas el numero de plaas del vehiculo.
     */
    public V_Transporte(String matricula, int plazas) {
        super(matricula);
        this.plazas = plazas;
    }

    /**
     * Devuelve el numero de plazas del vehiculo.
     * @return el numero de plazas del vehiculo.
     */
    public int getPlazas() {
        return plazas;
    }

    /**
     * Establece el numero de plazas.
     * @param plazas el numero de plazas del vehiculo.
     */
    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
    
    /**
     * Devuleve una cadena con informacion sobre el vehiculo.
     *
     * @return una cadena con informacion sobre el vehiculo.
     */
    @Override
    public String obtenerInformacion() {
        return super.obtenerInformacion() + "Plazas: " + plazas;
    }

    /**
     * Devuelve el precio de alquilar el vehiculo durante los dias dados.
     * @param dias el numero de dias por los que se alquila el vechiculo.
     * @return el precio de alquilar el vechiculo por dias dias.
     */
    @Override
    public double alquilerTotal(int dias) {
        return super.alquilerTotal(dias) + 1.5 * plazas * dias;
    }
}
