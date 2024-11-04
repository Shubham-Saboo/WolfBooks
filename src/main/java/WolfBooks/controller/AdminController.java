package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.services.AdminService;
import src.main.java.WolfBooks.models.UserModel;

import java.util.Arrays;
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
                    System.out.println("E-textbook created successfully!");
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
                        System.out.println("Chapter created successfully!");
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

    private void handleAddNewSection(String textbookId, String chapterId) {
        System.out.println("\n=== Add New Section ===");
        try {
            // Step 1: Get Section details from admin input
            System.out.print("Enter Section ID: ");
            String sectionId = scanner.nextLine();

            System.out.print("Enter Section Title: ");
            String sectionTitle = scanner.nextLine();

            // Step 2: Display menu options for adding blocks or going back
            System.out.println("\n1. Add New Block");
            System.out.println("2. Go Back");
            System.out.println("3. Landing Page");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            boolean isHidden = false;
            String createdBy = "admin";

            switch (choice) {
                case "1":
                    // Option 1: Add a new block after creating a section
                    if (adminService.createSection(textbookId, chapterId, sectionId, sectionTitle, isHidden, createdBy)) {
                        System.out.println("Section created successfully!");
                        handleAddNewBlock(textbookId, chapterId, sectionId); // Redirect to add new block
                    } else {
                        System.out.println("Failed to create section.");
                    }
                    break;
                case "2":
                    // Option 2: Go back without adding a block
                    return;
                case "3":
                    // Option 3: Return to Admin Landing Page
                    showAdminMenu();
                    break;
                default:
                    // Handle invalid input
                    System.out.println("Invalid choice. Returning to Admin Landing Page.");
                    showAdminMenu();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddNewBlock(String textbookId, String chapterId, String sectionId) {
        System.out.println("\n=== Add New Block ===");
        try {
            // Step 1: Get Block details from admin input
            System.out.print("Enter Content Block ID: ");
            String blockId = scanner.nextLine();

            // Step 2: Display menu options for adding text, picture, or activity
            System.out.println("\n1. Add Text");
            System.out.println("2. Add Picture");
            System.out.println("3. Add Activity");
            System.out.println("4. Go Back");
            System.out.println("5. Landing Page");
            System.out.print("Enter your choice (1-5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Option 1: Redirect to add text
                    handleAddText(textbookId,chapterId, sectionId, blockId);
                    break;
                case "2":
                    // Option 2: Redirect to add picture
                    handleAddPicture(textbookId,chapterId, sectionId, blockId);
                    break;
                case "3":
                    // Option 3: Redirect to add activity
                    handleAddActivity(textbookId,chapterId, sectionId, blockId);
                    break;
                case "4":
                    // Option 4: Go back without saving
                    return;
                case "5":
                    // Option 5: Return to Admin Landing Page
                    showAdminMenu();
                    break;
                default:
                    // Handle invalid input
                    System.out.println("Invalid choice. Returning to Admin Landing Page.");
                    showAdminMenu();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddText(String textbookId, String chapterId, String sectionId, String blockId) {
        System.out.println("\n=== Add Text ===");
        try {
            // Step 1: Get Text details from admin input
            System.out.print("Enter Text Content: ");
            String textContent = scanner.nextLine();

            // Step 2: Display menu options for saving or going back
            System.out.println("\n1. Save Text");
            System.out.println("2. Go Back");
            System.out.println("3. Landing Page");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Save text in the database using AdminService
                    if (adminService.createBlock(sectionId, textbookId, blockId, chapterId, "text", textContent, false, "Admin")) {
                        System.out.println("Text added successfully!");
                    } else {
                        System.out.println("Failed to add text.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showAdminMenu(); // Return to landing page
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddPicture(String textbookId, String chapterId, String sectionId, String blockId) {
        System.out.println("\n=== Add Picture ===");
        try {
            // Step 1: Get Picture details from admin input
            System.out.print("Enter Picture URL or Path: ");
            String pictureUrl = scanner.nextLine();

            // Step 2: Display menu options for saving or going back
            System.out.println("\n1. Save Picture");
            System.out.println("2. Go Back");
            System.out.println("3. Landing Page");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Save picture in the database using AdminService
                    if (adminService.createBlock(sectionId, textbookId, blockId, chapterId, "picture", pictureUrl, false, "Admin" )) {
                        System.out.println("Picture added successfully!");
                    } else {
                        System.out.println("Failed to add picture.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showAdminMenu(); // Return to landing page
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddActivity(String textbookId, String chapterId, String sectionId, String blockId) {
        System.out.println("\n=== Add Activity ===");
        try {
            // Step 1: Get Activity details from admin input
            System.out.print("Enter Activity ID: ");
            String activityId = scanner.nextLine();

            // Step 2: Display menu options for saving or going back
            System.out.println("\n1. Save Activity");
            System.out.println("2. Go Back");
            System.out.println("3. Landing Page");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Save activity in the database using AdminService
                    if (adminService.createBlock(sectionId, textbookId, blockId, chapterId, "activity", activityId, false, "Admin")) {
                        adminService.createActivity(textbookId, chapterId, sectionId, blockId, activityId, false, "Admin");
                        System.out.println("Activity added successfully!");
                        handleAddQuestion(textbookId, chapterId, sectionId, blockId, activityId); // Redirect to add questions if needed
                    } else {
                        System.out.println("Failed to add activity.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showAdminMenu(); // Return to landing page
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void handleAddQuestion(String textbookId,String chapterId,String sectionId,String blockId,String activityId) {
        System.out.println("\n=== Add New Question ===");
        try {
            // Step 1: Get Question details from admin input
            System.out.print("Enter Question ID: ");
            String questionId = scanner.nextLine();

            System.out.print("Enter Question Text: ");
            String questionText = scanner.nextLine();

            // Collect options individually without loops
            System.out.print("Enter Option 1: ");
            String optionOne = scanner.nextLine();

            System.out.print("Enter Explanation for Option 1: ");
            String explanationOne = scanner.nextLine();

            System.out.print("Enter Option 2: ");
            String optionTwo = scanner.nextLine();

            System.out.print("Enter Explanation for Option 2: ");
            String explanationTwo = scanner.nextLine();

            System.out.print("Enter Option 3: ");
            String optionThree = scanner.nextLine();

            System.out.print("Enter Explanation for Option 3: ");
            String explanationThree = scanner.nextLine();

            System.out.print("Enter Option 4: ");
            String optionFour = scanner.nextLine();

            System.out.print("Enter Explanation for Option 4: ");
            String explanationFour = scanner.nextLine();

            System.out.print("Enter Correct Answer (1/2/3/4): ");
            String correctAnswer = scanner.nextLine();


            // Step 2: Display menu options for saving or going back
            System.out.println("\n1. Save");
            System.out.println("2. Cancel");
            System.out.println("3. Landing Page");

            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Save question in the database using AdminService
                    if (adminService.addQuestion(textbookId ,chapterId ,sectionId ,blockId ,activityId ,
                            questionId ,questionText ,explanationOne ,
                            explanationTwo ,explanationThree ,
                            explanationFour ,optionOne ,optionTwo ,
                            optionThree ,optionFour , correctAnswer)) {
                        System.out.println("Question added successfully!");
                    } else {
                        System.out.println("Failed to add question.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showAdminMenu(); // Return to landing page
                    break;
                default:
                    System.out.println("Invalid choice.");
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
    

    private void handleModifyChapter(String textbookId) {
        // Implementation for modifying chapter
    }
}