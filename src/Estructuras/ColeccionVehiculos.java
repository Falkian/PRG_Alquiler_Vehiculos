package Estructuras;

import Abstractas.V_Carga;
import Abstractas.V_Transporte;
import Abstractas.Vehiculo;
import Clases.Camion;
import Clases.Coche;
import Clases.Furgoneta;
import Clases.Microbus;
import Excepciones.FormatoIncorrectoException;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Coleccion de objetos de la clase Objeto.
 *
 * @author Kevin
 */
public class ColeccionVehiculos {

    private static final String PATH = "ficheros/listaVehiculos.txt";

    private final ArrayList<Vehiculo> vehiculos;      //Coleccion de vehiculos.

    /**
     * Inicializa la coleccion con un tamanyo determinado.
     */
    public ColeccionVehiculos() {
        vehiculos = new ArrayList<>();
    }

    /**
     * Anyade un vehiculo a la colccion dando sus caracteristicas.
     *
     * @param tipo el tipo de vehiculo
     * @param matricula la matricula del vehiculo
     * @param caract las plazas o PMA
     * @throws ObjetoYaExistenteException si ya existe un vehiculo con la misma
     * matricula
     * @throws FormatoIncorrectoException si las plazas o PMA no tienen un valor
     * valido
     */
    public void anyadirVehiculo(String tipo, String matricula, double caract) throws ObjetoYaExistenteException, FormatoIncorrectoException {
        if (posicionVehiculo(matricula) < 0) {
            switch (tipo) {
                case "Coche":
                    if (caract < 2 || caract > Coche.PLAZAS_MAX) {
                        throw new FormatoIncorrectoException("Las plazas de un coche deben estar entre 2 y " + Coche.PLAZAS_MAX);
                    } else {
                        vehiculos.add(new Coche(matricula, (int) caract));
                    }
                    break;
                case "Microbus":
                    if (caract < 2 || caract > Microbus.PLAZAS_MAX) {
                        throw new FormatoIncorrectoException("Las plazas de un microbus deben estar entre 2 y " + Microbus.PLAZAS_MAX);
                    } else {
                        vehiculos.add(new Microbus(matricula, (int) caract));
                    }
                    break;
                case "Furgoneta":
                    if (caract < 500 || caract > Furgoneta.PMA_MAX) {
                        throw new FormatoIncorrectoException("El PMA de una furgoneta debe estar entre 500 y " + Furgoneta.PMA_MAX);
                    } else {
                        vehiculos.add(new Furgoneta(matricula, caract));
                    }
                    break;
                case "Camion":
                    if (caract < 500 || caract > Furgoneta.PMA_MAX) {
                        throw new FormatoIncorrectoException("El PMA de un camion debe estar entre 500 y " + Camion.PMA_MAX);
                    } else {
                        vehiculos.add(new Camion(matricula, caract));
                    }
                    break;
            }
        } else {
            throw new ObjetoYaExistenteException();
        }
    }

    /**
     * Devuelve el vechiculo identificado por la matricula dada.
     *
     * @param matricula la matricula del vehiculo a buscar.
     * @return el vehiculo con laa matricula dada.
     * @throws ObjetoNoExistenteException si no existe el vehiculo en la
     * coleccion.
     */
    public Vehiculo obtenerVechiculo(String matricula) throws ObjetoNoExistenteException {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equals(matricula)) {
                return vehiculo;
            }
        }
        throw new ObjetoNoExistenteException("El vehiculo con matricula " + matricula + " no existe en el almacen.");
    }

    /**
     * Modifica el vehiculo identificado por la matricula dada, asignandole las
     * caracteristicas dadas.
     *
     * @param matricula la matricula del vehiculo a modificar.
     * @param tipo el tipo de vehiculo
     * @param caract las plazas o PMA del vehiculo
     * @throws ObjetoNoExistenteException si el vehiculo a modificar no existe.
     * @throws FormatoIncorrectoException si los datos no tienen el formato
     * adecuado
     */
    public void modificarVehiculo(String matricula, String tipo, double caract) throws ObjetoNoExistenteException, FormatoIncorrectoException {
        int posicionVehiculo = posicionVehiculo(matricula);
        if (posicionVehiculo < 0) {
            switch (tipo) {
                case "Coche":
                    if (caract < 2 || caract > Coche.PLAZAS_MAX) {
                        throw new FormatoIncorrectoException("Las plazas de un coche deben estar entre 2 y " + Coche.PLAZAS_MAX);
                    } else {
                        vehiculos.set(posicionVehiculo, new Coche(matricula, (int) caract));
                    }
                    break;
                case "Microbus":
                    if (caract < 2 || caract > Microbus.PLAZAS_MAX) {
                        throw new FormatoIncorrectoException("Las plazas de un microbus deben estar entre 2 y " + Microbus.PLAZAS_MAX);
                    } else {
                        vehiculos.set(posicionVehiculo, new Microbus(matricula, (int) caract));
                    }
                    break;
                case "Furgoneta":
                    if (caract < 500 || caract > Furgoneta.PMA_MAX) {
                        throw new FormatoIncorrectoException("El PMA de una furgoneta debe estar entre 500 y " + Furgoneta.PMA_MAX);
                    } else {
                        vehiculos.set(posicionVehiculo, new Furgoneta(matricula, (int) caract));
                    }
                    break;
                case "Camion":
                    if (caract < 500 || caract > Furgoneta.PMA_MAX) {
                        throw new FormatoIncorrectoException("El PMA de un camion debe estar entre 500 y " + Camion.PMA_MAX);
                    } else {
                        vehiculos.set(posicionVehiculo, new Camion(matricula, (int) caract));
                    }
                    break;
            }
        } else {
            throw new ObjetoNoExistenteException();
        }
    }

    /**
     * Elimina el vehiculo identificado por la matricula dada.
     *
     * @param matricula la matricula del vehiculo.
     * @throws ObjetoNoExistenteException so no existe el vehiculo en la
     * coleccion.
     */
    public void eliminarVehiculo(String matricula) throws ObjetoNoExistenteException {
        int index = posicionVehiculo(matricula);
        if (index >= 0) {
            vehiculos.remove(index);
            guardar();
        } else {
            throw new ObjetoNoExistenteException("El vehiculo con matricula " + matricula + " no existe en el almacen.");
        }
    }

    /**
     * Devuelve un array bidimensional con la informacion de la lista. Cada fila
     * contiene un elemento, y las columnas contienen la matricula, tipo y
     * caracteristica (plazas/PMA), respepctivamente.
     *
     * @return un array bidimensional con la informacion de la lista.
     */
    public String[][] obtenerDataArray() {
        String[][] ret = new String[vehiculos.size()][3];
        for (int i = 0; i < vehiculos.size(); i++) {
            ret[i] = vehiculos.get(i).dataToArray();
        }
        return ret;
    }

    /**
     * Carga la informacion del fichero en el programa
     */
    public void cargar() {
        File archivo = new File(PATH);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(archivo));

            //Lee el encabezado del archivo e informa si esta vacio.
            String str = reader.readLine();
            if (str == null) {
                System.out.println("Archivo de vehiclos en blanco.");
                return;
            } else {
                //Lee la primera linea del archivo e informa si no contiene datos.
                str = reader.readLine();
                if (str == null || str.equals("")) {
                    System.out.println("El archivo de vechiculos no contiene informacion.");
                    return;
                }
                int linea = 1;
                while (str != null && !str.equals("")) {
                    String[] datos = str.split("\\t\\t");
                    if (datos.length != 3) {
                        System.out.println("Datos en la linea " + linea + " incorrectos.");
                    } else {
                        String tipo = datos[0].trim();
                        String matricula = datos[1].trim();
                        switch (tipo) {
                            case "Coche":
                                vehiculos.add(new Coche(matricula, Integer.parseInt(datos[2])));
                                break;
                            case "Microbus":
                                vehiculos.add(new Microbus(matricula, Integer.parseInt(datos[2])));
                                break;
                            case "Furgoneta":
                                vehiculos.add(new Furgoneta(matricula, Double.parseDouble(datos[2])));
                                break;
                            case "Camion":
                                vehiculos.add(new Camion(matricula, Double.parseDouble(datos[2])));
                                break;
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
            System.out.println("Fin de la carga de vehiculos.\n");
        }
    }

    /**
     * Guarda la informacion almacenada en la lista en un fichero.
     */
    public void guardar() {
        File archivo = new File(PATH);
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("Vehiculo\t\tMatricula\t\tNumPlazas / PMA");

            for (Vehiculo vehiculo : vehiculos) {
                String tipo = vehiculo.getClass().getSimpleName();
                writer.printf("%s\t\t%s\t\t", tipo, vehiculo.getMatricula());
                if (vehiculo instanceof V_Transporte) {
                    V_Transporte v = (V_Transporte) vehiculo;
                    writer.println(v.getPlazas());
                } else {
                    V_Carga v = (V_Carga) vehiculo;
                    writer.println(v.getPMA());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Devuelve la posicion del vehiculo identificado por la matricula
     * introducida en la coleccion.
     *
     * @param matricula del vehiculo a buscar.
     * @return la posicion del vehiculo; -1 si no existe en la coleccion.
     */
    private int posicionVehiculo(String matricula) {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getMatricula().equals(matricula)) {
                return i;
            }
        }
        return -1;
    }

}
