package Abstractas;

import Excepciones.AlquilerVehiculoException;

/**
 * Clase abstracta Vechiculo, define las propiedades comunes a todos los tipos
 * de vehiculo.
 *
 * @author Kevin
 */
public abstract class Vehiculo {

    private final String matricula;           //Maricula del vehiculo
    private boolean alquilado;          //Define si esta alquilado o no
    //private boolean primer;

    /**
     * Inicializa el vehiculo con la matricula dad como no alquilado.
     *
     * @param matricula matricula del vehiculo.
     */
    public Vehiculo(String matricula) {
        alquilado = false;
        //primer = true;
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
     * Devuelve si el vechiculo esta alquilado o no.
     *
     * @return true si el vechiulo esta alquilado; false en caso contrario.
     */
    public boolean isAlquilado() {
        return alquilado;
    }
    /*
     * Devuleve si es la primera vez que se alquila el vehiculo.
     * @return true si es la primera vez; false en caso contrario.
     *
     public boolean isPrimerAlquiler() {
     return primer;
     }*/

    /**
     * Alquila el vehiculo al cliente pasado.
     *
     * //* @param c el cliente al que se le alquila el vehiculo.
     *
     * @throws AlquilerVehiculoException si el vehiculo ya esta alquilado.
     */
    public void alquilar() throws AlquilerVehiculoException {
        if (alquilado) {
            throw new AlquilerVehiculoException("El vehiculo ya esta alquilado.");
        } else {
            alquilado = true;
        }
    }

    /**
     * Devuelve el vehiculo al almacen.
     *
     * @throws AlquilerVehiculoException si el vehiculo no esta alquilado.
     */
    public void devolver() throws AlquilerVehiculoException {
        if (alquilado) {
            alquilado = false;
            //primer = false;
        } else {
            throw new AlquilerVehiculoException("El vechiculo no esta alquilado.");
        }
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
     * Devuleve una cadena con informacion sobre el vehiculo.
     *
     * @return una cadena con informacion sobre el vehiculo.
     */
    public String obtenerInformacion() {
        String info = "Matricula: " + matricula;
        info += "Tipo: " + this.getClass().getSimpleName();
        return info;
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
