package Utilidades;

import Excepciones.FormatoIncorrectoException;
import java.util.Scanner;

/**
 * Calse que repressenta un escaner pare leer de teclado personalizado.
 *
 * @author Kevin
 */
public class MiScanner {

    private final Scanner scanner;          //El scanner que leera de teclado.

    /**
     * Constructor por defecto. Inicializa el scaner.
     */
    public MiScanner() {
        scanner = new Scanner(System.in);
    }

    /**
     * Lee una cadena segun un patron dado.
     *
     * @param regex l aexpresio regular que debe generar la cadena leida.
     * @param msg El mensaje con el que se pedira la cadena.
     * @param errmsg El mensaje de error que se mostrara si lacadena no se ciñe
     * al patron.
     * @param caseSen Si la cadena es sensible a mayusculas. De no serlo se
     * pasara automaticamente a mayusculas.
     * @return Una caden que se ciñe al patron dado.
     * @throws FormatoIncorrectoException si laa cadena no cumple con el patron.
     */
    public String leerSegunPatron(String regex, String msg, String errmsg, boolean caseSen)
            throws FormatoIncorrectoException {
        System.out.print(msg);
        String ret = scanner.next();
        scanner.nextLine();
        if (!caseSen) {
            ret = ret.toUpperCase();
        }
        if (ret.matches(regex)) {
            return ret;
        } else {
            throw new FormatoIncorrectoException(errmsg);
        }
    }

    /**
     * Lee una respuesta adirmativa o negativa.
     *
     * @param msg El mensaje con el que se pedira la respuesta.
     * @param errmsg El mensaje que se mostrara si la respuesta no es valida.
     * @return Verdadero si se responde afirmativamente; Falso en caso
     * contrario.
     * @throws FormatoIncorrectoException Si la cadena leida no es S, SI, N o
     * NO.
     */
    public boolean leerSN(String msg, String errmsg) throws FormatoIncorrectoException {
        System.out.print(msg);
        String s = scanner.next().toUpperCase();
        scanner.nextLine();
        switch (s) {
            case "S":
            case "SI":
                return true;
            case "N":
            case "NO":
                return false;
            default:
                throw new FormatoIncorrectoException(errmsg);
        }
    }

    /**
     * Lee una cadena y la compara con una serie de valores validos. Devuelve la
     * cadena si es uno de los valores validos.
     *
     * @param validos La coleccion de valores validos.
     * @param msg El mensaje con el que se pedira la cadena.
     * @param errmsg El mensaje que se mostrara si la cadena no es uno de los
     * mensajes validos.
     * @param caseSen Si la cadena es sensible a muyusculas. De no serlo se
     * pasara a mayusculas.
     * @return Una cadena que pertenece a un conjubto de cadenas validas.
     * @throws FormatoIncorrectoException Si la cadena no es valida.
     */
    public String leerEntrePosibles(String[] validos, String msg, String errmsg, boolean caseSen)
            throws FormatoIncorrectoException {
        System.out.print("msg");
        String ret = scanner.next();
        scanner.nextLine();
        if (!caseSen) {
            ret = ret.toUpperCase();
        }
        for (String valor : validos) {
            if (ret.equals(valor)) {
                return ret;
            }
        }
        throw new FormatoIncorrectoException(errmsg);
    }
}
