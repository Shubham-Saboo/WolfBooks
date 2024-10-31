package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.models.UserModel;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WolfbooksService {

    UserModel user;

    Connection conn;

    Map<Integer, String> roles;

    public WolfbooksService(Connection conn) {
        this.conn = conn;
        roles = new HashMap<Integer, String>();
        roles.put(1, "admin");
        roles.put(2, "faculty");
        roles.put(3, "ta");
        roles.put(4, "student");
    }

    public void runService() {
        boolean run = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to WolfBooks Service");
        while (run) {
            if (user == null) {
                run = login(sc);
            }
            else {
                user.getOptions();
                int option = sc.nextInt();
                user.useOption(option);
            }
        }
        user = null;
    }

    public boolean login(Scanner sc) {
        System.out.println("Please login.");
        System.out.println("1. Admin Login");
        System.out.println("2. Faculty Login");
        System.out.println("3. TA Login");
        System.out.println("4. Student Login");
        System.out.println("5. Exit");
        int role = sc.nextInt();
        if (role == 5) {
            return false;
        }
        String username = sc.nextLine();
        String password = sc.nextLine();
        // Prepare a SQL statement and query the db for the user.
        // See if the user exists

        return true;
    }
}
