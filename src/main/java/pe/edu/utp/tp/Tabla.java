package pe.edu.utp.tp;

import java.util.Arrays;

public class Tabla {
    private final String espaciosSeparador = " ".repeat(5);
    private final StringBuilder tablaGenerada = new StringBuilder();
    private String separador; // =
    private Integer[] longitudesCabeceras;
    // ASD

    private int CalcularLongitud(String linea) {
        return linea.length() + espaciosSeparador.length();
    }

    public void setCabeceras(String[] cabeceras) {
        //this.cabeceras = cabeceras;
        tablaGenerada.delete(0, tablaGenerada.length()); //Borrado de tabla completa
        longitudesCabeceras = Arrays.stream(cabeceras).map(this::CalcularLongitud).toArray(Integer[]::new);
        separador = "=".repeat(Arrays.stream(longitudesCabeceras)
                .reduce(0, Integer::sum) - espaciosSeparador.length());
        tablaGenerada.append(String.join(espaciosSeparador, cabeceras)).append("\n"); //Agregado de cabeceras
        tablaGenerada.append(separador).append("\n");
    }

    public boolean setTablaGenerada(String[] datos) throws Exception {
        StringBuilder stringFormat = new StringBuilder();
        if (tablaGenerada.length() == 0) {
            Auditoria audi = new Auditoria();
            audi.RegistrarExcepcion(new Exception("No se estableció las cabeceras para generar la tabla."));
            return false;
        }
        for (int i = 0; i < longitudesCabeceras.length; i++) {
            stringFormat.append("%-").append(longitudesCabeceras[i]).append("s");
            datos[i] = (datos[i].length() > longitudesCabeceras[i])
                    ? datos[i].substring(0, longitudesCabeceras[i]) : datos[i];
        }
        for (Integer longitudCabecera : longitudesCabeceras) {
            stringFormat.append("%-").append(longitudCabecera).append("s");
        }
        tablaGenerada.append(String.format(stringFormat.toString(),
                (Object) datos)).append("\n"); //datos como object?
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

        tablaGenerada.append(separador).append("\n");
        System.out.println(tablaGenerada);
    }

}
