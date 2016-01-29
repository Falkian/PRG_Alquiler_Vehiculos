package Estructuras;

import Abstractas.Vehiculo;

public class ColeccionVehiculos {

    private Vehiculo[] vehiculos;
    private int primeraPosicionLibre;

    public ColeccionVehiculos(int tamanyo) {
        vehiculos = new Vehiculo[tamanyo];
        primeraPosicionLibre = 0;
    }

    public void anyadirVehiculo(Vehiculo v) {
        if (primeraPosicionLibre < vehiculos.length) {            
            for (Vehiculo vehiculo : vehiculos) {
                if (vehiculo != null && vehiculo.getMatricula().equals(v.getMatricula())) {
                    System.out.println("Ya existe un vehiculo con esa matricula.");
                    return;
                }
            }
            vehiculos[primeraPosicionLibre++] = v;
            System.out.println("Vehiculo anyadido.");
        } else {
            System.out.println("No se pueden introducir más vehiculos.");
        }
    }

    public Vehiculo obtenerVechiculo(String matricula) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null && vehiculo.getMatricula().equals(matricula)) {
                return vehiculo;
            }
        }
        return null;
    }
    
    public boolean isFull() {
        return primeraPosicionLibre >= vehiculos.length;
    }
}
