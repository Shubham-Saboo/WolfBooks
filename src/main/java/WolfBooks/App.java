package src.main.java.WolfBooks;
import java.sql.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner sc = new Scanner(System.in);
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/wolfbooks";
            String dbUser = sc.nextLine(); // Most likely 'root'
            String dbPass = sc.nextLine(); // Most likely ''
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);



            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}