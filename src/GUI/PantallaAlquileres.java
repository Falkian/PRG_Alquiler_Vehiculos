package GUI;

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
import java.sql.SQLException;
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
    JComboBox introMatricula;
    JLabel textoDNI;
    JComboBox introDNI;
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
        introMatricula = new JComboBox(vehiculos.obtenerArrayMatriculas());
        ci.gridx = 0;
        ci.gridy = 0;
        ci.ipadx = 20;
        ci.weightx = 0.5;
        ci.weighty = 0.5;
        contenido.add(textoMatricula, ci);
        ci.gridx = 1;
        contenido.add(introMatricula, ci);

        textoDNI = new JLabel("DNI");
        introDNI = new JComboBox(clientes.obtenerArrayDnis());
        ((DefaultComboBoxModel) introDNI.getModel()).insertElementAt("- Ninguno -", 0);
        introDNI.setSelectedIndex(0);
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
        JButton botonConsulta = new JButton("Consultar precio");
        botonConsulta.addActionListener(new botonConsultaListener());

        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        consultaAlquileres.add(botonConsulta, c);

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
        introMatricula = new JComboBox(vehiculos.obtenerArrayMatriculasLibres());
        ci.gridx = 0;
        ci.gridy = 0;
        ci.ipadx = 20;
        ci.weightx = 0.5;
        ci.weighty = 0.5;
        contenido.add(textoMatricula, ci);
        ci.gridx = 1;
        contenido.add(introMatricula, ci);

        textoDNI = new JLabel("DNI");
        introDNI = new JComboBox(clientes.obtenerArrayDnis());
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

    /**
     * Crea y coloca los elementos del apartado de devolucion de vehiculos.
     */
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
        introMatricula = new JComboBox(alquileres.obtenerMatriculasAlquilados());
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
        ci.gridx = 1;
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
                String matricula = (String) introMatricula.getSelectedItem();
                if (introDNI.getSelectedIndex() == 0) {
                    int dias = (Integer) introDias.getValue();
                    double precio = vehiculos.obtenerVechiculo(matricula).alquilerTotal(dias);
                    double pdia = vehiculos.obtenerVechiculo(matricula).alquilerTotal(1);
                    JOptionPane.showMessageDialog(rightComponent, "Alquilar el vehiculo " + matricula + " de tipo: "
                            + vehiculos.obtenerVechiculo(matricula).getClass().getSimpleName()
                            + " durante " + dias + " dias costaria " + precio + "€ (" + pdia + "€/dia).",
                            "Precio alquiler", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String DNI = (String) introDNI.getSelectedItem();
                    int dias = (Integer) introDias.getValue();
                    double preciob = alquileres.obtenerPrecioAlquiler(matricula, DNI, dias);
                    double precio = preciob;
                    if (clientes.obtenerCliente(DNI).isVip()) {
                        precio -= preciob * 0.15;
                    }
                    if (historial.isPrimeraVez(matricula)) {
                        precio -= preciob * 0.25;
                    }
                    JOptionPane.showMessageDialog(rightComponent, "Alquilar el vehiculo " + matricula + " de tipo: "
                            + vehiculos.obtenerVechiculo(matricula).getClass().getSimpleName()
                            + " durante " + dias + " dias por el cliente " + DNI + " costaria " + precio + "€.",
                            "Precio alquiler", JOptionPane.INFORMATION_MESSAGE);
                }
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
                String matricula = (String) introMatricula.getSelectedItem();
                String DNI = (String) introDNI.getSelectedItem();
                alquileres.anyadirAlquiler(matricula, DNI);
                introMatricula.removeItem(matricula);
                JOptionPane.showMessageDialog(rightComponent, "Vehiculo con matricula " + matricula + " alquilado al cliente "
                        + DNI + ".", "Vehiculo alquilado", JOptionPane.INFORMATION_MESSAGE);
            } catch (ObjetoNoExistenteException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Obejto no existente", JOptionPane.ERROR_MESSAGE);
            } catch (AlquilerVehiculoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo al alquilar el vehiculo", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la base de datos.\n"
                        + "Error MySQL: " + ex.getMessage(), "Error MySQL", JOptionPane.ERROR_MESSAGE);
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
                String matricula = (String) introMatricula.getSelectedItem();
                int dias = obtenerDias();
                double precio = historial.anyadirRegistro(alquileres.obtenerAlquilerPorMatricula(matricula), dias);
                alquileres.eliminarAlquilerPorMatricula(matricula);
                introMatricula.removeItem(matricula);
                JOptionPane.showMessageDialog(rightComponent, "Vehiculo con matricula " + matricula + " devuelto. \n"
                        + "El precio ha sido de " + precio + "€.", "Vehiculo devuelto", JOptionPane.INFORMATION_MESSAGE);
            } catch (FormatoIncorrectoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo en el formato de los datos", JOptionPane.ERROR_MESSAGE);
            } catch (AlquilerVehiculoException ex) {
                JOptionPane.showMessageDialog(rightComponent, ex.getMessage(), "Fallo al alquilar el vehiculo", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la base de datos.\n"
                        + "Error MySQL: " + ex.getMessage(), "Error MySQL", JOptionPane.ERROR_MESSAGE);
            }

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
