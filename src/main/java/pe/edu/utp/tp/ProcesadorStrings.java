package pe.edu.utp.tp;

public class ProcesadorStrings {
    public static String EliminaCaracteresInvisibles(String input) {
        return input.replaceAll("\\p{C}", "");
    }
}
