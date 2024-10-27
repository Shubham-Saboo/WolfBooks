package src.main.java.WolfBooks;
import java.sql.*;

public class App {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner sc = new Scanner(System.in);
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/wolfbooks";
            String dbUser = "root";
            String dbPass = "";
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);



            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}