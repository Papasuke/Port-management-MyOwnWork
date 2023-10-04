package View.Manager;

import Controller.IOContainer;
import Controller.IOPort;
import Controller.IOVehicle;
import Model.Container.Container;
import Model.Port.Port;
import Model.User.Manager;
import Model.Vehicle.Ship;
import Model.Vehicle.Truck;
import Model.Vehicle.Vehicle;
import lib.crud.CreateTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChosenContainersView {

    public boolean isPortIdValid(String PortId, String currentPort) throws IOException {
        IOPort ioPort = new IOPort();
        Port[] ports = ioPort.getAllPorts();
        for (Port port: ports){
            if (port.getId().equals(PortId) && port.isLandingAbility() && !port.getId().equals(currentPort)){
                return true;
            }
        }
        System.out.println("There is no port with id: " + PortId + " ,Please try again!!!!!");
        return false;
    }

    public boolean checkYesNoQuestion(String answer){
        boolean isRightAnswer = false;
        if(answer.equals("Yes") || answer.equals("No")){
            isRightAnswer = true;
        }else {
        System.out.println("Wrong answer!!! try again!!!");}
        return isRightAnswer;
    }

    public void chosenContainerViewModel(Manager manager) throws IOException {
        String desPortId;
        String containerId;
        String yesNo;
        ManagerMenu managerMenu = new ManagerMenu();
        boolean isUsedRecommendation = true;
        boolean isAgree = true;
        System.out.println("╔═══════════════════════╗");
        System.out.println("║     RECOMMENDATION    ║");
        System.out.println("╚═══════════════════════╝");
        IOContainer ioContainer = new IOContainer();
        IOPort ioPort = new IOPort();
        Port[] ports = ioPort.getAllPorts();
        Container[] containers = ioContainer.getAllContainers();
        List<Container> containersData = new ArrayList<>();
        List<Container> chosenContainers = new ArrayList<>();
        for (Container container : containers){
            if (container.getAddressId().equals(manager.getPortId())){
                containersData.add(container);
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("List accepted ports");
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("PORT ID", "NAME", "LATITUDE", "LONGITUDE");
        for (Port port: ports) {
            if (port.isLandingAbility() && !port.getId().equals(manager.getPortId())) {
                CreateTable.addRow(port.getId(), port.getName(), Double.toString(port.getLatitude()), Double.toString(port.getLongitude()));
            }
        }
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
        do {
            System.out.print("Enter destination port Id: ");
            desPortId = sc.nextLine();
        }while (!isPortIdValid(desPortId, manager.getPortId()));

        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("CONTAINER ID", "WEIGHT", "TYPE", "STATUS");
        for (Container container : containersData){
            CreateTable.addRow(container.getId(), Double.toString(container.getWeight()), container.getType(), container.getStatus());
        }
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());

        while (isAgree){
            System.out.print("Enter container Id: ");
            containerId = sc.nextLine();
            chosenContainers.add(ioContainer.getContainerDetails(containerId));
            System.out.print("Do you want to continue adding container!?(Yes/No): ");
            do {
                yesNo = sc.nextLine();
                if (yesNo.equals("No")){
                    isAgree = false;
                }
            }while (!checkYesNoQuestion(yesNo));
        }
        List<Vehicle> recommendedVehicles = manager.vehicleRecommendation(desPortId, chosenContainers);
        System.out.println("Recommended vehicles for the trip to port " + desPortId);
        if (recommendedVehicles.size() >= 1) {
            if (chosenContainers.size() == 1) {
                CreateTable.setShowVerticalLines(true);
                CreateTable.setHeaders("TRUCK ID", "TRUCK NAME", "CURRENT FUEL", "CARRYING CAPACITY", "FUEL CAPACITY", "TRUCK TYPE");
                for (Vehicle vehicle : recommendedVehicles) {
                    if (vehicle instanceof Truck truck) {
                        CreateTable.addRow(truck.getId(), truck.getName(), Double.toString(truck.getCurrentFuel()), Double.toString(truck.getCarryingCapacity()), Double.toString(truck.getFuelCapacity()), truck.getTruckType());
                    }
                }
                CreateTable.render();
                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());
            }

            if (chosenContainers.size() > 1) {
                CreateTable.setShowVerticalLines(true);
                CreateTable.setHeaders("SHIP ID", "SHIP NAME", "CURRENT FUEL", "CARRYING CAPACITY", "FUEL CAPACITY");
                for (Vehicle vehicle : recommendedVehicles) {
                    if (vehicle instanceof Ship ship) {
                        CreateTable.addRow(ship.getId(), ship.getName(), Double.toString(ship.getCurrentFuel()), Double.toString(ship.getCarryingCapacity()), Double.toString(ship.getFuelCapacity()));
                    }
                }
                CreateTable.render();
                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());
            }
        }else {
            System.out.println("There is no vehicle suitable for the trip!!!!");
        }
        System.out.println("Do you want to use recommended vehicles for the trip ?(Yes/No)");
        do {
            yesNo = sc.nextLine();
            if (yesNo.equals("No") && checkYesNoQuestion(yesNo)){
                isUsedRecommendation = false;
            }
        }while (!checkYesNoQuestion(yesNo));
        while (isUsedRecommendation){
            IOVehicle ioVehicle = new IOVehicle();
            System.out.print("Choose the vehicle from recommendation: ");
            String vehicleId = sc.nextLine();
            double requiredFuel = ioVehicle.calFuelNeed(vehicleId, desPortId);
            for (Vehicle vehicle : recommendedVehicles){
                if (vehicle.getId().equals(vehicleId)){
                    if (vehicle.getCurrentFuel() < requiredFuel){
                        System.out.println("The current fuel of vehicle not enough!!! Do you want to refuel ??");
                        do {
                            yesNo = sc.nextLine();
                            if (yesNo.equals("No") && checkYesNoQuestion(yesNo)){
                                isUsedRecommendation = false;
                            }
                        }while (!checkYesNoQuestion(yesNo));

                    }
                }else {
                    System.out.println("The vehicle is ready for the trip");
                }
            }
        }
        managerMenu.ManagerManuViewModel(manager);
    }
}
