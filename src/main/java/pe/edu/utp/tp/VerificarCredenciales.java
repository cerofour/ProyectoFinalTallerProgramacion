package pe.edu.utp.tp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Clase que tiene como funcion loguear al usuario para acceder a las
// funciones del programa.
//

public class VerificarCredenciales {
    private int intentos = 3;
    private String[][] usuariosPskArray;
    Auditoria auditoria = new Auditoria();

    private boolean LeerArchivoUsuariosPsk(String file_) throws FileNotFoundException {
        File nombreArchivo = new File(file_);
        String[] temporal;
        String datosExtraidos = "";
        String strrng;

        Scanner contentFile = new Scanner(nombreArchivo);
        while (contentFile.hasNextLine()) {
            strrng = contentFile.nextLine();
            if (strrng.contains("::") && !(strrng.contains("//"))) {
                datosExtraidos += strrng + "\n";
            }
        }
        if (!(datosExtraidos.equals(""))) {
            temporal = datosExtraidos.split("\n");
            int cantidadUsers = temporal.length;
            usuariosPskArray = new String[cantidadUsers][cantidadUsers];
            for (int i = 0; i < cantidadUsers; i++) {
                usuariosPskArray[0][i] = temporal[i].split("::")[0];
                usuariosPskArray[1][i] = temporal[i].split("::")[1];
            }
            return true;
        }

        contentFile.close();
        return false;
    }

    public boolean LoguearUsuario() {
        boolean archivoLeido;
        boolean  pskNoValido;
        String user;
        String password;
        Scanner lector = new Scanner(System.in);

        try {
            archivoLeido = LeerArchivoUsuariosPsk(
                    "F:\\UTP\\CICLO III\\TALLER DE PROGRAMACION\\ProyectoFinal\\src\\main\\resources\\password.txt");
        }catch (FileNotFoundException e){
            auditoria.RegistrarExcepcion(e);
            System.err.println("ERROR: No se pudo abrir el archivo de usuarios y contraseñas.");
            return false;
        }

        do {
            System.out.print("Usuario: ");
            user = lector.nextLine();
            System.out.print("Contraseña: ");
            password = lector.nextLine();
            pskNoValido = false;
            if (intentos > 0 && archivoLeido){
                for (int i = 0; i < usuariosPskArray[0].length; i++) {
                    if (usuariosPskArray[0][i].equals(user)) {
                        if (usuariosPskArray[1][i].equals(password)) {
                            System.out.println("MENSAJE: Credenciales correctas!");
                            return true;
                        } else {
                            intentos--;
                            System.out.println("MENSAJE: Credenciales incorrectas!\nIntentos: " + intentos);
                            if (intentos == 0) {
                                System.out.println("MENSAJE: Intentos agotados.!");
                            }
                            pskNoValido = true;
                        }
                    }
                }
                if(!(pskNoValido)) {
                    intentos--;
                    System.out.println("MENSAJE: Usuario no encontrado\nIntentos: " + intentos);
                }
            }
        } while (intentos > 0);
        return false;
    }

}


