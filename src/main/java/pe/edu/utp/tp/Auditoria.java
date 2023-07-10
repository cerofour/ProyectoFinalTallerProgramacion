package pe.edu.utp.tp;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clase

public class Auditoria {
    public String pathArchivo = "F:\\UTP\\CICLO III\\TALLER DE PROGRAMACION\\ProyectFinal\\src\\main\\java\\pe\\edu\\utp\\Application\\auditoria.log";
    public void RegistrarExcepcion(String User_, Exception Exception, String MessageException){
        String datetime = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss a").format(LocalDateTime.now());
        try{
            File archivo = new File(pathArchivo);
            FileWriter escribir = new FileWriter(archivo, true);
            escribir.write(String.format("[%s] Excepción Capturada!: Usuario: %s | Excepcion: %s | Mensaje: %s\n", datetime,
                    User_, Exception.toString().split(":")[0], MessageException));
            escribir.close();
        }catch (Exception e){
            System.out.println("ERROR: Se generó un error de escritura al registrar la excepcion.");
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
            System.out.println("ERROR: Se generó un error de escritura al registrar la excepcion.");
        }

    }


}
