package Estructuras;

/**
 * Registro de un alquiler realizado.
 */
public class RegistroAlquiler {

    private final Alquiler alquiler;    //Alquiler que representa
    private final int dias;             //Dias que duro el alquiler
    private final double precio;        //Precio del alquiler

    /**
     * Constructor por defecto.
     */
    public RegistroAlquiler() {
        alquiler = null;
        dias = 0;
        precio = 0;
    }
    
    /**
     * Constructor.
     * @param alquiler alquiler a registrar.
     * @param dias dias que duro el alquiler.
     * @param precio precio del alquiler.
     */
    public RegistroAlquiler(Alquiler alquiler, int dias, double precio) {
        this.alquiler = alquiler;
        this.dias = dias;
        this.precio = precio;
    }
    
    /**
     * Devuleve el alquiler del registro.
     * @return el alquiler del registro.
     */
    public Alquiler getAlquiler() {
        return alquiler;
    }
    
    /**
     * Devuelve la cantidad de dias que duro el alquiler.
     * @return la cantidad de dias que duro el alquiler.
     */
    public int getDias() {
        return dias;
    }
    
    /**
     * Devuelve el precio del alquiler.
     * @return el precio del alquiler.
     */
    public double getPrecio() {
        return precio;
    }
}
