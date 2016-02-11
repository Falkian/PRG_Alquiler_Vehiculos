package Abstractas;

/**
 * Clase abstracta Vechiculo, define las propiedades comunes a todos los tipos
 * de vehiculo.
 *
 * @author Kevin
 */
public abstract class Vehiculo {

    private String matricula;           //Maricula del vehiculo

    /**
     * Contructor vacio, inicializa el vehiculo como no alquilado.
     */
    public Vehiculo() {
    }

    /**
     * Inicializa el vehiculo con la matricula dad como no alquilado.
     *
     * @param matricula matricula del vehiculo.
     */
    public Vehiculo(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Devuelve la matricula del vehiculo.
     *
     * @return la matricula del vehiculo.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matricula del vehiculo.
     *
     * @param matricula la matricual a asignarle al vehiulo.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Devuelve el precio base de alquilar un vehiculo para los dias dados.
     *
     * @param dias los dias por los que va a estar alquilado el vehiculo.
     * @return el precio de alquilar el vehiculo durante dias dias.
     */
    public double alquilerTotal(int dias) {
        return 50 * dias;
    }

    /**
     * Muestra incoformacion sobre el precio de alquilar el vechiulo durante una
     * cantidad de dias.
     *
     * @param dias los dias por los que se alquilara.
     * @param alquiler el precio del alquiler del vechiculo.
     */
    public abstract void mostrarInfoAlquiler(int dias, double alquiler);
}
