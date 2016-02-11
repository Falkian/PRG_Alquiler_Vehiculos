package Aplicacion;

import Abstractas.Vehiculo;
import Clases.*;
import Estructuras.ColeccionVehiculos;
import java.util.Scanner;

public class Alquiler {

    private static final String COCHE = "COCHE";
    private static final String BUS = "MICROBUS";
    private static final String FURGO = "FURGONETA";
    private static final String CAMION = "CAMION";

    private static final int TAMANYO = 3;

    private static Scanner scanner = new Scanner(System.in);
    private static ColeccionVehiculos vehiculos = new ColeccionVehiculos(TAMANYO);

    /**
     * Ejecuta el menu principal de programa.
     */
    public static void ejecutar() {
        int opcion;
        do {
            System.out.print("- - - MENU - - -\n"
                    + "1.)Anyadir un vehiculo.\n"
                    + "2.)Obtener el alquiler de un vehiculo.\n"
                    + "9.)Salir\n"
                    + "Introduzca su opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    anyadirVehiculo();
                    break;
                case 2:
                    obtenerAlquiler();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        } while (opcion != 9);
    }

    /**
     * Pide al usuario los datos para introducir un vehiculo y lo añade a la
     * coleccion de vehiculos.
     */
    private static void anyadirVehiculo() {
        if (vehiculos.isFull()) {
            System.out.println("La lista de vehiculos esta llena.");
        } else {
            String tipo = obtenerTipo();
            String matricula = obtenerMatricula();
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
        }
        System.out.println("");
    }

    /**
     * Pide al usuario los datos de un vehiculo y cuantos dias dura un alquiler
     * y muestra por pantalla el precio de alquilar el vehiculo durante los dias
     * introducidos.
     */
    private static void obtenerAlquiler() {
        String matricula = obtenerMatricula();
        int dias = obtenerDias();

        Vehiculo vehiculo = vehiculos.obtenerVechiculo(matricula);
        if (vehiculo == null) {
            System.out.println("El vehiculo no existe.");
        } else {
            double alquiler;
            if (vehiculo instanceof Coche) {
                Coche coche = (Coche) vehiculo;
                alquiler = coche.alquilerTotal(dias);
                System.out.println("El vehiculo es un coche de " + coche.getPlazas() + " plazas "
                        + "y el alquiler para " + dias + " dias es de " + alquiler + " euros.");
            } else if (vehiculo instanceof Microbus) {
                Microbus bus = (Microbus) vehiculo;
                alquiler = bus.alquilerTotal(dias);
                System.out.println("El vehiculo es un microbus de " + bus.getPlazas() + " plazas "
                        + "y el alquiler para " + dias + " dias es de " + alquiler + "euros.");
            } else if (vehiculo instanceof Furgoneta) {
                Furgoneta furgoneta = (Furgoneta) vehiculo;
                alquiler = furgoneta.alquilerTotal(dias);
                System.out.println("El vehiculo es una furgoneta con PMA " + furgoneta.getPMA() + "kilos "
                        + "y el alquiler para " + dias + " dias es de " + alquiler + " euros.");
            } else if (vehiculo instanceof Camion) {
                Camion camion = (Camion) vehiculo;
                alquiler = camion.alquilerTotal(dias);
                System.out.println("El vehiculo es un camion con PMA " + camion.getPMA() + "kilos "
                        + "y el alquiler para " + dias + " dias es de " + alquiler + " euros.");
            }
        }
        System.out.println("");
    }

    /**
     * Pide al usuario que intoduzca un tipo de vehiculo hasta que lo introduzca
     * correctamente, entonces lo devuelve.
     *
     * @return El String que representa u ntipo de vehiculo.
     */
    private static String obtenerTipo() {
        System.out.print("Introduce el tipo de vehiculo a introducir (Coche, Microbus, Furgoneta, Camion):");
        String tipo = scanner.next().toUpperCase();
        scanner.nextLine();
        while (!tipo.equals(COCHE) && !tipo.equals(BUS) && !tipo.equals(FURGO) && !tipo.equals(CAMION)) {
            System.out.println("Tipo no valido.");
            System.out.print("Introduce el tipo de vehiculo a introducir (C-Coche, B-Microbus, F-Furgoneta, T-Camion):");
            tipo = scanner.next().toUpperCase();
            scanner.nextLine();
        }
        return tipo;
    }

    /**
     * Pide una matricula al usuario hasta que introduzca una valida, y la
     * devuelve.
     *
     * @return una cadena con una matricula valida.
     */
    private static String obtenerMatricula() {
        System.out.print("Introduce la matricula (4 digitos y 3 letras: ddddlll): ");
        String matricula = scanner.next().toUpperCase();
        scanner.nextLine();
        while (!matriculaValida(matricula)) {
            System.out.println("La matricla no es valida.\n"
                    + "Debe constar de cuatro digitos seguidos de tres letras.");
            System.out.print("Introduce la matricula (4 digitos y 3 letras: ddddlll): ");
            matricula = scanner.next().toUpperCase();
            scanner.nextLine();
        }
        return matricula;
    }

    /**
     * Devuelve cierto si la cadena introducida es una matricula valida.
     *
     * @param matricula la matricula a comprobar.
     * @return cierto si la matricula es valida; false en caso contrario.
     */
    private static boolean matriculaValida(String matricula) {
        String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        String digitos = "0123456789";
        boolean valida = true;

        if (matricula.length() != 7) {
            valida = false;
        } else {
            for (int i = 0; i < matricula.length(); i++) {
                if (i < 4) {
                    if (digitos.indexOf(matricula.charAt(i)) < 0) {
                        valida = false;
                    }
                } else {
                    if (letras.indexOf(matricula.charAt(i)) < 0) {
                        valida = false;
                    }
                }
            }
        }

        return valida;
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
        int plazas = (int) leerNumero();
        while (plazas < 2 || plazas > max) {
            System.out.println("Cantidad de plazas no permitida. Deben estar entre 2 y " + max + ".");
            System.out.print("Introduce las plazas del vehiculo: ");
            plazas = (int) leerNumero();
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
        double pma = leerNumero();
        while (pma < 1000 || pma > max) {
            System.out.println("PMA no permitido. Debe estar entre 1000 y " + max + ".");
            System.out.print("Introduce el Peso Maximo Autorizado del vehiculo: ");
            pma = leerNumero();
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
        int dias = (int) leerNumero();
        while (dias < 1) {
            System.out.println("No se puede alquilar un vehiculo para menos de 1 dia.");
            System.out.print("Introduce los dias del alquiler: ");
            dias = (int) leerNumero();
        }
        return dias;
    }

    /**
     * Lee por teclado un numero y si no es valido lo vuelve a pedir hasta que
     * sea valido.
     *
     * @return un numero valido.
     */
    private static double leerNumero() {
        String digitos = "0123456789";
        boolean valido;
        String numero = scanner.next();
        scanner.nextLine();

        do {
            valido = true;
            for (int i = 0; i < numero.length(); i++) {
                if (digitos.indexOf(numero.charAt(i)) < 0) {
                    valido = false;
                }
            }
            if (!valido) {
                System.out.print("Debes introducir un numero :");
                numero = scanner.next();
                scanner.nextLine();
            }
        } while (!valido);

        return Double.parseDouble(numero);
    }
}