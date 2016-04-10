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
 *
 * @author Kevin
 */
public class ColeccionAlquileres {

    private static final String PATH = "ficheros/listaAlquileres.txt";

    private final ArrayList<Alquiler> alquileres;       //Coleccion de alquielres

    private final ColeccionVehiculos vehiculos;     //Coleccion de vehiculos
    private final ColeccionClientes clientes;       //Coleccion de clientes

    /**
     * Constructor de la coleccion de alquileres.
     *
     * @param vehiculos la coleccion de vehiculos
     * @param clientes la coleccion de clientes
     */
    public ColeccionAlquileres(ColeccionVehiculos vehiculos, ColeccionClientes clientes) {
        alquileres = new ArrayList<>();
        this.vehiculos = vehiculos;
        this.clientes = clientes;
    }

    /**
     * Añade un alquiler con un cliente y un vechiculo dados, y los alquila
     * automaticamente.
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
        guardar();
    }

    /**
     * Dado un dni elimina todos los alquileres que tenga el cliente
     * identidicado por dicho dni.
     *
     * @param matricula la matricula que identifica al vehiculo.
     * @param dni que identifica al cliente.
     * @throws Excepciones.ObjetoNoExistenteException si el objeto no existe en
     * la coleccion.
     * @throws AlquilerVehiculoException si el cliente no tiene vehiculos
     * alquilados.
     */
    public void anyadirAlquiler(String matricula, String dni) throws ObjetoNoExistenteException, AlquilerVehiculoException {
        Vehiculo v = vehiculos.obtenerVechiculo(matricula);
        Cliente c = clientes.obtenerCliente(dni);
        alquileres.add(new Alquiler(v, c));
        v.alquilar();
        c.alquilar();
        guardar();
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
     * Devuleve una coleccion con los vehiculos que tiene alquilados un cliente
     *
     * @param dni que identifica al cliente.
     * @return una coleccion con los vehiculos que tiene alquilados el cliente.
     * @throws AlquilerVehiculoException si el cliente no tiene vehiculos
     * alquilados.
     */
    public ArrayList<Vehiculo> obtenerAlquileresCliente(String dni) throws AlquilerVehiculoException {
        ArrayList<Vehiculo> ac = new ArrayList<>();
        boolean existe = false;
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente().getDni().equals(dni)) {
                ac.add(alquiler.getVehiculo());
                existe = true;
            }
        }
        if (!existe) {
            throw new AlquilerVehiculoException("El cliente no tiene vehiculos alquilados.");
        }
        return ac;
    }

    /**
     * Dada una matricula eliminina un alquiler. El cliente devuelve el vehiculo
     * y finaliza el alquiler.
     *
     * @param matricula que identifica al vehiculo.
     * @throws AlquilerVehiculoException si el vehiculo no esta alquilado.
     */
    public void eliminarAlquilerPorMatricula(String matricula) throws AlquilerVehiculoException {
        Iterator<Alquiler> iter = alquileres.listIterator();
        while (iter.hasNext()) {
            Alquiler alquiler = iter.next();
            if (alquiler.getVehiculo().getMatricula().equals(matricula)) {
                alquiler.getCliente().devolverVehiculo();
                alquiler.getVehiculo().devolver();
                alquileres.remove(alquiler);
            }
        }
        guardar();
    }

    /**
     * Dado un dni elimina todos los alquileres que tenga el cliente
     * identidicado por dicho dni.
     *
     * @param dni que identifica al cliente.
     * @throws AlquilerVehiculoException si el cliente no tiene vehiculos
     * alquilados.
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
        guardar();
    }

    /**
     * Devuelve el coste de alquilar un vehiculo durante una cantidad de dias
     * por un cliente determinado.
     *
     * @param matricula identifica al vehiculo
     * @param dni identifica al cliente
     * @param dias que dura el alquiler
     * @return catidad de alquilar el vehiculo durante los dias dados
     * @throws ObjetoNoExistenteException
     */
    public double obtenerPrecioAlquiler(String matricula, String dni, int dias) throws ObjetoNoExistenteException {
        double precio = vehiculos.obtenerVechiculo(matricula).alquilerTotal(dias);
        if (clientes.obtenerCliente(dni).isVip()) {
            precio *= 0.85;
        }
        return precio;
    }
    
    /**
     * Devuelve un array que contiene las matriculas de los vehiculos
     * alquilados.
     *
     * @return un array que contiene las matriculas de los vehiculos alquilados.
     */
    public String[] obtenerMatriculasAlquilados() {
        ArrayList<String> matriculas = new ArrayList<>();
        for (Alquiler alquiler : alquileres) {
            matriculas.add(alquiler.getVehiculo().getMatricula());
        }
        return matriculas.toArray(new String[matriculas.size()]);
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
                //Archio de alquileres vacio
                return;
            } else {
                //Lee la primera linea e informa si esta vacia
                if ((str = reader.readLine()) == null || str.equals("")) {
                    //Archivo de alquilereres sin informacion
                    return;
                }
                int linea = 1;
                while (str != null && !str.equals("")) {
                    String[] datos = str.split("\\t\\t");
                    if (datos.length != 2) {
                        //Datos de la linea incorrectos
                    } else {
                        try {
                            String matricula = datos[0].trim();
                            Vehiculo v = vehiculos.obtenerVechiculo(matricula);
                            String dni = datos[1].trim();
                            Cliente c = clientes.obtenerCliente(dni);
                            anyadirAlquiler(v, c);
                        } catch (ObjetoNoExistenteException e) {
                            //Datos de la linea incorrectos
                        } catch (AlquilerVehiculoException e) {
                            //Fallo en el alquiler
                        }
                    }
                    str = reader.readLine();
                    linea++;
                }
            }
            reader.close();
        } catch (IOException e) {
            //Error de lectura del aqchivo
        }
    }

    /**
     * Guarda la informacion en un fichero.
     */
    public void guardar() {
        File archivo = new File(PATH);
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("Matricula\t\tCliente");

            for (Alquiler alquiler : alquileres) {
                writer.printf("%s\t\t%s%n", alquiler.getVehiculo().getMatricula(), alquiler.getCliente().getDni());
            }

        } catch (IOException e) {
            //Fallo al escribir en el archivo
        }
    }
}
