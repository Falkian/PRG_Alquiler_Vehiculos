package Excepciones;

public class ObjetoYaExistenteException extends Exception {

    public ObjetoYaExistenteException() {
        super("El objeto ya eciste en la lista.");
    }
    
    public ObjetoYaExistenteException(String msg) {
        super(msg);
    }
}
