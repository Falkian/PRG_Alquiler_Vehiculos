package Excepciones;

public class FormatoIncorrectoException extends Exception {
    
    public FormatoIncorrectoException() {
        super("Formato incorrecto.");
    }
    
    public FormatoIncorrectoException(String msg) {
        super(msg);
    }
}
