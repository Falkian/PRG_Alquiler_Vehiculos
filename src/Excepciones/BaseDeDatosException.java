package Excepciones;

public class BaseDeDatosException extends Exception {

    public BaseDeDatosException() {
        super("Error en la conexion MySQL.");
    }

    public BaseDeDatosException(String msg) {
        super(msg);
    }
}
