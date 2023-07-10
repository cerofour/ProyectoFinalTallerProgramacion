package pe.edu.utp.tp;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        LectorCSV lector = new LectorCSV();

        try {
            lector.Inicializar();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < 10; i++) {
            String linea = lector.SiguienteLinea();
            System.out.println(linea);
        }
    }
}