package Aplicacion;

import Abstractas.Vehiculo;
import Clases.*;
import Estructuras.*;
import Excepciones.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase encargada de gestionar la logica del programa.
 *
 * @author Kevin
 */
public class Alquiler {

    private static final String COCHE = "COCHE";        //Valor de la cadena COCHE
    private static final String BUS = "MICROBUS";       //Valor de la cadena BUS
    private static final String FURGO = "FURGONETA";    //Valor de la cadena FURGNETA
    private static final String CAMION = "CAMION";      //Valor de la cadena CAMION

    private static final int TAMANYO = 50;              //Tamanyo predeterminado de las colecciones de la aplicacion.

    private static Scanner scanner = new Scanner(System.in);                        //Scanner utilizado en toda la aplicacion.
    private static ColeccionVehiculos vehiculos = new ColeccionVehiculos(TAMANYO);  //Coleccion de vehiculos.
    private static ColeccionClientes clientes = new ColeccionClientes(TAMANYO);     //Coleccion de clientes.

    /**
     * Ejecuta el menu principal de programa.
     */
    public static void ejecutar() {
        int opcion;
        do {
            System.out.print("===== MENU =====\n"
                    + "1.)Anyadir un vehiculo.\n"
                    + "2.)Obtener el alquiler de un vehiculo.\n"
                    + "3.)Anyadir un cliente.\n"
                    + "4.)Alquilar vechiulo.\n"
                    + "5.)Devolver vehiculo.\n"
                    + "9.)Salir\n"
                    + "Introduzca su opcion: ");
            opcion = leerInt();
            System.out.println("");
            switch (opcion) {
                case 1:
                    anyadirVehiculo();
                    break;
                case 2:
                    obtenerAlquiler();
                    break;
                case 3:
                    anyadirCliente();
                    break;
                case 4:
                    alquilarVehiculo();
                    break;
                case 5:
                    devolverVehiculo();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Opcion no valida.\n");
                    break;
            }
        } while (opcion != 9);
    }

    /**
     * Pide al usuario los datos para introducir un vehiculo y lo anyade a la
     * coleccion de vehiculos.
     */
    private static void anyadirVehiculo() {
        System.out.println("- - - Anyadir Vechiulo - - -");
        String tipo = obtenerTipo();
        try {
            String matricula = leerMatricula();
            int plazas;
            double pma;
            switch (tipo) {
                case COCHE:
                    plazas = obtenerPlazas(Coche.PLAZAS_MAX);
                    Coche coche = new Coche(matricula, plazas);
                    vehiculos.anyadirVehiculo(coche);
                    break;
                case BUS:
                    plazas = obtenerPlazas(Microbus.PLAZAS_MAX);
                    Microbus bus = new Microbus(matricula, plazas);
                    vehiculos.anyadirVehiculo(bus);
                    break;
                case FURGO:
                    pma = obtenerPMA(Furgoneta.PMA_MAX);
                    Furgoneta furgoneta = new Furgoneta(matricula, pma);
                    vehiculos.anyadirVehiculo(furgoneta);
                    break;
                case CAMION:
                    pma = obtenerPMA(Camion.PMA_MAX);
                    Camion camion = new Camion(matricula, pma);
                    vehiculos.anyadirVehiculo(camion);
                    break;
            }
        } catch (AlmacenVehiculosLlenoException | ObjetoYaExistenteException | FormatoIncorrectoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario los datos de un vehiculo y cuantos dias dura un alquiler
     * y muestra por pantalla el precio de alquilar el vehiculo durante los dias
     * introducidos.
     */
    private static void obtenerAlquiler() {
        System.out.println("- - - Obtener Precio de Alquler - - -");
        try {
            String matricula = leerMatricula();
            Vehiculo vehiculo = vehiculos.obtenerVechiculo(matricula);            
            int dias = obtenerDias();
            double alquiler = vehiculo.alquilerTotal(dias);
            vehiculo.mostrarInfoAlquiler(dias, alquiler);
        } catch (FormatoIncorrectoException | ObjetoNoExistenteException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario los datos de un cliente y lo anyade a la lista de
     * clientes si todos los datos son validos.
     */
    private static void anyadirCliente() {
        System.out.println("- - - Anyadir Cliente - - -");
        try {
            Cliente cliente = new Cliente();
            cliente.setDni(leerDNI());
            System.out.print("Introduce el nombre del cliente: ");
            cliente.setNombre(scanner.nextLine());
            System.out.print("Introduce la direccion del cliente: ");
            cliente.setDireccion(scanner.nextLine());
            cliente.setTlf(leerTlf());
            cliente.setVip(leerVip());
            clientes.anyadirCliente(cliente);
        } catch (ListaClientesLlenaException | ObjetoYaExistenteException | FormatoIncorrectoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario los datos de un vehiculo y de un cliente y le asigna el
     * vehiculo al cliente.
     */
    private static void alquilarVehiculo() {
        System.out.println("- - - Alquilar Vechiulo - - -");
        try {
            String matricula = leerMatricula();
            Vehiculo v = vehiculos.obtenerVechiculo(matricula);
            String dni = leerDNI();
            Cliente c = clientes.obtenerCliente(dni);
            v.alquilar(c);
            System.out.println("El vechiculo " + v.getMatricula() + " se le ha alquilado al cliente " + c.getDni());
        } catch (FormatoIncorrectoException | ObjetoNoExistenteException | AlquilerVehiculoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario la matricula de un coche y los dias que ha estado
     * alquilado y devulve qué coste ha tenido. Se le aplica un descuento del
     * 25% si el cliente es vIP.
     */
    //TODO intentar devolver antes de pedir los dias.
    private static void devolverVehiculo() {
        System.out.println("- - - Devolver Vechiulo - - -");
        try {
            String matricula = leerMatricula();
            Vehiculo vehiculo = vehiculos.obtenerVechiculo(matricula);
            boolean vip = vehiculo.getCliente().isVip();
            vehiculo.devolver();
            int dias = obtenerDias();
            double alquiler = vehiculo.alquilerTotal(dias);
            vehiculo.mostrarInfoAlquiler(dias, alquiler);
            if (vip) {
                alquiler *= 0.75;
                System.out.println("El cliente es VIP, por lo que se le aplica un descuento del 25%.");
                System.out.println("El alquiler es de " + alquiler + "euros.");
            }
        } catch (FormatoIncorrectoException | ObjetoNoExistenteException | AlquilerVehiculoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario que intoduzca un tipo de vehiculo hasta que lo introduzca
     * correctamente, entonces lo devuelve.
     *
     * @return El String que representa un tipo de vehiculo.
     */
    private static String obtenerTipo() {
        System.out.print("Introduce el tipo de vehiculo a introducir (Coche, Microbus, Furgoneta, Camion): ");
        String tipo = scanner.next().toUpperCase();
        scanner.nextLine();
        while (!tipo.equals(COCHE) && !tipo.equals(BUS) && !tipo.equals(FURGO) && !tipo.equals(CAMION)) {
            System.out.println("Tipo no valido.");
            System.out.print("Introduce el tipo de vehiculo a introducir (Coche, Microbus, Furgoneta, Camion):");
            tipo = scanner.next().toUpperCase();
            scanner.nextLine();
        }
        return tipo;
    }

    /**
     * Pide una matricula y la devuleve si es valida, lanza una excepcion en
     * caso contrario.
     *
     * @return una matricula valida.
     * @throws FormatoIncorrectoException si la matricula no es valida.
     */
    private static String leerMatricula() throws FormatoIncorrectoException {
        System.out.print("Introduce la matricula (4 numeros y 3 letras): ");
        String matricula = scanner.next();
        scanner.nextLine();
        matricula = matricula.toUpperCase();
        if (matricula.matches("\\d{4}[A-Z]{3}")) {
            return matricula;
        } else {
            throw new FormatoIncorrectoException("La matricula debe tener 4 numeros seguidos de 3 letras.");
        }
    }

    /**
     * Pide las plazas de un vehiculo hasta que se introduzca una cantidad
     * valida (entre 2 y el maximo).
     *
     * @param max la maxima cantidad de plazas del vehiculo.
     * @return una cantidad deplazas valida.
     */
    private static int obtenerPlazas(int max) {
        System.out.print("Introduce las plazas del vehiculo: ");
        int plazas = leerInt();
        while (plazas < 2 || plazas > max) {
            System.out.println("Cantidad de plazas no permitida. Deben estar entre 2 y " + max + ".");
            System.out.print("Introduce las plazas del vehiculo: ");
            plazas = leerInt();
        }
        return plazas;
    }

    /**
     * Pide el PMA de un vehiculo hasta que se introduzca una cantidad valida
     * (entre 1000 y el maximo).
     *
     * @param max el PMA maximo.
     * @return un PMA valido.
     */
    private static double obtenerPMA(double max) {
        System.out.print("Introduce el Peso Maximo Autorizado del vehiculo: ");
        double pma = leerDouble();
        while (pma < 1000 || pma > max) {
            System.out.println("PMA no permitido. Debe estar entre 1000 y " + max + ".");
            System.out.print("Introduce el Peso Maximo Autorizado del vehiculo: ");
            pma = leerDouble();
        }
        return pma;
    }

    /**
     * Pide al usuario una cantidad de dias validos (mayores que 0).
     *
     * @return la cantidad de dias.
     */
    private static int obtenerDias() {
        System.out.print("Introduce los dias del alquiler: ");
        int dias = leerInt();
        while (dias < 1) {
            System.out.println("No se puede alquilar un vehiculo para menos de 1 dia.");
            System.out.print("Introduce los dias del alquiler: ");
            dias = leerInt();
        }
        return dias;
    }

    /**
     * Pide un DNI y lo devuleve si es valido, lanza una excepcion en caso
     * contrario.
     *
     * @return un DNI valido.
     * @throws FormatoIncorrectoException si el dni no es valido.
     */
    private static String leerDNI() throws FormatoIncorrectoException {
        System.out.print("Introduce el DNI (8 numeros y 1 letra): ");
        String dni = scanner.next();
        dni = dni.toUpperCase();
        scanner.nextLine();
        if (dni.matches("\\d{8}[A-Z]")) {
            return dni;
        } else {
            throw new FormatoIncorrectoException("El DNI debe tener 8 numeros seguidos de una letra.");
        }
    }

    /**
     * Pide un telefono y lo devuleve si es valido, lanza una excepcion en caso
     * contrario.
     *
     * @return un telefono valido.
     * @throws FormatoIncorrectoException si el telefono no es valido.
     */
    private static String leerTlf() throws FormatoIncorrectoException {
        System.out.print("introduce el telefono (prefijo opcional + 9 numeros): ");
        String tlf = scanner.next();
        scanner.nextLine();
        if (tlf.matches("(\\(?\\+\\d{2}\\)?)?\\d{9}")) {
            return tlf;
        } else {
            throw new FormatoIncorrectoException("El telefono debe tener 9 numeros y un prefijo opccional.");
        }
    }

    /**
     * Pregunta si el cliente es VIP.
     *
     * @return si el cliente es VIP.
     * @throws FormatoIncorrectoException la introduccion no es valida.
     */
    private static boolean leerVip() throws FormatoIncorrectoException {
        System.out.print("Es un cliente VIP (S/N)? ");
        String vip = scanner.next().toUpperCase();
        scanner.nextLine();
        switch (vip) {
            case "S":
            case "SI":
                return true;
            case "N":
            case "NO":
                return false;
            default:
                throw new FormatoIncorrectoException("Debes ser una afirmacion o negacion.");
        }
    }

    /**
     * Pide un numero hasta que se introduzca un entero ´alido.
     *
     * @return un entero valido.
     */
    private static int leerInt() {
        boolean valido = false;
        int ret = 0;
        do {
            try {
                ret = scanner.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.print("Debes introducir un entero: ");
            } finally {
                scanner.nextLine();
            }
        } while (!valido);
        return ret;
    }

    /**
     * Pide un numero hasta que se introduzca uno valido.
     *
     * @return un numero decimal valido.
     */
    private static double leerDouble() {
        boolean valido = false;
        double ret = 0;
        do {
            try {
                ret = scanner.nextDouble();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.print("Debes introducir un número: ");
            } finally {
                scanner.nextLine();
            }
        } while (!valido);
        return ret;
    }
}
