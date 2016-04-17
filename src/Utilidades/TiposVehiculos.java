package Utilidades;

/**
 * Enum para proporcionar constantes simbolicas y utilidades a los distintos
 * tipos de vehiculo.
 *
 * @author Kevin
 */
public enum TiposVehiculos {

    COCHE(2, 7), MICROBUS(5, 50), FURGONETA(500, 10000), CAMION(1000, 100000);

    private final double caractMin;     //Plazas o PMA minimo del tipo de vehiculo
    private final double caractMax;     //Plazas o PMA maximo del tipo de 

    /**
     * Constructor predeterminado.
     *
     * @param caractMin plazas o PMA minimo.
     * @param caractMax plazas o PMA maximo.
     */
    private TiposVehiculos(double caractMin, double caractMax) {
        this.caractMin = caractMin;
        this.caractMax = caractMax;
    }

    /**
     * Devuelve las plazas o el PMA minimo.
     *
     * @return las plazas o el PMA minimo.
     */
    public double getCaractMin() {
        return caractMin;
    }

    /**
     * Deveulve las plazas o el PMA maxim.
     *
     * @return las plazas o el PMA maximo.
     */
    public double getCaractMax() {
        return caractMax;
    }

    /**
     * Devuelve el tipo de vehiculo como cadena.
     *
     * @return el tipo de vehiculo como cadena.
     */
    public String getTipo() {
        switch (this) {
            case COCHE:
                return "Coche";
            case MICROBUS:
                return "Microbus";
            case FURGONETA:
                return "Furgoneta";
            case CAMION:
                return "Camion";
            default:
                return "Desconecido";
        }
    }
}
