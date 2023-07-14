package src;
import lombok.Getter;
import lombok.Setter;
public class BoardingManagement {
    @Getter
    private static BoardingManagement instance;
    private BoardingGate[] gates;

    private BoardingManagement() {
        gates = new BoardingGate[11]; // Ejemplo de 11 puertas de embarque
        for (int i = 0; i < gates.length; i++) {
            gates[i] = new BoardingGate();
            gates[i].setGateNumber(i + 1);
            gates[i].setAvailability(true); // Todas las puertas están inicialmente habilitadas
        }
    }

    public static synchronized BoardingManagement getInstance() {
        if (instance == null) {
            instance = new BoardingManagement();
        }
        return instance;
    }

    public void getAvailability() {
        System.out.println("Disponibilidad de puertas de embarque:");
        for (BoardingGate gate : gates) {
            if (gate.isAvailability()) {
                System.out.println("Puerta " + gate.getGateNumber() + ": Disponible");
            } else if (gate.getAssignedAirplane() != null) {
                Airplane airplane = gate.getAssignedAirplane();
                System.out.println("Puerta " + gate.getGateNumber() + ": Ocupada por Avión " +
                        airplane.getRegistrationNumber() + " (" + airplane.getAirline() + ")");
            } else {
                System.out.println("Puerta " + gate.getGateNumber() + ": Ocupada");
            }
        }
    }

    public void assignAirplane(Airplane airplane, BoardingGate gate) {
        if (gate.isAvailability()) {
            gate.setAvailability(false);
            gate.setAssignedAirplane(airplane);
            airplane.setGate(gate.getGateNumber());
            System.out.println("Avión " + airplane.getRegistrationNumber() + " (" + airplane.getAirline() +
                    ") asignado a la puerta " + gate.getGateNumber());
        } else {
            System.out.println("La puerta " + gate.getGateNumber() + " está ocupada");
        }
    }

    public BoardingGate getBoardingGateByNumber(int gateNumber) {
        for (BoardingGate gate : gates) {
            if (gate.getGateNumber() == gateNumber) {
                return gate;
            }
        }
        return null;
    }
}
