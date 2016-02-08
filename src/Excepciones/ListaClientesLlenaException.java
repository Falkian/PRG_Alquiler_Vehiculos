package Excepciones;

public class ListaClientesLlenaException extends Exception {

    public ListaClientesLlenaException() {
        super("La lista de clientes ya esta llena.");
    }

    public ListaClientesLlenaException(String msg) {
        super(msg);
    }
}
