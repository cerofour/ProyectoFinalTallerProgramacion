package pe.edu.utp.tp.LecturaCSV;

import java.io.*;
import java.util.function.Predicate;

// Autor del modulo: Diego Alexis Llacsahuanga Buques

// Esta clase se utiliza para abrir y leer un archivo CSV.
// define diversos métodos para extraer información por regsitro/campo, etc.

public class LectorCSV {

    BufferedReader lectorArchivoCSV;
    FileInputStream archivoCSV;
    // Marca si el lector se encuentra en el final del archivo o no.
    boolean enElFinalDelArchivo = false;

    // Coloca el BufferedReader al inicio del archivo. Esto debe hacerse después de cada vez que se
    // termine de leer todo el archivo o no será posible leer otra vez los datos.
    private void InicializarLector() throws IOException {
        // asigna el archivo CSV del que extreaeremos los datos.

        this.archivoCSV = new FileInputStream(getClass()
                .getClassLoader()
                .getResource("soypro.csv")
                .getPath());

        if (this.lectorArchivoCSV != null)
            this.lectorArchivoCSV.close();
        this.lectorArchivoCSV = new BufferedReader(new InputStreamReader(this.archivoCSV));
    }

    public void Reiniciar() throws IOException {
        this.archivoCSV.close();
        this.InicializarLector();
        this.SaltarComentarios();
        this.lectorArchivoCSV.readLine(); // Saltar la línea de cabeceras
    }

    // TODO: este método debería leer un archivo csv del usuario, no del archivo de recursos.
    public void Inicializar() throws Exception {

        this.InicializarLector();

        // inicializa todo lo demás que sea necesario.
        if (RegistroCSV.getCabeceras() == null)
            this.CreaCabeceras();
    }

    private String SaltarComentarios() throws IOException {
        // Al final de este bucle, lineaActual debería ser la linea que contiene las cabeceras.
        String lineaActual = this.SiguienteLinea();
        for (; lineaActual != null && lineaActual.length() > 0; lineaActual = this.SiguienteLinea())
            if (lineaActual.trim().startsWith("//") == false)
                return lineaActual;

        // no alcanzable
        return null;
    }

    private void CreaCabeceras() throws Exception {
        // Despues de crear el BufferedReader,  las cabeceras del archivo CSV. Las cabeceras son los valores separados por coma en la primera línea que
        // NO empieza por // (línea comentada)

        String linea = this.SaltarComentarios();

        // Archivo vacío
        String[] cabeceras = this.ProcesarLineaCSV(linea);
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
        if (this.enElFinalDelArchivo) {
            this.Reiniciar();
            this.enElFinalDelArchivo = false;
        }

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