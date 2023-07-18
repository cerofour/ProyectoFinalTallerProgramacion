package pe.edu.utp.tp;

import java.io.*;
import java.util.function.Predicate;

// Esta clase se utiliza para abrir y leer un archivo CSV.
// define diversos métodos para extraer información por regsitro/campo, etc.

public class LectorCSV {

    BufferedReader lectorArchivoCSV;
    // Marca si el lector se encuentra en el final del archivo o no.
    boolean enElFinalDelArchivo = false;

    // Coloca el BufferedReader al inicio del archivo. Esto debe hacerse después de cada vez que se
    // termine de leer todo el archivo o no será posible leer otra vez los datos.
    public void Reiniciar() throws IOException {
        this.lectorArchivoCSV.mark(0);
        this.lectorArchivoCSV.reset();
    }

    // TODO: este método debería leer un archivo csv del usuario, no del archivo de recursos.
    public void Inicializar() throws IOException {

        // asigna el archivo CSV del que sacaremos los datos.
        InputStream csvInputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("Registro 2022 Donantes Órganos Perú (DATASET).csv");
        if (csvInputStream == null)
            throw new FileNotFoundException("Base de datos inexistente");

        this.lectorArchivoCSV = new BufferedReader(new InputStreamReader(csvInputStream));

        // inicializa todo lo demás que sea necesario.
        this.CreaCabeceras();
    }

    private void CreaCabeceras() throws IOException {
        // Despues de crear el BufferedReader,  las cabeceras del archivo CSV. Las cabeceras son los valores separados por coma en la primera línea que
        // NO empieza por // (línea comentada)

        // Al final de este bucle, lineaActual debería ser la linea que contiene las cabeceras.
        String lineaActual = this.SiguienteLinea();
        for (; lineaActual != null && lineaActual.length() > 0; lineaActual = this.SiguienteLinea()) {
            if (lineaActual.startsWith("//"))
                continue;
            else break;
        }

        // Archivo vacío
        if (lineaActual == null) {
            RegistroCSV.setCabecerasVacias();
            return;
        }

        String[] cabeceras = this.ProcesarLineaCSV(lineaActual);
        RegistroCSV.setCabeceras(cabeceras);
    }

    // Hace el proceso de "parsing" de cada línea CSV. actualmente es bastante simple ya que solo se separa por comas
    // se abstrae en una función por si es necesario cambiar su funcionamiento luego.
    private String[] ProcesarLineaCSV(String linea) {
        return linea.split(",");
    }

    private String SiguienteLinea() throws IOException {
        return this.lectorArchivoCSV.readLine();
    }

    public RegistroCSV SiguienteRegistro() throws IOException {
        RegistroCSV registro = new RegistroCSV();

        String linea = this.SiguienteLinea();
        if (linea == null) return null;

        registro.setValores(this.ProcesarLineaCSV(linea));

        return registro;
    }

    public RegistroCSV SiguienteRegistroFiltrado(Predicate<RegistroCSV> condicion) throws IOException {
        if (this.enElFinalDelArchivo)
            return null;

        RegistroCSV registro = new RegistroCSV();

        while (true) {
            String linea = this.SiguienteLinea();

            // fin del archivo CSV
            if (linea == null) {
                this.enElFinalDelArchivo = true;
                return null;
            }
            String[] valores = this.ProcesarLineaCSV(linea);
            registro.setValores(valores);

            if (condicion.test(registro)) {
                break;
            }
        }

        return registro;
    }
}
