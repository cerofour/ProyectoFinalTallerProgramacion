package pe.edu.utp.tp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

enum AccionesReporte {
    Invalida, // esta nunca debería ser lanzada en ningún momento ya que solo se usa para marcar el cero.
    ImprimirPantalla, // Imprimir el reporte en una tabla formateada a la pantalla
    ExportarArchivo, // Imprimir y exportar el reporte a un archivo plano
    RegresarMenu, // Regresar al menú principal
}

public class Programa {

    private Scanner teclado;
    private LectorCSV lectorCSV;
    private String usuario;
    private VerificadorCredenciales verificadorCredenciales;

    public Programa() throws FileNotFoundException {
        this.verificadorCredenciales = new VerificadorCredenciales();
        this.teclado = new Scanner(System.in);
        this.lectorCSV = new LectorCSV();
        this.lectorCSV.Inicializar();
        this.usuario = "dev"; // cambiar esto
    }

    public void Ejecutar() throws Exception {
        if (this.verificadorCredenciales.LoguearUsuario()) {
            this.usuario = this.verificadorCredenciales.getUsuario();
            this.EjecutarMenuPrincipal();
        } else {
            System.out.println("No se han podido verificar las credenciales. Finalizando el programa");
        }
    }

    private void EjecutarMenuPrincipal() throws InputMismatchException {

        boolean salir = false;
        int opcion = 0;

        while (!salir){
        System.out.println("""
                | ============================================================================| 
                | --------------------------MENÚ DE OPCIONES----------------------------------| 
                | 1) Cantidad de personas por condición de donación dado un departamento      |    
                | 2) Cantidad de personas por provincia, sexo y condición de donación dado    |   
                |    un rango de edades y un departamento.                                    | 
                | 3) Cantidad de donantes por departamento dada una edad y sexo.              | 
                | 4) Cantidad de personas por departamento dada una condición de donación     |   
                | 5) Salir del menú                                                           |   
                |                            ELIJA UNA OPCIÓN                                 |   
                | ============================================================================| 
                """);
            System.out.println("Introduce un número de acuerdo a la opción que desea ");
            opcion = this.teclado.nextInt();
            this.teclado.nextLine();

            switch (opcion){
                case 1 -> {
                    System.out.println("Esta es la opción 1 ");
                    //El usuario ingresa un departamento de su preferencia
                    System.out.println("Ingrese el departamento a filtrar");
                    String departamento = this.teclado.nextLine();
                    this.OpcionMenu1(departamento);
                }

                case 2 -> {
                    System.out.println("ARREGLAR ESTO ");
                    //El usuario ingresa edad
                    System.out.println("Ingrese la edad mínima del donante");
                    int edad_minima = this.teclado.nextInt();
                    this.teclado.nextLine();

                    System.out.println("Ingrese la edad máxima del donante");
                    int edad_maxima = this.teclado.nextInt();
                    this.teclado.nextLine();

                    System.out.println("Ingrese el departamento a filtrar");
                    String departamento = this.teclado.nextLine();


                    this.OpcionMenu2(edad_minima, edad_maxima, departamento);
                }

                case 3 -> {
                    System.out.println("CAMBIAR ESTE MENSAJE ");
                    //Usuario ingresa una edad y sexo
                    System.out.println("Ingrese el sexo a filtrar, si es hombre (H) y si es mujer (M) ");

                    while (true) {
                        String sexo = this.teclado.nextLine().toUpperCase();
                        if (sexo.equals("H") || sexo.equals("M")) {
                            System.out.println("Ingrese la edad a filtrar");
                            int edad = this.teclado.nextInt();
                            this.teclado.nextLine();
                            this.OpcionMenu3(sexo, edad);
                            break;
                        }
                        else
                            System.out.println("El sexo ingresado no es valido ");
                    }
                }
                case 4 -> {
                    System.out.println("ARREGLAR ESTE MENSAJE");

                    // TODO: Este bucle debe abstraerse tal vez?
                    while (true) {
                        System.out.println("Ingrese la condición de donación a filtrar (SI, NO, NE)");
                        String condicionDonacion = this.teclado.nextLine().toUpperCase();
                        if (condicionDonacion.equals("SI") || condicionDonacion.equals("NO") || condicionDonacion.equals("NE")) {
                            this.OpcionMenu4(condicionDonacion);
                            break;
                        }
                        else System.out.println("La condicion ingresada no es valida ");
                    }
                }
                case 5 -> salir = true;
                default -> System.out.println("Las ocpiones que usted debe elegir es entre (1-5) ");
            }
        }
        System.out.println("Fin de la ejecución.");
}

    private void OpcionMenu4(String condicionDonacion) {
    }

    private void OpcionMenu3(String sexo, int edad) {
    }

    private void OpcionMenu2(int edadMinima, int edadMaxima, String departamento) {
    }

    private void OpcionMenu1(String departamento) {
        this.SubmenuModulo(1, "PERSONAS POR CONDICIóN DE DONACIóN");

    }

    private AccionesReporte SubmenuModulo(int numeroOpcion, String nombreOpcion) throws InputMismatchException {
        final String separador = "-".repeat(nombreOpcion.length() + 20);
        System.out.println(separador);
        System.out.printf("MODULO %d - %s\n", numeroOpcion, nombreOpcion);
        System.out.println(separador);
        System.out.println("""
                1. Imprimir por pantalla
                2. Exportar a archivo plano
                3. Volver al menú principal
                """);
        while (true) {
            int i = this.teclado.nextInt(); // lanza excepcion
            if (i == 1 || i == 2 || i == 3)
                return AccionesReporte.values()[i];
        }
    }

    public String getUsuario() {
        return this.usuario;
    }
}
