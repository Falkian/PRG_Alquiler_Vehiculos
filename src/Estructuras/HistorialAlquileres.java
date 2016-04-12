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
//TODO - Cambiar ficheros por base de datos
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
     * Devuelve si es la primera vez que se alquila un vehiculo.
     *
     * @param matricula matricula del vehiculo.
     * @return true si el vehiculo ha sido alquilado con anterioridad; false en
     * caso contrario.
     */
    public boolean isPrimeraVez(String matricula) {
        for (RegistroAlquiler registro : historial) {
            if (registro.getAlquiler().getVehiculo().getMatricula().equals(matricula)) {
                return false;
            }
        }
        return true;
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
                //Archivo en blanco
            } else {
                str = reader.readLine();
                //Lee la primera linea del archivo e informa si no contiene datos.
                if (str == null || str.equals("")) {
                    //Archivo sin informacion
                } else {
                    int linea = 1;
                    while (str != null && !str.equals("")) {
                        String[] datos = str.split("\\t\\t");
                        if (datos.length != 3) {
                            //Datos de la linea incorrectos
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
                                    //Referencia a objeto no existente
                                }
                                historial.add(new RegistroAlquiler(new Alquiler(v, c), dias));
                            } catch (ObjetoNoExistenteException e) {
                                //Referencia a vehiculo no existente
                            }
                        }
                        str = reader.readLine();
                        linea++;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            //Error de lectura
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
                Cliente c = registro.getAlquiler().getCliente();
                String dni;
                if (c != null) {
                    dni = c.getDni();
                } else {
                    dni = "Ya no existe.";
                }
                int dias = registro.getDias();
                writer.println(matricula + "\t\t" + dni + "\t\t" + dias);
            }
        } catch (IOException e) {
            //Error de escritura
        }
    }
}
