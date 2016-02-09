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
}
