import Controller.IOContainer;
import Controller.IOPort;
import Controller.IOUser;
import Controller.IOVehicle;
import Model.Container.Container;
import Model.Port.Port;
import Model.User.User;
import Model.Vehicle.Ship;
import Model.Vehicle.Truck;
import Model.Vehicle.Vehicle;


import java.io.IOException;
import java.util.List;

public class TestMain {
    public static void main(String[] args)throws IOException {
//           User user = new User();
////   String username = user.userNameRegisterInput();
////   String password = user.passwordSignUpInput();
////   String vehicleId = user.ownVehicleInput();
////        user.addUser(username, password, vehicleId);
//
//    user.acceptedUser("khoa123");
//
////   System.out.println("\nSuccessfully Registering");
////        Container container = new Container("c-122", 100.001, "DryStorage","123455","1234567");
////        container.addContainer(container);
////

//        IOContainer ioContainer = new IOContainer();
//        Container[] containers = ioContainer.getAllContainers();
//        for (Container container: containers){
//            System.out.println(container.toString());
//        }

//        Container containers1 = ioContainer.getContainerDetails("c-124");
//        System.out.println(containers1);

//        ioContainer.deleteContainer("c-122");
//        IOVehicle ioVehicle = new IOVehicle();
//        List<Vehicle> vehicles = ioVehicle.getAllVehicle();
//        IOContainer ioContainer = new IOContainer();
//        Container container = new Container("c-122", 20, "Refrigerated", "dunno", "p-123");
//        ioContainer.addContainer(container);
//        ioContainer.loadContainerToVehicle("c-123", "tr-123");
//        for (Vehicle vehicle : vehicles) {
//            if (vehicle instanceof Truck truck) {
//                System.out.println("Truck ID: " + truck.getId());
//                System.out.println("Truck Name: " + truck.getName());
//                System.out.println("Truck Current Fuel: " + truck.getCurrentFuel());
//                System.out.println("Truck Carrying Capacity: " + truck.getCarryingCapacity());
//                System.out.println("Truck Fuel Capacity: " + truck.getFuelCapacity());
//                System.out.println("Truck Stay Port ID: " + truck.getStayPortId());
//                System.out.println("Truck Type: " + truck.getTruckType());
//            } else if (vehicle instanceof Ship ship) {
//                System.out.println("Ship ID: " + ship.getId());
//                System.out.println("Ship Name: " + ship.getName());
//                System.out.println("Ship Current Fuel: " + ship.getCurrentFuel());
//                System.out.println("Ship Carrying Capacity: " + ship.getCarryingCapacity());
//                System.out.println("Ship Fuel Capacity: " + ship.getFuelCapacity());
//                System.out.println("Ship Stay Port ID: " + ship.getStayPortId());
//            }
//            System.out.println(); // Add a blank line between each vehicle
//        }
//        Vehicle vehicle = new Truck("137", "SGP", 100, 100, 100, "P-123","ASDASDSD");
//        ioVehicle.addVehicle(vehicle);
//        Vehicle vehicle1 = new Ship("134", "SGP", 100, 100, 100, "P-123");
//        ioVehicle.addVehicle(vehicle1);
//        ioVehicle.removeVehicleById("sh-123");
//        IOPort ioPort = new IOPort();
//        Port port = new Port("p-123", "HaiDuong", 100.0, 76.0, 1000000.0, true);
//        ioPort.addPort(port);
//        IOUser ioUser = new IOUser();
//        ioUser.printUsersByRole(ioUser.getAllUsers());
//        System.out.println(ioUser.isUserNameValid("Phuoc"));
        IOPort ioPort = new IOPort();
        double distance = ioPort.calDistanceToPort(ioPort.getPortDetails("p-123"), ioPort.getPortDetails("p-127"));
        System.out.println(distance);
    }
}
