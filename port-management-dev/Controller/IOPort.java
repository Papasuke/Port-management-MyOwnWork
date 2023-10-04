package Controller;

import Model.Container.*;
import Model.Port.Port;
import Model.Vehicle.Vehicle;
import lib.crud.Read;
import lib.crud.Write;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class IOPort {

    //Read and update container path
    private static final String filepath = "data/port.txt";
    private static final String attributes = "ID,NAME,LATITUDE,LONGITUDE,STORING CAPACITY,LANDING,TRANSPORT HISTORY";

    public Port[] getAllPorts() throws IOException {
        ArrayList<String[]> portData = Read.readAllLine(filepath);
        Port[] ports = new Port[portData.size()];

        for (int i = 0; i < portData.size(); i++) {
            String[] portValues = portData.get(i);
            String id = portValues[0];
            String name = portValues[1];
            double latitude = Double.parseDouble(portValues[2]);
            double longitude = Double.parseDouble(portValues[3]);
            double storingCapacity = Double.parseDouble(portValues[4]);
            boolean landingAbility = Boolean.parseBoolean(portValues[5]);

            // Create a new Port object
            Port port = new Port(id, name, latitude, longitude, storingCapacity, landingAbility);

            // Add the Port object to the ports array
            ports[i] = port;
        }

        return ports;
    }

    public void addPort(Port port) throws IOException {
        Write.write(filepath, attributes, port.toString());
    }

    public boolean checkAvailablePort(String PortId) throws IOException {
        Port[] ports = getAllPorts();
        for (Port port: ports){
            if (port.getId().equals(PortId)){
                return true;
            }
        }
        return false;
    }

    public List<Container> getPortContainerList(String PortId) throws IOException {
        List<Container> portContainers = new ArrayList<>();
        IOContainer ioContainer = new IOContainer();
        Container[] containerData = ioContainer.getAllContainers();
        for(Container container : containerData){
            if(container.getAddressId().equals(PortId)){
                portContainers.add(container);
            }
        }
        return portContainers;
    }

    public List<Vehicle> getPortVehicleList(String PortId) throws IOException {
        List<Vehicle> portVehicles = new ArrayList<>();
        IOVehicle ioVehicle = new IOVehicle();
        Vehicle[] vehicleData = new Vehicle[ioVehicle.getAllVehicle().size()];
        vehicleData = ioVehicle.getAllVehicle().toArray(vehicleData);
        for (Vehicle vehicle : vehicleData){
            if (vehicle.getStayPortId().equals(PortId)){
                portVehicles.add(vehicle);
            }
        }
        return portVehicles;
    }

    public Port getPortDetails(String PortId) throws IOException {
        Port[] ports = getAllPorts();
        for (Port port : ports) {
            if (port.getId().equals(PortId)) {
                return port;
            }
        }
        return null;
    }

    public double calDistanceToPort(Port currentPort, Port targetPort){
        final int R = 6300; //assume that the Radius in km is 6300(actually the right number is ~= 6340)
        double currentLat = Math.toRadians(currentPort.getLatitude());
        double targetLat = Math.toRadians(targetPort.getLatitude());
        double currentLon = Math.toRadians(currentPort.getLongitude());
        double targetLon = Math.toRadians(targetPort.getLongitude());

        double latDistance = targetLat - currentLat;
        double lonDistance = targetLon - currentLon;

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(currentLat) * Math.cos(targetLat)
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
