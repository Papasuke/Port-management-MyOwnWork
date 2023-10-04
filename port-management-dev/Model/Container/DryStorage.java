package Model.Container;

public class DryStorage extends Container{
    //Extend Container as father class

    public DryStorage(String id, double weight, String type, String status, String addressId) {
        super(id, weight, "DryStorage", status, addressId);
    }

    @Override
    public double getFuelForShip() {
        return weight * 3.5;
    }

    @Override
    public double getFuelForTruck() {
        return weight * 4.6;
    }
}
