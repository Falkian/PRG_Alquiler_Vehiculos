package GUI;

import Estructuras.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static final ColeccionAlquileres alquileres = new ColeccionAlquileres(vehiculos, clientes);    //Coleccion de alquileres
    private static final HistorialAlquileres historial = new HistorialAlquileres();     //Registro de alquileres realizados

    private JSplitPane menu;        //Parte principal del menu

    private JButton botonVehiculos;
    private JButton botonClientes;
    private JButton botonAlquileres;
    private JButton botonFinanzas;
    
    //private PantallaInicio pantallaInicio;          //Pestaña de inicio
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
        menu = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        //Crea los menus
        pantallaVehiculos = new PantallaVehiculos(vehiculos, alquileres);
        pantallaClientes = new PantallaClientes(clientes, alquileres);
        pantallaAlquileres = new PantallaAlquileres(alquileres, vehiculos, clientes, historial);
        pantallaFinanzas = new PantallaFinanzas(historial);

        JPanel botonesMenu = new JPanel();       
        botonesMenu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        botonVehiculos = new JButton("Vehiculos");
        botonClientes = new JButton("Clientes");        
        botonAlquileres = new JButton("Alquileres");        
        botonFinanzas = new JButton("Finanzas");
        
        botonVehiculos.setActionCommand("Vehiculos");
        botonClientes.setActionCommand("Clientes");
        botonAlquileres.setActionCommand("Alquileres");
        botonFinanzas.setActionCommand("Finanzas");
        
        botonVehiculos.addActionListener(new botonMenuListener());
        botonClientes.addActionListener(new botonMenuListener());
        botonAlquileres.addActionListener(new botonMenuListener());
        botonFinanzas.addActionListener(new botonMenuListener());
        
        botonesMenu.add(botonVehiculos);
        botonesMenu.add(botonClientes);
        botonesMenu.add(botonAlquileres);
        botonesMenu.add(botonFinanzas);
        
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        int yin = botonesMenu.getHeight() / 5 - botonVehiculos.getHeight() / 2;
        int xin = botonesMenu.getWidth() / 2 - botonVehiculos.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        botonesMenu.add(botonVehiculos, c);

        c.gridy = 1;
        yin = botonesMenu.getHeight() / 5 - botonClientes.getHeight() / 2;
        xin = botonesMenu.getWidth() / 2 - botonClientes.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        botonesMenu.add(botonClientes, c);

        c.gridy = 2;
        yin = botonesMenu.getHeight() / 5 - botonAlquileres.getHeight() / 2;
        xin = botonesMenu.getWidth() / 2 - botonAlquileres.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        botonesMenu.add(botonAlquileres, c);

        c.gridy = 3;
        yin = botonesMenu.getHeight() / 5 - botonFinanzas.getHeight() / 2;
        xin = botonesMenu.getWidth() / 2 - botonFinanzas.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        botonesMenu.add(botonFinanzas, c);
        
        menu.setLeftComponent(botonesMenu);
        menu.setRightComponent(pantallaVehiculos);

        getContentPane().add(menu);

        setVisible(true);
    }
    
    private class botonMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Vehiculos":
                    menu.setRightComponent(pantallaVehiculos);
                    break;
                case "Clientes":
                    menu.setRightComponent(pantallaClientes);
                    break;
                case "Alquileres":
                    menu.setRightComponent(pantallaAlquileres);
                    break;
                case "Finanzas":
                    menu.setRightComponent(pantallaFinanzas);
                    break;
            }
        }
        
        
    }
}
