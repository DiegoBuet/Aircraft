package src;

import lombok.Getter;
import lombok.Setter;
public class Airplane {
    @Getter @Setter
    private int registrationNumber;
    @Getter @Setter
    private String airline;
    @Getter @Setter
    private int passengerCapacity;
    @Getter @Setter
    private String state;
    @Getter @Setter
    private int gate;

    @Override
    public String toString() {
        return "Airplane{" +
                "Registration Number: " + registrationNumber +
                ", Airline: '" + airline + '\'' +
                ", Passenger Capacity: " + passengerCapacity +
                ", State: '" + state + '\'' +
                '}';
    }
}
