package Controller;

//Import package
import Model.Container.*;
import Model.Vehicle.Ship;
import Model.Vehicle.Truck;
import Model.Vehicle.Vehicle;
import lib.crud.Read;
import lib.crud.Write;

//Import library
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOContainer {
    //Create unity filepath and attributes for saving containers and reading container from file
    private static final String filepath = "data/container.txt";
    private static final String attributes = "ContainerID,Weight,Type,Status,AddressId";

    public Container[] getAllContainers() throws IOException {
        ArrayList<String[]> containerData = Read.readAllLine(filepath);
        Container[] containers = new Container[containerData.size()];

        for (int i = 0; i < containerData.size(); i++) {
            String[] containerValues = containerData.get(i);
            String id = containerValues[0];
            double weight = Double.parseDouble(containerValues[1]);
            String type = containerValues[2];
            String status = containerValues[3];
            String addressId = containerValues[4];
            if (type.equals("DryStorage")){
                containers[i] = new DryStorage(id, weight, type, status, addressId);
            }
            if (type.equals("Liquid")){
                containers[i] = new Liquid(id, weight, type, status, addressId);
            }
            if (type.equals("OpenSide")){
                containers[i] = new OpenSide(id, weight, type, status, addressId);
            }
            if (type.equals("OpenTop")){
                containers[i] = new OpenTop(id, weight, type, status, addressId);
            }
            if (type.equals("Refrigerated")){
                containers[i] = new Refrigerated(id, weight, type, status, addressId);
            }
        }
        //Return an array of Container[]
        return containers;
    }


    public void addContainer(Container container) throws IOException {
        //Write new container to file
        Write.write(filepath, attributes, container.toString());
    }

    //Remove container
    public void deleteContainer(String id) throws IOException {
        Container[] containers = getAllContainers();

        List<Container> containerList = new ArrayList<>(Arrays.asList(containers));
        containerList.removeIf(container -> container.getId().equals(id));

        // Check if there is no container matching ID
        if (containerList.size() == containers.length) {
            System.out.println("There is no container with id " + id);
        }

        // Convert the list back to an array
        containers = containerList.toArray(new Container[0]);

        // Delete the data from the file before re-adding containers
        Write.RemoveDataFromFile(filepath);

        // Update the ContainerData
        writeContainers(containers);
    }

    //Get container details
    public Container getContainerDetails(String containerId) throws IOException {
        Container[] containers = getAllContainers();
        for (Container container : containers) {
            //Condition to get container matching with ID
            if (container.getId().equals(containerId)) {
                return container;
            }
        }
        return null;
    }

    private void writeContainers(Container[] Containers) throws IOException {
        //Write all the containers to file
        for (Container container : Containers) {
            Write.write(filepath, attributes, container.toString());
        }
    }
    //Every truck just can carry only 1 container, the method to check truck if it contained one
    private boolean notCarrying(String truckId) throws IOException {
        boolean notCarrying = true;
        Container[] containers = getAllContainers();
        for (Container container: containers){
            if (container.getAddressId().equals(truckId)) {
                notCarrying = false;
                System.out.println("The truck is already assigned for another container");
                break;
            }
        }
        return notCarrying;
    }

    private double calculateTotalCarrying(String shipId) throws IOException {
        double totalWeight = 0.0;
        Container[] containers = getAllContainers();
        for (Container container : containers){
            if (container.getAddressId().equals(shipId)){
                totalWeight += container.getWeight();
            }
        }
        return totalWeight;
    }

    public void loadContainerToVehicle(String containerId, String vehicleId) throws IOException {
        IOVehicle ioVehicle = new IOVehicle();
        Vehicle vehicle = ioVehicle.getVehicleDetailsById(vehicleId);
        Container container = getContainerDetails(containerId);
        if (vehicle != null && container != null) {
            //Check if the container is available in the current port or not
            if ((container.getAddressId()).equals(vehicle.getStayPortId())) {
                if (vehicle instanceof Truck truck) {
                    truck.loadContainer(container);
                    if (notCarrying (truck.getId()) && (truck.getCarryingCapacity() > container.getWeight()) && (truck.getTruckType().equals("Basic")
                            && (container instanceof DryStorage || container instanceof OpenTop || container instanceof OpenSide))
                            || (truck.getTruckType().equals("Reefer") && container instanceof Refrigerated)
                            || (truck.getTruckType().equals("Tanker") && container instanceof Liquid)) {
                        container.setAddressId(truck.getId());
                        deleteContainer(containerId);
                        String stringContainerObj = "" + container.getId() +
                                "," + container.getWeight() +
                                "," + container.getType() +
                                "," + container.getStatus() +
                                "," + container.getAddressId();
                        Write.write(filepath, attributes, stringContainerObj);
                        System.out.println("Loading container....");
                    }
                }
                if (vehicle instanceof Ship ship) {
                    if ((calculateTotalCarrying(ship.getId()) + container.getWeight()) <= ship.getCarryingCapacity()) {
                        ship.loadContainer(container);
                        container.setAddressId(ship.getId());
                        deleteContainer(containerId);
                        String stringContainerObj = "" + container.getId() +
                                "," + container.getWeight() +
                                "," + container.getType() +
                                "," + container.getStatus() +
                                "," + container.getAddressId();
                        Write.write(filepath, attributes, stringContainerObj);
                        System.out.println("Loading container....");
                    }
                    else System.out.println("The left carrying capacity not enough to carry this container !!!!!");
                }
            }else System.out.println("The vehicle and container must be the same port");
        }else {
            System.out.println("Invalid vehicle or container");
        }
    }
}