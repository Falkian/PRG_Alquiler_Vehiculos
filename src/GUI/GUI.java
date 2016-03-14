package GUI;

import Estructuras.*;
import javax.swing.*;

/**
 * Clase que representa la ventana princiapal de la aplicación.
 *
 * @author Kevin
 */
public class GUI extends JFrame {

    //Constantes con los valores de los tipo de vehiculo
    public static final int COCHE = 0;
    public static final int MICROBUS = 1;
    public static final int FURGONETA = 2;
    public static final int CAMION = 3;

    private static final ColeccionVehiculos vehiculos = new ColeccionVehiculos();       //Coleccion de vehiculos.
    private static final ColeccionClientes clientes = new ColeccionClientes();          //Coleccion de clientes.
    private static final ColeccionAlquileres alquileres = new ColeccionAlquileres();    //Coleccion de alquileres
    private static final HistorialAlquileres historial = new HistorialAlquileres();     //Registro de alquileres realizados

    private JTabbedPane menuPestanas;           //Parte principal del menu

    private PantallaInicio pantallaInicio;          //Pestaña de inicio
    private PantallaVehiculos pantallaVehiculos;    //Pestaña de gestion de vehiculos
    private PantallaClientes pantallaClientes;      //Pestaña de gestion de clientes
    private PantallaAlquileres pantallaAlquileres;  //Pestaña de gestion de alquileres
    private PantallaFinanzas pantallaFinanzas;      //Pestaña de finanzas

    /**
     * Crea la ventana princiapl y carga los datos
     */
    public GUI() {
        super("Aplicación Alquiler");

        //Carga los datos
        vehiculos.cargar();
        clientes.cargar();
        alquileres.cargar(vehiculos, clientes);
        historial.cargar(vehiculos, clientes);

        //Inicia el programa en la pestaña de inicio
        iniciar();
    }

    /**
     * Inicializa la ventana principal y las subventanas.
     */
    private void iniciar() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        
        //Crea el menu de epstañas principal
        menuPestanas = new JTabbedPane(JTabbedPane.TOP);

        //Crea los menus
        pantallaInicio = new PantallaInicio(menuPestanas);
        pantallaVehiculos = new PantallaVehiculos(vehiculos, alquileres);
        pantallaClientes = new PantallaClientes(clientes, alquileres);
        pantallaAlquileres = new PantallaAlquileres(alquileres, vehiculos, clientes, historial);
        pantallaFinanzas = new PantallaFinanzas(historial);

        menuPestanas.addTab("Inicio", null, pantallaInicio, "Bienvenido!");
        menuPestanas.addTab("Gestión de Vehiculos", null, pantallaVehiculos, "Gestión de los vehiculos");
        menuPestanas.addTab("Gestión de Clientes", null, pantallaClientes, "Gestión de los clientes");
        menuPestanas.addTab("Gestión de Alquileres", null, pantallaAlquileres, "Gestión de los Alquileres");
        menuPestanas.addTab("Finanzas", null, pantallaFinanzas, "Gestión de las finanzas");

        getContentPane().add(menuPestanas);

        setVisible(true);
    }
}
