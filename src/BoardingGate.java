package src;

import lombok.Getter;
import lombok.Setter;
public class BoardingGate {
    @Getter @Setter
    private int gateNumber;
    @Getter @Setter
    private boolean availability;
    @Getter @Setter
    private Airplane assignedAirplane;
}
