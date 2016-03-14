package GUI;

import Estructuras.HistorialAlquileres;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

/**
 * Clase que contiene el menu de finanzas
 *
 * @author Kevin
 */
public class PantallaFinanzas extends JPanel {
    
    private final HistorialAlquileres historial;
    
    /**
     * Crea e inicializa el menu de vehiculos.
     *
     * @param historial el hishtorial de alquileres realizados
     */
    public PantallaFinanzas(HistorialAlquileres historial) {
        super();
        this.historial = historial;
        iniciar();
    }
    
    /**
     * Inicia la pantalla de finanzas, creando y colocando todos sus
     * elementos. Empieza en el apartado de alta de vehiculos.
     */
    private void iniciar() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JTable listado = new JTable(historial.obtenerDataArray(), new String[]{"Matricula", "DNI", "Dias", "Primer", "VIP", "Precio"});

        JScrollPane listadoFinanzas = new JScrollPane(listado);
        listado.setFillsViewportHeight(true);

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 8;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(listadoFinanzas, c);
        c.gridy = 9;
        c.gridheight = 1;
        c.weightx = 0.1;
        c.weighty = 0.1;
        add(new JLabel("Total: " + historial.getTotal() + "€"), c);
    }
    
}
