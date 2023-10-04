package Model.Container;

public class OpenSide extends Container {
    //Extend Container as father class

    public OpenSide(String id, double weight, String type, String status, String addressId) {
        super(id, weight, "OpenSide", status, addressId);
    }

    @Override
    public double getFuelForShip() {
        return weight * 2.7;
    }

    @Override
    public double getFuelForTruck() {
        return weight * 3.2;
    }
}
