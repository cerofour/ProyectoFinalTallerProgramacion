package pe.edu.utp.tp;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Esta clase se utiliza para capturar las excepciones generadas en tiempo de ejecucion
// y son guardadas en el archivo auditoria.log con detalles sobre la excepcion.
//
public class Auditoria {
    Scanner lector = new Scanner(System.in);

    public String pathArchivo = "./auditoria.log";

    private void EscribirEnArchivo(String mensaje){
        String datetime = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss a").format(LocalDateTime.now());
        try{
            File archivo = new File(pathArchivo);
            FileWriter escribir = new FileWriter(archivo, true);
            escribir.write(String.format("[%s] %s\n", datetime, mensaje));
            escribir.close();
        }catch (Exception e){
            System.err.println("ERROR: Se generó un error de escritura al registrar la excepcion.");
        }
    }

    private void PreguntaEjecucion(){
        System.out.println("[?] Desea continuar con la ejecucion del programa? (s/n): ");
        char opcionAuditoria = lector.nextLine().toLowerCase().charAt(0);
        if(opcionAuditoria != 's'){
            System.exit(1);
        }
    }

    public void RegistrarExcepcion(String usuario, Exception excepcion){
        EscribirEnArchivo(String.format("Excepción Capturada!: Usuario: %s | Excepcion: %s | Mensaje: %s\n",
                usuario, excepcion.toString().split(":")[0], excepcion.getMessage()));
        PreguntaEjecucion();
    }

    public void RegistrarExcepcion(Exception excepcion){
        EscribirEnArchivo(String.format("Excepción Capturada!: Usuario: No logueado | Excepcion: %s | Mensaje: %s\n",
                excepcion.toString().split(":")[0], excepcion.getMessage()));
        PreguntaEjecucion();
    }

    public void RegistrarExcepcionIrrecuperable(Exception excepcion){
        EscribirEnArchivo(String.format("Excepción irrecuperable capturada!: Usuario: No logueado | Excepcion: %s | Mensaje: %s\n",
                excepcion.toString().split(":")[0], excepcion.getMessage()));
        System.err.println("ERROR: Se produjo una excepcion de la cual el programa no se puede recuperar.");
        System.exit(1);
    }

    public void RegistrarExcepcionIrrecuperable(String usuario, Exception excepcion){
        EscribirEnArchivo(String.format("Excepción irrecuperable capturada!: Usuario: %s | Excepcion: %s | Mensaje: %s\n",
                usuario, excepcion.toString().split(":")[0], excepcion.getMessage()));
        System.err.println("ERROR: Se produjo una excepcion de la cual el programa no se puede recuperar.");
        System.exit(1);

    }

    public void RegistrarAdvertencias(String usuario, String advertencia){
        EscribirEnArchivo(String.format("Advertencia generada!: Usuario: %s | Advertencia: %s\n",
                usuario, advertencia));
    }

    public void RegistrarAdvertencias(String advertencia){
        String adv = String.format("Advertencia generada!: Usuario: No logueado | Advertencia: %s\n",
                advertencia);
        EscribirEnArchivo(adv);
        System.err.println(adv);
    }

}
