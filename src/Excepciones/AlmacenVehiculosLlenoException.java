package Excepciones;

public class AlmacenVehiculosLlenoException extends Exception {

    public AlmacenVehiculosLlenoException() {
        super("El almacen de vehiculos esta lleno.");
    }

    public AlmacenVehiculosLlenoException(String msg) {
        super(msg);
    }
}
