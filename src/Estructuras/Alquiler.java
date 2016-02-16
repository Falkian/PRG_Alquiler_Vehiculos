package Estructuras;

import Abstractas.Vehiculo;
import Clases.Cliente;

/**
 * Representa un alquiler emparejando la matricula de un vehiculo con el dni de
 * un cliente.
 */
public class Alquiler {

    private final Vehiculo vehiculo;
    private final Cliente cliente;

    /**
     * Crea un alquiler con el cliente y vechiulos dados.
     *
     * @param vehiculo el vehiculo alquilado.
     * @param cliente el cliente que alquila.
     */
    public Alquiler(Vehiculo vehiculo, Cliente cliente) {
        this.vehiculo = vehiculo;
        this.cliente = cliente;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
}
