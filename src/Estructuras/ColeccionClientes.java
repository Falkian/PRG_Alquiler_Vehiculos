package Estructuras;

import Clases.Cliente;
import Excepciones.ListaClientesLlenaException;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import Excepciones.FormatoArchivoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Coleccion de objetos de la clase Cliente.
 *
 * @author Kevin
 */
public class ColeccionClientes {

    private static final String PATH = "ficheros/clientes.txt";

    private final ArrayList<Cliente> clientes;             //Coleccion de clientes

    /**
     * Inicializa la coleccion con un tamanyo determinado.
     */
    public ColeccionClientes() {
        clientes = new ArrayList<>();
    }

    /**
     * Anayade un cliente a la coleccion.
     *
     * @param c el cliente a anyadir.
     * @throws ListaClientesLlenaException si la coleccion y aesta llena.
     * @throws ObjetoYaExistenteException si el objeto a añadir ya existe.
     */
    public void anyadirCliente(Cliente c) throws ListaClientesLlenaException, ObjetoYaExistenteException {
        if (posicionCliente(c.getDni()) < 0) {
            clientes.add(c);
        } else {
            throw new ObjetoYaExistenteException();
        }
    }

    /**
     * Devuelve el cliente identificado por el dni dado.
     *
     * @param dni el dni del cliente a buscar.
     * @return el cliente con el dni dado.
     * @throws ObjetoNoExistenteException si el cliente no existe en la
     * coleccion.
     */
    public Cliente obtenerCliente(String dni) throws ObjetoNoExistenteException {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getDni().equals(dni)) {
                return cliente;
            }
        }
        throw new ObjetoNoExistenteException("El cliente con dni " + dni + " no existe.");
    }

    /**
     * Elimina el cliente identificado por el DNI dado.
     *
     * @param dni el DNI del cliente.
     * @throws ObjetoNoExistenteException so no existe el cliente en la
     * coleccion,
     */
    public void eliminarCliente(String dni) throws ObjetoNoExistenteException {
        int index = posicionCliente(dni);
        if (index >= 0) {
            clientes.remove(index);
        } else {
            throw new ObjetoNoExistenteException("El cliente con dni " + dni + " no existe.");
        }
    }

    /**
     * Carga la informacion del fichero en el programa
     *
     * @throws FormatoArchivoException si la informacion del archivo no tiene el
     * formato adecuado.
     */
    public void cargar() throws FormatoArchivoException {
        File archivo = new File(PATH);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(archivo));

            //Lee el encabezado del archivo e informa si esta vacio.
            String str = reader.readLine();
            if (str == null) {
                System.out.println("Archivo de clientes en blanco.");
                return;
            } else {
                //Lee la primera linea del archivo e informa si no contiene datos.
                str = reader.readLine();
                if (str == null) {
                    System.out.println("El archivo de clientes no contiene informacion.");
                }
                while (str != null) {
                    String[] datos = str.split("\\t+");
                    if (datos.length != 5) {
                        clientes.clear();
                        throw new FormatoArchivoException("Fallo en el formato de los datos de los clientes.");
                    }
                    Cliente c = new Cliente(datos[0].trim(), datos[1].trim(), datos[2].trim(), datos[3].trim());
                    if (datos[4].equals("S")) {
                        c.setVip(true);
                    }
                    clientes.add(c);
                    str = reader.readLine();
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
     * Guarda la informacion almacenada en la lista en un fichero.
     */
    public void guardar() {
        File archivo = new File(PATH);
        PrintWriter writer = null;
        try {
            archivo.createNewFile();
            writer = new PrintWriter(new FileWriter(archivo));

            writer.println("NumDNI\t\tNombre\t\tDireccion\t\tTelefono\t\tVIP");

            for (Cliente cliente : clientes) {
                String dni = cliente.getDni();
                String nombre = cliente.getNombre();
                String dir = cliente.getDireccion();
                String tlf = cliente.getTlf();
                String vip = cliente.isVip() ? "S" : "N";
                writer.printf("%-9s\t%-7s\t\t%-12s\t\t%-18s\t%s%n", dni, nombre, dir, tlf, vip);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Devuelve la posicion del cliente identificado por el dni introducido en
     * la coleccion.
     *
     * @param dni del cliente a buscar.
     * @return la posicion del cliente; -1 si no existe en la coleccion.
     */
    private int posicionCliente(String dni) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni().equals(dni)) {
                return i;
            }
        }
        return -1;
    }
}
