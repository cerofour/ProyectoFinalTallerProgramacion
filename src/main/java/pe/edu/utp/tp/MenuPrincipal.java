package pe.edu.utp.tp;



public class MenuPrincipal {

    public void OpcionesProceso (int opcion){
        switch (opcion){
            case 0:
                System.out.println("INFO: Saliendo del programa...");
                System.exit(0);
                break;
            case 1:
                MenuSecundario mn = new MenuSecundario();
                String[] n = {"hoa", "qetlasd", "sdcs"};
                mn.ImprimirTabla(n);
                break;
            case 2:
                //
                break;
            case 3:
                //
                break;
            case 4:
                //
                break;
            default:
                System.out.println("ERROR: Opcion no valida\n");
                break;

        }
    }




}
