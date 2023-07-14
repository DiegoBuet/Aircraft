package src;

import src.AircraftManagement;
import src.Airplane;
import src.BoardingGate;
import src.BoardingManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Scanner userInput = new Scanner(System.in);
    private static List<Airplane> airplanes = new ArrayList<>();
    private static AircraftManagement aircraftManagement = AircraftManagement.getInstance();
    private static BoardingManagement boardingManagement = BoardingManagement.getInstance();

    public static void main(String[] args) {
        int menuInput = 0;

        while (menuInput != 6) {
            showMainMenu();

            if (userInput.hasNextInt()) {
                menuInput = userInput.nextInt();
                userInput.nextLine(); // Consumir el carácter de salto de línea

                switch (menuInput) {
                    case 1:
                        createAirplane();
                        break;
                    case 2:
                        editAirplane();
                        break;
                    case 3:
                        deleteAirplane();
                        break;
                    case 4:
                        assignAirplaneToGate();
                        break;
                    case 5:
                        boardingManagement.getAvailability();
                        break;
                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                        break;
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

    private static void createAirplane() {
        Airplane airplane = new Airplane();

        System.out.print("Número de registro: ");
        int registrationNumber = validateFourDigitNumberInput();
        airplane.setRegistrationNumber(registrationNumber);

        System.out.print("Aerolínea: ");
        String airline = userInput.nextLine();
        airplane.setAirline(airline);

        System.out.print("Capacidad de pasajeros: ");
        int passengerCapacity = userInput.nextInt();
        userInput.nextLine(); // Consumir el carácter de salto de línea
        airplane.setPassengerCapacity(passengerCapacity);

        System.out.println("Estado:");
        System.out.println("1. Disponible");
        System.out.println("2. En mantenimiento");
        System.out.println("3. En vuelo");
        System.out.print("Ingrese el número correspondiente al estado: ");
        int stateChoice = userInput.nextInt();
        userInput.nextLine(); // Consumir el carácter de salto de línea

        String state;

        switch (stateChoice) {
            case 1:
                state = "Disponible";
                break;
            case 2:
                state = "En mantenimiento";
                break;
            case 3:
                state = "En vuelo";
                break;
            default:
                System.out.println("Opción inválida. Se asignará el estado como Disponible por defecto.");
                state = "Disponible";
                break;
        }

        airplane.setState(state);

        airplanes.add(airplane);
        aircraftManagement.createAirplane(airplane);
        System.out.println("Avión creado exitosamente.");
        // Mostrar lista de aviones
        System.out.println("Aviones agregados:");
        for (Airplane addedAirplane : airplanes) {
            System.out.println(addedAirplane);
            System.out.println("------------------------------");
        }
    }

    private static void editAirplane() {
        System.out.print("Número de registro del avión a editar: ");
        int registrationNumber = validateFourDigitNumberInput();
        userInput.nextLine(); // Consumir el carácter de salto de línea

        Airplane foundAirplane = null;

        for (Airplane existingAirplane : airplanes) {
            if (existingAirplane.getRegistrationNumber() == registrationNumber) {
                foundAirplane = existingAirplane;
                break;
            }
        }

        if (foundAirplane != null) {
            System.out.print("Nuevo número de registro: ");
            int newRegistrationNumber = validateFourDigitNumberInput();
            userInput.nextLine(); // Consumir el carácter de salto de línea
            foundAirplane.setRegistrationNumber(newRegistrationNumber);

            System.out.print("Nueva aerolínea: ");
            String newAirline = userInput.nextLine();
            foundAirplane.setAirline(newAirline);

            System.out.print("Nueva capacidad de pasajeros: ");
            int newPassengerCapacity = userInput.nextInt();
            userInput.nextLine(); // Consumir el carácter de salto de línea
            foundAirplane.setPassengerCapacity(newPassengerCapacity);

            System.out.print("Nuevo estado: ");
            String newState = userInput.nextLine();
            foundAirplane.setState(newState);

            aircraftManagement.editAirplane(foundAirplane);
            // Mostrar lista de aviones
            System.out.println("Aviones actualizados:");
            for (Airplane updatedAirplane : airplanes) {
                System.out.println(updatedAirplane);
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("No se encontró el avión con el número de registro proporcionado.");
        }
    }

    private static void deleteAirplane() {
        System.out.print("Número de registro del avión a eliminar: ");
        int registrationNumber = validateFourDigitNumberInput();
        userInput.nextLine(); // Consumir el carácter de salto de línea

        Airplane foundAirplane = null;

        for (Airplane existingAirplane : airplanes) {
            if (existingAirplane.getRegistrationNumber() == registrationNumber) {
                foundAirplane = existingAirplane;
                break;
            }
        }

        if (foundAirplane != null) {
            airplanes.remove(foundAirplane);
            aircraftManagement.deleteAirplane(foundAirplane);
            System.out.println("Avión eliminado exitosamente.");
            // Mostrar lista de aviones
            System.out.println("Aviones restantes:");
            for (Airplane remainingAirplane : airplanes) {
                System.out.println(remainingAirplane);
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("No se encontró el avión con el número de registro proporcionado.");
        }
    }

    private static void assignAirplaneToGate() {
        List<Airplane> availableAirplanes = new ArrayList<>();

        System.out.println("Aviones disponibles para asignar una puerta:");
        int index = 1;
        for (Airplane airplane : airplanes) {
            if (airplane.getState() != null && airplane.getState().equalsIgnoreCase("Disponible")) {
                System.out.println(index + ". " + airplane.getRegistrationNumber() + " (" + airplane.getAirline() + ")");
                availableAirplanes.add(airplane);
                index++;
            }
        }

        if (availableAirplanes.isEmpty()) {
            System.out.println("No hay aviones disponibles para asignar una puerta.");
            return;
        }

        System.out.print("Ingrese el número correspondiente al avión a asignar: ");
        int choice = userInput.nextInt();
        userInput.nextLine(); // Consumir el carácter de salto de línea

        if (choice >= 1 && choice <= availableAirplanes.size()) {
            Airplane selectedAirplane = availableAirplanes.get(choice - 1);

            System.out.print("Número de puerta de embarque: ");
            int gateNumber = userInput.nextInt();
            userInput.nextLine(); // Consumir el carácter de salto de línea

            BoardingGate gate = boardingManagement.getBoardingGateByNumber(gateNumber);

            if (gate != null) {
                try {
                    aircraftManagement.assignGate(selectedAirplane, gate);
                    System.out.println("Avión " + selectedAirplane.getRegistrationNumber() + " asignado exitosamente a la puerta " + gateNumber + ".");
                } catch (Exception e) {
                    System.out.println("Error al asignar la puerta. Detalles: " + e.getMessage());
                }
            } else {
                System.out.println("La puerta de embarque con el número " + gateNumber + " no existe.");
            }
        } else {
            System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private static int validateFourDigitNumberInput() {
        int registrationNumber = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Número de registro: ");
            String input = userInput.nextLine();
            if (input.matches("\\d{4}")) {
                registrationNumber = Integer.parseInt(input);
                validInput = true;
            } else {
                System.out.println("Solo se aceptan valores numéricos de 4 cifras. Intente nuevamente.");
            }
        }
        return registrationNumber;
    }
}
