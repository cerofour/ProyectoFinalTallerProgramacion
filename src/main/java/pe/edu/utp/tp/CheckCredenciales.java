package pe.edu.utp.tp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CheckCredenciales {
    private int Intentos = 3;
    private String[][] UsersPasswordsFile;

    public boolean ReadFileUserPasswords(String file_) throws FileNotFoundException {
        File nombreArchivo = new File(file_);
        String[] temporal;
        String datosExtraidos = "";
        String strrng;

        Scanner contentFile = new Scanner(nombreArchivo);
        while (contentFile.hasNextLine()){
            strrng = contentFile.nextLine();
            if(strrng.contains("::") && !(strrng.contains("//"))){
                datosExtraidos += strrng+"\n";
            }
        }
        if(!(datosExtraidos.equals(""))) {
            temporal = datosExtraidos.split("\n");
            int cantidadUsers = temporal.length;
            UsersPasswordsFile = new String[cantidadUsers][cantidadUsers];
            for (int i = 0; i < cantidadUsers; i++) {
                UsersPasswordsFile[0][i] = temporal[i].split("::")[0];
                UsersPasswordsFile[1][i] = temporal[i].split("::")[1];
            }
            return true;
        }

        contentFile.close();
        return false;
    }

    public boolean CheckCredencialesInput(String user, String password){
        if (Intentos > 0) {
            for (int i = 0; i < UsersPasswordsFile[0].length; i++) {
                if (UsersPasswordsFile[0][i].equals(user)) {
                    if (UsersPasswordsFile[1][i].equals(password)) {
                        System.out.println("MENSAJE: Credenciales correctas!");
                       return true;
                    }else{
                        Intentos--;
                        System.out.println("MENSAJE: Credenciales incorrectas!\nIntentos: "+Intentos);
                        if(Intentos == 0){
                            System.out.println("MENSAJE: Intentos agotados.!");
                        }
                        return false;
                    }
                }
            }
            Intentos--;
            System.out.println("MENSAJE: Usuario no encontrado\nIntentos: "+Intentos);
        }
        return false;
    }

    public int getIntentos() {
        return Intentos;
    }
}


