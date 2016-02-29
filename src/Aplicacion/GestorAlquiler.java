package Aplicacion;

import Abstractas.Vehiculo;
import Clases.*;
import Estructuras.*;
import Excepciones.*;
import Utilidades.MiScanner;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar la logica del programa.
 *
 * @author Kevin
 */
public class GestorAlquiler {

    private static final String COCHE = "COCHE";        //Valor de la cadena COCHE
    private static final String BUS = "MICROBUS";       //Valor de la cadena BUS
    private static final String FURGO = "FURGONETA";    //Valor de la cadena FURGNETA
    private static final String CAMION = "CAMION";      //Valor de la cadena CAMION
    private static final String[] TIPOS = {COCHE, BUS, FURGO, CAMION};

    private static final MiScanner scanner = new MiScanner();                           //Scanner utilizado.
    private static final ColeccionVehiculos vehiculos = new ColeccionVehiculos();       //Coleccion de vehiculos.
    private static final ColeccionClientes clientes = new ColeccionClientes();          //Coleccion de clientes.
    private static final ColeccionAlquileres alquileres = new ColeccionAlquileres();    //Coleccion de alquileres
    private static final HistorialAlquileres historial = new HistorialAlquileres();     //Registro de alquileres realizados

    /**
     * Ejecuta el menu principal de programa.
     */
    public static void ejecutar() {
        System.out.println("- - - Carga de archivos - - -\n");
        //Carga los vehiculos del fichero.
        vehiculos.cargar();
        //Carga los clientes del archivo
        clientes.cargar();
        //Carga los alquileres del archivo
        alquileres.cargar(vehiculos, clientes);
        //Carga el historial de registros
        historial.cargar(vehiculos, clientes);
        System.out.println("- - - Fin de la carga - - -\n");

        int opcion = 0;
        do {
            System.out.print("===== MENU =====\n"
                    + "1.)Anyadir un vehiculo.\n"
                    + "2.)Obtener el alquiler de un vehiculo.\n"
                    + "3.)Anyadir un cliente.\n"
                    + "4.)Alquilar vehiculo.\n"
                    + "5.)Devolver vehiculo.\n"
                    + "6.)Eliminar vehiculo.\n"
                    + "7.)Eliminar cliente.\n"
                    + "8.)Mostrar vechiulo.\n"
                    + "9.)Mostrar cliente\n"
                    + "10.)Mostrar ingresos\n"
                    + "0.)Salir\n");
            try {
                opcion = leerInt("Introduzca su opcion: ");
            } catch (FormatoIncorrectoException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("");
            }
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
                case 6:
                    eliminarVehiculo();
                    break;
                case 7:
                    eliminarCliente();
                    break;
                case 8:
                    mostrarVehiculo();
                    break;
                case 9:
                    mostrarCliente();
                    break;
                case 10:
                    mostrarIngresos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida.\n");
                    break;
            }
        } while (opcion != 0);
    }

    /**
     * Pide al usuario los datos para introducir un vehiculo y lo anyade a la
     * coleccion de vehiculos.
     */
    private static void anyadirVehiculo() {
        System.out.println("- - - Anyadir Vechiulo - - -");
        try {
            String msgtipo = "Introduce el tipo de vehiculo a introducir (Coche, Microbus, Furgoneta, Camion): ";
            String tipo = scanner.leerEntrePosibles(TIPOS, msgtipo, "Tipo no valido.", false);
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
        } catch (ObjetoYaExistenteException | FormatoIncorrectoException e) {
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
            Cliente cliente = new Cliente(leerDNI());
            cliente.setNombre(scanner.leerSegunPatron(".+", "Introduce el nombre del cliente: ", "Nombre no valido", true));
            cliente.setDireccion(scanner.leerSegunPatron(".+", "Introduce la direccion del cliente: ", "Direccion no valida", true));
            cliente.setTlf(leerTlf());
            cliente.setVip(leerVip());
            clientes.anyadirCliente(cliente);
        } catch (ObjetoYaExistenteException | FormatoIncorrectoException e) {
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
            alquileres.anyadirAlquiler(v, c);
            System.out.println("El vechiculo " + v.getMatricula() + " se le ha alquilado al cliente " + c.getDni());
        } catch (FormatoIncorrectoException | ObjetoNoExistenteException | AlquilerVehiculoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario la matricula de un vehiculo y los dias que ha estado
     * alquilado y devulve qué coste ha tenido. Se le aplica un descuento del
     * 25% si el cliente es vIP.
     */
    private static void devolverVehiculo() {
        System.out.println("- - - Devolver Vechiulo - - -");
        try {
            //Lee la matricula y obtiene el vechiculo
            String matricula = leerMatricula();
            Vehiculo vehiculo = alquileres.obtenerAlquilerPorMatricula(matricula).getVehiculo();
            //Guarda la informacion necesaria para calcular el precio del alquiler y registrarlo
            boolean vip = alquileres.obtenerAlquilerPorMatricula(matricula).getCliente().isVip();
            boolean primera = historial.isPrimerAlquiler(matricula);
            Alquiler alquiler = alquileres.obtenerAlquilerPorMatricula(matricula);
            //Devuelve el alquiler.
            alquileres.eliminarAlquilerPorMatricula(matricula);
            //Lee los dias y calcula el precio final
            int dias = obtenerDias();
            double precioAlquiler = vehiculo.alquilerTotal(dias);
            vehiculo.mostrarInfoAlquiler(dias, precioAlquiler);
            double descuentovip = 0, descuentoprim = 0;
            boolean descontado = false;
            if (vip) {
                descuentovip = precioAlquiler * 0.15;
                descontado = true;
                System.out.println("El cliente es VIP, por lo que se le aplica un descuento del 25%.");
                System.out.println("El descuento es de " + descuentovip + "euros.");
            }
            if (primera) {
                descuentoprim = precioAlquiler * 0.25;
                descontado = true;
                System.out.println("Como es la primera vez que se alquila el vehiculo tiene un descuento del 75%.");
                System.out.println("El descuento es de " + descuentoprim + "euros.");
            }
            if (descontado) {
                precioAlquiler -= descuentoprim + descuentovip;
                System.out.println("El precio tras los descuentos es de " + precioAlquiler + "euros.");
            }
            alquileres.guardar();
            //Añade el alquiler y su precio al registro de alquileres
            historial.anyadirRegistro(alquiler, dias);
        } catch (FormatoIncorrectoException | AlquilerVehiculoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario la matricula de un vehiculo y lo elimina del almacen.
     */
    private static void eliminarVehiculo() {
        System.out.println("- - - Eliminar Vehiculo - - -");
        try {
            String matricula = leerMatricula();
            //Si el vechiculo esta alquilado confirmar eliminacion
            if (vehiculos.obtenerVechiculo(matricula).isAlquilado()) {
                String dni = alquileres.obtenerAlquilerPorMatricula(matricula).getCliente().getDni();
                System.out.println("El vehiculo que quiere eliminar esta alquilado por el cliente " + dni);
                boolean eliminar = scanner.leerSN("Seguro que quiere eliminarlo (S/N)? ", "Debe responder adirmativa o negativamente.");
                if (eliminar) {
                    alquileres.eliminarAlquilerPorMatricula(matricula);
                    vehiculos.eliminarVehiculo(matricula);
                    System.out.println("Vehiculo eliminado.");
                } else {
                    System.out.println("No se eliminara el vechiculo");
                }
            } else {
                vehiculos.eliminarVehiculo(matricula);
                System.out.println("Vehiculo eliminado.");
            }
        } catch (FormatoIncorrectoException | ObjetoNoExistenteException | AlquilerVehiculoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario un DNI y elimina al cliente que identifica.
     */
    private static void eliminarCliente() {
        System.out.println("- - - Eliminar Cliente - - -");
        try {
            String dni = leerDNI();
            //Si el cliente tiene vechiculos alquilados confirmar eliminacion.
            if (clientes.obtenerCliente(dni).isAlquilado()) {
                System.out.println("El cliente tiene vehiculos alquilados.");
                boolean eliminar = scanner.leerSN("Seguro que quiere eliminarlo (S/N))? ", "Debe responder afirmativa o negativamente.");
                if (eliminar) {
                    alquileres.eliminarAlquilerPorDni(dni);
                    clientes.eliminarCliente(dni);
                    System.out.println("Cliente eliminado.");
                } else {
                    System.out.println("No se eliminara el cliente.");
                }
            } else {
                clientes.eliminarCliente(dni);
                System.out.println("Cliente eliminado.");
            }
        } catch (FormatoIncorrectoException | ObjetoNoExistenteException | AlquilerVehiculoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Muestra información sobre un vehiculo. Si esta alquilado muestra
     * informacion sobre el cliente que lo tiene alquilado. Si no lo esta indica
     * si ha sidod alquilado alguna vez.
     */
    public static void mostrarVehiculo() {
        System.out.println("- - - Mostrar Vehiculo - - -");
        try {
            String matricula = leerMatricula();
            Vehiculo v = vehiculos.obtenerVechiculo(matricula);
            //Mostrar información del vehiculo
            System.out.println(v.obtenerInformacion());
            //Si esta alquilado mostrar quien lo tiene alquilado
            if (v.isAlquilado()) {
                String dni = alquileres.obtenerAlquilerPorMatricula(matricula).getCliente().getDni();
                String nombre = alquileres.obtenerAlquilerPorMatricula(matricula).getCliente().getNombre();
                System.out.println("El vehiculo esta alquilado por " + nombre + ", con DNI: " + dni);
            } else {
                //Si no esta alquilado indicar si ha sido alquilado
                if (historial.isPrimerAlquiler(matricula)) {
                    System.out.println("Es la primera vez que se alquila.");
                } else {
                    System.out.println("Ya ha sido alquilado con anterioridad.");
                }
            }
        } catch (FormatoIncorrectoException | ObjetoNoExistenteException | AlquilerVehiculoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Muestra informacion sobre un cliente. Si tiene vehiculos alquilados
     * muestra informacion sobre dichos vehiculos.
     */
    public static void mostrarCliente() {
        System.out.println("- - - Mostrar Cliente - - ");
        try {
            String dni = leerDNI();
            Cliente c = clientes.obtenerCliente(dni);
            System.out.println(c.obtenerInformacion());
            if (c.isAlquilado()) {
                try {
                    System.out.println("\nEl cliente tiene vehiculos alquilados: ");
                    ArrayList<Vehiculo> ac = alquileres.obtenerAlquileresCliente(dni);
                    for (Vehiculo vehiculo : ac) {
                        System.out.println(vehiculo.obtenerInformacion());
                    }
                } catch (AlquilerVehiculoException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (FormatoIncorrectoException | ObjetoNoExistenteException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
    }

    /**
     * Muestra por pantalla cuanto costo cada alquiler realizado y el total de
     * los precios de dichos alquileres.
     */
    public static void mostrarIngresos() {
        System.out.println("- - - Mostrar ingresos - - -");
        double total = historial.ingresos();
        System.out.printf("Total: %.2f€%n",  total);
        System.out.println("");
    }

    /**
     * Pide una matricula y la devuleve si es valida, lanza una excepcion en
     * caso contrario.
     *
     * @return una matricula valida.
     * @throws FormatoIncorrectoException si la matricula no es valida.
     */
    private static String leerMatricula() throws FormatoIncorrectoException {
        String msg = "Introduce la matricula (4 numeros y 3 letras): ";
        String errmsg = "La matricula debe tener 4 numeros seguidos de 3 letras.";
        String regex = "\\d{4}[A-Z]{3}";
        return scanner.leerSegunPatron(regex, msg, errmsg, false);
    }

    /**
     * Pide las plazas de un vehiculo hasta que se introduzca una cantidad
     * valida (entre 2 y el maximo).
     *
     * @param max la maxima cantidad de plazas del vehiculo.
     * @return una cantidad deplazas valida.
     */
    private static int obtenerPlazas(int max) {
        int plazas = 0;
        try {
            plazas = leerInt("Introduce las plazas del vehiculo: ");
            while (plazas < 2 || plazas > max) {
                System.out.println("Cantidad de plazas no permitida. Deben estar entre 2 y " + max + ".");
                plazas = leerInt("Introduce las plazas del vehiculo: ");
            }
        } catch (FormatoIncorrectoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
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
        double pma = 0;
        try {
            pma = leerDouble("Introduce el Peso Maximo Autorizado del vehiculo: ");
            while (pma < 500 || pma > max) {
                System.out.println("PMA no permitido. Debe estar entre 1000 y " + max + ".");
                pma = leerDouble("Introduce el Peso Maximo Autorizado del vehiculo: ");
            }
        } catch (FormatoIncorrectoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
        }
        return pma;
    }

    /**
     * Pide al usuario una cantidad de dias validos (mayores que 0).
     *
     * @return la cantidad de dias.
     */
    private static int obtenerDias() {
        int dias = 0;
        try {
            dias = leerInt("Introduce los dias del alquiler: ");
            while (dias < 1) {
                System.out.println("No se puede alquilar un vehiculo para menos de 1 dia.");
                dias = leerInt("Introduce los dias del alquiler: ");
            }
        } catch (FormatoIncorrectoException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("");
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
        String msg = "Introduce el DNI (8 numeros y 1 letra): ";
        String errmsg = "El DNI debe tener 8 numeros seguidos de una letra.";
        String regex = "\\d{8}[A-Z]";
        return scanner.leerSegunPatron(regex, msg, errmsg, false);
    }

    /**
     * Pide un telefono y lo devuleve si es valido, lanza una excepcion en caso
     * contrario.
     *
     * @return un telefono valido.
     * @throws FormatoIncorrectoException si el telefono no es valido.
     */
    private static String leerTlf() throws FormatoIncorrectoException {
        String msg = "Introduce el telefono (prefijo opcional + 9 numeros): ";
        String errmsg = "El telefono debe tener 9 numeros y un prefijo opccional.";
        String regex = "(\\(?\\+\\d{2,3}\\)?)?\\d{9}";
        return scanner.leerSegunPatron(regex, msg, errmsg, false);
    }

    /**
     * Pregunta si el cliente es VIP.
     *
     * @return si el cliente es VIP.
     * @throws FormatoIncorrectoException la introduccion no es valida.
     */
    private static boolean leerVip() throws FormatoIncorrectoException {
        String msg = "Es un cliente VIP (S/N)? ";
        String errmsg = "Debes ser una afirmacion o negacion.";
        return scanner.leerSN(msg, errmsg);
    }

    /**
     * Pide un entero y lo devulve si es valido
     *
     * @return un entero valido.
     * @throws FormatoIncorrectoException si el numero no es valido.
     */
    private static int leerInt(String msg) throws FormatoIncorrectoException {
        String errmsg = "Debe ser un entero.";
        String regex = "\\d+";
        return Integer.parseInt(scanner.leerSegunPatron(regex, msg, errmsg, false));
    }

    /**
     * Pide un numero y lo devulve si es valido
     *
     * @return un entero valido.
     * @throws FormatoIncorrectoException si el numero no es valido.
     */
    private static double leerDouble(String msg) throws FormatoIncorrectoException {
        String errmsg = "Debe ser un numero.";
        String regex = "(\\d+\\.)?\\d+";
        return Double.parseDouble(scanner.leerSegunPatron(regex, msg, errmsg, false));
    }
}
