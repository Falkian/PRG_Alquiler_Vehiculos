package Utilidades;

import Excepciones.FormatoIncorrectoException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MiScanner {

    private final Scanner scanner;

    public MiScanner() {
        scanner = new Scanner(System.in);
    }

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
    
    /**
     * Pide un numero hasta que se introduzca un entero ´alido.
     *
     * @return un entero valido.
     */
    public int leerInt() {
        boolean valido = false;
        int ret = 0;
        do {
            try {
                ret = scanner.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.print("Debes introducir un entero: ");
            } finally {
                scanner.nextLine();
            }
        } while (!valido);
        return ret;
    }
    
    /**
     * Pide un numero hasta que se introduzca uno valido.
     *
     * @return un numero decimal valido.
     */
    public double leerDouble() {
        boolean valido = false;
        double ret = 0;
        do {
            try {
                ret = scanner.nextDouble();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.print("Debes introducir un número: ");
            } finally {
                scanner.nextLine();
            }
        } while (!valido);
        return ret;
    }
}
