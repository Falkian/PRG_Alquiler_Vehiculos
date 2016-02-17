package Estructuras;

import Abstractas.Vehiculo;
import Clases.Cliente;
import Excepciones.AlquilerVehiculoException;
import Excepciones.FormatoArchivoException;
import Excepciones.ObjetoNoExistenteException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ColeccionAlquileres {

    private static final String PATH = "ficheros/alquileres.txt";

    private final ArrayList<Alquiler> alquileres;

    public ColeccionAlquileres() {
        alquileres = new ArrayList<>();
    }

    public void anyadirAlquiler(Vehiculo v, Cliente c) throws AlquilerVehiculoException {
        alquileres.add(new Alquiler(v, c));
        v.alquilar();
        c.alquilar();
    }

    public Alquiler obtenerAlquilerPorMatricula(String matricula) throws AlquilerVehiculoException {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo().getMatricula().equals(matricula)) {
                return alquiler;
            }
        }
        throw new AlquilerVehiculoException("El vechiculo no esta alquilado.");
    }

    public Alquiler obtenerAlquilerPorDni(String dni) throws AlquilerVehiculoException {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente().getDni().equals(dni)) {
                return alquiler;
            }
        }
        throw new AlquilerVehiculoException("El cliente no tiene vechiculos alquilados.");
    }

    public void eliminarAlquiler(String matricula) throws ObjetoNoExistenteException, AlquilerVehiculoException {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo().getMatricula().equals(matricula)) {
                alquiler.getCliente().devolver();
                alquiler.getVehiculo().devolver();
                alquileres.remove(alquiler);
            }
        }
        throw new ObjetoNoExistenteException("El vehiculo no esta alquilado.");
    }

    /**
     * Carga la informacion desde un fichero;
     *
     * @param vehiculos la coleccion en la que se almacenan los vehiculos
     * existentes.
     * @param clientes la coleccion en la que se laquilan los clientes
     * existentes.
     * @throws FormatoArchivoException si la informacion del archivo no tiene el
     * formato adecuado.
     */
    public void cargar(ColeccionVehiculos vehiculos, ColeccionClientes clientes) throws FormatoArchivoException {
        File archivo = new File(PATH);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(archivo));

            //Lee el encabezado del archivo e informa si esta vacio
            String str = reader.readLine();
            if (str == null) {
                System.out.println("El archivo de alquileres esta vacio.");
                return;
            } else {
                //Lee la primera linea e informa si esta vacia
                str = reader.readLine();
                if (str == null) {
                    System.out.println("El archivo de alquileres no contiene informacion.");
                }
                while (str != null) {
                    String[] datos = str.split("\\t+");
                    if (datos.length != 2) {
                        alquileres.clear();
                        throw new FormatoArchivoException("Fallo en el formato de los datos de los alquileres.");
                    }
                    try {
                        String matricula = datos[0];
                        Vehiculo v = vehiculos.obtenerVechiculo(matricula);
                        String dni = datos[1];
                        Cliente c = clientes.obtenerCliente(dni);
                        alquileres.add(new Alquiler(v, c));
                    } catch (ObjetoNoExistenteException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        str = reader.readLine();
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Fin de la carga de clientes.");
        }
    }

    /**
     * Guarda la informacion en un fichero.
     */
    public void guardar() {
        File archivo = new File(PATH);
        PrintWriter writer = null;
        try {
            archivo.createNewFile();
            writer = new PrintWriter(new FileWriter(archivo));

            writer.println("Matricula\t\tCliente");

            for (Alquiler alquiler : alquileres) {
                writer.printf("%-9s\t\t%-9s%n", alquiler.getVehiculo().getMatricula(), alquiler.getCliente().getDni());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
