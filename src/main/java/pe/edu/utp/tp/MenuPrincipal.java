package pe.edu.utp.tp;

import java.util.Arrays;

public class MenuPrincipal {
    private String[] opciones;
    int cantidadOpciones;

    public MenuPrincipal() {
        this.cantidadOpciones = 0;
        this.opciones = new String[1];
    }

    public void AgregarOpcion(String opcion) {
        if (this.cantidadOpciones == this.opciones.length) {
            String[] nuevasOpciones = new String[this.opciones.length + 2];
            System.arraycopy(this.opciones, 0, nuevasOpciones, 0, this.opciones.length);
            this.opciones = nuevasOpciones;
        }

        this.opciones[cantidadOpciones++] = opcion;
    }

    @Override
    public String toString() {
        return "MenuPrincipal{" +
                "opciones=" + Arrays.toString(opciones) +
                '}';
    }
}



