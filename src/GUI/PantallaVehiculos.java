package GUI;

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

/**
 * Clase que contiene el menu de gestion de vehiculos
 *
 * @author Kevin
 */
public class PantallaVehiculos extends JSplitPane {

    private final ColeccionVehiculos vehiculos;     //Colecccion de vehiculos
    private final ColeccionAlquileres alquileres;   //Coleccion de alquileres (para saber si un vehiculo esta alquilado)

    private JLabel textoMatricula;
    private JTextField introMatricula;
    private JLabel textoTipo;
    private JComboBox introTipo;
    private JLabel textoCaract;
    private JTextField introCaract;

    /**
     * Crea e inicializa el menu de vehiculos.
     *
     * @param vehiculos la coleccion de vehiculos
     * @param alquileres la coleccion de alquileres
     */
    public PantallaVehiculos(ColeccionVehiculos vehiculos, ColeccionAlquileres alquileres) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.vehiculos = vehiculos;
        this.alquileres = alquileres;
        iniciar();
    }

    /**
     * Inicia el menu de gestion de vehiculos, creando y colocando todos sus
     * elementos. Empieza en el apartado de alta de vehiculos.
     */
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

    /**
     * Crea y coloca los elementos del apartado de alta de vehiculos.
     */
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

    /**
     * Crea y coloca los elementos del apartado de modificacion de vehiculos.
     */
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

    /**
     * Crea y coloca los elementos del apartado de borrado de vehiculos.
     */
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

    /**
     * Crea y coloca los elementos del apartado de listado de vehiculos.
     */
    private void inicioPantallaVehiculosListado() {
        JTable listado = new JTable(vehiculos.obtenerDataArray(), new String[]{"Matricula", "Tipo", "Plazas\\PMA"});
        listado.setEnabled(false);
        listado.getTableHeader().setReorderingAllowed(false);

        JScrollPane listadoVehiculos = new JScrollPane(listado);
        listado.setFillsViewportHeight(true);

        setRightComponent(listadoVehiculos);
    }

    /**
     * Manejador de eventos de los botones de navegacion del menu de gestion de
     * vehiculos.
     */
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

    /**
     * Manejador de eventos del boton de alta de vehiculos. Asegura que los
     * datos sean validos al clicar, indicando los posibles errores o exito de
     * la accion.
     */
    private class botonAltaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //Obtiene la informacion del vehiculo
                String tipo = (String) introTipo.getSelectedItem();
                String matricula = obtenerMatricula();
                double caract = obtenerCaracteristica();
                vehiculos.anyadirVehiculo(tipo, matricula, caract);
                String info = tipo.equals("Coche") || tipo.equals("Microbus") ? 
                        " con " + (int)caract + "plazas" : "con PMA " + caract;
                JOptionPane.showMessageDialog(rightComponent, tipo + info + " anyadido", "Vehiculo anyadido", JOptionPane.INFORMATION_MESSAGE);
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

    /**
     * Manejador de eventos del boton de modificacion de vehiculos. Asegura que
     * los datos sean validos al clicar, indicando los posibles errores o exito
     * de la accion.
     */
    private class botonModificarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //Obtiene la informacion del vehiculo
                String tipo = (String) introTipo.getSelectedItem();
                String matricula = obtenerMatricula();
                double caract = obtenerCaracteristica();
                vehiculos.modificarVehiculo(matricula, tipo, caract);
                JOptionPane.showMessageDialog(rightComponent, "Vehiculo modificado correctamente", "Vehiculo modificado", JOptionPane.INFORMATION_MESSAGE);
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Ya existe el vehiculo", JOptionPane.ERROR_MESSAGE);
            } finally {
                introMatricula.setText("");
                introTipo.setSelectedIndex(0);
                introCaract.setText("");
            }
        }

    }

    /**
     * Manejador de eventos del boton de borrado de vehiculos. Asegura que los
     * datos sean validos al clicar, indicando los posibles errores o exito de
     * la accion.
     */
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

    /**
     * Manejador de eventos del selector del tipo de vehiculo.
     */
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

    /**
     * Lee la matricula de la zona de introduccion correspondiente.
     *
     * @return una matriculacon un formato valido
     * @throws FormatoIncorrectoException si la matricula no tiene el formato
     * adecuado o es vacio
     */
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

    /**
     * Lee las plazas o PMA de la zona de introduccion correspondiente.
     *
     * @return un numero de plazas o PMA con un formato valido
     * @throws FormatoIncorrectoException si las plazas o el PMA no tienen el
     * formato adecuado o esta vacio
     */
    private double obtenerCaracteristica() throws FormatoIncorrectoException {
        try {
            double caract = Double.parseDouble(introCaract.getText());
            return caract;
        } catch (NumberFormatException e) {
            if (introTipo.getSelectedItem().equals("Coche") || introTipo.getSelectedItem().equals("Microbus")) {
                throw new FormatoIncorrectoException("Las plazas deben ser u numero entero.");
            } else {
                throw new FormatoIncorrectoException("El PMA debe ser un numero.");
            }
        }
    }
}
