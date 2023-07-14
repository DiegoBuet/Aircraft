package src;

import lombok.Getter;
import lombok.Setter;

public class BoardingGate {
    @Getter
    private int gateNumber;
    @Getter
    private boolean availability;
    @Getter @Setter
    private Airplane assignedAirplane; // Nuevo campo para almacenar el avión asignado

    public void setGateNumber(int gateNumber) {
        this.gateNumber = gateNumber;
    }
    public void setAssignedAirplane(Airplane airplane) {
        this.assignedAirplane = airplane;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

