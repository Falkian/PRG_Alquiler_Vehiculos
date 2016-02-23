package Clases;

import Excepciones.AlquilerVehiculoException;

public class Cliente {

    private final String dni;
    private String nombre;
    private String direccion;
    private String tlf;
    private boolean vip;
    //private boolean alquilado;
    private int vehiculosAlquilados;
    
    public Cliente(String dni) {
        this.dni = dni;
    }

    public Cliente(String dni, String nombre, String direccion, String tlf) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tlf = tlf;
        //alquilado = false;
        vehiculosAlquilados = 0;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
    
    public boolean isAlquilado() {
        return vehiculosAlquilados > 0;
    }
    
    public void alquilar() {
        vehiculosAlquilados++;
    }
    
    public void devolverVehiculo() throws AlquilerVehiculoException {
        if (vehiculosAlquilados > 0) {
            vehiculosAlquilados--;
        } else {
            throw new AlquilerVehiculoException("El cliente no tiene ningun vehiculo alquilado.");
        }
    }
}
