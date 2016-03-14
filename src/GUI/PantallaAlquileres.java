package GUI;

import Abstractas.Vehiculo;
import Clases.Cliente;
import Estructuras.ColeccionAlquileres;
import Estructuras.ColeccionClientes;
import Estructuras.ColeccionVehiculos;
import Estructuras.HistorialAlquileres;
import Excepciones.AlquilerVehiculoException;
import Excepciones.FormatoIncorrectoException;
import Excepciones.ObjetoNoExistenteException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Clase que contiene el menu de gestion de alquileres
 *
 * @author Kevin
 */
public class PantallaAlquileres extends JSplitPane {

    private final ColeccionAlquileres alquileres;   //Coleccion de alquileres
    private final ColeccionVehiculos vehiculos;     //Coleccion de vehiculos (para comprobar la existencia de los vehiculos introducidos)    
    private final ColeccionClientes clientes;       //Coleccion de clientes (para comprobar la existencia de los clientes introducidos)
    private final HistorialAlquileres historial;    //Historial de alquileres (para actualizarlo al devolver un vehiculo)

    JLabel textoMatricula;
    JTextField introMatricula;
    JLabel textoDNI;
    JTextField introDNI;
    JLabel textoDias;
    JSpinner introDias;

    /**
     * Crea e inicializa el menu de alquileres.
     *
     * @param alquileres la colecion de alquileres
     * @param vehiculos la coleccion de vehiculos
     * @param clientes la coleccion de clientes
     * @param historial el historial de alquileres
     */
    public PantallaAlquileres(ColeccionAlquileres alquileres, ColeccionVehiculos vehiculos, ColeccionClientes clientes, HistorialAlquileres historial) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.alquileres = alquileres;
        this.vehiculos = vehiculos;
        this.clientes = clientes;
        this.historial = historial;
        iniciar();
    }

    /**
     * Inicia el menu de gestion de alquileres, creando y colocando todos sus
     * elementos. Empieza en el apartado de creacion de alquileres.
     */
    private void iniciar() {
        JPanel menuAlquileres = new JPanel();        //Menu izquierdo
        menuAlquileres.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Botones de la parte izquierda del menu de gestion de alquileres
        JButton botonConsulta = new JButton("Consulta");
        JButton botonAlquilar = new JButton("Alquilar");
        JButton botonDevolver = new JButton("Devolver");

        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        int yin = menuAlquileres.getHeight() / 4 - botonConsulta.getHeight() / 2;
        int xin = menuAlquileres.getWidth() / 2 - botonConsulta.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuAlquileres.add(botonConsulta, c);

        c.gridy = 1;
        yin = menuAlquileres.getHeight() / 4 - botonAlquilar.getHeight() / 2;
        xin = menuAlquileres.getWidth() / 2 - botonAlquilar.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuAlquileres.add(botonAlquilar, c);

        c.gridy = 2;
        yin = menuAlquileres.getHeight() / 4 - botonDevolver.getHeight() / 2;
        xin = menuAlquileres.getWidth() / 2 - botonDevolver.getWidth() / 2;
        c.insets = new Insets(yin, xin, yin, xin);
        menuAlquileres.add(botonDevolver, c);

        botonConsulta.addActionListener(new botonesMenuListener());
        botonAlquilar.addActionListener(new botonesMenuListener());
        botonDevolver.addActionListener(new botonesMenuListener());

        botonConsulta.setActionCommand("Consultar");
        botonAlquilar.setActionCommand("Alquilar");
        botonDevolver.setActionCommand("Devolver");

        setLeftComponent(menuAlquileres);

        //- - - Inicia el menu de gestion de vehiculos en el apartado de alta
        inicioPantallaAlquileresConsulta();
    }

    /**
     * Crea y coloca los elementos del apartado de consulta de alquileres.
     */
    private void inicioPantallaAlquileresConsulta() {
        JPanel consultaAlquileres = new JPanel();
        consultaAlquileres.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U></B>CONSULTA DE PRECIO DE ALQUILER</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        consultaAlquileres.add(titulo, c);

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

        textoDNI = new JLabel("DNI");
        introDNI = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 1;
        contenido.add(textoDNI, ci);
        ci.gridx = 1;
        contenido.add(introDNI, ci);

        textoDias = new JLabel("Dias");
        introDias = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        ci.gridx = 0;
        ci.gridy = 2;
        contenido.add(textoDias, ci);
        ci.gridx = 1;
        contenido.add(introDias, ci);

        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        consultaAlquileres.add(contenido, c);

        //Boton de Consulta
        JButton botonModificacion = new JButton("Consultar precio");
        botonModificacion.addActionListener(new botonConsultaListener());

        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        consultaAlquileres.add(botonModificacion, c);

        setRightComponent(consultaAlquileres);
    }

    /**
     * Crea y coloca los elementos del apartado de alquilar vehiculos.
     */
    private void inicioPantallaAlquileresAlquilar() {
        JPanel realizarAlquileres = new JPanel();
        realizarAlquileres.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U></B>ALQUILAR VEHICULOS</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        realizarAlquileres.add(titulo, c);

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

        textoDNI = new JLabel("DNI");
        introDNI = new JTextField(5);
        ci.gridx = 0;
        ci.gridy = 1;
        contenido.add(textoDNI, ci);
        ci.gridx = 1;
        contenido.add(introDNI, ci);

        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        realizarAlquileres.add(contenido, c);

        //Boton de alquiler
        JButton botonAlquilar = new JButton("Alquilar");
        botonAlquilar.addActionListener(new botonAlquilarListener());

        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        realizarAlquileres.add(botonAlquilar, c);

        setRightComponent(realizarAlquileres);
    }

    private void inicioPantallaAlquileresDevolver() {
        JPanel devolverVehiculo = new JPanel();
        devolverVehiculo.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Titulo del submenu
        JPanel titulo = new JPanel();
        titulo.add(new JLabel("<HTML><U></B>DEVOLVER VEHICULOS</B></U></HTML>"));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        devolverVehiculo.add(titulo, c);

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

        textoDias = new JLabel("Dias");
        introDias = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        ci.gridx = 0;
        ci.gridy = 1;
        contenido.add(textoDias, ci);
        contenido.add(introDias, ci);
        
        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        devolverVehiculo.add(contenido, c);

        //Boton de devolucion
        JButton botonDevolver = new JButton("Devolver");
        botonDevolver.addActionListener(new botonDevolverListener());

        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        devolverVehiculo.add(botonDevolver, c);

        setRightComponent(devolverVehiculo);
    }

    /**
     * Manejador de eventos de los botones de navegacion del menu de gestion de
     * alquileres.
     */
    private class botonesMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Consultar":
                    inicioPantallaAlquileresConsulta();
                    break;
                case "Alquilar":
                    inicioPantallaAlquileresAlquilar();
                    break;
                case "Devolver":
                    inicioPantallaAlquileresDevolver();
                    break;
            }
        }

    }
    
    /**
     * Manejador de eventos del boton de consulta de precios de alquiler.
     * Asegura que los datos sean validos al clicar, indicando los posibles
     * errores o exito de la accion.
     */
    private class botonConsultaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String matricula = obtenerMatricula();
                String DNI = obtenerDNI();
                int dias = obtenerDias();
                double precio = alquileres.obtenerPrecioAlquiler(matricula, DNI, dias);
                if (clientes.obtenerCliente(DNI).isVip()) {
                    precio -= precio * 0.15;
                }
                JOptionPane.showMessageDialog(rightComponent, "Alquilar el vehiculo " + matricula + " de tipo: " 
                        + vehiculos.obtenerVechiculo(matricula).getClass().getSimpleName()
                        + " durante " + dias + " dias por el cliente " + DNI + " costaria " + precio + "€.",
                        "Precio alquiler", JOptionPane.INFORMATION_MESSAGE);
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Obejto no existente", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Manejador de eventos del boton de alquilar vehiculos. Asegura que los
     * datos sean validos al clicar, indicando los posibles errores o exito de
     * la accion.
     */
    private class botonAlquilarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String matricula = obtenerMatricula();
                String DNI = obtenerDNI();
                alquileres.anyadirAlquiler(matricula, DNI);
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Obejto no existente", JOptionPane.ERROR_MESSAGE);
            } catch (AlquilerVehiculoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo al alquilar el vehiculo", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
    
    /**
     * Manejador de eventos del boton de devolver vehiculos. Asegura que los
     * datos sean validos al clicar, indicando los posibles errores o exito de
     * la accion.
     */
    private class botonDevolverListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //Añade el alquiler al regsitro y lo devuelve
                String matricula = obtenerMatricula();
                int dias = obtenerDias();
                historial.anyadirRegistro(alquileres.obtenerAlquilerPorMatricula(matricula), dias);
                alquileres.eliminarAlquilerPorMatricula(matricula);
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (AlquilerVehiculoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo al alquilar el vehiculo", JOptionPane.ERROR_MESSAGE);
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
     * Lee los dias de la zona de introduccion correspondiente.
     *
     * @return un numero de dias
     * @throws FormatoIncorrectoException si el DNI no tiene el formato adecuado
     * o es vacio
     */
    private int obtenerDias() throws FormatoIncorrectoException {
        try {
            return (Integer) introDias.getValue();
        } catch (NumberFormatException ex) {
            throw new FormatoIncorrectoException("Las plazas deben ser un numero entero.");
        }
    }
}
