package pe.edu.utp.tp.Login;

import pe.edu.utp.tp.Auditoria.Auditoria;
import pe.edu.utp.tp.Utilidades.Tupla;

import java.io.*;
import java.util.Scanner;

// Clase que tiene como funcion loguear al usuario para acceder a las
// funciones del programa
// Inicializa los pares usuario/contraseña leyendo el archivo de recursos password.txt
// luego de este paso ya se puede realizar el login.
public class VerificadorCredenciales {
    private int intentos = 3;

    private Tupla<String, String>[] paresUsuarioContrasena;
    private boolean paresUsuarioContrasenaInicializado;

    private String usuario;

    public VerificadorCredenciales() {
        this.paresUsuarioContrasena = new Tupla[1];
        paresUsuarioContrasena[0] = new Tupla("dev", "dev");
    }

    private BufferedReader AbrirArchivoUsuarios() {
        InputStream usuariosInputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("password.txt");
        if (usuariosInputStream == null) {
            Auditoria audi = new Auditoria();
            audi.RegistrarExcepcion(new Exception("Archivo de usuarios no encontrado. Utilize las credenciales dev:dev."));
        }

        // esto siempre debería ser verdadero
        assert usuariosInputStream != null;

        return new BufferedReader(new InputStreamReader(usuariosInputStream));

    }

    private void CrearParesUsuarioContrasena() throws IOException {
        BufferedReader lector = this.AbrirArchivoUsuarios();
        Auditoria audi = new Auditoria();
        String linea = null;

        for (linea = lector.readLine(); linea != null; linea = lector.readLine()) {
            if (linea.startsWith("//"))
                 continue;
            String[] usuarioContrasena = linea.split(":");
            if (usuarioContrasena.length != 2)
                continue;

                // TODO: crear una función genérica para hacer añadir un valor a un arreglo.
                int longitudAnterior = this.paresUsuarioContrasena.length;
                Tupla<String, String>[] nuevoArregloDePares = new Tupla[longitudAnterior + 1];
                System.arraycopy(this.paresUsuarioContrasena,
                        0,
                        nuevoArregloDePares,
                        0,
                        longitudAnterior);
                this.paresUsuarioContrasena = nuevoArregloDePares;
                this.paresUsuarioContrasena[longitudAnterior] = new Tupla(usuarioContrasena[0], usuarioContrasena[1]);
            }

        if (this.paresUsuarioContrasena.length == 1) {
            audi.RegistrarAdvertencias("Archivo de usuarios vacío, utilize las credenciales por defecto dev:dev.");
        }
        this.paresUsuarioContrasenaInicializado = true;
    }

    public boolean LoguearUsuario() throws IOException {
        boolean archivoLeido;
        boolean  pskNoValido;
        String usuario;
        String pass;
        Scanner lector = new Scanner(System.in);

        if (!this.paresUsuarioContrasenaInicializado) {
            this.CrearParesUsuarioContrasena();
        }

        do {
            System.out.print("Usuario: ");
            usuario = lector.nextLine();
            System.out.print("Contraseña: ");
            pass = lector.nextLine();
            pskNoValido = false;

            // TODO: refactorizar esto, muchos niveles de indentado.
            if (intentos > 0){
                for (int i = 0; i < paresUsuarioContrasena.length; i++) {
                    if (paresUsuarioContrasena[i].primero.equals(usuario)) {
                        if (paresUsuarioContrasena[i].segundo.equals(pass)) {
                            System.out.println("Credenciales correctas.");
                            this.usuario = usuario;
                            return true;
                        } else {
                            intentos--;
                            System.out.println("Credenciales incorrectas\nQuedan " + intentos + " de loggeo");
                            if (intentos == 0) {
                                System.out.println("Intentos de loggeo agotados.");
                            }
                            pskNoValido = true;
                        }
                    }
                }
                if(!(pskNoValido)) {
                    intentos--;
                    System.out.println("Usuario no encontrado\nIntentos: " + intentos);
                }
            }
        } while (intentos > 0);
        return false;
    }

    public String getUsuario() {
        return usuario;
    }

}


