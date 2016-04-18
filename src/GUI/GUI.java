package GUI;

import Estructuras.*;
import Utilidades.ConexionMySQL;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;

/**
 * Clase que representa la ventana princiapal de la aplicación.
 *
 * @author Kevin
 */
//TODO - Revisar el uso de sort como metodo de ArrayList en todos los ficheros
public class GUI extends JFrame {
    
    private ConexionMySQL conexionMySQL = null;

    private ColeccionVehiculos vehiculos;       //Coleccion de vehiculos.
    private ColeccionClientes clientes;          //Coleccion de clientes.
    private ColeccionAlquileres alquileres;    //Coleccion de alquileres
    private HistorialAlquileres historial;     //Registro de alquileres realizados

    private JSplitPane menu;        //Parte principal del menu

    private JButton botonVehiculos;
    private JButton botonClientes;
    private JButton botonAlquileres;
    private JButton botonFinanzas;

    private PantallaVehiculos pantallaVehiculos = null;    //Pestaña de gestion de vehiculos
    private PantallaClientes pantallaClientes = null;      //Pestaña de gestion de clientes
    private PantallaAlquileres pantallaAlquileres = null;  //Pestaña de gestion de alquileres
    private PantallaFinanzas pantallaFinanzas = null;      //Pestaña de finanzas

    /**
     * Crea la ventana princiapl y carga los datos
     */
    public GUI() {
        super("Aplicación Alquiler");

        try {
            conexionMySQL = new ConexionMySQL();
            
            vehiculos = new ColeccionVehiculos(conexionMySQL, new ConexionMySQL());
            clientes = new ColeccionClientes(conexionMySQL);
            alquileres = new ColeccionAlquileres(vehiculos, clientes, conexionMySQL);
            historial = new HistorialAlquileres(conexionMySQL, new ConexionMySQL());

            //Carga los datos
            vehiculos.cargar();
            clientes.cargar();
            alquileres.cargar(vehiculos, clientes);
            historial.cargar(vehiculos, clientes);

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error de carga de archivo", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error de enlazado de biblioteca", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error de SQL", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error de lectura del archivo", JOptionPane.ERROR_MESSAGE);
        } finally {
            //Inicia el programa en la pestaña de inicio
            iniciar();
        }
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
                    menu.setRightComponent(new PantallaVehiculos(vehiculos, alquileres));
                    break;
                case "Clientes":
                    menu.setRightComponent(new PantallaClientes(clientes, alquileres));
                    break;
                case "Alquileres":
                    menu.setRightComponent(new PantallaAlquileres(alquileres, vehiculos, clientes, historial));
                    break;
                case "Finanzas":
                    menu.setRightComponent(new PantallaFinanzas(historial));
                    break;
            }
        }

    }
}
