package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PantallaInicio extends JPanel {
    
    private final JTabbedPane menuPrincipal;
    
    public PantallaInicio(JTabbedPane menuPrincipal) {
        super();
        this.menuPrincipal = menuPrincipal;
        iniciar();
    }
    
    private void iniciar() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JButton botonVehiculos = new JButton("Gestión de Vehiculos");
        JButton botonClientes = new JButton("Gestión de Clientes");
        JButton botonAlquileres = new JButton("Gestión de Alquileres");
        JButton botonFinanzas = new JButton("Finanzas");
        
        botonVehiculos.addActionListener(new buttonListener());
        botonClientes.addActionListener(new buttonListener());
        botonAlquileres.addActionListener(new buttonListener());
        botonFinanzas.addActionListener(new buttonListener());
        
        botonVehiculos.setActionCommand("Vehiculos");
        botonClientes.setActionCommand("Clientes");
        botonAlquileres.setActionCommand("Alquileres");
        botonFinanzas.setActionCommand("Finanzas");

        //TODO - Centrar los botones y ajustarlos al mismo tamaño
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        add(botonVehiculos, c);
        
        c.gridx = 1;
        c.gridy = 0;
        add(botonClientes, c);
        
        c.gridx = 0;
        c.gridy = 1;
        add(botonAlquileres, c);
        
        c.gridx = 1;
        c.gridy = 1;
        add(botonFinanzas, c);
    }
    
    private class buttonListener implements ActionListener {

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
