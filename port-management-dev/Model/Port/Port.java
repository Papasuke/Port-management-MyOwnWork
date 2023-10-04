package Model.Port;
import Model.Container.Container;
import Model.Trip.Trip;
import Model.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Port{
    // Constructor
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private List<Container> containersDB = new ArrayList<>();
    private List<Vehicle> vehiclesDB = new ArrayList<>();
    private List<Trip> transportHistory = new ArrayList<>();



    public Port(String id, String name, double latitude, double longitude, double storingCapacity, boolean landingAbility) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
    }

    @Override
    public String toString() {
        return "" + id +
                "," + name +
                "," + latitude +
                "," + longitude +
                "," + storingCapacity +
                "," + landingAbility;
    }


    public double TotalWeight() {
        return containersDB.stream()
                .mapToDouble(Container::getWeight)
                .sum();
    }

    public double DistanceTo(Port otherPort) {
        final int R = 6000; //assume that the Radius in km is 6000(actually the right number is ~= 6300)
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(otherPort.latitude);
        double lon2 = Math.toRadians(otherPort.longitude);

        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;

        double sinLatDiff = Math.sin(latDiff / 2);
        double sinLonDiff = Math.sin(lonDiff / 2);
        double cosLat1 = Math.cos(lat1);
        double cosLat2 = Math.cos(lat2);

        double a = sinLatDiff * sinLatDiff + cosLat1 * cosLat2 * sinLonDiff * sinLonDiff;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }


    public boolean isLandingAbility() {
        return landingAbility;
    }

}
