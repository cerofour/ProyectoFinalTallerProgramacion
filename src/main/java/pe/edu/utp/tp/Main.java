package pe.edu.utp.tp;

import java.util.InputMismatchException;
import java.util.Scanner;

//
public class Main {

    public static void main(String[] args) {
        String usuario="";
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        LectorCSV lector = new LectorCSV();
        Scanner leerEntradaUsuario = new Scanner(System.in);
        Auditoria auditoria = new Auditoria();
        VerificarCredenciales Sesion = new VerificarCredenciales();

        menuPrincipal.AgregarOpcion("Personas por condición de donación dado un departamento");
        menuPrincipal.AgregarOpcion("Personas por provincia, sexo y condición de donación dado un rango de edades y departamento");
        menuPrincipal.AgregarOpcion("Donantes por departamento dada una edad y sexo");
        menuPrincipal.AgregarOpcion("Personas por departamento dada una condición de donación");

        // Inicio de sesion
        System.out.println("[+] Login:");
        if(!(Sesion.LoguearUsuario())){
            System.out.println("MENSAJE: No se pudo ingresar");
            System.exit(1);
        }else{
            System.out.println("MENSAJE: Sesion iniciada correctamente!\n");
            usuario = Sesion.getUsuario();
        }

        // Menu de opciones
        do {
            try {
                int opcion = leerEntradaUsuario.nextInt();
                menuPrincipal.OpcionesProceso(opcion);
            }catch (InputMismatchException e){
                System.err.println("ERROR: Solo se admiten números enteros.");
                auditoria.RegistrarExcepcion(usuario, e);
            }

        } while (true);
    }
}