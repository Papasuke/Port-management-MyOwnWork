package Model.User;

public class Manager extends User{
    private String portId;

    public Manager(String username, String password, String role, String portId) {
        super(username, password, role);
        this.portId = portId;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public void assignTrip(){

    }
}
