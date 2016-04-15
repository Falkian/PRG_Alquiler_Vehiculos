package GUI;

import Estructuras.ColeccionAlquileres;
import Estructuras.ColeccionClientes;
import Estructuras.IteradorClientes;
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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Clase que contiene el menu de gestion de vehiculos
 *
 * @author Kevin
 */
public class PantallaClientes extends JPanel {

    private final ColeccionClientes clientes;     //Colecccion de vehiculos
    private final ColeccionAlquileres alquileres;   //Coleccion de alquileres (para saber si un vehiculo esta alquilado)

    private final IteradorClientes iterador;

    private JPanel parteIzq;            //Contendra la zona de datos y el menu de navegacion

    private JPanel zonaDatos;           //Contendra los campos de introduccion y visualizacion de datos

    private JLabel textoDNI;
    private JTextField introDNI;
    private JLabel textoNombre;
    private JTextField introNombre;
    private JLabel textoDireccion;
    private JTextField introDireccion;
    private JLabel textoTelefono;
    private JTextField introTelefono;
    private JLabel textoVIP;
    private JCheckBox introVIP;

    private JDialog ventanaLista;

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
     * Crea e inicializa el menu de clientes.
     *
     * @param clientes coleccion de clientes
     * @param alquileres la coleccion de alquileres
     */
    public PantallaClientes(ColeccionClientes clientes, ColeccionAlquileres alquileres) {
        super();
        this.clientes = clientes;
        this.alquileres = alquileres;
        iterador = clientes.getIterador();
        creaElementos();
        iniciar();
    }

    /**
     * Inicializa todos los elementos necesarios para poder operar.
     */
    private void creaElementos() {
        textoDNI = new JLabel("DNI");
        introDNI = new JTextField(10);
        introDNI.setEnabled(false);

        textoNombre = new JLabel("Nombre");
        introNombre = new JTextField(10);
        introNombre.setEnabled(false);

        textoDireccion = new JLabel("Direccion");
        introDireccion = new JTextField(10);
        introDireccion.setEnabled(false);

        textoTelefono = new JLabel("Telefono");
        introTelefono = new JTextField(9);

        textoVIP = new JLabel("VIP");
        introVIP = new JCheckBox();

        botonPrimero = new JButton("|<");
        botonAnterior = new JButton("<");
        botonSiguiente = new JButton(">");
        botonUltimo = new JButton(">|");
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
        parteIzq.add(new JLabel("<HTML><H3>GESTION DE CLIENTES</H3></HTML>"), ci);

        //Parte de la ventana en la que se mostraran y modificaran los datos
        zonaDatos = new JPanel();
        zonaDatos.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Inicia los datos con los valores del primer elemento de la coleccion
        try {
            introDNI.setEnabled(false);
            introNombre.setEnabled(false);
            introDireccion.setEnabled(false);
            introTelefono.setEnabled(false);
            introVIP.setEnabled(false);

            seleccionActual();
        } catch (NullPointerException e) {
            seleccionVacia();
        }

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        zonaDatos.add(textoDNI, c);
        c.gridx = 1;
        zonaDatos.add(introDNI, c);
        c.gridx = 0;
        c.gridy = 1;
        zonaDatos.add(textoNombre, c);
        c.gridx = 1;
        zonaDatos.add(introNombre, c);
        c.gridx = 0;
        c.gridy = 2;
        zonaDatos.add(textoDireccion, c);
        c.gridx = 1;
        zonaDatos.add(introDireccion, c);
        c.gridx = 0;
        c.gridy = 3;
        zonaDatos.add(textoTelefono, c);
        c.gridx = 1;
        zonaDatos.add(introTelefono, c);
        c.gridx = 0;
        c.gridy = 4;
        zonaDatos.add(textoVIP, c);
        c.gridx = 1;
        zonaDatos.add(introVIP, c);

        ci.weightx = 0.8;
        ci.weighty = 0.8;
        ci.gridy = 1;
        ci.gridheight = 3;
        ci.fill = GridBagConstraints.BOTH;
        parteIzq.add(zonaDatos, ci);

        //Parte de la ventana con los botones de navegacion
        zonaNav = new JPanel();
        zonaNav.setLayout(new GridBagLayout());

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

            introDNI.setText("");
            introDNI.requestFocus();
            introNombre.setText("");
            introDireccion.setText("");
            introTelefono.setText("");
            introVIP.setSelected(false);

            introDNI.setEnabled(true);
            introNombre.setEnabled(true);
            introDireccion.setEnabled(true);
            introTelefono.setEnabled(true);
            introVIP.setEnabled(true);

            botonAceptar.setEnabled(true);
            botonDescartar.setEnabled(true);
            botonAceptar.setActionCommand("Alta");
            botonDescartar.setActionCommand("Alta");

            botonAnadir.setEnabled(false);
            botonEditar.setEnabled(false);
            botonBorrar.setEnabled(false);

            botonPrimero.setEnabled(false);
            botonAnterior.setEnabled(false);
            botonSiguiente.setEnabled(false);
            botonUltimo.setEnabled(false);
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
            introDNI.requestFocus();

            introNombre.setEnabled(true);
            introDireccion.setEnabled(true);
            introTelefono.setEnabled(true);
            introVIP.setEnabled(true);

            botonAceptar.setEnabled(true);
            botonDescartar.setEnabled(true);
            botonAceptar.setActionCommand("Modificacion");
            botonDescartar.setActionCommand("Modificacion");

            botonAnadir.setEnabled(false);
            botonEditar.setEnabled(false);
            botonBorrar.setEnabled(false);

            botonPrimero.setEnabled(false);
            botonAnterior.setEnabled(false);
            botonSiguiente.setEnabled(false);
            botonUltimo.setEnabled(false);
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
                //Obtiene la informacion del cliente
                String DNI = obtenerDNI();
                boolean eliminar = true;
                if (clientes.obtenerCliente(DNI).isAlquilado()) {
                    //Informar si tiene vehiculos alquilados y confirmar eliminacion
                    int resp = JOptionPane.showConfirmDialog(PantallaClientes.this, "El cliente que quieres leiminar tiene vehiculos alquilados.\n"
                            + "¿Seguro que desea eliminarlo (se cancelara el alquiler sin agregarse al historial)?",
                            "Cliente alquilado", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        alquileres.eliminarAlquilerPorDni(DNI);
                    } else {
                        eliminar = false;
                        JOptionPane.showMessageDialog(PantallaClientes.this, "No se eliminara el cliente",
                                "Cliente no eliminado", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (eliminar) {
                    //Elimina el cliente
                    clientes.eliminarCliente(DNI);
                    JOptionPane.showMessageDialog(PantallaClientes.this, "Cliente con DNI " + DNI + "eliminado.",
                            "Cliente eliminado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(PantallaClientes.this, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(PantallaClientes.this, ex.getMessage(), "No existe el vehiculo", JOptionPane.ERROR_MESSAGE);
            } catch (AlquilerVehiculoException ex) {
                JOptionPane.showMessageDialog(PantallaClientes.this, ex.getMessage(), "Fallo al eliminar el alquiler", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(PantallaClientes.this, "Fallo al eliminar el cliente de la base de datos.\n"
                        + "ErrorSQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            } finally {
                iterador.anterior();
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
            ventanaLista = new JDialog((JFrame) PantallaClientes.this.getParent().getParent().getParent().getParent().getParent(), "Listado de vehiculos", true);
            ventanaLista.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JTable listado = new JTable(clientes.obtenerDataArray(), new String[]{"DNI", "Nombre", "Direccion", "Telefono", "VIP"});
            TableColumn columnaTipo = listado.getColumnModel().getColumn(1);
            columnaTipo.setCellEditor(new DefaultCellEditor(introNombre));

            listado.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    JTable tabla = (JTable) me.getSource();
                    Point p = me.getPoint();
                    int row = tabla.rowAtPoint(p);
                    //Si se clica una vez ir al elemento
                    if (me.getClickCount() == 1) {
                        iterador.seleccionar((String) tabla.getValueAt(row, 0));
                        seleccionActual();
                    } //Si se clica dos veces editar el elemento
                    else if (me.getClickCount() == 2) {
                        iterador.seleccionar((String) tabla.getValueAt(row, 0));
                        botonEditar.doClick();
                        ventanaLista.dispose();
                    }
                }
            });
            JScrollPane listadoClientes = new JScrollPane(listado);
            listado.setFillsViewportHeight(true);
            listado.setEnabled(false);

            ventanaLista.add(listadoClientes);

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
                        //Obtiene la informacion del cliente
                        String dni = obtenerDNI();
                        String nombre = obtenerNombre();
                        String direccion = obtenerDireccion();
                        String telefono = obtenerTelefono();
                        boolean VIP = obtenerVIP();
                        //Añade el cliente al listado
                        clientes.anyadirCliente(dni, nombre, direccion, telefono, VIP);
                        JOptionPane.showMessageDialog(PantallaClientes.this, "Cliente anyadido con los siguientes datos:\n"
                                + "DNI: " + dni + "\nNombre: " + nombre + "\nDireccion: " + direccion + "\n"
                                + "Telefono: " + telefono + "\nVIP: " + (VIP ? "\u2713" : "\u2717"),
                                "Cliente anyadido", JOptionPane.INFORMATION_MESSAGE);
                    } catch (FormatoIncorrectoException ex) {
                        JOptionPane.showMessageDialog(PantallaClientes.this, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
                    } catch (ObjetoYaExistenteException ex) {
                        JOptionPane.showMessageDialog(PantallaClientes.this, ex.getMessage(), "Ya existe el cliente", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Fallo al actualizar el cliente en la base de datos.\n"
                                + "ErrorSQL: " + ex.getMessage(), "Error MySQL", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        iterador.ultimo();
                        seleccionActual();

                        introDNI.setEnabled(false);
                        introNombre.setEnabled(false);
                        introDireccion.setEnabled(false);
                        introTelefono.setEnabled(false);
                        introVIP.setEnabled(false);

                        botonAceptar.setEnabled(false);
                        botonDescartar.setEnabled(false);

                        botonAnadir.setEnabled(true);
                        botonEditar.setEnabled(true);
                        botonBorrar.setEnabled(true);
                    }
                    break;
                case "Modificacion":
                    try {
                        //Obtiene la informacion del cliente
                        String DNI = obtenerDNI();
                        String nombre = obtenerNombre();
                        String direccion = obtenerDireccion();
                        String telefono = obtenerTelefono();
                        boolean VIP = obtenerVIP();
                        //Modifica el cliente
                        clientes.modificarCliente(DNI, nombre, direccion, telefono, VIP);
                        JOptionPane.showMessageDialog(PantallaClientes.this, "Modificado el cliente " + DNI + " con los siguientes datos:\n"
                                + "\nNombre: " + nombre + "\nDireccion: " + direccion + "\n"
                                + "Telefono: " + telefono + "\nVIP: " + (VIP ? "\u2713" : "\u2717"),
                                "Cliente modificado", JOptionPane.INFORMATION_MESSAGE);
                    } catch (FormatoIncorrectoException ex) {
                        JOptionPane.showMessageDialog(PantallaClientes.this, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
                    } catch (ObjetoNoExistenteException ex) {
                        JOptionPane.showMessageDialog(PantallaClientes.this, ex.getMessage(), "No existe el cliente", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(PantallaClientes.this, "Fallo al actualizar la informacion en la base de datos.\n"
                                + "Error SQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        seleccionActual();

                        introDNI.setEnabled(false);
                        introNombre.setEnabled(false);
                        introDireccion.setEnabled(false);
                        introTelefono.setEnabled(false);
                        introVIP.setEnabled(false);

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
            seleccionActual();
            introDNI.setEnabled(false);
            introNombre.setEnabled(false);
            introDireccion.setEnabled(false);
            introTelefono.setEnabled(false);
            introVIP.setEnabled(false);

            botonAceptar.setEnabled(false);
            botonDescartar.setEnabled(false);

            botonAnadir.setEnabled(true);
            botonEditar.setEnabled(true);
            botonBorrar.setEnabled(true);

            botonPrimero.setEnabled(true);
            botonAnterior.setEnabled(true);
            botonSiguiente.setEnabled(true);
            botonUltimo.setEnabled(true);
        }

    }

    /**
     * Lee el DNI de la zona de introduccion correspondiente.
     *
     * @return un DNI con un formato valido
     * @throws FormatoIncorrectoException si el DNI no tiene el formato adecuado
     * o es vacio
     */
    private String obtenerDNI() throws FormatoIncorrectoException {
        String DNI = introDNI.getText().toUpperCase();
        if (DNI.matches("\\d{8}[a-zA-Z]")) {
            return DNI;
        } else if (DNI.equals("")) {
            throw new FormatoIncorrectoException("Debes introducir el DNI.");
        } else {
            throw new FormatoIncorrectoException("El DNI debe tener 8 numeros seguidos de una letra.");
        }
    }

    /**
     * Lee el nombre de la zona de introduccion correspondiente.
     *
     * @return un nombre con un formato valido
     * @throws FormatoIncorrectoException si el nombre no es correcto o es vacio
     */
    private String obtenerNombre() throws FormatoIncorrectoException {
        String nombre = introNombre.getText();
        //if (nombre.matches("\\D")) {
        //  return nombre;            
        //} else 
        if (nombre.equals("")) {
            throw new FormatoIncorrectoException("Debes introducir el nombre.");
        } else {
            //  throw new FormatoIncorrectoException(nombre + " no es un nombre valido.");
            return nombre;
        }
    }

    /**
     * Lee la direccion de la zona de introduccion correspondiente.
     *
     * @return una direccion no vacia
     * @throws FormatoIncorrectoException si la direccion esta vacia
     */
    private String obtenerDireccion() throws FormatoIncorrectoException {
        String direccion = introDireccion.getText();
        if (direccion.equals("")) {
            throw new FormatoIncorrectoException("Debes introducir la direccion.");
        } else {
            return direccion;
        }
    }

    /**
     * Lee el telefono de la zona de introduccion correspondiente,
     *
     * @return un telefono valido
     * @throws FormatoIncorrectoException si el telefono no tiene un formato
     * valido o es vacio
     */
    private String obtenerTelefono() throws FormatoIncorrectoException {
        String tlf = introTelefono.getText();
        if (tlf.equals("")) {
            throw new FormatoIncorrectoException("Debes introducir el telefono.");
        } else if (!tlf.matches("(\\(?\\+\\d{2,3}\\)?)?\\d{9}")) {
            throw new FormatoIncorrectoException("Telefono no valido.");
        } else {
            return tlf;
        }
    }

    /**
     * Lee si el cliente es VIP de la zona de introduccion correspondiente
     *
     * @return true si el cliente es VIP; false en caso contrario
     */
    private boolean obtenerVIP() {
        return introVIP.isSelected();
    }

    /**
     * Deja los campos en un estado predeterminado.
     */
    private void seleccionVacia() {
        introDNI.setText("");
        introNombre.setText("");
        introDireccion.setText("");
        introTelefono.setText("");
        introVIP.setSelected(false);
    }

    /**
     * Llena los campos de datos con la informacion del vehiculo actual.
     */
    private void seleccionActual() {
        String dni = iterador.getActual().getDni();
        introDNI.setText(dni);
        String nombre = iterador.getActual().getNombre();
        introNombre.setText(nombre);
        String direccion = iterador.getActual().getDireccion();
        introDireccion.setText(direccion);
        String telefono = iterador.getActual().getTlf();
        introTelefono.setText(telefono);
        introVIP.setSelected(iterador.getActual().isVip());

        //Deja los botones en un estado correcto
        botonUltimo.setEnabled(iterador.tieneSiguiente());
        botonSiguiente.setEnabled(iterador.tieneSiguiente());
        botonPrimero.setEnabled(iterador.tieneAnterior());
        botonAnterior.setEnabled(iterador.tieneAnterior());
    }
}
