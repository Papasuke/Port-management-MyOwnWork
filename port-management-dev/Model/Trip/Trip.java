package Model.Trip;

import Model.Port.Port;
import Model.Vehicle.Vehicle;
import lib.crud.Read;
import lib.crud.Write;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Trip {
    // Constructor
    protected Vehicle vehicle;
    protected Port basePort;
    protected Port desPort;
    protected Date departureDate;
    protected Date arrivalDate;
    private String status;


    public Trip(Vehicle vehicle, Port basePort, Port desPort, Date departureDate, Date arrivalDate, String status) {
        this.vehicle = vehicle;
        this.basePort = basePort;
        this.desPort = desPort;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }
    public String toString() {
        return vehicle.getId() + ", " + basePort.getId() + ", " + desPort.getId() + ", "
                + DATE_FORMAT.format(departureDate) + ", " + DATE_FORMAT.format(arrivalDate)
                + ", " + status;
    }


    private static final String filepath = "data/Trip.txt";
    private static final String attributes = "VEHICLE-ID,BASE-PORT,DES-PORT-ID,DE-DATE,ARRIVAL-DATE,STATUS";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public ArrayList<String[]> getAllTrips() throws IOException {
        return Read.readAllLine(filepath);
    }

//    public void addTrip(Trip trip) throws IOException {
//        Vehicle vehicle = trip.getVehicle();
//        Port departurePort = trip.basePort;
//
//        if (vehicle.canMoveToDesPort(vehicle, departurePort)) {
//            Write.write(filepath, attributes, trip.toString());
//        } else {
//            System.out.println("The vehicle cannot move to the destination port with its current fuel.");
//        }
//    }

    private void writeAllTrips(ArrayList<String[]> Trips) throws IOException {
        for (String[] trip : Trips) {
            Write.write(filepath, attributes, String.join(",",trip));
        }
    }

    public void deleteTrip(String id) throws IOException {
        ArrayList<String[]> trips = getAllTrips();
        trips.removeIf(port -> port[0].equals(id));
        Write.RemoveDataFromFile(filepath);
        writeAllTrips(trips);
        System.out.println("The trip of vehicle with id " + id + " was cancelled");
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Port getBasePort() {
        return basePort;
    }

    public void setBasePort(Port basePort) {
        this.basePort = basePort;
    }

    public Port getDesPort() {
        return desPort;
    }

    public void setDesPort(Port desPort) {
        this.desPort = desPort;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
