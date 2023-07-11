package pe.edu.utp.tp;

import java.util.Arrays;

public class MenuSecundario {
    String espaciosSeparador = "     ";
    Integer[] longitudCabeceras;

    private int LongitudAdecuada(String linea){
        return linea.length()+espaciosSeparador.length();
    }

    private StringBuilder creaStringFormat(String linea){
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < longitudCabeceras.length; i++) {
            resultado.append("%"+longitudCabeceras[i].toString()+"s");
        }
        return resultado;
    }

    public void ImprimirTabla(String[] cabeceras){
        longitudCabeceras = Arrays.stream(cabeceras).map(this::LongitudAdecuada).toArray(Integer[]::new);
        StringBuilder tabla = new StringBuilder();
        tabla.append(String.join(espaciosSeparador, cabeceras));
        tabla.append(String.format(""));
        //TODO: Probar

    }


}
