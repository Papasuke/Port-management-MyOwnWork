package View.Customer;

import Controller.IOPort;
import Controller.IOUser;
import Controller.IOVehicle;
import Model.Port.Port;
import Model.User.Customer;
import Model.Vehicle.Ship;
import Model.Vehicle.Truck;
import Model.Vehicle.Vehicle;
import View.LoginView;
import lib.crud.CreateTable;
import lib.crud.Write;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RegisterVehicleView {

    private static final String userFilepath = "data/users.txt";
    private static final String userAttributes = "USERNAME,PASSWORD,ROLE,MANAGE_PORT,VEHICLE,ACCESS_RIGHT";

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("The input type wrong!!!!!");
            return false;
        }
    }

    public boolean isPortIdValid(String PortId) throws IOException {
        IOPort ioPort = new IOPort();
        Port[] ports = ioPort.getAllPorts();
        for (Port port: ports){
            if (port.getId().equals(PortId) && port.isLandingAbility()){
                return true;
            }
        }
        System.out.println("There is no port with id: " + PortId + " ,Please try again!!!!!");
        return false;
    }

    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Wrong input type!!! try again.");
            return false;
        }
    }

    public boolean isTruckTypeValid(String truckType) {
        if( truckType.equals("Basic") || truckType.equals("Reefer") || truckType.equals("Tanker")){
            return true;
        }
        else {
            System.out.println("Wrong truck type!!!!!");
            return false;
        }
    }

    public void RegisterTruck(Customer customer) throws IOException {
        IOPort ioPort = new IOPort();
        Port[] ports = ioPort.getAllPorts();
        IOUser ioUser = new IOUser();
        IOVehicle ioVehicle = new IOVehicle();
        Scanner sc = new Scanner(System.in);
        String vehicleId;
        String currentFuel;
        String carryingCapacity;
        String fuelCapacity;
        String truckType;
        String portId;
        System.out.print("Enter truck name: ");
        String truckName = sc.nextLine();
        System.out.println("Remember!!!! just receive number for vehicle ID");
        do {
            System.out.print("Enter truck ID: ");
            vehicleId = sc.nextLine();
        } while (!(ioUser.checkValidVehicle("tr-" + vehicleId) && isInteger(vehicleId)));


        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("Port ID", "Port Name", "Latitude", "Longitude");
        for (Port port: ports) {
            if (port.isLandingAbility()) {
                CreateTable.addRow(port.getId(), port.getName(), Double.toString(port.getLatitude()), Double.toString(port.getLongitude()));
            }
        }
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());

        System.out.println("Just ports from the port list above is accepted");

        do{
            System.out.print("Enter port ID to park your vehicle: ");
            portId = sc.nextLine();
        }while (!isPortIdValid(portId));
        System.out.println("Just receive Basic, Reefer, Tanker for truck type !!!!!");
        do{
            System.out.print("Enter truck type: ");
            truckType = sc.nextLine();
        }while (!isTruckTypeValid(truckType));

        do{
            System.out.print("Enter current fuel: ");
            currentFuel = sc.nextLine();
        }while (!isDouble(currentFuel));

        do{
            System.out.print("Enter carrying capacity: ");
            carryingCapacity = sc.nextLine();
        }while (!isDouble(carryingCapacity));

        do{
            System.out.print("Enter fuel capacity: ");
            fuelCapacity = sc.nextLine();
        }while (!isDouble(fuelCapacity));

        Vehicle vehicle = new Truck(vehicleId, truckName, Double.parseDouble(currentFuel), Double.parseDouble(carryingCapacity), Double.parseDouble(fuelCapacity), portId, truckType);
        ioVehicle.addVehicle(vehicle);
        customer.setVehicleId(vehicle.getId());
        ioUser.deleteUser(customer.getUsername());
        String stringCustomerObj = "" + customer.getUsername() +
                "," + customer.getPassword() +
                "," + customer.getRole() +
                "," + "tr-" + customer.getVehicleId() +
                "," + customer.isAccepted();
        Write.write(userFilepath,userAttributes,stringCustomerObj);
        System.out.println("Register new truck successfully!!!!");
        LoginView.LoginViewModel();
    }
    public void RegisterShip(Customer customer) throws IOException {
        IOPort ioPort = new IOPort();
        Port[] ports = ioPort.getAllPorts();
        IOUser ioUser = new IOUser();
        IOVehicle ioVehicle = new IOVehicle();
        Scanner sc = new Scanner(System.in);
        String vehicleId;
        String currentFuel;
        String carryingCapacity;
        String fuelCapacity;
        String portId;
        System.out.print("Enter ship name: ");
        String shipName = sc.nextLine();
        System.out.println("Remember!!!! just receive number for vehicle ID");
        do {
            System.out.print("Enter ship ID: ");
            vehicleId = sc.nextLine();
        } while (!(ioUser.checkValidVehicle("sh-" + vehicleId) && isInteger(vehicleId)));


        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("Port ID", "Port Name", "Latitude", "Longitude");
        for (Port port: ports) {
            if (port.isLandingAbility()) {
                CreateTable.addRow(port.getId(), port.getName(), Double.toString(port.getLatitude()), Double.toString(port.getLongitude()));
            }
        }
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());

        System.out.println("Just ports from the port list above is accepted");

        do{
            System.out.print("Enter port ID to park your vehicle: ");
            portId = sc.nextLine();
        }while (!isPortIdValid(portId));

        do{
            System.out.print("Enter current fuel: ");
            currentFuel = sc.nextLine();
        }while (!isDouble(currentFuel));

        do{
            System.out.print("Enter carrying capacity: ");
            carryingCapacity = sc.nextLine();
        }while (!isDouble(carryingCapacity));

        do{
            System.out.print("Enter fuel capacity: ");
            fuelCapacity = sc.nextLine();
        }while (!isDouble(fuelCapacity));

        Vehicle vehicle = new Ship(vehicleId, shipName, Double.parseDouble(currentFuel), Double.parseDouble(carryingCapacity), Double.parseDouble(fuelCapacity), portId);
        ioVehicle.addVehicle(vehicle);
        customer.setVehicleId(vehicle.getId());
        ioUser.deleteUser(customer.getUsername());
        String stringCustomerObj = "" + customer.getUsername() +
                "," + customer.getPassword() +
                "," + customer.getRole() +
                "," + "sh-" + customer.getVehicleId() +
                "," + customer.isAccepted();
        Write.write(userFilepath,userAttributes,stringCustomerObj);
        System.out.println("Register new ship successfully!!!!");
        LoginView.LoginViewModel();
    }
}
