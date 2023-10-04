package View;

import Controller.IOUser;

import java.io.IOException;
import java.util.Scanner;

public class RegisterView {
    public static void RegisterViewModel() throws IOException {
        System.out.println("╔═══════════════════════╗");
        System.out.println("║       REGISTER        ║");
        System.out.println("╚═══════════════════════╝");
        IOUser ioUser = new IOUser();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String userName = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (ioUser.checkValidUser(userName)){
            ioUser.addNewCustomer(userName, ioUser.hash(password));
            System.out.println("Successfully register!!!!");
            HomePageView.HomeViewModel();
        }
        else {
            HomePageView.HomeViewModel();
        }
    }
}
