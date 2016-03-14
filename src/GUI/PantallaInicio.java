package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;

/**
 * Clase que contiene el menu principal de la aplicacion
 *
 * @author Kevin
 */
public class PantallaInicio extends JPanel {

    private final JTabbedPane menuPrincipal;
    
    private JButton botonVehiculos;
    private JButton botonClientes;
    private JButton botonAlquileres;
    private JButton botonFinanzas;

    /**
     * Crea el menu princiapl y lo inicializa.
     *
     * @param menuPrincipal
     */
    public PantallaInicio(JTabbedPane menuPrincipal) {
        super();
        this.menuPrincipal = menuPrincipal;
        iniciar();
    }

    /**
     * Inicia el menu principal, creando y colocando todos sus elementos.
     */
    private void iniciar() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        botonVehiculos = new JButton("Gestión de Vehiculos");
        botonClientes = new JButton("Gestión de Clientes");
        botonAlquileres = new JButton("Gestión de Alquileres");
        botonFinanzas = new JButton("Finanzas");

        botonVehiculos.addActionListener(new botonListener());
        botonClientes.addActionListener(new botonListener());
        botonAlquileres.addActionListener(new botonListener());
        botonFinanzas.addActionListener(new botonListener());

        botonVehiculos.setActionCommand("Vehiculos");
        botonClientes.setActionCommand("Clientes");
        botonAlquileres.setActionCommand("Alquileres");
        botonFinanzas.setActionCommand("Finanzas");

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        add(botonVehiculos, c);
        c.gridx = 1;
        add(botonClientes, c);
        c.gridx = 0;
        c.gridy = 1;
        add(botonAlquileres, c);
        c.gridx = 1;
        c.gridy = 1;
        add(botonFinanzas, c);
    }

    /**
     * Manejador de eventos de los botones del menu principal
     */
    private class botonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Vehiculos":
                    menuPrincipal.setSelectedIndex(1);
                    break;
                case "Clientes":
                    menuPrincipal.setSelectedIndex(2);
                    break;
                case "Alquileres":
                    menuPrincipal.setSelectedIndex(3);
                    break;
                case "Finanzas":
                    menuPrincipal.setSelectedIndex(4);
                    break;
            }
        }

    }
}
