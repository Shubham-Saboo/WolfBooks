package src.main.java.WolfBooks.controller;
import src.main.java.WolfBooks.services.TeachingAssistantService;
import src.main.java.WolfBooks.services.AdminService;
import src.main.java.WolfBooks.models.UserModel;
import src.main.java.WolfBooks.models.TeachingAssistantModel;
import src.main.java.WolfBooks.dao.QueryDAO;

import java.sql.SQLException;
import java.util.Scanner;


public class LandingPageController {
    private final AdminService adminService;
    private final Scanner scanner;
    private final AdminController adminController;
    private final TeachingAssistantService taService;
    private final TeachingAssistantController taController;
    // TODO: Add other controllers when implemented
    // private final FacultyController facultyController;
    // private final TAController taController;
    private final StudentController studentController;

    public LandingPageController(AdminService adminService,TeachingAssistantService taService ) {
        this.adminService = adminService;
        this.taService = taService;
        this.scanner = new Scanner(System.in);
        this.adminController = new AdminController(adminService);
        this.taController = new TeachingAssistantController(taService);

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
                        studentController.start();
                        break;
                    case "5": // Exit
                        System.out.println("Thank you for using WolfBooks. Goodbye!");
                        running = false;
                        break;
                    case "6":
                        displayQueries();
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
        System.out.println("6. Queries");
        System.out.print("Enter your choice (1-6): ");
    }

    private void displayQueries() throws SQLException, SQLException {
        QueryDAO queryDAO = new QueryDAO();

        System.out.println("1. Write a query that prints the number of sections of the first chapter of a textbook.");
        System.out.println("2. Print the names of faculty and TAs of all courses. For each person print their role next to their names.");
        System.out.println("3. For each active course, print the course id, faculty member and total number of students.");
        System.out.println("4. Find the course which has the largest waiting list.");
        System.out.println("5. Print the contents of Chapter 02 of textbook 101.");
        System.out.println("6. For Q2 of Activity0 in Sec02 of Chap01 in textbook 101, print the incorrect answers and explanations.");
        System.out.println("7. Find any book that is in active status by one instructor but evaluation status by a different instructor.");

        System.out.print("Enter your choice (1-7): ");

        String choice = scanner.nextLine();


        switch (choice) {
            case "1":
                System.out.print("Enter textbookId: ");
                String textbookID = scanner.nextLine();
                queryDAO.printNumberOfSectionsInFirstChapter(textbookID, "Chap01");
                break;
            case "2":
                queryDAO.printFacultyAndTAsWithRoles();
                break;
            case "3":
                queryDAO.printActiveCoursesWithFacultyAndStudents();
                break;
            case "4":
                queryDAO.printCourseWithLargestWaitingList();
                break;
            case "5":
                queryDAO.printContentsOfChapter02();
                break;
            case "6":
                queryDAO.printIncorrectAnswersForQuestion();
                break;
            case "7":
                queryDAO.findBooksInActiveAndEvaluationStatus();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }

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
                        try {
                        TeachingAssistantModel ta = taService.authenticateTA(userId, password);
                        if (ta != null) {
                            taController.start(ta);
                        } else {
                            System.out.println("Login failed. Invalid credentials.");
                        }
                    } catch (Exception e) {
                        System.out.println("Login error: " + e.getMessage());
                    }
                                    System.out.println("TA login successful!");
                        break;
                    case "student":
                        System.out.println("Student login successful! (Controller not implemented)");
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