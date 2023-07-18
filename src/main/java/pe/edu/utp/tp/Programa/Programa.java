package pe.edu.utp.tp.Programa;

import pe.edu.utp.tp.ElaboracionReporte.TablaImprimible;
import pe.edu.utp.tp.LecturaCSV.LectorCSV;
import pe.edu.utp.tp.LecturaCSV.RegistroCSV;
import pe.edu.utp.tp.Login.VerificadorCredenciales;
import pe.edu.utp.tp.Utilidades.ProcesadorStrings;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;


// La clase Programa abstrae todo aquello que es específico a esta aplicación
// como por ejemplo: los menues, los cálculos estadísticos y las peticiones de información al usuario.
public class Programa {

    private Scanner teclado;
    private LectorCSV lectorCSV;
    private String usuario;
    private VerificadorCredenciales verificadorCredenciales;
    private TablaImprimible tablaImprimible;

    public Programa() throws Exception {
        this.verificadorCredenciales = new VerificadorCredenciales();
        this.teclado = new Scanner(System.in);
        this.lectorCSV = new LectorCSV();
        this.lectorCSV.Inicializar();
        this.tablaImprimible = new TablaImprimible();
    }

    public void Ejecutar() throws Exception {
        if (this.verificadorCredenciales.LoguearUsuario()) {
            this.usuario = this.verificadorCredenciales.getUsuario();
            this.EjecutarMenuPrincipal();
        } else {
            System.out.println("No se han podido verificar las credenciales. Finalizando el programa");
        }
    }

    private void EjecutarMenuPrincipal() throws Exception {

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
                case 1 -> this.OpcionMenu1();

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

    // La opcion 1 nos reporta la cantidad de personas por condición de donación dado un departamento.
    // Solo nos interesa entonces, calcular cuantas personas han respondido SI, NO o NE.
    private void OpcionMenu1() throws Exception {

        String departamento = this.SolicitarDatosAUsuario("Ingrese el departamento a filtrar").get(0);

        // Filtrado de datos
        String[] cabeceras = {"Donación", "Frecuencia", "Frecuencia Porcentual"};
        this.tablaImprimible.setCabeceras(cabeceras);
        Predicate<RegistroCSV> filtrarPorDepartamento = registro -> registro.ValorDeCampo("Departamento").equals(departamento);

        // Conteo
        int cantidadSi = 0, cantidadNo = 0, cantidadNE = 0, totalPersonas = 0;
        RegistroCSV registro = this.lectorCSV.SiguienteRegistroFiltrado(filtrarPorDepartamento);
        while (registro != null) {
            String donacion = registro.ValorDeCampo("Donacion");
            switch (donacion) {
                case "SI" -> cantidadSi++;
                case "NO" -> cantidadNo++;
                case "NE" -> cantidadNE++;
            }
            totalPersonas++;
            registro = this.lectorCSV.SiguienteRegistroFiltrado(filtrarPorDepartamento);
        }

        // Calculos estadísiticos

        double frecuenciaPorcentualSi = (double) cantidadSi/(double) totalPersonas * 100.0;
        double frecuenciaPorcentualNo = (double) cantidadNo/(double) totalPersonas * 100.0;
        double frecuenciaPorcentualNE = (double) cantidadNE/(double) totalPersonas * 100.0;
        double porcentajeTotal = frecuenciaPorcentualNE + frecuenciaPorcentualNo + frecuenciaPorcentualSi;

        // Imprime tres filas para la tabla
        this.tablaImprimible.setTablaGenerada("SI", Integer.toString(cantidadSi), ProcesadorStrings.DoubleAString2Dec(frecuenciaPorcentualSi));
        this.tablaImprimible.setTablaGenerada("NO", Integer.toString(cantidadNo), ProcesadorStrings.DoubleAString2Dec(frecuenciaPorcentualNo));
        this.tablaImprimible.setTablaGenerada("NE", Integer.toString(cantidadNE), ProcesadorStrings.DoubleAString2Dec(frecuenciaPorcentualNE));
        this.tablaImprimible.AnadirSeparador();
        this.tablaImprimible.setTablaGenerada("TOTAL", Integer.toString(totalPersonas), ProcesadorStrings.DoubleAString2Dec(porcentajeTotal));

        BucleSubMenuModulo(departamento, "PERSONAS POR CONDICIÓIN DE DONACIÓN");
    }

    private void BucleSubMenuModulo(String nombreReporte, String nombreOpcion) throws IOException {
        while (true) {
            AccionesReporte accion = this.SubMenuModulo(1, nombreOpcion);

            if (accion == AccionesReporte.ImprimirPantalla)
                this.tablaImprimible.ImprimirTabla();
            else if (accion == AccionesReporte.ExportarArchivo)
                this.tablaImprimible.ImprimirAArchivo(String.format("personas-por-condicion-donacion-%s", nombreReporte));
            else if (accion == AccionesReporte.Invalida || accion == AccionesReporte.RegresarMenu)
                return;
        }
    }

    private List<String> SolicitarDatosAUsuario(String... mensajes) {
        List<String> datos = new ArrayList<>();

        for (String msg : mensajes) {
            System.out.print(msg + " ");
            datos.add(this.teclado.nextLine());
        }

        return datos;
    }

    private AccionesReporte SubMenuModulo(int numeroOpcion, String nombreOpcion) throws InputMismatchException {
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
            this.teclado.nextLine();
            if (i == 1 || i == 2 || i == 3)
                return AccionesReporte.values()[i];
        }
    }

    public String getUsuario() {
        return this.usuario;
    }
}
