package Model.User;

import Controller.IOPort;
import Controller.IOVehicle;
import Model.Vehicle.Ship;
import Model.Vehicle.Truck;
import Model.Vehicle.Vehicle;

import java.io.IOException;
import java.util.List;

public class Customer extends User{
    private boolean isAccepted;
    private String vehicleId;// Assume that every customer just only own 1 vehicle

    public Customer(String username, String password, String role, String vehicleId, boolean isAccepted) {
        super(username, password, role);
        this.vehicleId = vehicleId;
        this.isAccepted = isAccepted;// After register, the customer must wait for the admin to activate the account to park their vehicle to the chosen port
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public void getVehicleDetail() throws IOException {
        IOVehicle vehicle = new IOVehicle();
        List<Vehicle> vehicleData = vehicle.getAllVehicle();
        Vehicle[] vehicleArray = new Vehicle[vehicleData.size()];
        vehicleArray = vehicleData.toArray(vehicleArray);
        for (Vehicle vehicle1 : vehicleArray){
            if (vehicle1.getId().equals(this.getVehicleId())){
                if (vehicle1 instanceof Truck truck){
                    System.out.println("Truck ID: "+ truck.getId());
                    System.out.println("Truck Name: " + truck.getName());
                    System.out.println("Current fuel: " + truck.getCurrentFuel());
                    System.out.println("Carrying Capacity: " + truck.getCarryingCapacity());
                    System.out.println("Fuel capacity: " + truck.getFuelCapacity());
                    System.out.println("Current address: " + truck.getStayPortId());
                    System.out.println("Truck type: " + truck.getTruckType());
                }
                else if (vehicle1 instanceof Ship ship){
                    System.out.print("Truck ID: "+ ship.getId());
                    System.out.println("Truck Name: " + ship.getName());
                    System.out.println("Current fuel: " + ship.getCurrentFuel());
                    System.out.println("Carrying Capacity: " + ship.getCarryingCapacity());
                    System.out.println("Fuel capacity: " + ship.getFuelCapacity());
                    System.out.println("Current address: " + ship.getStayPortId());
                }
            }
        }
    }
}
