package pe.edu.utp.tp;

import java.io.*;
import java.util.function.Predicate;

// Esta clase se utiliza para abrir y leer un archivo CSV.
// define diversos métodos para extraer información por regsitro/campo, etc.

public class LectorCSV {

    BufferedReader lectorArchivoCSV;

    public LectorCSV() {
    }

    // TODO: este método debería leer un archivo csv del usuario, no del archivo de recursos.
    public void Inicializar() throws FileNotFoundException {

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

    private void CreaCabeceras() {
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

    private String SiguienteLinea() {
        try {
            return this.lectorArchivoCSV.readLine();
        } catch (IOException e) {
            Auditoria audi = new Auditoria();
            audi.RegistrarExcepcion(e);
            return null;
        }
    }

    public RegistroCSV SiguienteRegistro() {
        RegistroCSV registro = new RegistroCSV();

        String linea = this.SiguienteLinea();
        if (linea == null) return null;

        registro.setValores(this.ProcesarLineaCSV(linea));

        return registro;
    }

    public RegistroCSV SiguienteRegistroFiltrado(Predicate<RegistroCSV> condicion) {
        RegistroCSV registro = new RegistroCSV();

        String linea = this.SiguienteLinea();
        while (linea != null) {
            String[] valores = this.ProcesarLineaCSV(linea);
            registro.setValores(valores);

            if (condicion.test(registro)) {
                break;
            } else {
                linea = this.SiguienteLinea();
            }
        }

        return registro;
    }
}
