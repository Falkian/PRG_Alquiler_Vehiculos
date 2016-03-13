package Estructuras;

import Clases.RegistroAlquiler;
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
     * Devuelve el total de todos los alquileres realizados.
     *
     * @return el total de todos los alquileres realizados
     */
    public double getTotal() {
        double total = 0;
        ArrayList<Vehiculo> aparecidos = new ArrayList<>();
        for (RegistroAlquiler registro : historial) {
            Vehiculo v = registro.getAlquiler().getVehiculo();
            double precio = v.alquilerTotal(registro.getDias());
            Cliente c = registro.getAlquiler().getCliente();
            
            double descuentoprimera = 0;
            //Comprueba si es la primera vez que se alquilo el vehiculo
             if (!aparecidos.contains(registro.getAlquiler().getVehiculo())) {
                aparecidos.add(registro.getAlquiler().getVehiculo());
                descuentoprimera = precio * 0.25;
            }
             
            double descuentovip = 0;
            //Comprueba si el cliente era VIP
            if (c != null && c.isVip()) {
                descuentovip = precio * 0.15;
            }
            total += precio - descuentoprimera - descuentovip;
        }
        return total;
    }

    /**
     * Devuelve un array bidimensional con la informacion de la lista. Cada fila
     * contiene un elemento, y las columnas contienen la matricula, DNI,
     * duracion, si era el primer alquiler, si era VIP y precio,
     * respepctivamente.
     *
     * @return un array bidimensional con la informacion de la lista.
     */
    public String[][] obtenerDataArray() {
        String[][] ret = new String[historial.size()][];
        ArrayList<Vehiculo> aparecidos = new ArrayList<>();
        for (int i = 0; i < historial.size(); i++) {
            Vehiculo v = historial.get(i).getAlquiler().getVehiculo();
            Cliente c = historial.get(i).getAlquiler().getCliente();
            int dias = historial.get(i).getDias();
            double precio = historial.get(i).getAlquiler().getVehiculo().alquilerTotal(dias);

            String primerainfo = "\u2717";
            //Comprueba si es la primera vez que se alquilo el vehiculo
            double descuentoprimera = 0;
             if (!aparecidos.contains(historial.get(i).getAlquiler().getVehiculo())) {
                aparecidos.add(historial.get(i).getAlquiler().getVehiculo());
                descuentoprimera = precio * 0.25;
                
                primerainfo = "\u2713 (" + descuentoprimera + "€)";
            }

            String vipinfo = "\u2717";
            //Comprueba si el cliente era VIP
            double descuentovip = 0;
            if (c != null && c.isVip()) {
                descuentovip = precio * 0.15;
                vipinfo = "\u2713 (" + descuentovip + "€)";
            }

            precio -= descuentoprimera + descuentovip;
            ret[i] = new String[]{v.getMatricula(), c == null ? "Ya no existe" : c.getDni(), "" + dias, primerainfo, vipinfo, "" + precio + "€"};
        }
        return ret;
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
