package Estructuras;

import Clases.Cliente;
import Excepciones.ListaClientesLlenaException;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import java.util.ArrayList;

/**
 * Coleccion de objetos de la clase Cliente.
 *
 * @author Kevin
 */
public class ColeccionClientes {

    private final ArrayList<Cliente> clientes;             //Coleccion de clientes

    /**
     * Inicializa la coleccion con un tamanyo determinado.
     *
     * @param tamanyo el tamanyo de la coleccion.
     */
    public ColeccionClientes() {
        clientes = new ArrayList<>();
    }

    /**
     * Anayade un cliente a la coleccion.
     *
     * @param c el cliente a anyadir.
     * @throws ListaClientesLlenaException si la coleccion y aesta llena.
     * @throws ObjetoYaExistenteException si el objeto a añadir ya existe.
     */
    public void anyadirCliente(Cliente c) throws ListaClientesLlenaException, ObjetoYaExistenteException {
        if (posicionCliente(c.getDni()) < 0) {
            clientes.add(c);
        } else {
            throw new ObjetoYaExistenteException();
        }
    }

    /**
     * Devuelve el cliente identificado por el dni dado.
     *
     * @param dni el dni del cliente a buscar.
     * @return el cliente con el dni dado.
     * @throws ObjetoNoExistenteException si el cliente no existe en la
     * coleccion.
     */
    public Cliente obtenerCliente(String dni) throws ObjetoNoExistenteException {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getDni().equals(dni)) {
                return cliente;
            }
        }
        throw new ObjetoNoExistenteException("El cliente no existe.");
    }

    /**
     * Devuelve la posicion del cliente identificado por el dni introducido en
     * la coleccion.
     *
     * @param dni del cliente a buscar.
     * @return la posicion del cliente; -1 si no existe en la coleccion.
     */
    public int posicionCliente(String dni) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni().equals(dni)) {
                return i;
            }
        }
        return -1;
    }
}
