package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.models.TeachingAssistantModel;
import src.main.java.WolfBooks.models.UserModel;
import src.main.java.WolfBooks.services.TeachingAssistantService;
import java.util.List;
import java.util.Scanner;

public class TeachingAssistantController {
    private final TeachingAssistantService taService;
    private final Scanner scanner;
    private TeachingAssistantModel currentTA;

    public TeachingAssistantController(TeachingAssistantService taService) {
        this.taService = taService;
        this.scanner = new Scanner(System.in);
    }

    public void start(TeachingAssistantModel ta) {
        this.currentTA = ta;
//        if (ta.isFirstLogin()) {
//            handleFirstTimeLogin();
//        }
        showTAMenu();
    }

    private void handleFirstTimeLogin() {
        System.out.println("\n=== First Time Login - Password Change Required ===");
        handleChangePassword();
    }

    // ==================== Main Menu ====================
    private void showTAMenu() {
        while (true) {
            System.out.println("\n=== Teaching Assistant Menu ===");
            System.out.println("1. Go to Active Course");
            System.out.println("2. View Courses");
            System.out.println("3. Change Password");
            System.out.println("4. Logout");
            System.out.print("Enter your choice (1-4): ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        handleGoToActiveCourse();
                        break;
                    case "2":
                        handleViewCourses();
                        break;
                    case "3":
                        handleChangePassword();
                        break;
                    case "4":
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

    private void handleGoToActiveCourse() {
        System.out.println("\n=== Active Course Access ===");
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();

        if (!taService.verifyTACourseAccess(currentTA.getUserId(), courseId)) {
            System.out.println("You don't have access to this course.");
            return;
        }

        manageCourse(courseId);
    }

    private void manageCourse(String courseId) {
        while (true) {
            System.out.println("\n=== Course Menu ===");
            System.out.println("1. View Students");
            System.out.println("2. Add New Chapter");
            System.out.println("3. Modify Chapters");
            System.out.println("4. Go Back");
            System.out.print("Enter your choice (1-4): ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    handleViewStudents(courseId);
                    break;
                case "2":
                    handleAddNewChapter(courseId);
                    break;
                case "3":
                    handleModifyChapters(courseId);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleViewCourses() {
        System.out.println("\n=== Your Assigned Courses ===");
        List<String> courses = taService.getAssignedCourses(currentTA.getUserId());
        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
        } else {
            for (String courseId : courses) {
                System.out.println("Course ID: " + courseId);
            }
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void handleChangePassword() {
        System.out.println("\n=== Change Password ===");
        System.out.print("Enter Current Password: ");
        String currentPassword = scanner.nextLine();

        System.out.print("Enter New Password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Confirm New Password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("New passwords do not match!");
            return;
        }

        if (taService.changePassword(currentTA.getUserId(), currentPassword, newPassword)) {
            System.out.println("Password successfully changed.");
        } else {
            System.out.println("Failed to change password.");
        }
    }

    // ==================== Chapter Management ====================
    private void handleAddNewChapter(String courseId) {
        System.out.println("\n=== Add New Chapter ===");
        System.out.print("Enter Chapter ID: ");
        String chapterId = scanner.nextLine();

        System.out.print("Enter Chapter Title: ");
        String chapterTitle = scanner.nextLine();

        String textbookId = taService.getAssignedTextbooksForCourse(currentTA.getUserId(),courseId).get(0);
        System.out.print("Textbook ID received in controller: " + textbookId);

        if (taService.addNewChapter(textbookId, chapterId, chapterTitle, currentTA.getUserId())) {
            System.out.println("Chapter added successfully!");
            promptForSectionAddition(chapterId, courseId);
        } else {
            System.out.println("Failed to add chapter.");
        }
    }

    private void promptForSectionAddition(String chapterId, String courseId) {
        System.out.println("1. Add New Section");
        System.out.println("2. Go Back");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            handleAddNewSection(taService.getAssignedTextbooksForCourse(currentTA.getUserId(),courseId).get(0), chapterId);
        }
    }

    // ==================== Section Management ====================
    private void handleAddNewSection(String textbookId, String chapterId) {
        System.out.println("\n=== Add New Section ===");
        System.out.print("Enter Section ID: ");
        String sectionId = scanner.nextLine();

        System.out.print("Enter Section Title: ");
        String sectionTitle = scanner.nextLine();

        if (taService.addNewSection(textbookId, chapterId, sectionId, sectionTitle, currentTA.getUserId())) {
            System.out.println("Section added successfully!");
            promptForContentBlockAddition(sectionId, chapterId, textbookId);
        } else {
            System.out.println("Failed to add section.");
        }
    }

    private void promptForContentBlockAddition(String sectionId, String chapterId, String textbookId) {
        System.out.println("1. Add New Content Block");
        System.out.println("2. Go Back");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            handleAddNewContentBlock(textbookId, chapterId, sectionId);
        }
    }

    // ==================== Content Block Management ====================
    private void handleAddNewContentBlock(String textbookId, String chapterId, String sectionId) {
        System.out.println("\n=== Add New Content Block ===");
        System.out.print("Enter Block ID: ");
        String blockId = scanner.nextLine();

        System.out.print("Enter Content Type (text/picture/activity): ");
        String contentType = scanner.nextLine();



        System.out.print("Enter Content: ");
        String content = scanner.nextLine();

        if (taService.addBlock(textbookId, chapterId, sectionId, blockId, contentType, content, currentTA.getUserId())) {
            System.out.println("Content block added successfully!");
        } else {
            System.out.println("Failed to add content block.");
        }
    }

    // ==================== Chapter Modification and Deletion ====================
    private void handleModifyChapters(String courseId) {
        System.out.println("\n=== Modify Chapter ===");
        System.out.print("Enter Chapter ID for modification: ");
        String chapterId = scanner.nextLine();
        String textbookId = taService.getAssignedTextbooksForCourse(currentTA.getUserId(),courseId).get(0);

        System.out.println("1. Modify Chapter Title");
        System.out.println("2. Delete Chapter");
        System.out.println("3. Hide Chapter");
        System.out.println("4. Add new section");
        System.out.println("5. Modify section");
        System.out.println("6. Go Back");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                modifyChapterTitle(chapterId, courseId);
                break;
            case "2":
                deleteChapter(chapterId, courseId);
                break;
            case "3":
                hideChapter(chapterId, courseId);
                break;
            case "4":
                handleAddNewSection(textbookId, chapterId);
                break;
            case "5":
                handleModifySection(textbookId,chapterId);

                break;
            case "6":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void modifyChapterTitle(String chapterId, String courseId) {
        System.out.print("Enter new chapter title: ");
        String newTitle = scanner.nextLine();
        if (taService.modifyChapter(taService.getAssignedTextbooksForCourse(currentTA.getUserId(),courseId).get(0), chapterId, newTitle, currentTA.getUserId(), courseId)) {
            System.out.println("Chapter title updated successfully!");
        } else {
            System.out.println("Failed to update chapter title.");
        }
    }

    private void deleteChapter(String chapterId, String courseId) {
        if (taService.deleteChapter(chapterId, currentTA.getUserId())) {
            System.out.println("Chapter deleted successfully!");
        } else {
            System.out.println("Failed to delete chapter.");
        }
    }

    private void hideChapter(String chapterId, String courseId) {
        if (taService.hideChapter(taService.getAssignedTextbooksForCourse(currentTA.getUserId(),courseId).get(0), chapterId, currentTA.getUserId(), courseId)) {
            System.out.println("Chapter hidden successfully!");
        } else {
            System.out.println("Failed to hide chapter.");
        }
    }

    // ==================== Section Modification, Deletion, and Hiding ====================
    private void handleModifySection(String textbookId, String chapterId) {
        System.out.println("\n=== Modify Section ===");
        System.out.print("Enter Section ID: ");
        String sectionId = scanner.nextLine();

        System.out.println("1. Modify Section Title");
        System.out.println("2. Delete Section");
        System.out.println("3. Hide Section");
        System.out.print("4. Add new block ");
        System.out.println("5. Modify exsiting block");
        System.out.println("6. Go Back");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                modifySectionTitle(sectionId, textbookId, chapterId);
                break;
            case "2":
                deleteSection(sectionId);
                break;
            case "3":
                hideSection(sectionId, textbookId, chapterId);
                break;
            case "4":
                handleAddNewContentBlock(textbookId, chapterId,sectionId);
                break;
            case "5":
                handleModifyContentBlock(textbookId, chapterId,sectionId);
                break;
            case "6":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void modifySectionTitle(String sectionId, String textbookId, String chapterId) {
        System.out.print("Enter new section title: ");
        String newTitle = scanner.nextLine();
        if (taService.modifySection(textbookId, chapterId, sectionId, newTitle, currentTA.getUserId(), currentTA.getAssignedCourseIds().get(0))) {
            System.out.println("Section title updated successfully!");
        } else {
            System.out.println("Failed to update section title.");
        }
    }

    private void deleteSection(String sectionId) {
        if (taService.deleteSection(sectionId, currentTA.getUserId())) {
            System.out.println("Section deleted successfully!");
        } else {
            System.out.println("Failed to delete section.");
        }
    }

    private void hideSection(String sectionId, String textbookId, String chapterId) {
        if (taService.hideSection(textbookId, chapterId, sectionId, currentTA.getUserId(), currentTA.getAssignedCourseIds().get(0))) {
            System.out.println("Section hidden successfully!");
        } else {
            System.out.println("Failed to hide section.");
        }
    }

    // ==================== Student Management ====================
    private void handleViewStudents(String courseId) {
        try {
            List<UserModel> students = taService.viewStudentsInCourse(courseId);
            System.out.println("\n=== Students in Course ===");
            if (students.isEmpty()) {
                System.out.println("No students enrolled.");
            } else {
                for (UserModel student : students) {
                    System.out.println("ID: " + student.getUserId() +
                            ", Name: " + student.getFirstName() + " " +
                            student.getLastName());
                }
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // ==================== Content Block Modification, Deletion, and Hiding ====================
    private void handleModifyContentBlock(String textbookId, String chapterId, String sectionId) {
        System.out.println("\n=== Modify Content Block ===");
        System.out.print("Enter Content Block ID: ");
        String blockId = scanner.nextLine();

        System.out.println("1. Modify Content");
        System.out.println("2. Delete Content Block");
        System.out.println("3. Hide Content Block");
        System.out.println("4. Go Back");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                modifyContentBlock(blockId, textbookId, chapterId, sectionId);
                break;
            case "2":
                deleteContentBlock(blockId);
                break;
            case "3":
                hideContentBlock(blockId, textbookId, chapterId, sectionId);
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void modifyContentBlock(String blockId, String textbookId, String chapterId, String sectionId) {
        System.out.print("Enter new content: ");
        String newContent = scanner.nextLine();
        System.out.print("Enter content type (text/picture/activity): ");
        String contentType = scanner.nextLine();

        if (taService.modifyBlock(blockId, newContent, contentType, currentTA.getUserId(), currentTA.getAssignedCourseIds().get(0))) {
            System.out.println("Content block updated successfully!");
        } else {
            System.out.println("Failed to update content block.");
        }
    }

    private void deleteContentBlock(String blockId) {
        if (taService.deleteBlock(blockId, currentTA.getUserId())) {
            System.out.println("Content block deleted successfully!");
        } else {
            System.out.println("Failed to delete content block.");
        }
    }

    private void hideContentBlock(String blockId, String textbookId, String chapterId, String sectionId) {
        if (taService.hideBlock(textbookId, chapterId, sectionId, blockId, currentTA.getUserId(), currentTA.getAssignedCourseIds().get(0))) {
            System.out.println("Content block hidden successfully!");
        } else {
            System.out.println("Failed to hide content block.");
        }
    }

}
