package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.services.AdminService;
import src.main.java.WolfBooks.models.UserModel;
import java.util.Scanner;

public class LandingPageController {
    private final AdminService adminService;
    private final Scanner scanner;
    private final AdminController adminController;
    // TODO: Add other controllers when implemented
    // private final FacultyController facultyController;
    // private final TAController taController;
     private final StudentController studentController;

    public LandingPageController(AdminService adminService) {
        this.adminService = adminService;
        this.scanner = new Scanner(System.in);
        this.adminController = new AdminController(adminService);
        this.studentController = new StudentController();
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1": // Admin Login
                        handleLogin("admin");
                        break;
                    case "2": // Faculty Login
                        handleLogin("faculty");
                        break;
                    case "3": // TA Login
                        handleLogin("teaching_assistant");
                        break;
                    case "4": // Student Login
//                        handleLogin("student");
                        studentController.start();
                        break;
                    case "5": // Exit
                        System.out.println("Thank you for using WolfBooks. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=== WolfBooks E-Textbook and Assessment Platform ===");
        System.out.println("1. Admin Login");
        System.out.println("2. Faculty Login");
        System.out.println("3. TA Login");
        System.out.println("4. Student Login");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }

    private void handleLogin(String role) {
        System.out.println("\n=== " + role.toUpperCase() + " Login ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            UserModel user = adminService.authenticateUser(userId, password, role);
            if (user != null) {
                switch (role) {
                    case "admin":
                        adminController.start(user);
                        break;
                    case "faculty":
                        System.out.println("Faculty login successful! (Controller not implemented)");
                        break;
                    case "teaching_assistant":
                        System.out.println("TA login successful! (Controller not implemented)");
                        break;
                    case "student":
//                        System.out.println("Student login successful! (Controller not implemented)");
                        studentController.start();
                        break;
                }
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }
}