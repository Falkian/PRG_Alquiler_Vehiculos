package Abstractas;

import java.util.ArrayList;

/**
 * Iterador para recorrer los elementos de una coleccion.
 *
 * @author Kevin
 * @param <E> tipo de objetos sobre los que se iterara.
 */
public abstract class Iterador<E> {

    protected ArrayList<E> lista;    //Lista sobre la que se itera
    protected int posicion;                       //Posicion actual

    /**
     * Crea un iterador sobre la lista dada.
     *
     * @param lista la lista sobre la que se iterara.
     */
    public Iterador(ArrayList<E> lista) {
        this.lista = lista;
        posicion = 0;
    }

    /**
     * Posiciona el iterador en el primer elemento.
     */
    public void primero() {
        posicion = 0;
    }

    /**
     * Devuelve si existe algun elemento anterior al actual.
     *
     * @return true si existe dicho elemento; false en caso contrario.
     */
    public boolean tieneAnterior() {
        return posicion != 0;
    }

    /**
     * Retrocede una posicion si es posible.
     */
    public void anterior() {
        if (tieneAnterior()) {
            posicion--;
        }
    }

    /**
     * Devuelve si existe algun elemento posterior al actual.
     *
     * @return true si existe dicho elemento; false en caso contrario.
     */
    public boolean tieneSiguiente() {
        return lista.size() > 0 && posicion != lista.size() - 1;
    }

    /**
     * Avanza una posicion si es posible.
     */
    public void siguiente() {
        if (tieneSiguiente()) {
            posicion++;
        }
    }

    /**
     * Posiciona el iterador en el ultimo elemento.
     */
    public void ultimo() {
        posicion = lista.size() - 1;
    }

    /**
     * Posiciona el iterador sobre el elemento identificado por la matricula
     * dada. Si no se encuentra se posiciona en la ultima posicion.
     *
     * @param matricula la matricula que identifica al elemento.
     */
    public abstract void seleccionar(String matricula);

    /**
     * Devuelve el vehiculo actual de la lista
     *
     * @return el vehiculo actual de la lista.
     */
    public E getActual() {
        return lista.size() > 0 ? lista.get(posicion) : null;
    }
}
