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
import java.util.Iterator;

/**
 * Coleccion de objetos de la clase Alquiler.
 */
public class ColeccionAlquileres {

    private static final String PATH = "ficheros/listaAlquileres.txt";

    private final ArrayList<Alquiler> alquileres;       //COleccion de alquielres

    /**
     * Constructor de la coleccion de alquileres.
     */
    public ColeccionAlquileres() {
        alquileres = new ArrayList<>();
    }

    /**
     * Añade un alquiler con un cliente y un vechiculo dados.
     *
     * @param v el vechiculo que se alquila.
     * @param c el cliente que alquila.
     * @throws AlquilerVehiculoException si el vehiculo o cliente ya estan
     * alquilados.
     */
    public void anyadirAlquiler(Vehiculo v, Cliente c) throws AlquilerVehiculoException {
        alquileres.add(new Alquiler(v, c));
        v.alquilar();
        c.alquilar();
    }

    /**
     * Dada una matricula devuleve el alquiler del vehiculo que representa.
     *
     * @param matricula que identifica al vehiculo.
     * @return el aluiler que involucra al vehiculo.
     * @throws AlquilerVehiculoException si el vehiculo no esta alquilado.
     */
    public Alquiler obtenerAlquilerPorMatricula(String matricula) throws AlquilerVehiculoException {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo().getMatricula().equals(matricula)) {
                return alquiler;
            }
        }
        throw new AlquilerVehiculoException("El vechiculo no esta alquilado.");
    }

    /**
     * Dado un dni devuelve el alquiler del cliente que representa,
     *
     * @param dni que identifica al cliente.
     * @return el alquiler que involucra al cliente.
     * @throws AlquilerVehiculoException si el cliente no tiene un vehiculo
     * alquilado.
     */
    public Alquiler obtenerAlquilerPorDni(String dni) throws AlquilerVehiculoException {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente().getDni().equals(dni)) {
                return alquiler;
            }
        }
        throw new AlquilerVehiculoException("El cliente no tiene vechiculos alquilados.");
    }

    /**
     * Dada una matricula eliminina un alquiler. El cliente devuelve el vehiculo
     * y finaliza el alquiler.
     *
     * @param matricula que identifica al vehiculo.
     * @throws AlquilerVehiculoException si el vehiculo no esta alquilado.
     */
    public void eliminarAlquilerPorMatricula(String matricula) throws AlquilerVehiculoException {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo().getMatricula().equals(matricula)) {
                alquiler.getCliente().devolverVehiculo();
                alquiler.getVehiculo().devolver();
                alquileres.remove(alquiler);
            }
        }
    }

    /**
     * Dado un dni elimina todos los alquileres que tenga el cliente
     * identidicado por dicho dni.
     *
     * @param dni que identifica al cliente.
     * @throws AlquilerVehiculoException si el cliente no tiene vehiculos alquilados.
     */
    public void eliminarAlquilerPorDni(String dni) throws AlquilerVehiculoException {
        Iterator<Alquiler> i = alquileres.iterator();
        while (i.hasNext()) {
            Alquiler alquiler = i.next();
            if (alquiler.getCliente().getDni().equals(dni)) {
                alquiler.getCliente().devolverVehiculo();
                alquiler.getVehiculo().devolver();
                i.remove();
            }
        }
    }

    /**
     * Carga la informacion desde un fichero;
     *
     * @param vehiculos la coleccion en la que se almacenan los vehiculos
     * existentes.
     * @param clientes la coleccion en la que se laquilan los clientes
     * existentes.
     */
    public void cargar(ColeccionVehiculos vehiculos, ColeccionClientes clientes) {
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
                if ((str = reader.readLine()) == null || str.equals("")) {
                    System.out.println("El archivo de alquileres no contiene informacion.");
                    return;
                }
                int linea = 1;
                while (str != null && !str.equals("")) {
                    String[] datos = str.split("\\t\\t");
                    if (datos.length != 2) {
                        System.out.println("Datos en la linea " + linea + " incorrectos.");
                    } else {
                        try {
                            String matricula = datos[0].trim();
                            Vehiculo v = vehiculos.obtenerVechiculo(matricula);
                            String dni = datos[1].trim();
                            Cliente c = clientes.obtenerCliente(dni);
                            anyadirAlquiler(v, c);
                        } catch (ObjetoNoExistenteException e) {
                            System.out.println("Datos en la linea " + linea + " incorrectos: ");
                            System.out.println(e.getMessage());
                        } catch (AlquilerVehiculoException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    str = reader.readLine();
                    linea++;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Fin de la carga de alquileres.\n");
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
                writer.printf("%s\t\t%s%n", alquiler.getVehiculo().getMatricula(), alquiler.getCliente().getDni());
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
