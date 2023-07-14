package src;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner  userInput = new Scanner(System.in);
        int menuInput=1;
        while (menuInput > 0 && menuInput <= 6){

            System.out.println("""
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                1_Agregar Avión.
                2_Editar Avión.
                3_Eliminar Avión.
                4_Asignar Avión.
                5_Gestión de puertas de embarque.
                6_Salir.
                """);
            menuInput = userInput.nextInt();userInput.nextLine();

        }
        while (menuInput > 0 && menuInput <= 6){

            System.out.println("""
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                """);
            System.out.print("Ingrese número de registro");
            menuInput = userInput.nextInt();userInput.nextLine();
            System.out.print("Ingrese nombre de la aerolinea");
            menuInput = userInput.nextInt();userInput.nextLine();
            System.out.print("Ingrese la Capacidad de pasajeros del avión");
            menuInput = userInput.nextInt();userInput.nextLine();
            System.out.print("Ingrese el estado");
            menuInput = userInput.nextInt();userInput.nextLine();

        }


    }
}
