package GUI;

import Estructuras.ColeccionAlquileres;
import Estructuras.ColeccionVehiculos;
import Estructuras.IteradorVehiculos;
import Excepciones.AlquilerVehiculoException;
import Excepciones.FormatoIncorrectoException;
import Excepciones.ObjetoNoExistenteException;
import Excepciones.ObjetoYaExistenteException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.TableColumn;

//TODO - Trabajamdo en aspecto
/**
 * Clase que contiene el menu de gestion de vehiculos
 *
 * @author Kevin
 */
public class PantallaVehiculos extends JPanel {

    private final ColeccionVehiculos vehiculos;     //Colecccion de vehiculos
    private final ColeccionAlquileres alquileres;   //Coleccion de alquileres (para saber si un vehiculo esta alquilado)

    private final IteradorVehiculos iterador;

    private JPanel parteIzq;            //Contendra la zona de datos y el menu de navegacion

    private JPanel zonaDatos;           //Contendra los campos de introduccion y visualizacion de datos

    private JLabel textoMatricula;
    private JTextField introMatricula;
    private JLabel textoTipo;
    private JComboBox introTipo;
    private JLabel textoCaract;
    private JSpinner introCaract;

    private JPanel zonaNav;             //Contendra los botones de navegacion

    private JButton botonPrimero;
    private JButton botonAnterior;
    private JButton botonSiguiente;
    private JButton botonUltimo;

    private JPanel parteDer;            //Contentra los botones de las opciones disponibles

    private JButton botonAnadir;
    private JButton botonEditar;
    private JButton botonBorrar;
    private JButton botonListar;

    private JButton botonAceptar;
    private JButton botonDescartar;

    /**
     * Crea e inicializa el menu de vehiculos.
     *
     * @param vehiculos la coleccion de vehiculos
     * @param alquileres la coleccion de alquileres
     */
    public PantallaVehiculos(ColeccionVehiculos vehiculos, ColeccionAlquileres alquileres) {
        super();
        this.vehiculos = vehiculos;
        this.alquileres = alquileres;
        iterador = vehiculos.getIterador();
        creaElementos();
        iniciar();
    }

    /**
     * Inicializa todos los elementos necesarios para poder operar.
     */
    private void creaElementos() {
        textoMatricula = new JLabel("Matricula");
        introMatricula = new JTextField(10);
        introMatricula.setEnabled(false);

        textoTipo = new JLabel("Tipo");
        introTipo = new JComboBox(new String[]{"Coche", "Microbus", "Furgoneta", "Camion"});
        introTipo.setEnabled(false);

        textoCaract = new JLabel("Plazas");
        introCaract = new JSpinner();
        introCaract.setEnabled(false);
    }

    /**
     * Inicia el menu de gestion de vehiculos, creando y colocando todos sus
     * elementos. Empieza en el apartado de alta de vehiculos.
     */
    private void iniciar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //Parte izquierda de la pantalla
        parteIzq = new JPanel();
        //parteIzq.setLayout(new BoxLayout(parteIzq, BoxLayout.Y_AXIS));
        parteIzq.setLayout(new GridBagLayout());
        GridBagConstraints ci = new GridBagConstraints();

        ci.weightx = 0.2;
        ci.weighty = 0.2;
        ci.gridx = 0;
        ci.gridy = 0;
        parteIzq.add(new JLabel("<HTML><H3>GESTION DE VEHICULOS</H3></HTML>"), ci);

        //Parte de la ventana en la que se mostraran y modificaran los datos
        zonaDatos = new JPanel();
        zonaDatos.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Inicia los datos con los valores del primer elemento de la coleccion
        try {
            introMatricula.setEnabled(false);
            introTipo.setEnabled(false);
            introTipo.addActionListener(new selectorTipoListener());
            introCaract.setEnabled(false);

            seleccionActual();
        } catch (NullPointerException e) {
            seleccionVacia();
        }

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        zonaDatos.add(textoMatricula, c);
        c.gridx = 1;
        zonaDatos.add(introMatricula, c);
        c.gridx = 0;
        c.gridy = 1;
        zonaDatos.add(textoTipo, c);
        c.gridx = 1;
        zonaDatos.add(introTipo, c);
        c.gridx = 0;
        c.gridy = 2;
        zonaDatos.add(textoCaract, c);
        c.gridx = 1;
        zonaDatos.add(introCaract, c);

        ci.weightx = 0.8;
        ci.weighty = 0.8;
        ci.gridy = 1;
        ci.gridheight = 3;
        ci.fill = GridBagConstraints.BOTH;
        parteIzq.add(zonaDatos, ci);

        //Parte de la ventana con los botones de navegacion
        zonaNav = new JPanel();
        zonaNav.setLayout(new GridBagLayout());

        botonPrimero = new JButton("|<");
        botonAnterior = new JButton("<");
        botonSiguiente = new JButton(">");
        botonUltimo = new JButton(">|");

        botonPrimero.setEnabled(false);
        botonAnterior.setEnabled(false);
        if (iterador.getActual() == null || !iterador.tieneSiguiente()) {
            botonSiguiente.setEnabled(false);
            botonUltimo.setEnabled(false);
        }

        botonPrimero.setActionCommand("Primero");
        botonAnterior.setActionCommand("Anterior");
        botonSiguiente.setActionCommand("Siguiente");
        botonUltimo.setActionCommand("Ultimo");

        botonPrimero.addActionListener(new BotonNavegacionListener());
        botonAnterior.addActionListener(new BotonNavegacionListener());
        botonSiguiente.addActionListener(new BotonNavegacionListener());
        botonUltimo.addActionListener(new BotonNavegacionListener());

        c.gridx = 0;
        c.gridy = 0;
        zonaNav.add(botonPrimero, c);
        c.gridx = 1;
        zonaNav.add(botonAnterior, c);
        c.gridx = 2;
        zonaNav.add(botonSiguiente, c);
        c.gridx = 3;
        zonaNav.add(botonUltimo, c);

        ci.weightx = 0.5;
        ci.weighty = 0.5;
        ci.gridy = 4;
        ci.fill = GridBagConstraints.HORIZONTAL;
        parteIzq.add(zonaNav, ci);

        add(parteIzq);

        //Parte de la pantalla con los botones de edicion
        parteDer = new JPanel();
        parteDer.setLayout(new GridBagLayout());

        botonAnadir = new JButton("+");
        botonEditar = new JButton("E");
        botonBorrar = new JButton("-");
        botonListar = new JButton("Lista");

        botonAnadir.addActionListener(new BotonAnadirListerner());
        botonEditar.addActionListener(new BotonEditarListener());
        botonBorrar.addActionListener(new BotonBorrarListener());
        botonListar.addActionListener(new BotonListarListener());

        botonAceptar = new JButton("\u2713");
        botonDescartar = new JButton("\u2717");
        botonAceptar.setEnabled(false);
        botonDescartar.setEnabled(false);

        botonAceptar.addActionListener(new BotonAceptarListener());
        botonDescartar.addActionListener(new BotonDescartarListener());

        c.gridx = 0;
        c.gridy = 0;
        parteDer.add(botonAnadir, c);
        c.gridy = 1;
        parteDer.add(botonEditar, c);
        c.gridy = 2;
        parteDer.add(botonBorrar, c);
        c.gridx = 1;
        c.gridy = 0;
        parteDer.add(botonAceptar, c);
        c.gridy = 1;
        parteDer.add(botonDescartar, c);

        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        parteDer.add(botonListar, c);

        add(parteDer);

        setVisible(true);
    }

    /**
     * Manejador de eventos del selector del tipo de vehiculo.
     */
    private class selectorTipoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (introTipo.getSelectedIndex()) {
                case GUI.COCHE:
                    textoCaract.setText("Plazas");
                    introCaract.setModel(new SpinnerNumberModel(2, 2, vehiculos.getPlazasMaxCoche(), 1));
                    break;
                case GUI.MICROBUS:
                    textoCaract.setText("Plazas");
                    introCaract.setModel(new SpinnerNumberModel(5, 5, vehiculos.getPlazasMaxMicrobus(), 1));
                    break;
                case GUI.FURGONETA:
                    textoCaract.setText("PMA");
                    introCaract.setModel(new SpinnerNumberModel(500.0, 500.0, vehiculos.getPMAMaxFurgoneta(), 10));
                    break;
                case GUI.CAMION:
                    textoCaract.setText("PMA");
                    introCaract.setModel(new SpinnerNumberModel(500.0, 500.0, vehiculos.getPMAMaxCamion(), 10));
                    break;
            }
        }
    }

    /**
     * Manejador de eventos de los botones de navegacion del menu de gestion de
     * vehiculos.
     */
    private class BotonNavegacionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Primero":
                    iterador.primero();
                    botonPrimero.setEnabled(false);
                    botonAnterior.setEnabled(false);
                    if (!botonSiguiente.isEnabled()) {
                        botonSiguiente.setEnabled(true);
                        botonUltimo.setEnabled(true);
                    }
                    break;
                case "Anterior":
                    iterador.anterior();
                    if (!iterador.tieneAnterior()) {
                        botonPrimero.setEnabled(false);
                        botonAnterior.setEnabled(false);
                    }
                    if (iterador.tieneSiguiente() && !botonSiguiente.isEnabled()) {
                        botonSiguiente.setEnabled(true);
                        botonUltimo.setEnabled(true);
                    }
                    break;
                case "Siguiente":
                    iterador.siguiente();
                    if (!iterador.tieneSiguiente()) {
                        botonSiguiente.setEnabled(false);
                        botonUltimo.setEnabled(false);
                    }
                    if (iterador.tieneAnterior() && !botonAnterior.isEnabled()) {
                        botonAnterior.setEnabled(true);
                        botonPrimero.setEnabled(true);
                    }
                    break;
                case "Ultimo":
                    iterador.ultimo();
                    botonUltimo.setEnabled(false);
                    botonSiguiente.setEnabled(false);
                    if (!botonAnterior.isEnabled()) {
                        botonAnterior.setEnabled(true);
                        botonPrimero.setEnabled(true);
                    }
                    break;
            }
            try {
                seleccionActual();
            } catch (NullPointerException ex) {
                botonPrimero.setEnabled(false);
                botonAnterior.setEnabled(false);
                botonSiguiente.setEnabled(false);
                botonUltimo.setEnabled(false);
            }
        }

    }

    /**
     * Manejador de eventos del boton de alta de vehiculos. Asegura que los
     * datos sean validos al clicar, indicando los posibles errores o exito de
     * la accion.
     */
    private class BotonAnadirListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            iterador.ultimo();

            introMatricula.setText("");
            introMatricula.requestFocus();
            introTipo.setSelectedIndex(GUI.COCHE);
            introCaract.setValue(((SpinnerNumberModel) introCaract.getModel()).getMinimum());

            introMatricula.setEnabled(true);
            introTipo.setEnabled(true);
            introCaract.setEnabled(true);
            botonAceptar.setEnabled(true);
            botonDescartar.setEnabled(true);
            botonAceptar.setActionCommand("Alta");
            botonDescartar.setActionCommand("Alta");

            botonAnadir.setEnabled(false);
            botonEditar.setEnabled(false);
            botonBorrar.setEnabled(false);
        }
    }

    /**
     * Manejador de eventos del boton de modificacion de vehiculos. Asegura que
     * los datos sean validos al clicar, indicando los posibles errores o exito
     * de la accion.
     */
    private class BotonEditarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            introMatricula.requestFocus();

            introMatricula.setEnabled(true);
            introTipo.setEnabled(true);
            introCaract.setEnabled(true);
            botonAceptar.setEnabled(true);
            botonDescartar.setEnabled(true);
            botonAceptar.setActionCommand("Modificacion");
            botonDescartar.setActionCommand("Modificacion");

            botonAnadir.setEnabled(false);
            botonEditar.setEnabled(false);
            botonBorrar.setEnabled(false);
        }
    }

    /**
     * Manejador de eventos del boton de borrado de vehiculos. Asegura que los
     * datos sean validos al clicar, indicando los posibles errores o exito de
     * la accion.
     */
    private class BotonBorrarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String matricula = obtenerMatricula();
                boolean eliminar = true;
                if (vehiculos.obtenerVechiculo(matricula).isAlquilado()) {
                    //Informar de que esta alquilado y confirmar eliminacion
                    int resp = JOptionPane.showConfirmDialog(PantallaVehiculos.this, "El vehiculo que quiere eliminar esta alquilado.\n"
                            + "¿Seguro que desea eliminarlo (se cancelará el alquiler sin agregarse al historial)?",
                            "Vehiculo alquilado", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        alquileres.eliminarAlquilerPorMatricula(matricula);
                    } else {
                        eliminar = false;
                        JOptionPane.showMessageDialog(PantallaVehiculos.this, "No se eliminara el vehiculo.",
                                "Vehiculo no eliminado", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (eliminar) {
                    vehiculos.eliminarVehiculo(matricula);
                    JOptionPane.showMessageDialog(PantallaVehiculos.this, "Vehiculo con matricula " + matricula + " eliminado.",
                            "Vehiculo eliminado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(PantallaVehiculos.this, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(PantallaVehiculos.this, ex.getMessage(), "No existe el vehiculo", JOptionPane.ERROR_MESSAGE);
            } catch (AlquilerVehiculoException ex) {
                JOptionPane.showMessageDialog(PantallaVehiculos.this, ex.getMessage(), "Fallo al eliminar el alquiler", JOptionPane.ERROR_MESSAGE);
            } finally {
                iterador.anterior();
                botonPrimero.setEnabled(iterador.tieneAnterior());
                botonAnterior.setEnabled(iterador.tieneAnterior());
                botonUltimo.setEnabled(iterador.tieneSiguiente());
                botonSiguiente.setEnabled(iterador.tieneSiguiente());
                if (iterador.getActual() == null) {
                    seleccionVacia();
                } else {
                    seleccionActual();
                }
            }
        }
    }

    /**
     * Manejador de deventos del boton de listado de vehiculos. Muestra una
     * ventana con un listado de todos los vehiculos existentetes.
     */
    private class BotonListarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog ventanaLista = new JDialog((JFrame) PantallaVehiculos.this.getParent().getParent().getParent().getParent().getParent(), "Listado de vehiculos", true);
            ventanaLista.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JTable listado = new JTable(vehiculos.obtenerDataArray(), new String[]{"Matricula", "Tipo", "Plazas\\PMA"});
            TableColumn columnaTipo = listado.getColumnModel().getColumn(1);
            columnaTipo.setCellEditor(new DefaultCellEditor(introTipo));

            listado.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    JTable tabla = (JTable) me.getSource();
                    Point p = me.getPoint();
                    int row = tabla.rowAtPoint(p);
                    if (me.getClickCount() == 2) {
                        //TODO - Ir al elemento como modificacion
                        iterador.seleccionar((String) tabla.getValueAt(row, 0));
                        seleccionActual();
                        //TODO - alt - modificar datos desde tabla
                        //http://stackoverflow.com/questions/11684903/how-to-add-a-drop-down-menu-to-a-jtable-cell
                        //http://stackoverflow.com/questions/15555183/jtable-update-selected-row
                    }
                }
            });
            JScrollPane listadoVehiculos = new JScrollPane(listado);
            listado.setFillsViewportHeight(true);
            listado.setEnabled(false);

            ventanaLista.add(listadoVehiculos);

            ventanaLista.pack();
            ventanaLista.setVisible(true);
        }

    }

    /**
     * Manejador de eventos del boton de aceptar. Añadra un vehiculo a la
     * coleccion o modificara uno existente.
     */
    private class BotonAceptarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Alta":
                    try {
                        //Obtiene la informacion del vehiculo
                        String tipo = (String) introTipo.getSelectedItem();
                        String matricula = obtenerMatricula();
                        double caract = obtenerCaracteristica();
                        vehiculos.anyadirVehiculo(tipo, matricula, caract);
                        String info = tipo.equals("Coche") || tipo.equals("Microbus")
                                ? " con " + (int) caract + " plazas" : " con PMA " + caract;
                        JOptionPane.showMessageDialog(zonaDatos, tipo + info + " anyadido", "Vehiculo anyadido", JOptionPane.INFORMATION_MESSAGE);
                    } catch (FormatoIncorrectoException ex) {
                        JOptionPane.showMessageDialog(PantallaVehiculos.this, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
                    } catch (ObjetoYaExistenteException ex) {
                        JOptionPane.showMessageDialog(PantallaVehiculos.this, ex.getMessage(), "Ya existe el vehiculo", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        iterador.ultimo();
                        seleccionActual();
                        introMatricula.setEnabled(false);
                        introTipo.setEnabled(false);
                        introCaract.setEnabled(false);
                        botonUltimo.setEnabled(false);
                        botonSiguiente.setEnabled(false);
                        botonPrimero.setEnabled(iterador.tieneAnterior());
                        botonAnterior.setEnabled(iterador.tieneAnterior());
                        botonAceptar.setEnabled(false);
                        botonDescartar.setEnabled(false);

                        botonAnadir.setEnabled(true);
                        botonEditar.setEnabled(true);
                        botonBorrar.setEnabled(true);
                    }
                    break;
                case "Modificacion":
                    try {
                        //Obtiene la informacion del vehiculo
                        String tipo = (String) introTipo.getSelectedItem();
                        String matricula = obtenerMatricula();
                        double caract = obtenerCaracteristica();
                        vehiculos.modificarVehiculo(tipo, matricula, caract);
                        JOptionPane.showMessageDialog(PantallaVehiculos.this, "Vehiculo modificado correctamente", "Vehiculo modificado", JOptionPane.INFORMATION_MESSAGE);
                    } catch (FormatoIncorrectoException ex) {
                        JOptionPane.showMessageDialog(PantallaVehiculos.this, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
                    } catch (ObjetoNoExistenteException ex) {
                        JOptionPane.showMessageDialog(PantallaVehiculos.this, ex.getMessage(), "Ya existe el vehiculo", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        seleccionActual();
                        introMatricula.setEnabled(false);
                        introTipo.setEnabled(false);
                        introCaract.setEnabled(false);
                        botonUltimo.setEnabled(iterador.tieneSiguiente());
                        botonSiguiente.setEnabled(iterador.tieneSiguiente());
                        botonPrimero.setEnabled(iterador.tieneAnterior());
                        botonAnterior.setEnabled(iterador.tieneAnterior());
                        botonAceptar.setEnabled(false);
                        botonDescartar.setEnabled(false);

                        botonAnadir.setEnabled(true);
                        botonEditar.setEnabled(true);
                        botonBorrar.setEnabled(true);
                    }
                    break;
            }
        }

    }

    /**
     * Manejador de eventos del boton de descartar cambios.
     */
    private class BotonDescartarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Alta":
                    seleccionActual();
                    introMatricula.setEnabled(false);
                    introTipo.setEnabled(false);
                    introCaract.setEnabled(false);
                    botonUltimo.setEnabled(iterador.tieneSiguiente());
                    botonSiguiente.setEnabled(iterador.tieneSiguiente());
                    botonPrimero.setEnabled(iterador.tieneAnterior());
                    botonAnterior.setEnabled(iterador.tieneAnterior());
                    botonAceptar.setEnabled(false);
                    botonDescartar.setEnabled(false);

                    botonAnadir.setEnabled(true);
                    botonEditar.setEnabled(true);
                    botonBorrar.setEnabled(true);
                    break;
                case "Modificacion":
                    seleccionActual();
                    introMatricula.setEnabled(false);
                    introTipo.setEnabled(false);
                    introCaract.setEnabled(false);
                    botonAceptar.setEnabled(false);
                    botonDescartar.setEnabled(false);

                    botonAnadir.setEnabled(true);
                    botonEditar.setEnabled(true);
                    botonBorrar.setEnabled(true);
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
            double caract;
            if (introTipo.getSelectedIndex() == 0 || introTipo.getSelectedIndex() == 1) {
                caract = (Integer) introCaract.getValue();
            } else {
                caract = (Double) introCaract.getValue();
            }
            return caract;
        } catch (NumberFormatException e) {
            if (introTipo.getSelectedItem().equals("Coche") || introTipo.getSelectedItem().equals("Microbus")) {
                throw new FormatoIncorrectoException("Las plazas deben ser un numero entero.");
            } else {
                throw new FormatoIncorrectoException("El PMA debe ser un numero.");
            }
        }
    }

    /**
     * Deja los campos en un estado predeterminado.
     */
    private void seleccionVacia() {
        introMatricula.setText("");
        introTipo.setSelectedIndex(GUI.COCHE);
        introCaract.setValue(((SpinnerNumberModel) introCaract.getModel()).getMinimum());
    }

    /**
     * Llena los campos de datos con la informacion del vehiculo actual.
     */
    private void seleccionActual() {
        String matricula = iterador.getActual().getMatricula();
        introMatricula.setText(matricula);
        String tipo = iterador.getActual().getClass().getSimpleName();
        switch (tipo) {
            case "Coche":
                introTipo.setSelectedIndex(GUI.COCHE);
                break;
            case "Microbus":
                introTipo.setSelectedIndex(GUI.MICROBUS);
                break;
            case "Furgoneta":
                introTipo.setSelectedIndex(GUI.FURGONETA);
                break;
            case "Camion":
                introTipo.setSelectedIndex(GUI.CAMION);
                break;
        }
        switch (iterador.getActual().getClass().getSimpleName()) {
            case "Coche":
                textoCaract.setText("Plazas");
                introCaract.setModel(new SpinnerNumberModel(2, 2, vehiculos.getPlazasMaxCoche(), 1));
                introCaract.setValue((int) iterador.getActual().getCaracteristica());
                break;
            case "Microbus":
                textoCaract.setText("Plazas");
                introCaract.setModel(new SpinnerNumberModel(5, 5, vehiculos.getPlazasMaxMicrobus(), 1));
                introCaract.setValue((int) iterador.getActual().getCaracteristica());
                break;
            case "Furgoneta":
                textoCaract.setText("PMA");
                introCaract.setModel(new SpinnerNumberModel(500.0, 500.0, vehiculos.getPMAMaxFurgoneta(), 10));
                introCaract.setValue(iterador.getActual().getCaracteristica());
                break;
            case "Camion":
                textoCaract.setText("PMA");
                introCaract.setModel(new SpinnerNumberModel(500.0, 500.0, vehiculos.getPMAMaxCamion(), 10));
                introCaract.setValue(iterador.getActual().getCaracteristica());
                break;
        }
    }
}
