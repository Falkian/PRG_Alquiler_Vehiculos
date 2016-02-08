package Excepciones;

public class ObjetoNoExistenteException extends Exception {

    public ObjetoNoExistenteException() {
        super("El objeto no existe en la lista.");
    }

    public ObjetoNoExistenteException(String msg) {
        super(msg);
    }
}
