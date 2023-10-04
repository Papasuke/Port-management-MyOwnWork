package Model.Container;

public class Refrigerated extends Container {
    //Extend Container as father class


    public Refrigerated(String id, double weight, String type, String status, String addressId) {
        super(id, weight, "Refrigerated", status, addressId);
    }

    @Override
    public double getFuelForShip() {
        return weight * 4.5;
    }

    @Override
    public double getFuelForTruck() {
        return weight * 5.4;
    }
}