package Clases;

import Abstractas.Vehiculo;

/**
 * Representa un alquiler emparejando la matricula de un vehiculo con el dni de
 * un cliente.
 *
 * @author Kevin
 */
public class Alquiler {

    private final Vehiculo vehiculo;    //Vehiculo alquilado
    private final Cliente cliente;      //CLiente que alquila

    /**
     * Crea un alquiler con el cliente y vehiculos dados.
     *
     * @param vehiculo el vehiculo alquilado.
     * @param cliente el cliente que alquila.
     */
    public Alquiler(Vehiculo vehiculo, Cliente cliente) {
        this.vehiculo = vehiculo;
        this.cliente = cliente;
    }

    /**
     * Devuelve el cliente.
     *
     * @return el cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Devuelve el vehiculo.
     *
     * @return el vehiculo.
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
}
