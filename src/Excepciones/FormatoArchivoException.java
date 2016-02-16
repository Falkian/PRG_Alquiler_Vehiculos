package Excepciones;

public class FormatoArchivoException extends Exception {
    
    public FormatoArchivoException() {
        super("Error en el manejod e archivos.");
    }
    
    public FormatoArchivoException(String msg) {
        super(msg);
    }
}
