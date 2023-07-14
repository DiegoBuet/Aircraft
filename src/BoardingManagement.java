package src;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
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

    public BoardingGate getBoardingGateByNumber(int gateNumber) {
        if (gateNumber >= 1 && gateNumber <= gates.length) {
            return gates[gateNumber - 1];
        } else {
            return null;
        }
    }

    public void assignAirplane(Airplane airplane, BoardingGate gate) {
        gate.setAvailability(false);
        gate.setAssignedAirplane(airplane);
        airplane.setGate(gate.getGateNumber());
        System.out.println("Avión " + airplane.getRegistrationNumber() + " (" + airplane.getAirline() +
                ") asignado a la puerta " + gate.getGateNumber());
    }
  /*  public void assignGate(Airplane airplane, BoardingGate gate) throws Exception {
        if (gate.isAvailability()) {
            if (gate.getAssignedAirplane() == null) {
                gate.setAvailability(false);
                gate.setAssignedAirplane(airplane);
                airplane.setGate(gate.getGateNumber());
            } else {
                throw new Exception("La puerta " + gate.getGateNumber() + " está ocupada por otro avión.");
            }
        } else {
            throw new Exception("La puerta " + gate.getGateNumber() + " está ocupada.");
        }
    }*/
}