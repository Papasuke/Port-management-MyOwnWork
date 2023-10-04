package View;

import Controller.IOUser;
import Model.User.Admin;
import Model.User.Customer;
import Model.User.Manager;
import Model.User.User;
import View.Customer.CustomerMenu;
import View.Manager.ManagerMenu;

import java.io.IOException;
import java.util.Scanner;

public class LoginView {
    public static void LoginViewModel() throws IOException {
        IOUser ioUser = new IOUser();
        User[] users = ioUser.getAllUsers();
        ManagerMenu managerMenu = new ManagerMenu();
        CustomerMenu customerMenu = new CustomerMenu();
        Scanner sc = new Scanner(System.in);
        System.out.println("╔═══════════════════════╗");
        System.out.println("║         LOGIN         ║");
        System.out.println("╚═══════════════════════╝");
        System.out.print("Enter username: ");
        String userName = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (ioUser.isUserNameValid(userName) && ioUser.isPasswordValid(password)){
            for (User user : users){
                if (user.getUsername().equals(userName) && (user instanceof Customer customer)){
                    customerMenu.CustomerMenuViewModel(customer);
                }
                if (user.getUsername().equals(userName) && (user instanceof Manager manager)){
                    managerMenu.ManagerManuViewModel(manager);
                }
                else {

                }
            }
        }
    }
}
