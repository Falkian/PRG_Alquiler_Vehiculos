package GUI;

import java.awt.GridLayout;
import javax.swing.*;

public class PantallaAlquileres extends JSplitPane {
    
    public PantallaAlquileres() {
        super(JSplitPane.HORIZONTAL_SPLIT);
        iniciar();
    }
    
    private void iniciar() {
        JPanel menuAlquileres = new JPanel(); //Menu izquierdo
        menuAlquileres.setLayout(new BoxLayout(menuAlquileres, BoxLayout.Y_AXIS));
        
        //- - - Botones de la parte izquierda del menu
        JButton botonConsultar = new JButton("Consultar precio");
        JButton botonAlquilar = new JButton("Alquilar");
        JButton botonDevolver = new JButton("Devolver");
        
        //TODO - Establecer tamaño y organizacion de botones
        menuAlquileres.add(botonConsultar);
        menuAlquileres.add(botonAlquilar);
        menuAlquileres.add(botonDevolver);
        
        //TODO - Añadir funcionalidad botones pantallaAlquileres (cambiar parte derecha)
        setLeftComponent(menuAlquileres);
        
        //- - - Inicia el menu de gestion de alquileres en el apartado de consulta
        inicioPantallaAlquileresConsulta();
    }
    
    private void inicioPantallaAlquileresConsulta() {
        //- - - Menu de consulta de precios de alquiler
        JPanel consultaAlquiler = new JPanel();
        consultaAlquiler.setLayout(new GridLayout(4, 2));
        
        JLabel textoMatricula = new JLabel("Matricula");
        JTextField introMatricula = new JTextField(5);
        
        consultaAlquiler.add(textoMatricula);
        consultaAlquiler.add(introMatricula);
        
        JLabel textoDNI = new JLabel("DNI");
        JTextField introDNI = new JTextField(5);
        
        consultaAlquiler.add(textoDNI);
        consultaAlquiler.add(introDNI);
        
        JLabel textoDias = new JLabel("Dias");
        JSpinner introDias = new JSpinner();
        
        consultaAlquiler.add(textoDias);
        consultaAlquiler.add(introDias);
        
        JLabel textoPrecio = new JLabel("Precio");
        JLabel textoCantidad = new JLabel();
        
        //TODO - Añadir funcionalidad consultaAlquiler
        
        //TODO - Añadir boton para aplicar cambios (puede que requiera una linea mas)
        setRightComponent(consultaAlquiler);
    }
    
    private void inicioPantallaAlquileresAlquilar() {
        //- - - Menu de registro de alquiler
        JPanel altaAlquiler = new JPanel();
        altaAlquiler.setLayout(new GridLayout(2, 2));
        
        JLabel textoMatricula = new JLabel("Matricula");
        JTextField introMatricula = new JTextField(5);
        
        altaAlquiler.add(textoMatricula);
        altaAlquiler.add(introMatricula);
        
        JLabel textoDNI = new JLabel("DNI");
        JTextField introDNI = new JTextField(5);
        
        altaAlquiler.add(textoDNI);
        altaAlquiler.add(introDNI);
        
        //TODO - Añadir funcionalidad altaAlquiler
        
        //TODO - Añadir boton para aplicar cambios (puede que requiera una line mas)
        setRightComponent(altaAlquiler);
    }
    
    private void inicioPantallaAlquileresDevolver() {
        //- - - Menu de devolucion de vehiculos
        JPanel devolucionAlquiler = new JPanel();
        devolucionAlquiler.setLayout(new GridLayout(1, 2));
        
        JLabel textoMatricula = new JLabel("Matricula");
        JTextField introMatricula = new JTextField(5);
        
        devolucionAlquiler.add(textoMatricula);
        devolucionAlquiler.add(introMatricula);
        
        //TODO - Añadir funcionalidad devolucionalquiler
        
        //TODO - Añadir boton para aplicar cambios (puede que requiera una linea mas)
        setRightComponent(devolucionAlquiler);
    }    
}
