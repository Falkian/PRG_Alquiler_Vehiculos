package Utilidades;

/**
 * Enum para proporcionar constantes simbolicas y utilidades a los distintos
 * tipos de vehiculo.
 *
 * @author Kevin
 */
public enum TiposVehiculos {
    COCHE(2, 7), MICROBUS(5, 50), FURGONETA(500, 10000), CAMION(1000, 100000);
    
    private final double caractMin;
    private final double caractMax;
    
    private TiposVehiculos(double caractMin, double caractMax) {
        this.caractMin = caractMin;
        this.caractMax = caractMax;
    }
    
    public double getCaractMin() {
        return caractMin;
    }
    
    public double getCaractMax() {
        return caractMax;
    }
    
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
