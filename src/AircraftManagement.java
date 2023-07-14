package src;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AircraftManagement{
    @Getter
    private static AircraftManagement instance;
    private List<Airplane> airplanes;

    private AircraftManagement() {
        // Constructor privado para evitar la creación de instancias desde fuera de la clase
         airplanes = new ArrayList<>();
    }

    public static AircraftManagement getInstance() {
        if (instance == null) {
            instance = new AircraftManagement();
        }
        return instance;
    }


    public  void createAirplane(Airplane airplane){
        airplanes.add(airplane);

    }
    public void editAirplane(Airplane airplane) {
        for (Airplane existingAirplane : airplanes) {
            if (existingAirplane.getRegistrationNumber() == airplane.getRegistrationNumber()) {
                existingAirplane.setAirline(airplane.getAirline());
                existingAirplane.setPassengerCapacity(airplane.getPassengerCapacity());
                existingAirplane.setState(airplane.getState());
                break;
            }
        }
    }
    public  void deleteAirplane(Airplane airplane){
        airplanes.remove(airplane);
    }
    public void assignGate(Airplane airplane, BoardingGate gate) {

        if (!gate.isAvailability()) {
            System.out.println("La puerta " + gate.getGateNumber() + " está ocupada");
            return;
        }

        if (airplane.getState() != null && !airplane.getState().equalsIgnoreCase("disponible")) {
            System.out.println("No se puede asignar una puerta al avión en estado de mantenimiento o en vuelo.");
            return;
        }

        gate.setAvailability(false);
        gate.setAssignedAirplane(airplane);
        airplane.setGate(gate.getGateNumber());

        System.out.println("Avión " + airplane.getRegistrationNumber() + " asignado exitosamente a la puerta " + gate.getGateNumber() + ".");
    }
}
