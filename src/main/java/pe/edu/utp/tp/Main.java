package pe.edu.utp.tp;

public class Main {
    public static void main(String[] args) {
        Programa programa = null;
        try {
            programa = new Programa();
            programa.Ejecutar();
        } catch (Exception e) {
            Auditoria audi = new Auditoria();
            if (programa != null)
                audi.RegistrarExcepcion(programa.getUsuario(), e);
            else
                audi.RegistrarExcepcion(e);
        }
    }
}