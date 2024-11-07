package src.main.java.WolfBooks;

import java.sql.*;
import java.util.*;

import src.main.java.WolfBooks.dao.*;
import src.main.java.WolfBooks.util.*;
import src.main.java.WolfBooks.services.WolfbooksService;
import src.main.java.WolfBooks.controller.LandingPageController;
import src.main.java.WolfBooks.services.AdminService;
import src.main.java.WolfBooks.services.TeachingAssistantService;

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
                init.insertDemoData(conn);
                conn.close();
                conn = DriverManager.getConnection(dbUrl + dbSchema, dbUser, dbPass);
            }

            System.out.println("Connected to database");
            // Initialize DAOs with connection
            UserDAO userDAO = new UserDAO();
            TextbookDAO textbookDAO = new TextbookDAO();
            ChapterDAO chapterDAO = new ChapterDAO();
            SectionDAO sectionDAO = new SectionDAO();
            BlockDAO blockDAO = new BlockDAO();
            TeachingAssistantDAO teachingAssistantDAO = new TeachingAssistantDAO();
            QueryDAO QueryDAO = new QueryDAO();
            

            // TODO: Initialize other DAOs when implemented

            // Initialize Services
            AdminService adminService = new AdminService(userDAO);
            WolfbooksService wolfbooksService = new WolfbooksService(conn);
            TeachingAssistantService taService = new TeachingAssistantService(teachingAssistantDAO);

            // Initialize Controllers
            LandingPageController landingPage = new LandingPageController(adminService,taService);

            // Start the application
            System.out.println("Starting WolfBooks Application...");
            landingPage.start();

            conn.close();




        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
