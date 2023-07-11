package pe.edu.utp.tp;

import java.util.Arrays;

public class MenuSecundario {
    private final String espaciosSeparador = " ".repeat(5);

    private int CalcularLongitud(String linea){
        return linea.length()+espaciosSeparador.length();
    }

    public void ImprimirTabla(String[] cabeceras, String[][] datos){
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

        Integer[] longitudCabeceras = Arrays.stream(cabeceras).map(this::CalcularLongitud).toArray(Integer[]::new);
        StringBuilder stringFormat= new StringBuilder();
        String separador = "=".repeat(Arrays.stream(longitudCabeceras).reduce(0, Integer::sum)
                -espaciosSeparador.length());
        StringBuilder tabla = new StringBuilder();

        tabla.append(String.join(espaciosSeparador, cabeceras)).append("\n"); //Agregado de cabeceras
        tabla.append(separador).append("\n");

        for (String[] dato : datos) {
            for (Integer longitudCabecera : longitudCabeceras) {
                stringFormat.append("%-").append(longitudCabecera).append("s");
            }
            tabla.append(String.format(stringFormat.toString(), (Object) dato)).append("\n"); //dato como object?
            stringFormat.delete(0, stringFormat.length());
        }

        tabla.append(separador).append("\n");
        System.out.println(tabla);
        tabla.delete(0, tabla.length());


    }



}
