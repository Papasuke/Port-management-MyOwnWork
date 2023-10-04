package Model.Vehicle;

import Model.Container.Container;

public class Ship extends Vehicle {


    public Ship(String id, String name, double currentFuel, double carryingCapacity, double fuelCapacity, String stayPortId) {
        super(id, name, "ship", currentFuel, carryingCapacity, fuelCapacity, stayPortId);
    }

    @Override
    public void loadContainer(Container container) {
        if (container.getWeight() + getTotalWeight() > getCarryingCapacity()) {
            System.out.println("No enough capacity to load this container!");
            return;
        }
        getContainersList().add(container);
    }
}
