package Estructuras;

import java.util.ArrayList;

/**
 * Clase que representa el histórico de todos los alquileres relizados.
 */
public class HistorialAlquileres {

    private final ArrayList<RegistroAlquiler> historial;

    /**
     * Constructor por defecto.
     */
    public HistorialAlquileres() {
        historial = new ArrayList<>();
    }

    /**
     * Anyade un registro dados el alquiler y la cantidad de dias.
     *
     * @param alquiler el alquiler a registrar.
     * @param dias la duración del alquiler.
     * @param precio el precio del alquiler.
     */
    public void anyadirRegistro(Alquiler alquiler, int dias, double precio) {
        historial.add(new RegistroAlquiler(alquiler, dias, precio));
    }

    /**
     * Busca en el registro para devolver si es la primera vez que se alquila el
     * vehiculo.
     *
     * @param matricula de vehiculo a buscar.
     * @return cierto si es la primera vez que se alquila; false en caso
     * contrario.
     */
    public boolean isPrimerAlquiler(String matricula) {
        for (RegistroAlquiler registro : historial) {
            if (registro.getAlquiler().getVehiculo().getMatricula().equals(matricula)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Muestra por pantalla un resumen de los ingresos obtenidos por los
     * alquileres y devuelve la suma de sus precios.
     * @return total el total obtenido por todos los ingresos.
     */
    public double ingresos() {
        double total = 0;
        for (RegistroAlquiler registro : historial) {
            String matricula = registro.getAlquiler().getVehiculo().getMatricula();
            String dni = registro.getAlquiler().getCliente().getDni();
            int dias = registro.getDias();
            double precio = registro.getPrecio();
            total += precio;
            System.out.printf("Vehiculo: %s, Cliente: %s, Dias: %d, Precio: %f€\n",
                    matricula, dni, dias, precio);
        }
        return total;
    }
    
    //TODO guardar
    public void guardar(){
        
    }
    
    //TODO cargar
    public void cargar(){
        
    }
}
