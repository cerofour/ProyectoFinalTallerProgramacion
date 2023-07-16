package pe.edu.utp.tp.ElaboracionReporte;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class TablaImprimible {
    private final String espaciosSeparador = " ".repeat(5);
    private final StringBuilder tablaGenerada = new StringBuilder();
    private String separador; // =
    private Integer[] longitudesCabeceras;

    private int CalcularLongitud(String linea) {
        return linea.length() + espaciosSeparador.length();
    }

    public void AnadirSeparador() {
        this.tablaGenerada.append(this.separador).append("\n");
    }

    public void setCabeceras(String[] cabeceras) {
        //this.cabeceras = cabeceras;
        this.tablaGenerada.delete(0, this.tablaGenerada.length()); //Borrado de tabla completa
        this.longitudesCabeceras = Arrays.stream(cabeceras).map(this::CalcularLongitud).toArray(Integer[]::new);
        this.separador = "=".repeat(Arrays.stream(this.longitudesCabeceras)
                .reduce(0, Integer::sum) - this.espaciosSeparador.length());
        this.tablaGenerada.append(String.join(this.espaciosSeparador, cabeceras)).append("\n"); //Agregado de cabeceras
        this.tablaGenerada.append(this.separador).append("\n");
    }

    public boolean setTablaGenerada(String... datos) throws Exception {
        StringBuilder stringFormat = new StringBuilder();
        if (this.tablaGenerada.length() == 0)
            throw new Exception("No se estableció las cabeceras para generar la tabla.");

        int limite = Math.min(this.longitudesCabeceras.length, datos.length);

        for (int i = 0; i < limite; i++) {
            stringFormat.append("%-").append(this.longitudesCabeceras[i]).append("s");
            datos[i] = (datos[i].length() > this.longitudesCabeceras[i])
                    ? datos[i].substring(0, this.longitudesCabeceras[i]) : datos[i];
        }

        /*
        for (Integer longitudCabecera : this.longitudesCabeceras) {
            stringFormat.append("%-").append(longitudCabecera).append("s");
        }
         */

        tablaGenerada.append(String.format(stringFormat.toString(),
                (Object[]) datos)).append("\n"); //datos como object?
        stringFormat.delete(0, stringFormat.length());
        return true;
    }

    //Override??
    public void ImprimirTabla() {
        /*
        FORMA DE PRUEBA:
        String[] cabecera = {"Departamemto", "Cond.", "Sexo", "Edad", "Cantidad", "Porc. "};
        String[][] datos = {
                {"Lambayeque", "SI", "Hombre", "12", "12343", "5,37%"},
                {"La libertad", "NE", "Mujer", "32", "43543", "100%"}
        };
        MenuSecundario ad = new MenuSecundario();
        ad.ImprimirTabla(cabecera, datos);
         */

        System.out.println(this.tablaGenerada);
    }

    // Crea un archivo llamado @rutaArchivo e imprime la tabla ahí
    public void ImprimirAArchivo(String rutaArchivo) throws IOException {
        File archivo = new File(rutaArchivo);
        FileWriter escribir = new FileWriter(archivo, true);
        escribir.write(this.tablaGenerada.toString());
        escribir.close();
    }

}
