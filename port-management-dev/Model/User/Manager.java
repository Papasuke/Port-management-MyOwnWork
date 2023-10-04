package Model.User;

import Controller.IOContainer;
import Controller.IOPort;
import Controller.IOVehicle;
import Model.Container.*;
import Model.Port.Port;
import Model.Vehicle.Ship;
import Model.Vehicle.Truck;
import Model.Vehicle.Vehicle;
import lib.crud.CreateTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Manager extends User{
    private String portId;

    public Manager(String username, String password, String role, String portId) {
        super(username, password, role);
        this.portId = portId;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public void assignTrip(){

    }

    public void showVehicle() throws IOException {
        IOVehicle ioVehicle = new IOVehicle();
        List<Vehicle> vehicleData = ioVehicle.getAllVehicle();
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("TRUCK ID", "TRUCK NAME", "CURRENT FUEL", "CARRYING CAPACITY", "FUEL CAPACITY", "TRUCK TYPE");
        for (Vehicle vehicle: vehicleData){
            if ((vehicle instanceof Truck truck) && (vehicle.getStayPortId().equals(this.portId))){
                CreateTable.addRow(truck.getId(), truck.getName(), Double.toString(truck.getCurrentFuel()), Double.toString(truck.getCarryingCapacity())
                        , Double.toString(truck.getFuelCapacity()), truck.getTruckType());
            }
        }
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("SHIP ID", "SHIP NAME", "CURRENT FUEL", "CARRYING CAPACITY", "FUEL CAPACITY");
        for (Vehicle vehicle: vehicleData){
            if (vehicle instanceof Ship ship && vehicle.getStayPortId().equals(this.portId)){
                CreateTable.addRow(ship.getId(), ship.getName(), Double.toString(ship.getCurrentFuel()), Double.toString(ship.getCarryingCapacity()), Double.toString(ship.getFuelCapacity()));
            }
        }
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    public void showContainer()throws IOException {
        IOContainer ioContainer = new IOContainer();
        Container[]containers = ioContainer.getAllContainers();
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("CONTAINER ID", "WEIGHT", "TYPE", "STATUS");
        for (Container container : containers){
            if (container.getAddressId().equals(this.portId)){
                CreateTable.addRow(container.getId(), Double.toString(container.getWeight()), container.getType(), container.getStatus());
            }
        }
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    public void showPortDetails() throws IOException {
        IOPort ioPort = new IOPort();
        IOContainer ioContainer = new IOContainer();
        IOVehicle ioVehicle = new IOVehicle();
        Port[]ports = ioPort.getAllPorts();
        Container[]containers = ioContainer.getAllContainers();
        List<Vehicle> vehicles = ioVehicle.getAllVehicle();
        int containerNumber = 0;
        int vehicleNumber = 0;
        double totalContainerWeight = 0.0;

        for (Container container: containers){
            if (container.getAddressId().equals(this.portId)){
                containerNumber += 1;
                totalContainerWeight += container.getWeight();
            }
        }

        for (Vehicle vehicle: vehicles){
            if (vehicle.getStayPortId().equals(this.portId)){
                vehicleNumber += 1;
            }
        }

        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("PORT ID", "NAME", "LATITUDE", "LONGITUDE", "TOTAL CONTAINER", "TOTAL VEHICLE", "TOTAL CARRYING WEIGHT", "STORING CAPACITY");
        for (Port port: ports){
            if (port.getId().equals(this.portId)){
                CreateTable.addRow(port.getId(), port.getName(), Double.toString(port.getLatitude()), Double.toString(port.getLongitude()),
                        Integer.toString(containerNumber), Integer.toString(vehicleNumber), Double.toString(totalContainerWeight), Double.toString(port.getStoringCapacity()));
            }
        }
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }


    // display recommended Vehicles if it matches conditions that are fuel capacity must be greater than required fuel, Carrying capacity must be greater than total weight of containers
    public List<Vehicle> vehicleRecommendation(String desPortId, List<Container> containers) throws IOException {
        IOVehicle ioVehicle = new IOVehicle();
        List<Vehicle> vehicles = ioVehicle.getAllVehicle();
        IOPort ioPort = new IOPort();
        double distance = ioPort.calDistanceToPort(ioPort.getPortDetails(this.getPortId()), ioPort.getPortDetails(desPortId));
        double requiredFuel = 0;
        double totalWeight = 0.0;
        Set<Vehicle> recommendedVehicles = new HashSet<>();

        vehicles.removeIf(vehicle -> !vehicle.getStayPortId().equals(this.portId));
        if (containers == null){
            System.out.println("There is no recommendation !!!!!");
        }

        else {
            for (Container container : containers) {
                totalWeight += container.getWeight();
                if (containers.size() == 1) {
                    if (container instanceof DryStorage dryStorage) {
                        requiredFuel += dryStorage.getFuelForTruck() * distance;
                    } else if (container instanceof Liquid liquid) {
                        requiredFuel += liquid.getFuelForTruck() * distance;
                    } else if (container instanceof OpenSide openSide) {
                        requiredFuel += openSide.getFuelForTruck() * distance;
                    } else if (container instanceof OpenTop openTop) {
                        requiredFuel += openTop.getFuelForTruck() * distance;
                    } else if (container instanceof Refrigerated refrigerated) {
                        requiredFuel += refrigerated.getFuelForTruck() * distance;
                    }
                } else if (containers.size() > 1) {
                    if (container instanceof DryStorage dryStorage) {
                        requiredFuel += dryStorage.getFuelForShip() * distance;
                    } else if (container instanceof Liquid liquid) {
                        requiredFuel += liquid.getFuelForShip() * distance;
                    } else if (container instanceof OpenSide openSide) {
                        requiredFuel += openSide.getFuelForShip() * distance;
                    } else if (container instanceof OpenTop openTop) {
                        requiredFuel += openTop.getFuelForShip() * distance;
                    } else if (container instanceof Refrigerated refrigerated) {
                        requiredFuel += refrigerated.getFuelForShip() * distance;
                    }
                }
            }

            for (Vehicle vehicle : vehicles) {
                if (containers.size() > 1) {
                    if (vehicle instanceof Ship ship && vehicle.getFuelCapacity() >= requiredFuel && vehicle.getCarryingCapacity() >= totalWeight) {
                        recommendedVehicles.add(ship);
                    }
                }
                if (containers.size() == 1) {
                    for (Container container : containers) {
                        if (vehicle instanceof Truck truck && vehicle.getFuelCapacity() >= requiredFuel && vehicle.getCarryingCapacity() >= totalWeight) {
                            if ((truck.getTruckType().equals("Basic") && (container instanceof DryStorage || container instanceof OpenTop || container instanceof OpenSide))
                                    || (truck.getTruckType().equals("Reefer") && container instanceof Refrigerated)
                                    || (truck.getTruckType().equals("Tanker") && container instanceof Liquid)) {
                                recommendedVehicles.add(truck);
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<>(recommendedVehicles);
    }
}
