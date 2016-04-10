package Estructuras;

import Abstractas.Iterador;
import Clases.Cliente;
import java.util.ArrayList;

/**
 * Iterador para recorrer los elementos de una coleccion de clientes.
 *
 * @author Kevin
 */
public class IteradorClientes extends Iterador<Cliente> {

    /**
     * Crea un iterador sobre una lista dada.
     *
     * @param lista la lsita sobre la que se iterara.
     */
    public IteradorClientes(ArrayList<Cliente> lista) {
        super(lista);
    }

    /**
     * Posiciona el iterador sobre el elemento identificado por el dni dado. Si
     * no se encuentra se posiciona en la ultima posicioon.
     *
     * @param dni el deni que identifical al elemento.
     */
    @Override
    public void seleccionar(String dni) {
        posicion = 0;
        for (Cliente cliente : lista) {
            if (cliente.getDni().equals(dni)) {
                return;
            }
            posicion++;
        }
    }
}
