package pe.edu.utp.tp;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Esta clase se utiliza para capturar las excepciones generadas en tiempo de ejecucion
// y son guardadas en el archivo auditoria.log con detalles sobre la excepcion.
//
public class Auditoria {
    public String pathArchivo = "F:\\UTP\\CICLO III\\TALLER DE PROGRAMACION\\ProyectoFinal\\src\\main\\java\\pe\\edu\\utp\\tp\\auditoria.log";
    public void RegistrarExcepcion(String User_, Exception exception){
        String datetime = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss a").format(LocalDateTime.now());
        try{
            File archivo = new File(pathArchivo);
            FileWriter escribir = new FileWriter(archivo, true);
            escribir.write(String.format("[%s] Excepción Capturada!: Usuario: %s | Excepcion: %s | Mensaje: %s\n", datetime,
                    User_, exception.toString().split(":")[0], exception.getMessage()));
            escribir.close();
        }catch (Exception e){
            System.err.println("ERROR: Se generó un error de escritura al registrar la excepcion.");
        }

    }
    public void RegistrarExcepcion(Exception excepcion){
        String datetime = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss a").format(LocalDateTime.now());
        try{
            File archivo = new File(pathArchivo);
            FileWriter escribir = new FileWriter(archivo, true);
            escribir.write(String.format("[%s] Esxcepción Capturada!: Usuario: No logueado | Excepcion: %s | Mensaje: %s\n", datetime,
                    excepcion.toString().split(":")[0], excepcion.getMessage()));
            escribir.close();
        }catch (Exception e){
            System.err.println("ERROR: Se generó un error de escritura al registrar la excepcion.");
        }

    }


}
