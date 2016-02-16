package Estructuras;

import Abstractas.Vehiculo;
import Clases.Cliente;
import Excepciones.AlquilerVehiculoException;
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

    public Alquiler obtenerAlquiler(String matricula) throws AlquilerVehiculoException {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo().getMatricula().equals(matricula)) {
                return alquiler;
            }
        }
        throw new AlquilerVehiculoException("El vechiculo no esta alquilado.");
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
     */
    //TODO hacer
    public void cargar() {
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
                    System.out.println("El archivo de laquileres no contiene informacion.");
                }
                while (str != null) {
                    //Escribir cada linea
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
    //TODO revisar
    public void guardar() {
        File archivo = new File(PATH);
        PrintWriter writer = null;
        try {
            archivo.createNewFile();
            writer = new PrintWriter(new FileWriter(archivo));

            writer.println("Matricula\t\tCliente");

            for (Alquiler alquiler : alquileres) {
                writer.printf("%-7s\t\t%-9s", alquiler.getVehiculo().getMatricula(), alquiler.getCliente().getDni());
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
