package pe.edu.utp.tp.Programa;

import pe.edu.utp.tp.ElaboracionReporte.TablaImprimible;
import pe.edu.utp.tp.LecturaCSV.LectorCSV;
import pe.edu.utp.tp.LecturaCSV.RegistroCSV;
import pe.edu.utp.tp.Login.VerificadorCredenciales;
import pe.edu.utp.tp.Utilidades.ProcesadorStrings;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

// Autores del modulo: Diego, Kevin, Leandro

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
            System.out.print("Introduce un número de acuerdo a la opción que desea [1-5]: ");
            opcion = this.teclado.nextInt();
            this.teclado.nextLine();

            switch (opcion){
                case 1 -> this.OpcionMenu1();

                case 2 -> this.OpcionMenu2();

                case 3 -> this.OpcionMenu3();

                case 4 -> this.OpcionMenu4();

                case 5 -> salir = true;
                default -> System.out.println("Las ocpiones que usted debe elegir es entre (1-5) ");
            }
        }
        System.out.println("Fin de la ejecución.");
    }

    private void OpcionMenu4() throws Exception {
        String donacion = this.SolicitarDatosAUsuario("Ingrese la condición de donación");

        String[] cabeceras = {"Departamento", "Frecuencia", "Frecuencia Porcentual"};
        this.tablaImprimible.setCabeceras(cabeceras);

        // Filtro de datos
        Predicate<RegistroCSV> filtraDonantesPorEdadYSexo = registro -> {
            return registro.ValorDeCampo("Donacion").equals(donacion);
        };

        LinkedHashMap<String, Integer> conteoPorDepartamento = new LinkedHashMap<>();

        Integer totalPersonas = 0;

        RegistroCSV registro = this.lectorCSV.SiguienteRegistroFiltrado(filtraDonantesPorEdadYSexo);
        while (registro != null) {

            String clave = registro.ValorDeCampo("Departamento");

            Integer conteo = Integer.parseInt(registro.ValorDeCampo("Cantidad"));
            if (conteoPorDepartamento.containsKey(clave))
                conteo += conteoPorDepartamento.get(clave);

            conteoPorDepartamento.put(clave, conteo);

            registro = this.lectorCSV.SiguienteRegistroFiltrado(filtraDonantesPorEdadYSexo);
        }

        for (Integer n : conteoPorDepartamento.values())
            totalPersonas += n;

        double totalPorcentual = 0.0;
        for (String departamento : conteoPorDepartamento.keySet()) {
            String freq = Integer.toString(conteoPorDepartamento.get(departamento));
            double porcentaje = (double) conteoPorDepartamento.get(departamento) / (double) totalPersonas * 100.0;
            totalPorcentual += porcentaje;
            String freqPorcentual = ProcesadorStrings.DoubleAString2Dec(porcentaje);
            this.tablaImprimible.setTablaGenerada(departamento, freq, freqPorcentual);
        }

        this.tablaImprimible.AnadirSeparador();
        this.tablaImprimible.setTablaGenerada("TOTAL", Integer.toString(totalPersonas), ProcesadorStrings.DoubleAString2Dec(totalPorcentual));
        BucleSubMenuModulo(donacion, "PERSONAS POR DEPARTAMENTO DADA UNA CONDICION DE DONACION");
    }

    // Donantes por departamento dada una edad y sexo
    private void OpcionMenu3() throws Exception {
        String edad = this.SolicitarDatosAUsuario("Ingrese la edad a filtrar");
        String sexo = this.SolicitarDatosAUsuario("Ingrese el sexo a filtrar");

        String[] cabeceras = {"Departamento", "Frecuencia", "Frecuencia Porcentual"};
        this.tablaImprimible.setCabeceras(cabeceras);

        // Filtro de datos
        Predicate<RegistroCSV> filtraDonantesPorEdadYSexo = registro -> {
            return registro.ValorDeCampo("Donacion").equals("SI") &&
                    registro.ValorDeCampo("Sexo").equals(sexo) &&
                    registro.ValorDeCampo("Edad").equals(edad);
        };

        LinkedHashMap<String, Integer> conteoPorDepartamento = new LinkedHashMap<String, Integer>();

        Integer totalPersonas = 0;

        RegistroCSV registro = this.lectorCSV.SiguienteRegistroFiltrado(filtraDonantesPorEdadYSexo);
        while (registro != null) {

            String clave = registro.ValorDeCampo("Departamento");

            Integer conteo = Integer.parseInt(registro.ValorDeCampo("Cantidad"));
            if (conteoPorDepartamento.containsKey(clave))
                conteo += conteoPorDepartamento.get(clave);

            conteoPorDepartamento.put(clave, conteo);

            registro = this.lectorCSV.SiguienteRegistroFiltrado(filtraDonantesPorEdadYSexo);
        }

        for (Integer n : conteoPorDepartamento.values())
            totalPersonas += n;

        double totalPorcentual = 0.0;
        for (String departamento : conteoPorDepartamento.keySet()) {
            String freq = Integer.toString(conteoPorDepartamento.get(departamento));
            double porcentaje = (double) conteoPorDepartamento.get(departamento) / (double) totalPersonas * 100.0;
            totalPorcentual += porcentaje;
            String freqPorcentual = ProcesadorStrings.DoubleAString2Dec(porcentaje);
            this.tablaImprimible.setTablaGenerada(departamento, freq, freqPorcentual);
        }

        this.tablaImprimible.AnadirSeparador();
        this.tablaImprimible.setTablaGenerada("TOTAL", Integer.toString(totalPersonas), ProcesadorStrings.DoubleAString2Dec(totalPorcentual));
        BucleSubMenuModulo(edad + "-" + sexo, "PERSONAS POR PROVINCIA, SEXO Y CONDICIÓN DE DONACIÓN");
    }

    private void OpcionMenu2() throws Exception {
        int edad_minima = Integer.parseInt(this.SolicitarDatosAUsuario("Ingrese la edad mínima del donante"));
        int edad_maxima = Integer.parseInt(this.SolicitarDatosAUsuario("Ingrese la edad máxima del donante"));
        String departamento = this.SolicitarDatosAUsuario("Ingrese el departamento a filtrar");

        String[] cabeceras = {"Provincia", "Sexo", "Donación", "Frecuencia", "Frecuencia Porcentual"};
        this.tablaImprimible.setCabeceras(cabeceras);

        // Filtro de datos
        Predicate<RegistroCSV> filtraPorRangoEdadYDepartamento = registro -> {
            int edad = Integer.parseInt(registro.ValorDeCampo("Edad"));
            return registro.ValorDeCampo("Departamento").equals(departamento) && edad >= edad_minima && edad <= edad_maxima;
        };

        // Cálculo de datos
        // Para realizar el conteo juntaremos los 3 datos de clasificacion en una sola string y lo utilizaremos como
        // clave para un HashMap
        // Provincia | Sexo  | Donacion |
        // Chiclayo  | Mujer | SI       |
        // clave: ChiclayoMujerSI
        // así nos ahorramos bastantes líneas de código
        LinkedHashMap<String, Integer> conteos = new LinkedHashMap<String, Integer>();

        Integer totalPersonas = 0;

        RegistroCSV registro = this.lectorCSV.SiguienteRegistroFiltrado(filtraPorRangoEdadYDepartamento);
        while (registro != null) {

            String clave;
            {
                String[] vals = {registro.ValorDeCampo("Provincia"), registro.ValorDeCampo("Sexo"), registro.ValorDeCampo("Donacion")};
                clave = String.join(";", vals);
            }

            Integer conteo = Integer.parseInt(registro.ValorDeCampo("Cantidad"));
            if (conteos.containsKey(clave))
                conteo += conteos.get(clave);

            conteos.put(clave, conteo);

            registro = this.lectorCSV.SiguienteRegistroFiltrado(filtraPorRangoEdadYDepartamento);
        }

        for (Integer n : conteos.values())
            totalPersonas += n;

        String[] claves = conteos.keySet().toArray(new String[0]);
        for (String clave : claves) {
            String[] valores = clave.split(";");
            Integer cuenta = conteos.get(clave);
            String frecPorcentual = ProcesadorStrings.DoubleAString2Dec((double) cuenta / (double) totalPersonas * 100.00);
            this.tablaImprimible.setTablaGenerada(valores[0], valores[1], valores[2], cuenta.toString(), frecPorcentual);
        }
        this.tablaImprimible.AnadirSeparador();
        this.tablaImprimible.setTablaGenerada("TOTAL", "", "", totalPersonas.toString(), "100.00");

        BucleSubMenuModulo(departamento, "PERSONAS POR PROVINCIA, SEXO Y CONDICIÓN DE DONACIÓN");
    }

    // La opcion 1 nos reporta la cantidad de personas por condición de donación dado un departamento.
    // Solo nos interesa entonces, calcular cuantas personas han respondido SI, NO o NE.
    private void OpcionMenu1() throws Exception {

        String departamento = this.SolicitarDatosAUsuario("Ingrese el departamento a filtrar");

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
                this.tablaImprimible.ImprimirAArchivo(String.format("%s-%s", nombreOpcion, nombreReporte));
            else if (accion == AccionesReporte.Invalida || accion == AccionesReporte.RegresarMenu)
                return;
        }
    }

    private String SolicitarDatosAUsuario(String mensaje) {
        System.out.print(mensaje + ": ");
        return this.teclado.nextLine();
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