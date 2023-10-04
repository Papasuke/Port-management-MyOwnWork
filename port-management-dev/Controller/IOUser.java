package Controller;

import Model.User.Admin;
import Model.User.Customer;
import Model.User.Manager;
import Model.User.User;
import lib.crud.Read;
import lib.crud.Write;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOUser {
    //ManagerUser
    private static final String filepath = "data/users.txt";
    private static final String attributes = "USERNAME,PASSWORD,ROLE,MANAGE_PORT,VEHICLE,ACCESS_RIGHT";



    public User[] getAllUsers() throws IOException {
        List<String[]> userList = Read.readAllLine(filepath);
        User[] users = new User[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            String[] userValues = userList.get(i);
            String name = userValues[0];
            String password = userValues[1];
            String role = userValues[2];
            users[i] = createUser(name, password, role, userValues);
        }
        return users;
    }

    private User createUser(String name, String password, String role, String[] userValues) {
        switch (role) {
            case "Manager":
                String portId = userValues[3];
                return new Manager(name, password, role, portId);
            case "Admin":
                return new Admin(name, password, role);
            case "Customer":
                String vehicleId = userValues[3];
                boolean isAccepted = Boolean.parseBoolean(userValues[4]);
                return new Customer(name, password, role, vehicleId, isAccepted);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public void printUsersByRole(User[] users) {
        for (User user : users) {
            if (user instanceof Manager || user instanceof Customer || user instanceof Admin) {
                System.out.println(user.toString());
            }
        }
    }


    public void addNewCustomer(String userName, String password) throws IOException {
        if (checkValidUser(userName)) {
                Customer customer = new Customer(userName, password, "Customer", "none", false);
                String stringUserObj = "" + customer.getUsername() +
                        "," + customer.getPassword() +
                        "," + customer.getRole() +
                        "," + customer.getVehicleId() +
                        "," + customer.isAccepted();
                Write.write(filepath, attributes, stringUserObj);
        }
    }

    public void addNewPortManager(String password, String PortId) throws IOException{
        IOPort ioPort = new IOPort();
        if (ioPort.checkAvailablePort(PortId) && checkValidManager(PortId)) {
            Manager manager = new Manager("M." +PortId, password, "manager", PortId);
            String stringManagerObj = "" + manager.getUsername() +
                    "," + manager.getPassword() +
                    "," + manager.getRole() +
                    "," + manager.getPortId();
            Write.write(filepath, attributes, stringManagerObj);
        }
    }

    public boolean checkValidManager(String PortId)throws IOException{
        User[] users = getAllUsers();
        for(User user : users){
            if (user instanceof Manager manager){
                return !manager.getPortId().equals(PortId);
            }
        }
        return false;
    }

    public boolean checkValidUser(String userName) throws IOException {
        User[] users = getAllUsers();
        for (User user : users) {
            if (user instanceof Customer customer && customer.getUsername().equals(userName)) {
                System.out.println("This username is registered by another user");
                return false;
            }
        }
        return true;
    }


    public boolean checkValidVehicle(String vehicleId) throws IOException {
        User[] users = getAllUsers();
        boolean isMatchFound = false;
        for (User user : users) {
            if (user instanceof Customer customer) {
                if (!customer.getVehicleId().equals(vehicleId)) {
                    isMatchFound = true;
                }
            }
        }
        return isMatchFound;
    }


    public boolean isUserNameValid(String userName) throws IOException {
        User[] users = getAllUsers();
        boolean isMatchFound = false;
        for (User user : users) {
            if (user.getUsername().equals(userName)) {
                isMatchFound = true;
                break;
            }
        }
        return isMatchFound;
    }

    public boolean isPasswordValid(String password)throws  IOException{
        User[] users = getAllUsers();
        boolean isMatchFound = false;
        for (User user : users) {
            if(user.getPassword().equals(this.hash(password))){
                isMatchFound = true;
            };
        }
        return isMatchFound;
    }


    //Hashing password
    public String hash(String data){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());
            byte[] resultByteArr = messageDigest.digest();
            StringBuilder sb = new StringBuilder();

            for(byte b: resultByteArr){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

    public void deleteUser(String userName)throws IOException{
        User[] users = getAllUsers();
        List<User> userList = new ArrayList<>(Arrays.asList(users));
        userList.removeIf(user -> user.getUsername().equals(userName));
        if (userList.size() == users.length) {
            System.out.println("There is no user: " + userName);
        }
        users = userList.toArray(new User[0]);
        Write.RemoveDataFromFile(filepath);
        writeUsers(users);
    }

    public void writeUsers(User[] users)throws IOException{
        for (User user: users){
            if (user instanceof Customer customer){
                String stringCustomerObj = "" + customer.getUsername() +
                        "," + customer.getPassword() +
                        "," + customer.getRole() +
                        "," + customer.getVehicleId() +
                        "," + customer.isAccepted();
                Write.write(filepath,attributes,stringCustomerObj);
            }
            else if (user instanceof Admin admin){
                String stringAdminObj = "" + admin.getUsername() +
                        "," + admin.getPassword() +
                        "," + admin.getRole();
                Write.write(filepath,attributes,stringAdminObj);
            }
            else if (user instanceof Manager manager){
                String stringManagerObj = "" + manager.getUsername() +
                        "," + manager.getPassword() +
                        "," + manager.getRole() +
                        "," + manager.getPortId();
                Write.write(filepath,attributes,stringManagerObj);
            }
        }
    }
}
