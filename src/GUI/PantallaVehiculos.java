package GUI;

import Clases.*;
import Estructuras.ColeccionAlquileres;
import Estructuras.ColeccionVehiculos;
import Excepciones.AlquilerVehiculoException;
import Excepciones.FormatoIncorrectoException;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PantallaVehiculos extends JSplitPane {

    private final ColeccionVehiculos vehiculos;
    private final ColeccionAlquileres alquileres;
    
    private JLabel textoMatricula;
    private JTextField introMatricula;
    private JLabel textoTipo;
    private JComboBox introTipo;
    private JLabel textoCaract;
    private JTextField introCaract;

    public PantallaVehiculos(ColeccionVehiculos vehiculos, ColeccionAlquileres alquileres) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.vehiculos = vehiculos;
        this.alquileres = alquileres;
        iniciar();
    }

    private void iniciar() {
        JPanel menuVehiculos = new JPanel();        //Menu izquierdo
        menuVehiculos.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //- - - Botones de la parte izquierda del menu de gestion de vehiculos
        JButton botonAnadir = new JButton("Alta");
        JButton botonModificacion = new JButton("Modificacion");
        JButton botonBorrado = new JButton("Borrado");
        JButton botonListado = new JButton("Listado");

        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        int yin = menuVehiculos.getHeight() / 5 - botonAnadir.getHeight() / 2;
        int xin = menuVehiculos.getWidth() / 2 - botonAnadir.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuVehiculos.add(botonAnadir, c);

        c.gridy = 1;
        yin = menuVehiculos.getHeight() / 5 - botonModificacion.getHeight() / 2;
        xin = menuVehiculos.getWidth() / 2 - botonModificacion.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuVehiculos.add(botonModificacion, c);

        c.gridy = 2;
        yin = menuVehiculos.getHeight() / 5 - botonBorrado.getHeight() / 2;
        xin = menuVehiculos.getWidth() / 2 - botonBorrado.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuVehiculos.add(botonBorrado, c);

        c.gridy = 3;
        yin = menuVehiculos.getHeight() / 5 - botonListado.getHeight() / 2;
        xin = menuVehiculos.getWidth() / 2 - botonListado.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuVehiculos.add(botonListado, c);

        botonAnadir.addActionListener(new botonesMenuListener());
        botonModificacion.addActionListener(new botonesMenuListener());
        botonBorrado.addActionListener(new botonesMenuListener());
        botonListado.addActionListener(new botonesMenuListener());

        botonAnadir.setActionCommand("Anadir");
        botonModificacion.setActionCommand("Modificar");
        botonBorrado.setActionCommand("Borrar");
        botonListado.setActionCommand("Lista");

        setLeftComponent(menuVehiculos);

        //- - - Inicia el menu de gestion de vehiculos en el apartado de alta
        inicioPantallaVehiculosAlta();
    }

    private void inicioPantallaVehiculosAlta() {
        JPanel altaVehiculos = new JPanel();
        altaVehiculos.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U><B>ALTA DE VEHICULOS</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        altaVehiculos.add(titulo, c);

        //Zona de introduccion de datos
        JPanel contenido = new JPanel();
        contenido.setLayout(new GridBagLayout());
        GridBagConstraints ci = new GridBagConstraints();

        textoMatricula = new JLabel("Matricula");
        introMatricula = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 0;
        ci.ipadx = 20;
        ci.weightx = 0.5;
        ci.weighty = 0.5;
        contenido.add(textoMatricula, ci);
        ci.gridx = 1;
        contenido.add(introMatricula, ci);

        textoTipo = new JLabel("Tipo");
        introTipo = new JComboBox(new String[]{"Coche", "Microbus", "Furgoneta", "Camion"});
        ci.gridx = 0;
        ci.gridy = 1;
        contenido.add(textoTipo, ci);
        ci.gridx = 1;
        contenido.add(introTipo, ci);

        introTipo.addActionListener(new selectorTipoListener());

        textoCaract = new JLabel("Plazas");
        introCaract = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 2;
        contenido.add(textoCaract, ci);
        ci.gridx = 1;
        contenido.add(introCaract, ci);

        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        altaVehiculos.add(contenido, c);

        //Boton de alta
        JButton botonAlta = new JButton("Alta");
        botonAlta.addActionListener(new botonAltaListener());

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        altaVehiculos.add(botonAlta, c);

        setRightComponent(altaVehiculos);
    }

    private void inicioPantallaVehiculosModificacion() {
        JPanel modificacionVehiculos = new JPanel();
        modificacionVehiculos.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U></B>MODIFICACION DE VEHICULOS</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        modificacionVehiculos.add(titulo, c);

        //Zona de introduccion de datos
        JPanel contenido = new JPanel();
        contenido.setLayout(new GridBagLayout());
        GridBagConstraints ci = new GridBagConstraints();

        textoMatricula = new JLabel("Matricula");
        introMatricula = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 0;
        ci.ipadx = 20;
        ci.weightx = 0.5;
        ci.weighty = 0.5;
        contenido.add(textoMatricula, ci);
        ci.gridx = 1;
        contenido.add(introMatricula, ci);

        textoTipo = new JLabel("Tipo");
        introTipo = new JComboBox(new String[]{"Coche", "Microbus", "Furgoneta", "Camion"});
        ci.gridx = 0;
        ci.gridy = 1;
        contenido.add(textoTipo, ci);
        ci.gridx = 1;
        contenido.add(introTipo, ci);

        introTipo.addActionListener(new selectorTipoListener());

        textoCaract = new JLabel("Plazas");
        introCaract = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 2;
        contenido.add(textoCaract, ci);
        ci.gridx = 1;
        contenido.add(introCaract, ci);

        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        modificacionVehiculos.add(contenido, c);

        //Boton de borrado
        JButton botonModificacion = new JButton("Modificar");
        botonModificacion.addActionListener(new botonModificarListener());

        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        modificacionVehiculos.add(botonModificacion, c);

        setRightComponent(modificacionVehiculos);
    }

    private void inicioPantallaVehiculosBorrado() {
        JPanel borradoVehiculos = new JPanel();
        borradoVehiculos.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U><B>BORRADO DE VEHICULOS</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        borradoVehiculos.add(titulo, c);

        //Zona de introduccion de datos
        JPanel contenido = new JPanel();
        contenido.setLayout(new GridBagLayout());
        GridBagConstraints ci = new GridBagConstraints();

        textoMatricula = new JLabel("Matricula");
        introMatricula = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 0;
        ci.ipadx = 20;
        ci.weightx = 0.5;
        ci.weighty = 0.5;
        contenido.add(textoMatricula, ci);
        ci.gridx = 1;
        contenido.add(introMatricula, ci);

        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        borradoVehiculos.add(contenido, c);

        //Boton de borrado
        JButton botonBorrado = new JButton("Eliminar");
        botonBorrado.addActionListener(new botonBorrarListener());

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        borradoVehiculos.add(botonBorrado, c);

        setRightComponent(borradoVehiculos);
    }

    private void inicioPantallaVehiculosListado() {
        JTable listado = new JTable(vehiculos.obtenerDataArray(), new String[]{"Matricula", "Tipo", "Plazas\\PMA"});

        JScrollPane listadoVehiculos = new JScrollPane(listado);
        listado.setFillsViewportHeight(true);

        setRightComponent(listadoVehiculos);
    }

    private class botonesMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Anadir":
                    inicioPantallaVehiculosAlta();
                    break;
                case "Modificar":
                    inicioPantallaVehiculosModificacion();
                    break;
                case "Borrar":
                    inicioPantallaVehiculosBorrado();
                    break;
                case "Lista":
                    inicioPantallaVehiculosListado();
                    break;
            }
        }

    }

    private class botonAltaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String matricula = obtenerMatricula();
                switch (introTipo.getSelectedIndex()) {
                    case GUI.COCHE:
                        int plazas = obtenerPlazas(Coche.PLAZAS_MAX);
                        Coche c = new Coche(matricula, plazas);
                        vehiculos.anyadirVehiculo(c);
                        JOptionPane.showMessageDialog(rightComponent,
                                "Coche con matricula " + matricula + " y " + plazas + "plazas introducido.", "Vehiculo introducido",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case GUI.MICROBUS:
                        plazas = obtenerPlazas(Microbus.PLAZAS_MAX);
                        Microbus b = new Microbus(matricula, plazas);
                        vehiculos.anyadirVehiculo(b);
                        JOptionPane.showMessageDialog(rightComponent,
                                "Microbus con matricula " + matricula + " y " + plazas + "plazas introducido.", "Vehiculo introducido",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case GUI.FURGONETA:
                        double PMA = obtenerPMA(Furgoneta.PMA_MAX);
                        Furgoneta f = new Furgoneta(matricula, PMA);
                        vehiculos.anyadirVehiculo(f);
                        JOptionPane.showMessageDialog(rightComponent,
                                "Furgoneta con matricula " + matricula + " y PMA " + PMA + " introducida.", "Vehiculo introducido",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case GUI.CAMION:
                        PMA = obtenerPMA(Camion.PMA_MAX);
                        Camion ca = new Camion(matricula, PMA);
                        vehiculos.anyadirVehiculo(ca);
                        JOptionPane.showMessageDialog(rightComponent,
                                "Camion con matricula " + matricula + " y PMA " + PMA + " introducido.", "Vehiculo introducido",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoYaExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Ya existe el vehiculo", JOptionPane.ERROR_MESSAGE);
            } finally {
                introMatricula.setText("");
                introTipo.setSelectedIndex(0);
                introCaract.setText("");
            }
        }

    }

    private class botonModificarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String matricula = obtenerMatricula();
                switch (introTipo.getSelectedIndex()) {
                    case GUI.COCHE:
                        int plazas = obtenerPlazas(Coche.PLAZAS_MAX);
                        Coche c = new Coche(matricula, plazas);
                        vehiculos.modificarVehiculo(matricula, c);
                        JOptionPane.showMessageDialog(rightComponent,
                                "Vehiculo con matricula " + matricula + " establecido como coche de " + plazas + "plazas.", "Vehiculo modificado",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case GUI.MICROBUS:
                        plazas = obtenerPlazas(Microbus.PLAZAS_MAX);
                        Microbus b = new Microbus(matricula, plazas);
                        vehiculos.modificarVehiculo(matricula, b);
                        JOptionPane.showMessageDialog(rightComponent,
                                "Vehiculo con matricula " + matricula + " establecido como microbus de " + plazas + "plazas.", "Vehiculo modificado",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case GUI.FURGONETA:
                        double PMA = obtenerPMA(Furgoneta.PMA_MAX);
                        Furgoneta f = new Furgoneta(matricula, PMA);
                        vehiculos.modificarVehiculo(matricula, f);
                        JOptionPane.showMessageDialog(rightComponent,
                                "Vehiculo con matricula " + matricula + " establecido como furgoneta con PMA " + PMA + ".", "Vehiculo modificado",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case GUI.CAMION:
                        PMA = obtenerPMA(Camion.PMA_MAX);
                        Camion ca = new Camion(matricula, PMA);
                        vehiculos.modificarVehiculo(matricula, ca);
                        JOptionPane.showMessageDialog(rightComponent,
                                "Vehiculo con matricula " + matricula + " establecido como camion con PMA " + PMA + ".", "Vehiculo modificado",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "No existe el vehiculo", JOptionPane.ERROR_MESSAGE);
            } finally {
                introMatricula.setText("");
                introTipo.setSelectedIndex(0);
                introCaract.setText("");
            }
        }

    }

    private class botonBorrarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String matricula = obtenerMatricula();
                boolean eliminar = true;
                if (vehiculos.obtenerVechiculo(matricula).isAlquilado()) {
                    //Informar de que esta alquilado y confirmar eliminacion
                    int resp = JOptionPane.showConfirmDialog(rightComponent, "El vehiculo que quiere eliminar esta alquilado.\n"
                            + "¿Seguro que desea eliminarlo (se cancelará el alquiler sin agregarse al historial)?",
                            "Vehiculo alquilado", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        alquileres.eliminarAlquilerPorMatricula(matricula);
                    } else {
                        eliminar = false;
                        JOptionPane.showMessageDialog(rightComponent, "No se eliminara el vehiculo.",
                                "Vehiculo no eliminado", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (eliminar) {                    
                    vehiculos.eliminarVehiculo(matricula);
                    JOptionPane.showMessageDialog(rightComponent, "Vehiculo con matricula " + matricula + " eliminado.",
                            "Vehiculo eliminado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "No existe el vehiculo", JOptionPane.ERROR_MESSAGE);
            } catch (AlquilerVehiculoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo al eliminar el alquiler", JOptionPane.ERROR_MESSAGE);
            } finally {
                introMatricula.setText("");
            }
        }
    }

    private class selectorTipoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (introTipo.getSelectedIndex()) {
                case GUI.COCHE:
                case GUI.MICROBUS:
                    textoCaract.setText("Plazas");
                    break;
                case GUI.FURGONETA:
                case GUI.CAMION:
                    textoCaract.setText("PMA");
                    break;
            }
        }

    }

    private String obtenerMatricula() throws FormatoIncorrectoException {
        String matricula = introMatricula.getText().toUpperCase();
        if (matricula.matches("[\\d]{4}[a-zA-Z]{3}")) {
            return matricula;
        } else if (matricula.equals("")) {
            throw new FormatoIncorrectoException("Debes introducir la matricula.");
        } else {
            throw new FormatoIncorrectoException("La matricula debe tener 4 numeros seguidos de 3 letras.");
        }
    }

    private int obtenerPlazas(int max) throws FormatoIncorrectoException {
        try {
            if (introCaract.getText().equals("")) {
                throw new FormatoIncorrectoException("Debes introducir las plazas.");
            } else {
                int plazas = Integer.parseInt(introCaract.getText());
                if (plazas < 2 || plazas > max) {
                    throw new FormatoIncorrectoException("Las plazas deben estar entre 2 y " + max);
                } else {
                    return plazas;
                }
            }
        } catch (NumberFormatException e) {
            throw new FormatoIncorrectoException("Las plazas deben ser un numero entero.");
        }
    }

    private double obtenerPMA(double max) throws FormatoIncorrectoException {
        try {
            if (introCaract.getText().equals("")) {
                throw new FormatoIncorrectoException("Debes introducir el PMA.");
            } else {
                double pma = Double.parseDouble(introCaract.getText());
                if (pma < 500 || pma > max) {
                    throw new FormatoIncorrectoException("El PMA debe estar entre 500 y " + max);
                } else {
                    return pma;
                }
            }
        } catch (NumberFormatException e) {
            throw new FormatoIncorrectoException("El PMA debe ser un numero.");
        }
    }
}
