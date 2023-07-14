package src;

import src.MenuUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        List<Airplane> airplanes = new ArrayList<>();
        AircraftManagement aircraftManagement = AircraftManagement.getInstance();
        BoardingManagement boardingManagement = BoardingManagement.getInstance();

        int menuInput = 0;

        while (menuInput != 6) {
            showMainMenu();

            if (userInput.hasNextInt()) {
                menuInput = userInput.nextInt();
                userInput.nextLine(); // Consumir el carácter de salto de línea

                switch (menuInput) {
                    case 1 -> MenuUtils.createAirplane(airplanes, aircraftManagement);
                    case 2 -> MenuUtils.editAirplane(airplanes, aircraftManagement);
                    case 3 -> MenuUtils.deleteAirplane(airplanes, aircraftManagement);
                    case 4 -> MenuUtils.assignAirplaneToGate(airplanes, aircraftManagement, boardingManagement);
                    case 5 -> boardingManagement.getAvailability(); // Modificar si es necesario
                    case 6 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } else {
                userInput.nextLine(); // Consumir la entrada inválida
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("""
                1_Agregar Avión.
                2_Editar Avión.
                3_Eliminar Avión.
                4_Asignar Avión.
                5_Gestión de puertas de embarque.
                6_Salir.
                """);
    }
}

