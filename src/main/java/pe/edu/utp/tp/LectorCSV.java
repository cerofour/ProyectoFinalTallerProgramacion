package pe.edu.utp.tp;

import java.io.*;

// Esta clase se utiliza para abrir y leer un archivo CSV.
// define diversos métodos para extraer información por regsitro/campo, etc.

public class LectorCSV {

    BufferedReader lectorArchivoCSV;

    public LectorCSV() {
    }

    public void Inicializar() throws FileNotFoundException {
        InputStream csvInputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("Registro 2022 Donantes Órganos Perú (DATASET).csv");
        if (csvInputStream == null)
            throw new FileNotFoundException("Base de datos inexistente");

        this.lectorArchivoCSV = new BufferedReader(new InputStreamReader(csvInputStream));
    }

    public String SiguienteLinea() {
        try {
            String linea = this.lectorArchivoCSV.readLine();
            return linea;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
