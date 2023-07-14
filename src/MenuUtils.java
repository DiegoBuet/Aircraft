package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuUtils {
    private static Scanner userInput = new Scanner(System.in);

    private MenuUtils() {
    }

    public static void createAirplane(List<Airplane> airplanes, AircraftManagement aircraftManagement) {
        Airplane airplane = new Airplane();

        System.out.print("Número de registro: ");
        int registrationNumber = validateFourDigitNumberInput();
        airplane.setRegistrationNumber(registrationNumber);

        System.out.print("Aerolínea: ");
        String airline = userInput.nextLine();
        airplane.setAirline(airline);

        int passengerCapacity = 0;
        boolean validPassengerCapacity = false;
        while (!validPassengerCapacity) {
            System.out.print("Capacidad de pasajeros: ");
            String input = userInput.nextLine();
            if (input.matches("\\d+") && Integer.parseInt(input) > 0) {
                passengerCapacity = Integer.parseInt(input);
                validPassengerCapacity = true;
            } else {
                System.out.println("Ingrese un valor numérico mayor a 0.");
            }
        }
        airplane.setPassengerCapacity(passengerCapacity);

        String state = selectState();
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

    public static void editAirplane(List<Airplane> airplanes, AircraftManagement aircraftManagement) {
        List<Airplane> availableAirplanes = getAvailableAirplanes(airplanes);

        if (availableAirplanes.isEmpty()) {
            System.out.println("No hay aviones disponibles para editar.");
            return;
        }

        System.out.println("Seleccione el avión a editar:");
        int index = 1;
        for (Airplane airplane : availableAirplanes) {
            System.out.println(index + ". " + airplane.getRegistrationNumber() + " (" + airplane.getAirline() + ")");
            index++;
        }

        System.out.print("Ingrese el número correspondiente al avión a editar: ");
        int choice = userInput.nextInt();
        userInput.nextLine(); // Consumir el carácter de salto de línea

        if (choice >= 1 && choice <= availableAirplanes.size()) {
            Airplane selectedAirplane = availableAirplanes.get(choice - 1);

            System.out.print("Nuevo número de registro: ");
            int newRegistrationNumber = validateFourDigitNumberInput();
            selectedAirplane.setRegistrationNumber(newRegistrationNumber);

            System.out.print("Nueva aerolínea: ");
            String newAirline = userInput.nextLine();
            selectedAirplane.setAirline(newAirline);

            int newPassengerCapacity = 0;
            boolean validPassengerCapacity = false;
            while (!validPassengerCapacity) {
                System.out.print("Nueva capacidad de pasajeros: ");
                String input = userInput.nextLine();
                if (input.matches("\\d+") && Integer.parseInt(input) > 0) {
                    newPassengerCapacity = Integer.parseInt(input);
                    validPassengerCapacity = true;
                } else {
                    System.out.println("Ingrese un valor numérico mayor a 0.");
                }
            }
            selectedAirplane.setPassengerCapacity(newPassengerCapacity);

            String newState = selectState();
            selectedAirplane.setState(newState);

            aircraftManagement.editAirplane(selectedAirplane);
            showUpdatedAirplanes(airplanes);
        } else {
            System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    public static void deleteAirplane(List<Airplane> airplanes, AircraftManagement aircraftManagement, BoardingManagement boardingManagement) {
        List<Airplane> availableAirplanes = getAvailableAirplanes(airplanes);

        if (availableAirplanes.isEmpty()) {
            System.out.println("No hay aviones disponibles para eliminar.");
            return;
        }

        System.out.println("Seleccione el avión a eliminar:");
        int index = 1;
        for (Airplane airplane : availableAirplanes) {
            System.out.println(index + ". " + airplane.getRegistrationNumber() + " (" + airplane.getAirline() + ")");
            index++;
        }

        System.out.print("Ingrese el número correspondiente al avión a eliminar: ");
        int choice = userInput.nextInt();
        userInput.nextLine(); // Consumir el carácter de salto de línea

        if (choice >= 1 && choice <= availableAirplanes.size()) {
            Airplane selectedAirplane = availableAirplanes.get(choice - 1);

            // Liberar la puerta asignada al avión eliminado
            if (selectedAirplane.getGate() != 0) {
                BoardingGate gate = boardingManagement.getBoardingGateByNumber(selectedAirplane.getGate());
                if (gate != null) {
                    gate.setAssignedAirplane(null);
                    gate.setAvailability(true);
                }
            }

            airplanes.remove(selectedAirplane);
            aircraftManagement.deleteAirplane(selectedAirplane);
            System.out.println("Avión eliminado exitosamente.");

            // Mostrar lista de aviones restantes
            System.out.println("Aviones restantes:");
            for (Airplane remainingAirplane : airplanes) {
                System.out.println(remainingAirplane);
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private static void showUpdatedAirplanes(List<Airplane> airplanes) {
        System.out.println("Aviones actualizados:");
        for (Airplane updatedAirplane : airplanes) {
            System.out.println(updatedAirplane);
            System.out.println("------------------------------");
        }
    }

    private static void showAvailableAirplanes(List<Airplane> airplanes) {
        System.out.println("Aviones disponibles para asignar una puerta:");
        int index = 1;
        for (Airplane airplane : airplanes) {
            if (airplane.getState() != null && airplane.getState().equalsIgnoreCase("Disponible")) {
                System.out.println(index + ". " + airplane.getRegistrationNumber() + " (" + airplane.getAirline() + ")");
                index++;
            }
        }

        if (index == 1) {
            System.out.println("No hay aviones disponibles para asignar una puerta.");
        }
    }

    public static void assignAirplaneToGate(List<Airplane> airplanes, AircraftManagement aircraftManagement, BoardingManagement boardingManagement) {
        showAvailableAirplanes(airplanes);

        if (airplanes.isEmpty()) {
            return;
        }

        System.out.print("Ingrese el número correspondiente al avión a asignar: ");
        int choice = userInput.nextInt();
        userInput.nextLine(); // Consumir el carácter de salto de línea

        if (choice >= 1 && choice <= airplanes.size()) {
            Airplane selectedAirplane = airplanes.get(choice - 1);

            System.out.print("Número de puerta de embarque: ");
            int gateNumber = userInput.nextInt();
            userInput.nextLine(); // Consumir el carácter de salto de línea

            if (gateNumber >= 1 && gateNumber <= 11) {
                BoardingGate gate = boardingManagement.getBoardingGateByNumber(gateNumber);

                if (gate != null) {
                    try {
                        if (gate.isAvailability()) {
                            // Eliminar la asignación existente en la puerta, si la hay
                            if (selectedAirplane.getGate() != 0) {
                                BoardingGate previousGate = boardingManagement.getBoardingGateByNumber(selectedAirplane.getGate());
                                if (previousGate != null) {
                                    previousGate.setAssignedAirplane(null);
                                    previousGate.setAvailability(true);
                                }
                            }

                            // Asignar el avión a la nueva puerta
                            boardingManagement.assignAirplane(selectedAirplane, gate);
                            System.out.println("Avión " + selectedAirplane.getRegistrationNumber() + " asignado exitosamente a la puerta " + gateNumber + ".");
                        } else {
                            System.out.println("La puerta " + gateNumber + " está ocupada por otro avión.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al asignar la puerta. Detalles: " + e.getMessage());
                    }
                } else {
                    System.out.println("La puerta de embarque con el número " + gateNumber + " no existe.");
                }
            } else {
                System.out.println("Número de puerta inválido. Intente nuevamente.");
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

    public static String selectState() {
        System.out.println("Estado:");
        System.out.println("1. Disponible");
        System.out.println("2. En mantenimiento");
        System.out.println("3. En vuelo");
        System.out.print("Ingrese el número correspondiente al estado: ");
        int stateChoice = userInput.nextInt();
        userInput.nextLine(); // Consumir el carácter de salto de línea

        String state;

        switch (stateChoice) {
            case 1 -> state = "Disponible";
            case 2 -> state = "En mantenimiento";
            case 3 -> state = "En vuelo";
            default -> {
                System.out.println("Opción inválida. Se asignará el estado como Disponible por defecto.");
                state = "Disponible";
            }
        }

        return state;
    }

    private static List<Airplane> getAvailableAirplanes(List<Airplane> airplanes) {
        List<Airplane> availableAirplanes = new ArrayList<>();
        int index = 1;

        for (Airplane airplane : airplanes) {
            if (airplane.getState() != null && airplane.getState().equalsIgnoreCase("Disponible")) {
                availableAirplanes.add(airplane);
                System.out.println(index + ". " + airplane.getRegistrationNumber() + " (" + airplane.getAirline() + ")");
                index++;
            }
        }

        return availableAirplanes;
    }
}


