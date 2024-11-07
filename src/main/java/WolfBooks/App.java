package src.main.java.WolfBooks;

import java.sql.*;
import java.util.*;

import src.main.java.WolfBooks.dao.*;
import src.main.java.WolfBooks.services.FacultyService;
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
            conn = DatabaseConnection.getConnection();
            System.out.println("Connected to database");
            // Initialize DAOs with connection
            UserDAO userDAO = new UserDAO();
            TextbookDAO textbookDAO = new TextbookDAO();
            ChapterDAO chapterDAO = new ChapterDAO();
            SectionDAO sectionDAO = new SectionDAO();
            BlockDAO blockDAO = new BlockDAO();
            TeachingAssistantDAO teachingAssistantDAO = new TeachingAssistantDAO();
            QueryDAO QueryDAO = new QueryDAO();

            CourseDAO CourseDAO = new CourseDAO();
            

            // TODO: Initialize other DAOs when implemented

            // Initialize Services
            AdminService adminService = new AdminService(userDAO);
            WolfbooksService wolfbooksService = new WolfbooksService(conn);
            TeachingAssistantService taService = new TeachingAssistantService(teachingAssistantDAO);
            FacultyService facultyService = new FacultyService();
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