package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.services.AdminService;
import src.main.java.WolfBooks.models.UserModel;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminController {
    private final AdminService adminService;
    private final Scanner scanner;
    private UserModel currentAdmin;
    private final SimpleDateFormat dateFormat;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void start(UserModel admin) {
        this.currentAdmin = admin;
        showAdminMenu();
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Create Faculty Account");
            System.out.println("2. Create E-textbook");
            System.out.println("3. Modify E-textbooks");
            System.out.println("4. Create New Active Course");
            System.out.println("5. Create New Evaluation Course");
            System.out.println("6. Logout");
            System.out.print("Enter your choice (1-6): ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        handleCreateFacultyAccount();
                        break;
                    case "2":
                        handleCreateETextbook();
                        break;
                    case "3":
                        handleModifyETextbooks();
                        break;
                    case "4":
                        handleCreateActiveCourse();
                        break;
                    case "5":
                        handleCreateEvaluationCourse();
                        break;
                    case "6":
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleCreateFacultyAccount() {
        System.out.println("\n=== Create Faculty Account ===");
        try {
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            System.out.println("\n1. Add User");
            System.out.println("2. Go Back");
            System.out.print("Enter your choice (1-2): ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                if (adminService.createFacultyAccount(firstName, lastName, email, password)) {
                    System.out.println("Faculty account created successfully!");
                } else {
                    System.out.println("Failed to create faculty account.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCreateETextbook() {
        System.out.println("\n=== Create E-textbook ===");
        try {
            System.out.print("Enter E-textbook Title: ");
            String title = scanner.nextLine();

            System.out.print("Enter E-textbook ID: ");
            String textbookId = scanner.nextLine();

            System.out.println("\n1. Add New Chapter");
            System.out.println("2. Go Back");
            System.out.print("Enter your choice (1-2): ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                if (adminService.createTextbook(title, textbookId)) {
                    handleAddNewChapter(textbookId);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddNewChapter(String textbookId) {
        System.out.println("\n=== Add New Chapter ===");
        try {
            System.out.print("Enter Chapter ID: ");
            String chapterId = scanner.nextLine();

            System.out.print("Enter Chapter Title: ");
            String chapterTitle = scanner.nextLine();

            System.out.println("\n1. Add New Section");
            System.out.println("2. Go Back");
            System.out.println("3. Landing Page");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            boolean isHidden = false;
            String createdBy = "admin";

            switch (choice) {
                case "1":
                    if (adminService.createChapter(textbookId, chapterId, chapterTitle, isHidden, createdBy)) {
                        handleAddNewSection(textbookId, chapterId);
                    }
                    break;
                case "2":
                    return;
                case "3":
                    showAdminMenu();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleModifyETextbooks() {
        System.out.println("\n=== Modify E-textbook ===");
        try {
            System.out.print("Enter E-textbook ID: ");
            String textbookId = scanner.nextLine();

            System.out.println("1. Add New Chapter");
            System.out.println("2. Modify Chapter");
            System.out.println("3. Go Back");
            System.out.println("4. Landing Page");
            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleAddNewChapter(textbookId);
                    break;
                case "2":
                    handleModifyChapter(textbookId);
                    break;
                case "3":
                    return;
                case "4":
                    showAdminMenu();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCreateActiveCourse() {
        System.out.println("\n=== Create New Active Course ===");
        try {
            System.out.print("Enter Course ID: ");
            String courseId = scanner.nextLine();

            System.out.print("Enter Course Name: ");
            String courseName = scanner.nextLine();

            System.out.print("Enter E-textbook ID: ");
            String textbookId = scanner.nextLine();

            System.out.print("Enter Faculty Member ID: ");
            String facultyId = scanner.nextLine();

            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            Date startDate = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            Date endDate = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter Token (7 characters): ");
            String token = scanner.nextLine();

            System.out.print("Enter Course Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());

            System.out.println("\n1. Save");
            System.out.println("2. Cancel");
            System.out.println("3. Landing Page");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (adminService.createActiveCourse(courseId, courseName, textbookId,
                            facultyId, startDate, endDate, token, capacity)) {
                        System.out.println("Active course created successfully!");
                    } else {
                        System.out.println("Failed to create active course.");
                    }
                    break;
                case "2":
                    return;
                case "3":
                    showAdminMenu();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCreateEvaluationCourse() {
        System.out.println("\n=== Create New Evaluation Course ===");
        try {
            System.out.print("Enter Course ID: ");
            String courseId = scanner.nextLine();

            System.out.print("Enter Course Name: ");
            String courseName = scanner.nextLine();

            System.out.print("Enter E-textbook ID: ");
            String textbookId = scanner.nextLine();

            System.out.print("Enter Faculty Member ID: ");
            String facultyId = scanner.nextLine();

            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            Date startDate = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            Date endDate = dateFormat.parse(scanner.nextLine());

            System.out.println("\n1. Save");
            System.out.println("2. Cancel");
            System.out.println("3. Landing Page");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (adminService.createEvaluationCourse(courseId, courseName, textbookId,
                            facultyId, startDate, endDate)) {
                        System.out.println("Evaluation course created successfully!");
                    } else {
                        System.out.println("Failed to create evaluation course.");
                    }
                    break;
                case "2":
                    return;
                case "3":
                    showAdminMenu();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Additional helper methods for content management would go here
    private void handleAddNewSection(String textbookId, String chapterId) {
        // Implementation for adding new section
    }

    private void handleModifyChapter(String textbookId) {
        // Implementation for modifying chapter
    }
}