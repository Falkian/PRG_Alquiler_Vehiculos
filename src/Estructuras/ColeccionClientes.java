package Estructuras;

import Clases.Cliente;
import Excepciones.ListaClientesLlenaException;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;

/**
 * Coleccion de objetos de la clase Cliente.
 *
 * @author Kevin
 */
public class ColeccionClientes {

    private Cliente[] clientes;             //Array de objetos Cliente.
    private int primeraPosicionLibre;       //Indice de la primera posicion vacia en el array.

    /**
     * Inicializa la coleccion con un tamanyo determinado.
     *
     * @param tamanyo el tamanyo de la coleccion.
     */
    public ColeccionClientes(int tamanyo) {
        clientes = new Cliente[tamanyo];
        primeraPosicionLibre = 0;
    }

    /**
     * Anayade un cliente a la coleccion.
     *
     * @param c el cliente a anyadir.
     * @throws ListaClientesLlenaException si la coleccion y aesta llena.
     * @throws ObjetoYaExistenteException si el objeto a añadir ya existe.
     */
    public void anyadirCliente(Cliente c) throws ListaClientesLlenaException, ObjetoYaExistenteException {
        if (primeraPosicionLibre < clientes.length) {
            for (Cliente vehiculo : clientes) {
                if (vehiculo != null && vehiculo.getDni().equals(c.getDni())) {
                    throw new ObjetoYaExistenteException("Ya existe un cliente con ese DNI.");
                }
            }
            clientes[primeraPosicionLibre++] = c;
            System.out.println("Cliente" + (c.isVip() ? " VIP " : " " )+ "anyadido.");
        } else {
            throw new ListaClientesLlenaException();
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
}
