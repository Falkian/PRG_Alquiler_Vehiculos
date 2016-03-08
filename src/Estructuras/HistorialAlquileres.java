package Estructuras;

import Abstractas.Vehiculo;
import Clases.Cliente;
import Excepciones.ObjetoNoExistenteException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Clase que representa el histórico de todos los alquileres relizados.
 *
 * @author Kevin
 */
public class HistorialAlquileres {

    private static final String PATH = "ficheros/historialAlquileres.txt";

    private final ArrayList<RegistroAlquiler> historial;        //Coleccion de registros

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
     */
    public void anyadirRegistro(Alquiler alquiler, int dias) {
        historial.add(new RegistroAlquiler(alquiler, dias));
        guardar();
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
     */
    public void ingresos() {
        double total = 0;
        ArrayList<Vehiculo> aparecidos = new ArrayList<>();
        for (RegistroAlquiler registro : historial) {
            String matricula = registro.getAlquiler().getVehiculo().getMatricula();
            Cliente c = registro.getAlquiler().getCliente();
            int dias = registro.getDias();
            double precio = registro.getAlquiler().getVehiculo().alquilerTotal(dias);
            double descuentovip = 0, descuentoprimera = 0;

            //Comprueba se es la primera vez que se alquiló un vechiculo
            if (!aparecidos.contains(registro.getAlquiler().getVehiculo())) {
                aparecidos.add(registro.getAlquiler().getVehiculo());
                descuentoprimera = precio * 0.25;
            }
            //Comprueba si el cliente es VIP
            if (c != null && registro.getAlquiler().getCliente().isVip()) {
                descuentovip = precio * 0.15;
            }
            precio -= descuentoprimera + descuentovip;
            total += precio;
            System.out.printf("Vehiculo: %s, Cliente: %s, Dias: %d, Precio: %f€%n",
                    matricula, c == null? "Ya no existe" : c.getDni(), dias, precio);
            if (descuentovip > 0) {
                System.out.printf("Se le aplicó un 15%% de descuento (%.2f€) por ser el ciente VIP.%n", descuentovip);
            }
            if (descuentoprimera > 0) {
                System.out.printf("Se le aplicó un 25%% de descuento (%.2f€) por ser la primera vez que se alquilaba el vechiculo.%n", descuentoprimera);
            }
            System.out.println("");
        }
        System.out.printf("Total: %.2f€%n", total);
    }

    /**
     * Carga la informacion del fichero en el programa
     *
     * @param vehiculos
     * @param clientes
     */
    public void cargar(ColeccionVehiculos vehiculos, ColeccionClientes clientes) {
        File archivo = new File(PATH);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(archivo));

            //Lee el encabezado e informa si esta vacio
            String str = reader.readLine();
            if (str == null) {
                System.out.println("Historial de alquileres en blanco.");
            } else {
                str = reader.readLine();
                //Lee la primera linea del archivo e informa si no contiene datos.
                if (str == null || str.equals("")) {
                    System.out.println("El historial de registros no contiene informacion.");
                } else {
                    int linea = 1;
                    while (str != null && !str.equals("")) {
                        String[] datos = str.split("\\t\\t");
                        if (datos.length != 3) {
                            System.out.println("Datos en la linea " + linea + " incorrectos.");
                        } else {
                            String matricula = datos[0];
                            String dni = datos[1];
                            int dias = Integer.parseInt(datos[2]);
                            try {
                                Vehiculo v = vehiculos.obtenerVechiculo(matricula);
                                Cliente c;
                                try {
                                    c = clientes.obtenerCliente(dni);
                                } catch (ObjetoNoExistenteException e) {
                                    c = null;
                                    System.out.println("La linea " + linea + " hace referencia a un cliente que ya no existe.");
                                }
                                historial.add(new RegistroAlquiler(new Alquiler(v, c), dias));
                            } catch (ObjetoNoExistenteException e) {
                                System.out.println("La linea " + linea + " hacen referencia a un vehiculo que ya no existe.");
                            }
                        }
                        str = reader.readLine();
                        linea++;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Fin de la carga del historial.\n");
        }
    }

    /**
     * Guarda la informacion almacenada en la lista en un fichero.
     */
    public void guardar() {
        File archivo = new File(PATH);
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("Matricula\t\tCliente\t\tNumDias");

            for (RegistroAlquiler registro : historial) {
                String matricula = registro.getAlquiler().getVehiculo().getMatricula();
                String dni = registro.getAlquiler().getCliente().getDni();
                int dias = registro.getDias();
                writer.println(matricula + "\t\t" + dni + "\t\t" + dias);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
