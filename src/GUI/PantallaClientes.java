package GUI;

import Clases.Cliente;
import Estructuras.ColeccionAlquileres;
import Estructuras.ColeccionClientes;
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
 * Clase que contiene el menu de gestion de clientes
 *
 * @author Kevin
 */
public class PantallaClientes extends JSplitPane {

    private final ColeccionClientes clientes;       //Coleccion de clientes
    private final ColeccionAlquileres alquileres;   //Coleccion de alquileres (para saber si un cliente tiene vehiculos alquilados)

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

    /**
     * Crea e inicializa el menu de clientes.
     *
     * @param clientes la coleccion de clientes
     * @param alquileres la coleccion de alquileres
     */
    public PantallaClientes(ColeccionClientes clientes, ColeccionAlquileres alquileres) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.clientes = clientes;
        this.alquileres = alquileres;
        iniciar();
    }

    /**
     * Inicia el menu de gestion de clientes, creando y colocando todos sus
     * elementos. Empieza en el apartado de alta de clientes.
     */
    private void iniciar() {
        JPanel menuClientes = new JPanel();        //Menu izquierdo
        menuClientes.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Botones de la parte izquierda del menu de gestion de vehiculos
        JButton botonAnadir = new JButton("Alta");
        JButton botonModificacion = new JButton("Modificacion");
        JButton botonBorrado = new JButton("Borrado");
        JButton botonListado = new JButton("Listado");

        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        int yin = menuClientes.getHeight() / 5 - botonAnadir.getHeight() / 2;
        int xin = menuClientes.getWidth() / 2 - botonAnadir.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuClientes.add(botonAnadir, c);

        c.gridy = 1;
        yin = menuClientes.getHeight() / 5 - botonModificacion.getHeight() / 2;
        xin = menuClientes.getWidth() / 2 - botonModificacion.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuClientes.add(botonModificacion, c);

        c.gridy = 2;
        yin = menuClientes.getHeight() / 5 - botonBorrado.getHeight() / 2;
        xin = menuClientes.getWidth() / 2 - botonBorrado.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuClientes.add(botonBorrado, c);

        c.gridy = 3;
        yin = menuClientes.getHeight() / 5 - botonListado.getHeight() / 2;
        xin = menuClientes.getWidth() / 2 - botonListado.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuClientes.add(botonListado, c);

        botonAnadir.addActionListener(new botonesMenuListener());
        botonModificacion.addActionListener(new botonesMenuListener());
        botonBorrado.addActionListener(new botonesMenuListener());
        botonListado.addActionListener(new botonesMenuListener());

        botonAnadir.setActionCommand("Anadir");
        botonModificacion.setActionCommand("Modificar");
        botonBorrado.setActionCommand("Borrar");
        botonListado.setActionCommand("Lista");

        setLeftComponent(menuClientes);

        //Inicia el menu de gestion de vehiculos en el apartado de alta
        inicioPantallaClientesAlta();
    }

    /**
     * Crea y coloca los elementos del apartado de alta de clientes.
     */
    private void inicioPantallaClientesAlta() {
        JPanel altaClientes = new JPanel();
        altaClientes.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U><B>ALTA DE CLIENTES</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        altaClientes.add(titulo, c);

        //Zona de introduccion de datos
        JPanel contenido = new JPanel();
        contenido.setLayout(new GridBagLayout());
        GridBagConstraints ci = new GridBagConstraints();

        textoDNI = new JLabel("DNI");
        introDNI = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 0;
        ci.ipadx = 20;
        ci.weightx = 0.5;
        ci.weighty = 0.5;
        contenido.add(textoDNI, ci);
        ci.gridx = 1;
        contenido.add(introDNI, ci);

        textoNombre = new JLabel("Nombre");
        introNombre = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 1;
        contenido.add(textoNombre, ci);
        ci.gridx = 1;
        contenido.add(introNombre, ci);

        textoDireccion = new JLabel("Direccion");
        introDireccion = new JTextField(10);
        ci.gridx = 0;
        ci.gridy = 2;
        contenido.add(textoDireccion, ci);
        ci.gridx = 1;
        contenido.add(introDireccion, ci);

        textoTelefono = new JLabel("Telefono");
        introTelefono = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 3;
        contenido.add(textoTelefono, ci);
        ci.gridx = 1;
        contenido.add(introTelefono, ci);

        textoVIP = new JLabel("VIP");
        introVIP = new JCheckBox();
        ci.gridx = 0;
        ci.gridy = 4;
        contenido.add(textoVIP, ci);
        ci.gridx = 1;
        contenido.add(introVIP, ci);

        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        altaClientes.add(contenido, c);

        //Boton de alta
        JButton botonAlta = new JButton("Alta");
        botonAlta.addActionListener(new botonAltaListener());

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        altaClientes.add(botonAlta, c);

        setRightComponent(altaClientes);
    }

    /**
     * Crea y coloca los elementos del apartado de modificacion de clientes.
     */
    private void inicioPantallaClientesModificacion() {
        JPanel modificacionClientes = new JPanel();
        modificacionClientes.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U></B>MODIFICACION DE CLIENTES</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        modificacionClientes.add(titulo, c);

        //Zona de introduccion de datos
        JPanel contenido = new JPanel();
        contenido.setLayout(new GridBagLayout());
        GridBagConstraints ci = new GridBagConstraints();

        textoDNI = new JLabel("DNI");
        introDNI = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 0;
        ci.ipadx = 20;
        ci.weightx = 0.5;
        ci.weighty = 0.5;
        contenido.add(textoDNI, ci);
        ci.gridx = 1;
        contenido.add(introDNI, ci);

        textoNombre = new JLabel("Nombre");
        introNombre = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 1;
        contenido.add(textoNombre, ci);
        ci.gridx = 1;
        contenido.add(introNombre, ci);

        textoDireccion = new JLabel("Direccion");
        introDireccion = new JTextField(10);
        ci.gridx = 0;
        ci.gridy = 2;
        contenido.add(textoDireccion, ci);
        ci.gridx = 1;
        contenido.add(introDireccion, ci);

        textoTelefono = new JLabel("Telefono");
        introTelefono = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 3;
        contenido.add(textoTelefono, ci);
        ci.gridx = 1;
        contenido.add(introTelefono, ci);

        textoVIP = new JLabel("VIP");
        introVIP = new JCheckBox();
        ci.gridx = 0;
        ci.gridy = 4;
        contenido.add(textoVIP, ci);
        ci.gridx = 1;
        contenido.add(introVIP, ci);

        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        modificacionClientes.add(contenido, c);

        //Boton de modificacion
        JButton botonModificacion = new JButton("Modificar");
        botonModificacion.addActionListener(new botonModificarListener());

        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        modificacionClientes.add(botonModificacion, c);

        setRightComponent(modificacionClientes);
    }

    /**
     * Crea y coloca los elementos del apartado de borrado de clientes.
     */
    private void inicioPantallaClientesBorrado() {
        JPanel borradoClientes = new JPanel();
        borradoClientes.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U><B>BORRADO DE VEHICULOS</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        borradoClientes.add(titulo, c);

        //Zona de introduccion de datos
        JPanel contenido = new JPanel();
        contenido.setLayout(new GridBagLayout());
        GridBagConstraints ci = new GridBagConstraints();

        textoDNI = new JLabel("DNI");
        introDNI = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 0;
        ci.ipadx = 20;
        ci.weightx = 0.5;
        ci.weighty = 0.5;
        contenido.add(textoDNI, ci);
        ci.gridx = 1;
        contenido.add(introDNI, ci);

        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        borradoClientes.add(contenido, c);

        //Boton de borrado
        JButton botonBorrado = new JButton("Eliminar");
        botonBorrado.addActionListener(new botonBorrarListener());

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        borradoClientes.add(botonBorrado, c);

        setRightComponent(borradoClientes);
    }

    /**
     * Crea y coloca los elementos del apartado de listado de clientes.
     */
    private void inicioPantallaClientesListado() {
        JTable listado = new JTable(clientes.obtenerDataArray(), new String[]{"DNI", "Nombre", "Direccion", "Telefono", "VIP"});

        JScrollPane listadoVehiculos = new JScrollPane(listado);
        listado.setFillsViewportHeight(true);

        setRightComponent(listadoVehiculos);
    }

    /**
     * Manejador de eventos de los botones de navegacion del menu de gestion de
     * clientes.
     */
    private class botonesMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Anadir":
                    inicioPantallaClientesAlta();
                    break;
                case "Modificar":
                    inicioPantallaClientesModificacion();
                    break;
                case "Borrar":
                    inicioPantallaClientesBorrado();
                    break;
                case "Lista":
                    inicioPantallaClientesListado();
                    break;
            }
        }

    }

    /**
     * Manejador de eventos del boton de alta de clientes. Asegura que los datos
     * sean validos al clicar, indicando los posibles errores o exito de la
     * accion.
     */
    private class botonAltaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //Obtiene la informacion del cliente
                String dni = obtenerDNI();
                String nombre = obtenerNombre();
                String direccion = obtenerDireccion();
                String telefono = obtenerTelefono();
                boolean VIP = obtenerVIP();
                Cliente c = new Cliente(dni, nombre, direccion, telefono);
                c.setVip(VIP);
                //Añade el cliente al listado
                clientes.anyadirCliente(c);
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoYaExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Ya existe el cliente", JOptionPane.ERROR_MESSAGE);
            } finally {
                introDNI.setText("");
                introNombre.setText("");
                introDireccion.setText("");
                introTelefono.setText("");
                introVIP.setSelected(false);
            }
        }
    }

    /**
     * Manejador de eventos del boton de modificacion de clientes. Asegura que
     * los datos sean validos al clicar, indicando los posibles errores o exito
     * de la accion.
     */
    private class botonModificarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //Obtiene la informacion del cliente
                String DNI = obtenerDNI();
                String nombre = obtenerNombre();
                String direccion = obtenerDireccion();
                String telefono = obtenerTelefono();
                boolean VIP = obtenerVIP();
                Cliente c = new Cliente(DNI, nombre, direccion, telefono);
                c.setVip(VIP);
                //Modifica el cliente
                clientes.modificarCliente(DNI, c);
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "No existe el cliente", JOptionPane.ERROR_MESSAGE);
            } finally {
                introDNI.setText("");
                introNombre.setText("");
                introDireccion.setText("");
                introTelefono.setText("");
                introVIP.setSelected(false);
            }
        }
    }

    /**
     * Manejador de eventos del boton de borrado de clientes. Asegura que los
     * datos sean validos al clicar, indicando los posibles errores o exito de
     * la accion.
     */
    private class botonBorrarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //Obtiene la informacion del cliente
                String DNI = obtenerDNI();
                boolean eliminar = true;
                if (clientes.obtenerCliente(DNI).isAlquilado()) {
                    //Informar si tiene vehiculos alquilados y confirmar eliminacion
                    int resp = JOptionPane.showConfirmDialog(rightComponent, "El cliente que quieres leiminar tiene vehiculos alquilados.\n"
                            + "¿Seguro que desea eliminarlo (se cancelara el alquiler sin agregarse al historial)?",
                            "Cliente alquilado", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        alquileres.eliminarAlquilerPorDni(DNI);
                    } else {
                        eliminar = false;
                        JOptionPane.showMessageDialog(rightComponent, "No se eliminara el cliente",
                                "Cliente no eliminado", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (eliminar) {
                    //Elimina el cliente
                    clientes.eliminarCliente(DNI);
                    JOptionPane.showMessageDialog(rightComponent, "Cliente con DNI " + DNI + "eliminado.",
                            "Cliente eliminado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "No existe el vehiculo", JOptionPane.ERROR_MESSAGE);
            } catch (AlquilerVehiculoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo al eliminar el alquiler", JOptionPane.ERROR_MESSAGE);
            } finally {
                introDNI.setText("");
            }
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
        if (nombre.equals("")) {
            throw new FormatoIncorrectoException("Debes introducir el nombre.");
        } else if (!nombre.matches("\\D")) {
            throw new FormatoIncorrectoException(nombre + " no es un nombre valido.");
        } else {
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
}
