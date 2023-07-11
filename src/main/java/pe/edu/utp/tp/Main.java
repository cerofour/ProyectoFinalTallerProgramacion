package pe.edu.utp.tp;

import java.util.*;

//
public class Main {

    public static void main(String[] args) {
        String usuario="";
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        LectorCSV lector = new LectorCSV();
        Scanner leerEntradaUsuario = new Scanner(System.in);
        Auditoria auditoria = new Auditoria();
        VerificarCredenciales Sesion = new VerificarCredenciales();

        // Inicio de sesion
        System.out.println("[+] Login:");
        if(!(Sesion.LoguearUsuario())){
            System.out.println("MENSAJE: No se pudo ingresar");
            System.exit(1);
        }else{
            System.out.println("MENSAJE: Sesion iniciada correctamente!\n");
            usuario = Sesion.getUsuario();
        }
        //

        // Menu de opciones
        do {
            System.out.println("""
                    --------------------------------------------------------
                    MENU PRINCIPAL
                    --------------------------------------------------------
                    1) Personas por condición de donación dado un departamento.
                    2) Personas por provincia, sexo y condición de donación dado un rango de
                       edades y un departamento.
                    3) Donantes por departamento dada una edad y sexo.
                    4) Personas por departamento dada una condición de donación.
                    0) FIN DEL PROGRAMA
                    --------------------------------------------------------""");
            System.out.print("Ingrese opción [0 – 4]: ");
            try {
                int opcion = leerEntradaUsuario.nextInt();
                menuPrincipal.OpcionesProceso(opcion);
            }catch (InputMismatchException e){
                System.err.println("ERROR: Solo se admiten números enteros.");
                auditoria.RegistrarExcepcion(usuario, e);
            }

        } while (true);
        //

        /*
        try {
            lector.Inicializar();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < 10; i++) {
            String linea = lector.SiguienteLinea();
            System.out.println(linea);
        }
        */
    }
}