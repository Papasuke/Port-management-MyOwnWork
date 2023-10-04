package View;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HomePageView {
    public static void HomeViewModel(){
        String sb = """
                                                             ╔════════════════════════╗
                                                             ║    WELCOME TO SYSTEM   ║
                                                             ╚════════════════════════╝
                1: Login
                2: Register
                3: Exit
                """;
        System.out.println(sb);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Your option: ");
        while (true){
            try {
                int option = Integer.parseInt(reader.readLine());
                switch (option){
                    case 1:
                        LoginView.LoginViewModel();
                        break;
                    case 2:
                        RegisterView.RegisterViewModel();
                        break;
                    case 3:
                        return; // Exit the program
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
