package Model.Vehicle;

import Model.Container.Container;

public class Truck extends Vehicle {
    private String truckType;

    public Truck(String id, String name, double currentFuel, double carryingCapacity, double fuelCapacity, String stayPortId, String truckType) {
        super(id, name, "truck", currentFuel, carryingCapacity, fuelCapacity, stayPortId);
        this.truckType = truckType;
    }

    @Override
    public void loadContainer(Container container) {
        if ((truckType.equals("Basic") && (container.getType().equals("DryStorage") || container.getType().equals("OpenTop") || container.getType().equals("OpenSide")))
                || (truckType.equals("Reefer") && container.getType().equals("Refrigerated"))
                || (truckType.equals("Tanker") && container.getType().equals("Liquid"))) {
            if (container.getWeight() + getTotalWeight() <= getCarryingCapacity()) {
                getContainersList().add(container);
            } else {
                System.out.println("The capacity cannot handle this container!");
            }
        } else {
            System.out.println("Incorrect vehicle or container type!");
        }
    }

    public String getTruckType() {
        return truckType;
    }
}
