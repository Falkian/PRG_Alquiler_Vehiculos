package Utilidades;

/**
 * Enum para proporcionar constantes simbolicas y utilidades a los distintos
 * tipos de vehiculo.
 *
 * @author Kevin
 */
//TODO - sustituir constantes simbolicas por enum
public enum TiposVehiculos {
    Coche(2, 7), Microbus(5, 50), Furgoneta(500, 10000), Camion(1000, 100000);
    
    private final double caractMin;
    private final double caractMax;
    
    private TiposVehiculos(double caractMin, double caractMax) {
        this.caractMin = caractMin;
        this.caractMax = caractMax;
    }
    
    public double getCaractMin() {
        return caractMax;
    }
    
    public double getCaractMax() {
        return caractMax;
    }
    
    public String getTipo() {
        switch (this) {
            case Coche:
                return "Coche";
            case Microbus:
                return "Microbus";
            case Furgoneta:
                return "Furgoneta";
            case Camion:
                return "Camion";
            default:
                return "Desconecido";
        }
    }
}
