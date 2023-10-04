package Model.Vehicle;

import Model.Container.Container;

import java.util.ArrayList;

public abstract class Vehicle{
    private String id;
    private String name;
    private String type;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private String stayPortId ;
    private ArrayList<Container> containersDB;
    // Constructor



    public Vehicle(String id, String name, String type, double currentFuel, double carryingCapacity, double fuelCapacity, String stayPortId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.stayPortId = stayPortId;
        this.containersDB = new ArrayList<Container>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Vehicle(){}


    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public ArrayList<Container> getContainersList() {
        return containersDB;
    }


    public abstract void loadContainer(Container container);

    // Unload a container from the vehicle
    public void unloadContainer(Container container) {
        containersDB.remove(container);
    }

    public double getTotalWeight() {
        return containersDB.stream()
                .mapToDouble(Container::getWeight)
                .sum();
    }


    public void refuel(double fuel) {
        currentFuel = Math.min(currentFuel + fuel, fuelCapacity);
        if (currentFuel == fuelCapacity) {
            System.out.println("Full fuel!");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public String getStayPortId() {
        return stayPortId;
    }

    public void setStayPortId(String stayPortId) {
        this.stayPortId = stayPortId;
    }
}