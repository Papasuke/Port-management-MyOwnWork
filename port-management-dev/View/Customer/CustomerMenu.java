package View.Customer;

import Model.User.Customer;
import View.HomePageView;
import View.LoginView;
import lib.crud.CreateTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CustomerMenu {
    private static final int OPTION_REGISTER_TRUCK = 1;
    private static final int OPTION_REGISTER_SHIP = 2;
    private static final int OPTION_LOGOUT = 3;
    public void CustomerMenuViewModel(Customer customer) {
        RegisterVehicleView registerVehicleView = new RegisterVehicleView();
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("UserName", "Vehicle ID");
        CreateTable.addRow(customer.getUsername(), customer.getVehicleId());
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
        boolean loggedIn = true;

            if ((customer.isAccepted() && customer.getVehicleId().equals("none"))){
                System.out.println("Your account is activated. Please register your vehicle!!!");
                System.out.println("1: Register truck");
                System.out.println("2: Register ship");
                System.out.println("3: Log out");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Your option: ");
                while (loggedIn){
                    try {
                        int option = Integer.parseInt(reader.readLine());
                        switch (option) {
                            case OPTION_REGISTER_TRUCK:
                                registerVehicleView.RegisterTruck(customer);
                                break;
                            case OPTION_REGISTER_SHIP:
                                registerVehicleView.RegisterShip(customer);
                                break;
                            case OPTION_LOGOUT:
                                LoginView.LoginViewModel();
                                loggedIn = false;
                                break;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else if(!customer.isAccepted()){
                System.out.println("Your account is inactivated. Please wait for activation from the admin");
                HomePageView.HomeViewModel();
            }
            else  {
                System.out.println("Hi" + customer.getUsername());
                System.out.println("1: Your vehicle details");
                System.out.println("2: Register ship");
                System.out.println("3: Log out");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while (loggedIn){
                    System.out.print("Your option: ");
                    try {
                        int option = Integer.parseInt(reader.readLine());
                        switch (option) {
                            case 1:
                                customer.getVehicleDetail();
                                break;
                            case 2:
                                registerVehicleView.RegisterShip(customer);
                                break;
                            case 3:
                                LoginView.LoginViewModel();
                                loggedIn = false;
                                break;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    }
}
