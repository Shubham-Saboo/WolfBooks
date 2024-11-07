package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.dao.BlockDAO;
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
            handleAddNewBlock(textbookId, chapterId, sectionId);
        }
    }

    // ==================== Content Block Management ====================

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
            System.out.println("4. Hide Activity");
            System.out.println("5. Go Back");
            System.out.println("6. Landing Page");
            System.out.print("Enter your choice (1-6): ");
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
                    // Option 3: Redirect to add activity
                   hideActivity(textbookId,chapterId, sectionId, blockId);
                    break;
                case "5":
                    // Option 4: Go back without saving
                    return;
                case "6":
                    // Option 5: Return to Admin Landing Page
                    showTAMenu();
                    break;
                default:
                    // Handle invalid input
                    System.out.println("Invalid choice. Returning to TA Landing Page.");
                    showTAMenu();
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
                    if (TeachingAssistantService.createBlock(sectionId, textbookId, blockId, chapterId, "text", textContent, false, currentTA.getUserId())) {
                        System.out.println("Text added successfully!");
                    } else {
                        System.out.println("Failed to add text.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showTAMenu(); // Return to landing page
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
                    if (TeachingAssistantService.createBlock(sectionId, textbookId, blockId, chapterId, "picture", pictureUrl, false, currentTA.getUserId() )) {
                        System.out.println("Picture added successfully!");
                    } else {
                        System.out.println("Failed to add picture.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showTAMenu(); // Return to landing page
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
    private void handleModifyChapters(String courseId) {
        System.out.println("\n=== Modify Chapter ===");
        System.out.print("Enter Chapter ID for modification: ");
        String chapterId = scanner.nextLine();
        String textbookId = taService.getAssignedTextbooksForCourse(currentTA.getUserId(),courseId).get(0);

//        System.out.println("1. Modify Chapter Title");
//        System.out.println("2. Delete Chapter");
//        System.out.println("3. Hide Chapter");Chapter
        System.out.println("1. Add new section");
        System.out.println("2. Modify section");
        System.out.println("3. Go Back");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
//            case "1":
//                modifyChapterTitle(chapterId, courseId);
//                break;
//            case "2":
//                deleteChapter(chapterId, courseId);
//                break;
//            case "3":
//                hideChapter(chapterId, courseId);
//                break;
            case "1":
                handleAddNewSection(textbookId, chapterId);
                break;
            case "2":
                handleModifySection(textbookId,chapterId);
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }



    private void handleModifyBlock(String textbookId, String chapterId, String sectionId) {
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
                    handleAddText(textbookId, chapterId, sectionId, blockId);
                    break;
                case "2":
                    // Option 2: Redirect to add picture
                    handleAddPicture(textbookId, chapterId, sectionId, blockId);
                    break;
                case "3":
                    // Option 3: Redirect to add activity
                    handleAddActivity(textbookId, chapterId, sectionId, blockId);
                    break;
                case "4":
                    return; // Go back to the previous menu
                case "5":
                    showTAMenu(); // Go back to the admin landing page
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    handleModifyBlock(textbookId, chapterId, sectionId); // Recursive call for invalid input
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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

//        System.out.println("1. Modify Section Title");
//        System.out.println("2. Delete Section");
//        System.out.println("3. Hide Section");
        System.out.print("1. Add new content block ");
        System.out.println("2. Modify existing content block");
        System.out.print("3. Delete content block");
        System.out.print("4. Hide content block");
        System.out.println("5. Go Back");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
//            case "1":
//                modifySectionTitle(sectionId, textbookId, chapterId);
//                break;
//            case "2":
//                deleteSection(sectionId);
//                break;
//            case "3":
//                hideSection(sectionId, textbookId, chapterId);
//                break;
            case "1":
                handleAddNewBlock(textbookId, chapterId,sectionId);
                break;
            case "2":
                handleAddNewBlock(textbookId, chapterId,sectionId);
                break;
            case "3":
                deleteContentBlock(textbookId,chapterId,sectionId);
                break;
            case "4":
                hideContentBlock(textbookId,chapterId,sectionId);
                break;
            case "5":
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
                deleteContentBlock(textbookId,chapterId,sectionId);
                break;
            case "3":
                hideContentBlock(textbookId, chapterId, sectionId);
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

    private void deleteContentBlock(String textbookId, String chapterId, String sectionId) {
        System.out.print("Enter Content Block ID that you want to delete: ");
        String blockId = scanner.nextLine();
        if (taService.deleteBlock(textbookId, chapterId, sectionId, blockId, currentTA.getUserId())) {
            System.out.println("Content block and related activities/questions deleted successfully!");
        } else {
            System.out.println("Failed to delete content block.");
        }
    }

    private void hideContentBlock(String textbookId, String chapterId, String sectionId) {
        System.out.print("Enter Content Block ID that you want to delete: ");
        String blockId = scanner.nextLine();
        if (taService.hideBlock(textbookId, chapterId, sectionId, blockId, currentTA.getUserId(), currentTA.getAssignedCourseIds().get(0))) {
            System.out.println("Content block hidden successfully!");
        } else {
            System.out.println("Failed to hide content block.");
        }
    }


    private void hideActivity(String textbookId, String chapterId, String sectionId, String blockId) {
        System.out.println("\n=== Hide Activity ===");
        try {
            System.out.print("Enter Activity ID that you want to hide: ");
            String activityId = scanner.nextLine();

            if (taService.hideActivity(textbookId, chapterId, sectionId, blockId, activityId,
                    currentTA.getUserId(), currentTA.getAssignedCourseIds().get(0))) {
                System.out.println("Activity hidden successfully!");
            } else {
                System.out.println("Failed to hide activity.");
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
                    if (TeachingAssistantService.createBlock(sectionId, textbookId, blockId, chapterId, "activity", activityId, false,  currentTA.getUserId())) {
                        TeachingAssistantService.createActivity(textbookId, chapterId, sectionId, blockId, activityId, false, currentTA.getUserId());
                        System.out.println("Activity added successfully!");
                        handleAddQuestion(textbookId, chapterId, sectionId, blockId, activityId); // Redirect to add questions if needed
                    } else {
                        System.out.println("Failed to add activity.");
                    }
                    break;
                case "2":
                    return; // Go back without saving
                case "3":
                    showTAMenu(); // Return to landing page
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
                    if (TeachingAssistantService.addQuestion(textbookId ,chapterId ,sectionId ,blockId ,activityId ,
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
                    showTAMenu(); // Return to landing page
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
