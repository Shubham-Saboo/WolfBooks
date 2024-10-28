package src.main.java.WolfBooks;
import java.sql.*;
import java.util.*;
import src.main.java.WolfBooks.helpers.*;

public class App {
    public static void main(String[] args) {
        Connection conn;
        Scanner sc = new Scanner(System.in);
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/";
            String dbSchema = "WolfBooks";
            String dbUser = "root"; //sc.nextLine(); // Most likely 'root'
            String dbPass = ""; //sc.nextLine(); // Most likely ''
            try {
                conn = DriverManager.getConnection(dbUrl + dbSchema, dbUser, dbPass);
            } catch (SQLException e) {
                conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                InitializeDatabase init = new InitializeDatabase();
                init.createSchema(conn);
                conn.close();
                conn = DriverManager.getConnection(dbUrl + dbSchema, dbUser, dbPass);
            }

            System.out.println("Connected to database");

            conn.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}