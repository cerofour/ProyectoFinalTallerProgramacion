package pe.edu.utp.tp;

import java.io.FileNotFoundException;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        LectorCSV lector = new LectorCSV();

        try {
            lector.Inicializar();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        Predicate<RegistroCSV> filtrarPorDepartamento = registro -> {
            if (registro.ValorDeCampo("Departamento").equals("Lambayeque")) {
                int edad = Integer.parseInt(registro.ValorDeCampo("Edad"));
                return edad > 19 && edad < 23;
            }
            return false;
        };

        for (int i = 0; i < 10; i++) {
            RegistroCSV linea = lector.SiguienteRegistroFiltrado(filtrarPorDepartamento);
            System.out.println(linea);
        }
    }
}