package Model.Container;

public class OpenTop extends Container {
    //Extend Container as father class


    public OpenTop(String id, double weight, String type, String status, String addressId) {
        super(id, weight, "OpenTop", status, addressId);
    }

    @Override
    public double getFuelForShip() {
        return weight * 2.8;
    }

    @Override
    public double getFuelForTruck() {
        return weight * 3.2;
    }
}
