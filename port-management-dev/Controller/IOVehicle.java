package Controller;

import Model.Container.*;
import Model.Port.Port;
import Model.Vehicle.Ship;
import Model.Vehicle.Truck;
import Model.Vehicle.Vehicle;
import lib.crud.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class IOVehicle {
    private static final String truckFilePath = "data/trucks.txt";
    private static final String truckAttributes = "ID,NAME,TYPE,CURRENT_FUEL,CARRYING_CAPACITY,FUEL_CAPACITY,CURRENT_PORT,TRUCK_TYPE";
    private static final String shipFilePath = "data/ships.txt";
    private static final String shipAttributes = "ID,NAME,TYPE,CURRENT_FUEL,CARRYING_CAPACITY,FUEL_CAPACITY,CURRENT_PORT";

    public List<Vehicle> getAllVehicle() throws IOException {
        List<Vehicle> vehicles = new ArrayList<>();
        ArrayList<String[]> truckData = Read.readAllLine(truckFilePath);
        ArrayList<String[]> shipData = Read.readAllLine(shipFilePath);
        for (String[] truck : truckData) {
            String id = truck[0];
            String name = truck[1];
            double currentFuel = Double.parseDouble(truck[3]);
            double carryingCapacity = Double.parseDouble(truck[4]);
            double fuelCapacity = Double.parseDouble(truck[5]);
            String stayPortId = truck[6];
            String truckType = truck[7];

            Truck truckObj = new Truck(id, name, currentFuel, carryingCapacity, fuelCapacity, stayPortId, truckType);
            vehicles.add(truckObj);
        }

        for (String[] ship : shipData) {
            try {
                String id = ship[0];
                String name = ship[1];
                double currentFuel = Double.parseDouble(ship[3]);
                double carryingCapacity = Double.parseDouble(ship[4]);
                double fuelCapacity = Double.parseDouble(ship[5]);
                String stayPortId = ship[6];

                Ship shipObj = new Ship(id, name, currentFuel, carryingCapacity, fuelCapacity, stayPortId);
                vehicles.add(shipObj);
            } catch (NumberFormatException e) {
                // Handle the exception here
                System.out.println("Invalid numeric value found in ship data: " + e.getMessage());
            }
        }

        return vehicles;
    }

    public void removeVehicleById(String vehicleId) throws IOException {
        List<Vehicle> vehicles = getAllVehicle();

        vehicles.removeIf(vehicle -> {
            if (vehicle instanceof Truck) {
                return vehicle.getId().equals(vehicleId);
            } else if (vehicle instanceof Ship) {
                return vehicle.getId().equals(vehicleId);
            }
            System.out.println("There is no vehicle with id " + vehicleId);
            return false;
        });

        Vehicle[] vehicleArray = new Vehicle[vehicles.size()];
        vehicleArray = vehicles.toArray(vehicleArray);
        // Update the vehicles data in the file
        Write.RemoveDataFromFile(shipFilePath);
        Write.RemoveDataFromFile(truckFilePath);
        writeVehicles(vehicleArray);
    }


    public Vehicle getVehicleDetailsById(String id) throws IOException {
        List<Vehicle> vehicles = getAllVehicle();

        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof Ship && vehicle.getId().equals(id)) {
                return vehicle;
            } else if (vehicle instanceof Truck && vehicle.getId().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    public void addVehicle(Vehicle vehicle) throws IOException {
        if(vehicle instanceof Truck truck){
            //String builder to write to file
            String stringTruckObj = "" + "tr-" + truck.getId() +
                    "," + truck.getName() +
                    "," + truck.getType() +
                    "," + truck.getCurrentFuel() +
                    "," + truck.getCarryingCapacity() +
                    "," + truck.getFuelCapacity() +
                    "," + truck.getStayPortId() +
                    "," + truck.getTruckType();
            Write.write(truckFilePath, truckAttributes, stringTruckObj);
        } else if (vehicle instanceof Ship ship) {
            //String builder to write to file
            String stringShipObj = "" + "sh-" + ship.getId() +
                    "," + ship.getName() +
                    "," + ship.getType() +
                    "," + ship.getCurrentFuel() +
                    "," + ship.getCarryingCapacity() +
                    "," + ship.getFuelCapacity() +
                    "," + ship.getStayPortId();
            Write.write(shipFilePath, shipAttributes, stringShipObj);
        }
    }

    private void writeVehicles(Vehicle[] vehicles) throws IOException {
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof Truck truck) {
                String stringTruckObj = "" + truck.getId() +
                        "," + truck.getName() +
                        "," + truck.getType() +
                        "," + truck.getCurrentFuel() +
                        "," + truck.getCarryingCapacity() +
                        "," + truck.getFuelCapacity() +
                        "," + truck.getStayPortId() +
                        "," + truck.getTruckType();
                Write.write(truckFilePath, truckAttributes, stringTruckObj);
            } else if (vehicle instanceof Ship ship) {
                String stringShipObj = "" + ship.getId() +
                        "," + ship.getName() +
                        "," + ship.getType() +
                        "," + ship.getCurrentFuel() +
                        "," + ship.getCarryingCapacity() +
                        "," + ship.getFuelCapacity() +
                        "," + ship.getStayPortId();
                Write.write(shipFilePath, shipAttributes, stringShipObj);
            }
        }
    }

    public boolean checkRequirementToPort(String vehicleId, String PortId) throws IOException {
        IOPort ioPort = new IOPort();
        IOVehicle ioVehicle = new IOVehicle();
        IOContainer ioContainer = new IOContainer();
        Vehicle vehicle = ioVehicle.getVehicleDetailsById(vehicleId);
        Port DeparturePort = ioPort.getPortDetails(vehicle.getStayPortId());
        if (vehicle.getStayPortId().equals("onTrip")){
            System.out.println("The vehicle is being assigned in another trip!!!!!");
            return false;
        }
        if (DeparturePort == null){
            System.out.println("The vehicle is not available in any port!!!!!");
            return false;
        }
        if (!DeparturePort.isLandingAbility()){
            System.out.println("The port is being repaired or blocked by the admin!!!!");
        }

        Port destinationPort = ioPort.getPortDetails(PortId);
        double distance = ioPort.calDistanceToPort(DeparturePort, destinationPort);

        double requiredFuel = 0;

        Container[] containers = ioContainer.getAllContainers();
        for (Container container : containers){
            if (container.getAddressId().equals(vehicle.getId())) {
                if (vehicle instanceof Truck) {
                    if (container instanceof DryStorage dryStorage){
                        requiredFuel += dryStorage.getFuelForTruck() * distance;
                    }
                    if (container instanceof Liquid liquid){
                        requiredFuel += liquid.getFuelForTruck() * distance;
                    }
                    if (container instanceof OpenSide openSide){
                        requiredFuel +=  openSide.getFuelForTruck() * distance;
                    }
                    if (container instanceof OpenTop openTop){
                        requiredFuel += openTop.getFuelForTruck() * distance;
                    }
                    if (container instanceof Refrigerated refrigerated){
                        requiredFuel += refrigerated.getFuelForTruck() * distance;
                    }
                } else if (vehicle instanceof Ship) {
                    if (container instanceof DryStorage dryStorage){
                        requiredFuel += dryStorage.getFuelForShip() * distance;
                    }
                    if (container instanceof Liquid liquid){
                        requiredFuel += liquid.getFuelForShip() * distance;
                    }
                    if (container instanceof OpenSide openSide){
                        requiredFuel += openSide.getFuelForShip() * distance;
                    }
                    if (container instanceof OpenTop openTop){
                        requiredFuel += openTop.getFuelForShip() * distance;
                    }
                    if (container instanceof Refrigerated refrigerated){
                        requiredFuel += refrigerated.getFuelForShip() * distance;
                    }
                }
            }
        }
        return vehicle.getCurrentFuel() >= requiredFuel;
    }
}
