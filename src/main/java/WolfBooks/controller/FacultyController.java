package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.dao.BlockDAO;
import src.main.java.WolfBooks.services.FacultyService;
import src.main.java.WolfBooks.models.CourseModel;
import src.main.java.WolfBooks.models.UserModel;
import src.main.java.WolfBooks.services.TeachingAssistantService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class FacultyController {
    private FacultyService facultyService;
    private Scanner scanner;

    public FacultyController() {
        this.facultyService = new FacultyService();
        this.scanner = new Scanner(System.in);
    }

    // Login functionality
    public boolean login() throws SQLException {
        while (true) {
            System.out.println("Faculty Login");
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            System.out.println("1. Sign-In");
            System.out.println("2. Go Back");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    if (facultyService.validateLogin(userId, password)) {
                        showFacultyMenu(userId);
                        return true;
                    } else {
                        System.out.println("Login Incorrect");
                    }
                    break;
                case 2:
                    return false; // Go back to home page
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Faculty Landing Menu
    public void showFacultyMenu(String facultyId) throws SQLException {
        while (true) {
            System.out.println("\nFaculty Menu:");
            System.out.println("1. Go to Active Course");
            System.out.println("2. Go to Evaluation Course");
            System.out.println("3. View Courses");
            System.out.println("4. Change Password");
            System.out.println("5. Logout");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    goToActiveCourse(facultyId);
                    break;
                case 2:
                    goToEvaluationCourse(facultyId);
                    break;
                case 3:
                    viewCourses(facultyId);
                    break;
                case 4:
                    changePassword(facultyId);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Active Course Menu
// Change from commented methods to actual method calls:
    private void goToActiveCourse(String facultyId) throws SQLException {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();

        while (true) {
            System.out.println("\nActive Course Menu:");
            System.out.println("1. View Worklist");
            System.out.println("2. Approve Enrollment");
            System.out.println("3. View Students");
            System.out.println("4. Add New Chapter");
            System.out.println("5. Modify Chapters");
            System.out.println("6. Add TA");
            System.out.println("7. Go Back");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewWorklist(courseId);
                    break;
                case 2:
                    approveEnrollment(courseId);
                    break;
                case 3:
                    viewStudents(courseId);
                    break;
                case 4:
                    handleAddNewChapter(courseId,facultyId);
                    break;
                case 5:
                    handleModifyChapters(courseId,facultyId);
                    break;
                case 6:
                    addTA(courseId, facultyId);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }


    // Evaluation Course Menu
    private void goToEvaluationCourse(String facultyId) throws SQLException {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();

        while (true) {
            System.out.println("\nEvaluation Course Menu:");
            System.out.println("1. Add New Chapter");
            System.out.println("2. Modify Chapters");
            System.out.println("3. Go Back");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    handleAddNewChapter(courseId,facultyId);
                    break;
                case 2:
                    handleModifyChapters(courseId,facultyId);
                    break;
                case 3:
                    return; // Go back to faculty menu
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // View Courses
    private void viewCourses(String facultyId) throws SQLException {
        List<CourseModel> courses = facultyService.getCoursesByFaculty(facultyId);

        System.out.println("\nAssigned Courses:");
        for (CourseModel course : courses) {
            System.out.println(course.getCourseTitle());
        }

        System.out.println("\n1. Go Back");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            return; // Go back to faculty menu
        }
    }

    // Change Password
    private void changePassword(String facultyId) throws SQLException {
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        System.out.println("1. Update");
        System.out.println("2. Go Back");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                if (!newPassword.equals(confirmPassword)) {
                    System.out.println("New passwords do not match!");
                    return;
                }
                boolean success = facultyService.changePassword(facultyId, currentPassword, newPassword);
                if (success) {
                    System.out.println("Password updated successfully.");
                } else {
                    System.out.println("Failed to update password.");
                }
                break;
            case 2:
                return; // Go back to faculty menu
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

    // View Worklist of students awaiting approval
    private void viewWorklist(String courseID) throws SQLException {
        System.out.println("\nWaiting List of Students:");
        List<UserModel> worklist = facultyService.getWorklistForCourse(courseID);

        for (UserModel student : worklist) {
            System.out.println(student.getFirstName() + " " + student.getLastName());
        }

        System.out.println("\n1. Go Back");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            return; // Go back to previous menu
        }
    }

    // Approve Enrollment of a student in a course
    private void approveEnrollment(String courseID) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();

        System.out.println("\n1. Save");
        System.out.println("2. Cancel");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                boolean success = facultyService.approveEnrollment(courseID, studentID);
                if (success) {
                    System.out.println("Student enrollment approved successfully.");
                } else {
                    System.out.println("Failed to approve student enrollment.");
                }
                break;
            case 2:
                return; // Go back without saving
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

    // View Students enrolled in a course
    private void viewStudents(String courseID) throws SQLException {
        System.out.println("\nEnrolled Students:");
        List<UserModel> students = facultyService.getStudentsByCourse(courseID);

        for (UserModel student : students) {
            System.out.println(student.getFirstName() + " " + student.getLastName());
        }

        System.out.println("\n1. Go Back");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            return; // Go back to previous menu
        }
    }

    // Add TA to a course
    private void addTA(String courseID, String FacultyId) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Default Password: ");
        String defaultPassword = scanner.nextLine();

        System.out.println("\n1. Save");
        System.out.println("2. Cancel");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                boolean success = facultyService.addTA(firstName, lastName, email, defaultPassword, courseID, FacultyId);
                if (success) {
                    System.out.println("TA added successfully.");
                } else {
                    System.out.println("Failed to add TA.");
                }
                break;
            case 2:
                return; // Go back without saving
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

// Add New Chapter
//private void addNewChapter(String courseID) throws SQLException {
//    Scanner scanner = new Scanner(System.in);
//
//    System.out.print("Enter Unique Chapter ID: ");
//    String chapterID = scanner.nextLine();
//
//    System.out.print("Enter Chapter Title: ");
//    String title = scanner.nextLine();
//
//    System.out.println("\n1. Add New Section");
//    System.out.println("2. Go Back");
//
//    int choice = Integer.parseInt(scanner.nextLine());
//
//    switch (choice) {
//        case 1:
//            boolean success = facultyService.addNewChapter(courseID, chapterID, title);
//            if (success) {
//                addNewSection(chapterID); // Proceed to add section
//            } else {
//                System.out.println("Failed to add chapter.");
//            }
//            break;
//        case 2:
//            return; // Go back without saving
//        default:
//            System.out.println("Invalid choice! Please try again.");
//    }
//}


    private void handleAddNewChapter(String courseId, String facultyId) {
        System.out.println("\n=== Add New Chapter ===");
        System.out.print("Enter Chapter ID: ");
        String chapterId = scanner.nextLine();

        System.out.print("Enter Chapter Title: ");
        String chapterTitle = scanner.nextLine();

        String textbookId = facultyService.getAssignedTextbooksForCourse(facultyId, courseId).get(0);
        System.out.print("Textbook ID received in controller: " + textbookId);

        if (facultyService.addNewChapter(textbookId, chapterId, chapterTitle, facultyId)) {
            System.out.println("Chapter added successfully!");
            promptForSectionAddition(chapterId, courseId,facultyId);
        } else {
            System.out.println("Failed to add chapter.");
        }
    }

    private void promptForSectionAddition(String chapterId, String courseId, String facultyId) {
        System.out.println("1. Add New Section");
        System.out.println("2. Go Back");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            handleAddNewSection(facultyService.getAssignedTextbooksForCourse(facultyId, courseId).get(0), chapterId,facultyId, courseId);
        }
    }

    // ==================== Section Management ====================
    private void handleAddNewSection(String textbookId, String chapterId,String facultyId,String courseId) {
        System.out.println("\n=== Add New Section ===");
        System.out.print("Enter Section ID: ");
        String sectionId = scanner.nextLine();

        System.out.print("Enter Section Title: ");
        String sectionTitle = scanner.nextLine();

        if (facultyService.addNewSection(textbookId, chapterId, sectionId, sectionTitle, facultyId)) {
            System.out.println("Section added successfully!");
            promptForContentBlockAddition(sectionId, chapterId, textbookId, facultyId, courseId);
        } else {
            System.out.println("Failed to add section.");
        }
    }

    private void promptForContentBlockAddition(String sectionId, String chapterId, String textbookId,String facultyId,String courseId) {
        System.out.println("1. Add New Content Block");
        System.out.println("2. Go Back");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            handleAddNewBlock(textbookId, chapterId, sectionId, facultyId, courseId);
        }
    }

    // ==================== Content Block Management ====================

    private void handleAddNewBlock(String textbookId, String chapterId, String sectionId,String facultyId,String courseId) {
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
                    handleAddText(textbookId, chapterId, sectionId, blockId, facultyId, courseId);
                    break;
                case "2":
                    // Option 2: Redirect to add picture
                    handleAddPicture(textbookId, chapterId, sectionId, blockId, facultyId);
                    break;
                case "3":
                    // Option 3: Redirect to add activity
                    handleAddActivity(textbookId, chapterId, sectionId, blockId, facultyId, courseId);
                    break;
                case "4":
                    // Option 4: Go back without saving
                    return;
                case "5":
                    // Option 5: Return to Admin Landing Page
                    showFacultyMenu( facultyId);
                    break;
                default:
                    // Handle invalid input
                    System.out.println("Invalid choice. Returning to faculty Landing Page.");
                    showFacultyMenu( facultyId);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddText(String textbookId, String chapterId, String sectionId, String blockId,String facultyId,String courseId) {
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
                    if (FacultyService.createBlock(sectionId, textbookId, blockId, chapterId, "text", textContent, false, facultyId)) {
                        System.out.println("Text added successfully!");
                    } else {
                        System.out.println("Failed to add text.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showFacultyMenu( facultyId); // Return to landing page
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddPicture(String textbookId, String chapterId, String sectionId, String blockId,String facultyId) {
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
                    if (facultyService.createBlock(sectionId, textbookId, blockId, chapterId, "picture", pictureUrl, false,  facultyId)) {
                        System.out.println("Picture added successfully!");
                    } else {
                        System.out.println("Failed to add picture.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showFacultyMenu( facultyId); // Return to landing page
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== Chapter Modification and Deletion ====================
    private void handleModifyChapters(String courseId,String facultyId) {
        System.out.println("\n=== Modify Chapter ===");
        System.out.print("Enter Chapter ID for modification: ");
        String chapterId = scanner.nextLine();
        String textbookId = facultyService.getAssignedTextbooksForCourse(facultyId, courseId).get(0);

//        System.out.println("1. Modify Chapter Title");
//        System.out.println("2. Delete Chapter");
//        System.out.println("3. Hide Chapter");Chapter
        System.out.println("1. Add new section\n");
        System.out.println("2. Modify section\n");
        System.out.println("3.Hide Chapter");
        System.out.println("4.Delete Chapter");
        System.out.println("5. Go Back\n");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
//            case "1":
//                modifyChapterTitle(chapterId, courseId,facultyId);
//                break;
//            case "2":
//                deleteChapter(chapterId, courseId, facultyId);
//                break;
//            case "3":
//                hideChapter(chapterId, courseId, facultyId);
//                break;
            case "1":
                handleAddNewSection(textbookId, chapterId, facultyId, courseId);
                break;
            case "2":
                handleModifySection(textbookId, chapterId, facultyId,courseId);
                break;
            case "3":
                hideChapter(textbookId, chapterId, facultyId,courseId);
                break;
            case "4":
                deleteChapter(textbookId, chapterId, facultyId,courseId);
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


    private void handleModifyBlock(String textbookId, String chapterId, String sectionId,String facultyId,String courseId) {
        System.out.println("\n=== Modify Content Block ===");
        try {
            // Step 1: Get Block ID from the user
            System.out.print("Enter Content Block ID: ");
            String blockId = scanner.nextLine();

            // Step 2: Check if the block exists in the database using BlockDAO
            if (BlockDAO.findBlock(textbookId, chapterId, sectionId, blockId) == null) {
                System.out.println("Error: Block not found.");
                return; // Exit the method if block doesn't exist
            }

            // Step 3: Display options for modifying the block
            System.out.println("1. Add Text");
            System.out.println("2. Add Picture");
            System.out.println("3. Add New Activity");
            System.out.println("4. Go Back");
            System.out.println("5. Landing Page");
            System.out.print("Enter your choice (1-5): ");
            String choice = scanner.nextLine();

            // Step 4: Handle user choice
            switch (choice) {
                case "1":
                    // Option 1: Redirect to add text
                    handleAddText(textbookId, chapterId, sectionId, blockId, facultyId, courseId);
                    break;
                case "2":
                    // Option 2: Redirect to add picture
                    handleAddPicture(textbookId, chapterId, sectionId, blockId, facultyId);
                    break;
                case "3":
                    // Option 3: Redirect to add activity
                    handleAddActivity(textbookId, chapterId, sectionId, blockId, facultyId, courseId);
                    break;
                case "4":
                    return; // Go back to the previous menu
                case "5":
                    showFacultyMenu( facultyId); // Go back to the admin landing page
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    handleModifyBlock(textbookId, chapterId, sectionId, facultyId, courseId); // Recursive call for invalid input
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

//    private void modifyChapterTitle(String chapterId, String courseId,String facultyId) {
//        System.out.print("Enter new chapter title: ");
//        String newTitle = scanner.nextLine();
//        if (facultyService.modifyChapter(facultyService.getAssignedTextbooksForCourse( facultyId, courseId).get(0), chapterId, newTitle, currentTA.getUserId(), courseId)) {
//            System.out.println("Chapter title updated successfully!");
//        } else {
//            System.out.println("Failed to update chapter title.");
//        }
//    }

    private void deleteChapter(String textboookId,String chapterId, String courseId,String facultyId) {
        if (facultyService.deleteChapter(chapterId, facultyId)) {
            System.out.println("Chapter deleted successfully!");
        } else {
            System.out.println("Failed to delete chapter.");
        }
    }

    private void hideChapter(String textbookId,String chapterId, String courseId,String facultyId) {
        if (facultyService.hideChapter(textbookId, chapterId, facultyId, courseId)) {
            System.out.println("Chapter hidden successfully!");
        } else {
            System.out.println("Failed to hide chapter.");
        }
    }

    // ==================== Section Modification, Deletion, and Hiding ====================
    private void handleModifySection(String textbookId, String chapterId,String facultyId,String courseId) {
        System.out.println("\n=== Modify Section ===");
        System.out.print("Enter Section ID: ");
        String sectionId = scanner.nextLine();

        System.out.println("1. Hide Section ");
        System.out.println("2. Delete Section");
        System.out.print("3. Add new content block\n ");
        System.out.println("4. Modify existing content block\n");
        System.out.println("5. Go Back\n");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                hideSection(sectionId, textbookId, chapterId,facultyId,courseId);
                ;
            case "2":
                deleteSection(sectionId,facultyId);
                break;

            case "3":
                handleAddNewBlock(textbookId, chapterId, sectionId, facultyId, courseId);
                break;
            case "4":
                handleModifyContentBlock(textbookId, chapterId, sectionId, facultyId, courseId);
                break;
            case "5":

                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void hideSection(String sectionId, String textbookId, String chapterId,String facultyId,String courseId) {
        facultyService.hideSection(textbookId,chapterId,sectionId,facultyId,courseId);

    }


    private void deleteSection(String sectionId,String facultyId) {
        if (facultyService.deleteSection(sectionId,facultyId)) {
            System.out.println("Section deleted successfully!");
        } else {
            System.out.println("Failed to delete section.");
        }
    }

//    private void hideSection(String sectionId, String textbookId, String chapterId,String facultyId) {
//        if (facultyService.hideSection(textbookId, chapterId, sectionId,  facultyId, currentTA.getAssignedCourseIds().get(0))) {
//            System.out.println("Section hidden successfully!");
//        } else {
//            System.out.println("Failed to hide section.");
//        }
//    }

    // ==================== Student Management ====================
//    private void handleViewStudents(String courseId) {
//        try {
//            List<UserModel> students = taService.viewStudentsInCourse(courseId);
//            System.out.println("\n=== Students in Course ===");
//            if (students.isEmpty()) {
//                System.out.println("No students enrolled.");
//            } else {
//                for (UserModel student : students) {
//                    System.out.println("ID: " + student.getUserId() +
//                            ", Name: " + student.getFirstName() + " " +
//                            student.getLastName());
//                }
//            }
//            System.out.println("\nPress Enter to continue...");
//            scanner.nextLine();
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }


    // ==================== Content Block Modification, Deletion, and Hiding ====================
    private void handleModifyContentBlock(String textbookId, String chapterId, String sectionId,String courseId,String facultyId) {
        System.out.println("\n=== Modify Content Block ===");
        System.out.print("Enter Content Block ID: ");
        String blockId = scanner.nextLine();

        System.out.println("1. Modify Content(add new contnent)\n");
        System.out.println("2. Delete Content Block(Activity, Text, Picture\n");
        System.out.println("3. Hide Content Block\n");
        System.out.println("4. Go Back\n");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
               promptForContentBlockAddition(textbookId, chapterId, sectionId, blockId, facultyId);
                break;
            case "2":
                deleteContentBlock(textbookId, chapterId, sectionId, courseId,facultyId);
                break;
            case "3":
                hideContentBlock(textbookId, chapterId, sectionId,facultyId,courseId);
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void modifyContentBlock(String blockId, String textbookId, String chapterId, String sectionId,String facultyId,String courseId) {
        System.out.print("Enter new content: ");
        String newContent = scanner.nextLine();
        System.out.print("Enter content type (text/picture/activity): ");
        String contentType = scanner.nextLine();

        if (facultyService.modifyBlock(blockId, newContent, contentType,facultyId,  courseId)) {
            System.out.println("Content block updated successfully!");
        } else {
            System.out.println("Failed to update content block.");
        }
    }

    private void deleteContentBlock(String textbookId, String chapterId, String sectionId,String courseId, String facultyId) {
        System.out.print("Enter Content Block ID that you want to delete: ");
        String blockId = scanner.nextLine();
        if (facultyService.deleteBlock(textbookId, chapterId, sectionId, blockId, facultyId)) {
            System.out.println("Content block and related activities/questions deleted successfully!");
        } else {
            System.out.println("Failed to delete content block.");
        }
    }

    private void hideContentBlock(String textbookId, String chapterId, String sectionId,String facultyId,String courseId) {
        System.out.print("Enter Content Block ID that you want to hide: ");
        String blockId = scanner.nextLine();
        if (facultyService.hideBlock(textbookId, chapterId, sectionId, blockId, facultyId, courseId)) {
            System.out.println("Content block hidden successfully!");
        } else {
            System.out.println("Failed to hide content block.");
        }
    }


    private void handleAddActivity(String textbookId, String chapterId, String sectionId, String blockId,String facultyId,String courseId) {
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
                    if (facultyService.createBlock(sectionId, textbookId, blockId, chapterId, "activity", activityId, false,facultyId)) {
                        facultyService.createActivity(textbookId, chapterId, sectionId, blockId, activityId, false, facultyId);
                        System.out.println("Activity added successfully!");
                        handleAddQuestion(textbookId, chapterId, sectionId, blockId, activityId,facultyId,courseId); // Redirect to add questions if needed
                    } else {
                        System.out.println("Failed to add activity.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showFacultyMenu(facultyId); // Return to landing page
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddQuestion(String textbookId, String chapterId, String sectionId, String blockId, String activityId,String facultyId,String courseId) {
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
                    if (facultyService.addQuestion(textbookId, chapterId, sectionId, blockId, activityId,
                            questionId, questionText, explanationOne,
                            explanationTwo, explanationThree,
                            explanationFour, optionOne, optionTwo,
                            optionThree, optionFour, correctAnswer)) {
                        System.out.println("Question added successfully!");
                    } else {
                        System.out.println("Failed to add question.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showFacultyMenu(facultyId); // Return to landing page
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}












