package View.Manager;

import Controller.IOPort;
import Model.Port.Port;
import Model.User.Manager;
import View.HomePageView;
import View.LoginView;
import lib.crud.CreateTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ManagerMenu {
    private static final int OPTION_CHECK_VEHICLES = 1;
    private static final int OPTION_CHECK_CONTAINER = 2;
    private static final int OPTION_RECOMMENDED_VEHICLES = 3;
    private static final int LOG_OUT = 4;
    public void ManagerManuViewModel(Manager manager) throws IOException {
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("UserName", "Port ID");
        CreateTable.addRow(manager.getUsername(), manager.getPortId());
        CreateTable.render();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
        ChosenContainersView chosenContainersView = new ChosenContainersView();
        boolean loggedIn = true;
        IOPort ioPort = new IOPort();
        Port port = ioPort.getPortDetails(manager.getPortId());
        if (port.isLandingAbility()){
            System.out.println("Welcome back port." + manager.getPortId() + " manager");
            System.out.println("Please choose option");
            System.out.println("1: Check all vehicles");
            System.out.println("2: Check all containers");
            System.out.println("3: Recommended vehicles for trip");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Your option: ");
            while (loggedIn){
                try {
                    int option = Integer.parseInt(reader.readLine());
                    switch (option) {
                        case OPTION_CHECK_VEHICLES:
                            manager.showVehicle();
                            break;
                        case OPTION_CHECK_CONTAINER:
                            manager.showContainer();
                            break;
                        case OPTION_RECOMMENDED_VEHICLES:
                            chosenContainersView.chosenContainerViewModel(manager);
                            break;
                        case LOG_OUT:
                            HomePageView.HomeViewModel();
                            loggedIn = false;
                            break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        } else {
            System.out.println("The port that you manage is blocked by the admin!!!!");
        }
    }
}
