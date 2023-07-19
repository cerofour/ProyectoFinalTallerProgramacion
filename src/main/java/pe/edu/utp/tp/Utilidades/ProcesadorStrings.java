package pe.edu.utp.tp.Utilidades;

import java.text.DecimalFormat;

// Autor del modulo: Leandro


public class ProcesadorStrings {
    public static String EliminaCaracteresInvisibles(String input) {
        return input.replaceAll("\\p{C}", "");
    }

    public static String DoubleAString2Dec(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }
}
