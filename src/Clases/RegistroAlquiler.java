package Clases;

import Estructuras.Alquiler;

/**
 * Registro de un alquiler realizado.
 */
public class RegistroAlquiler {

    private final Alquiler alquiler;    //Alquiler que representa
    private final int dias;             //Dias que duro el alquiler

    /**
     * Constructor por defecto.
     */
    public RegistroAlquiler() {
        alquiler = null;
        dias = 0;
    }

    /**
     * Constructor.
     *
     * @param alquiler alquiler a registrar.
     * @param dias dias que duro el alquiler.
     */
    public RegistroAlquiler(Alquiler alquiler, int dias) {
        this.alquiler = alquiler;
        this.dias = dias;
    }

    /**
     * Devuleve el alquiler del registro.
     *
     * @return el alquiler del registro.
     */
    public Alquiler getAlquiler() {
        return alquiler;
    }

    /**
     * Devuelve la cantidad de dias que duro el alquiler.
     *
     * @return la cantidad de dias que duro el alquiler.
     */
    public int getDias() {
        return dias;
    }
}
