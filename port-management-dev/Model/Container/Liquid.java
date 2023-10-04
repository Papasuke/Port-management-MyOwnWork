package Model.Container;

public class Liquid extends Container{
    //Extend Container as father class


    public Liquid(String id, double weight, String type, String status, String addressId) {
        super(id, weight, "OpenSide", status, addressId);
    }

    @Override
    public double getFuelForShip() {
        return weight * 4.8;
    }

    @Override
    public double getFuelForTruck() {
        return weight * 5.3;
    }
}
