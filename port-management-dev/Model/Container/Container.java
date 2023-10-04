package Model.Container;

public class Container{
    private String id;
    protected double weight;
    protected String type;
    private String status;
    private String addressId;

    public Container(String id, double weight, String type, String status, String addressId) {
        this.id = id;
        this.weight = weight;
        this.type = type;
        this.status = status;
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return
                "" + id +
                "," + weight +
                "," + type +
                "," + status+
                "," + addressId;
    }

    public String getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getAddressId() {
        return addressId;
    }

    public double getFuelForShip() {
        return 0.0;
    }

    public double getFuelForTruck() {
        return 0.0;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
