package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.services.FacultyService;
import src.main.java.WolfBooks.models.CourseModel;
import src.main.java.WolfBooks.models.UserModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class FacultyController {
    private FacultyService facultyService;
    private Scanner scanner;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
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
                addNewChapter(courseId);
                break;
            case 5:
                modifyChapters(courseId);
                break;
            case 6:
                addTA(courseId);
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
                    // addNewChapter(courseId);
                    break;
                case 2:
                    // modifyChapters(courseId);
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
private void addTA(String courseID) throws SQLException {
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
            boolean success = facultyService.addTA(firstName, lastName, email, defaultPassword, courseID);
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
private void addNewChapter(String courseID) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter Unique Chapter ID: ");
    String chapterID = scanner.nextLine();
    
    System.out.print("Enter Chapter Title: ");
    String title = scanner.nextLine();
    
    System.out.println("\n1. Add New Section");
    System.out.println("2. Go Back");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.addNewChapter(courseID, chapterID, title);
            if (success) {
                addNewSection(chapterID); // Proceed to add section
            } else {
                System.out.println("Failed to add chapter.");
            }
            break;
        case 2:
            return; // Go back without saving
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Modify Chapter
private void modifyChapters(String courseID) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter Unique Chapter ID: ");
    String chapterID = scanner.nextLine();
    
    while (true) {
        System.out.println("\n1. Hide Chapter");
        System.out.println("2. Delete Chapter");
        System.out.println("3. Add New Section");
        System.out.println("4. Modify Section");
        System.out.println("5. Go Back");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                hideChapter(chapterID);
                break;
            case 2:
                deleteChapter(chapterID);
                break;
            case 3:
                addNewSection(chapterID);
                break;
            case 4:
                modifySection(chapterID);
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
}

// Hide Chapter
private void hideChapter(String chapterID) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\n1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.hideChapter(chapterID);
            if (success) {
                System.out.println("Chapter hidden successfully.");
            } else {
                System.out.println("Failed to hide chapter.");
            }
            break;
        case 2:
            return; // Go back without saving
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Delete Chapter
private void deleteChapter(String chapterID) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\n1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.deleteChapter(chapterID);
            if (success) {
                System.out.println("Chapter deleted successfully.");
            } else {
                System.out.println("Failed to delete chapter.");
            }
            break;
        case 2:
            return; // Go back without saving
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Add New Section
private void addNewSection(String chapterId) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter Section Number: ");
    String sectionNumber = scanner.nextLine();
    
    System.out.print("Enter Section Title: ");
    String sectionTitle = scanner.nextLine();
    
    while (true) {
        System.out.println("\n1. Add New Content Block");
        System.out.println("2. Go Back");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                boolean success = facultyService.addNewSection(chapterId, sectionNumber, sectionTitle);
                if (success) {
                    addNewContentBlock(sectionNumber);
                } else {
                    System.out.println("Failed to add section.");
                }
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
}

// Modify Section
private void modifySection(String chapterId) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter Section Number: ");
    String sectionNumber = scanner.nextLine();
    
    while (true) {
        System.out.println("\n1. Hide Section");
        System.out.println("2. Delete Section");
        System.out.println("3. Add New Content Block");
        System.out.println("4. Modify Content Block");
        System.out.println("5. Go Back");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                hideSection(sectionNumber);
                break;
            case 2:
                deleteSection(sectionNumber);
                break;
            case 3:
                addNewContentBlock(sectionNumber);
                break;
            case 4:
                modifyContentBlock(sectionNumber);
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
}

// Hide Section
private void hideSection(String sectionNumber) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\n1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.hideSection(sectionNumber);
            if (success) {
                System.out.println("Section hidden successfully.");
            } else {
                System.out.println("Failed to hide section.");
            }
            break;
        case 2:
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Delete Section
private void deleteSection(String sectionNumber) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\n1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.deleteSection(sectionNumber);
            if (success) {
                System.out.println("Section deleted successfully.");
            } else {
                System.out.println("Failed to delete section.");
            }
            break;
        case 2:
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Add New Content Block
private void addNewContentBlock(String sectionNumber) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter Content Block ID: ");
    String contentBlockId = scanner.nextLine();
    
    while (true) {
        System.out.println("\n1. Add Text");
        System.out.println("2. Add Picture");
        System.out.println("3. Add Activity");
        System.out.println("4. Go Back");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                addText(contentBlockId, sectionNumber);
                break;
            case 2:
                addPicture(contentBlockId, sectionNumber);
                break;
            case 3:
                addActivity(contentBlockId, sectionNumber);
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
}

// Modify Content Block
private void modifyContentBlock(String sectionNumber) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter Content Block ID: ");
    String contentBlockId = scanner.nextLine();
    
    while (true) {
        System.out.println("\n1. Hide Content Block");
        System.out.println("2. Delete Content Block");
        System.out.println("3. Add Text");
        System.out.println("4. Add Picture");
        System.out.println("5. Hide Activity");
        System.out.println("6. Delete Activity");
        System.out.println("7. Add Activity");
        System.out.println("8. Go Back");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                hideContentBlock(contentBlockId);
                break;
            case 2:
                deleteContentBlock(contentBlockId);
                break;
            case 3:
                addText(contentBlockId, sectionNumber);
                break;
            case 4:
                addPicture(contentBlockId, sectionNumber);
                break;
            case 5:
                hideActivity(contentBlockId);
                break;
            case 6:
                deleteActivity(contentBlockId);
                break;
            case 7:
                addActivity(contentBlockId, sectionNumber);
                break;
            case 8:
                return;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
}

// Hide Content Block
private void hideContentBlock(String contentBlockId) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\n1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.hideContentBlock(contentBlockId);
            if (success) {
                System.out.println("Content Block hidden successfully.");
            } else {
                System.out.println("Failed to hide Content Block.");
            }
            break;
        case 2:
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Delete Content Block
private void deleteContentBlock(String contentBlockId) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\n1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.deleteContentBlock(contentBlockId);
            if (success) {
                System.out.println("Content Block deleted successfully.");
            } else {
                System.out.println("Failed to delete Content Block.");
            }
            break;
        case 2:
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Add Text
private void addText(String contentBlockId, String sectionNumber) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter Text: ");
    String text = scanner.nextLine();
    
    System.out.println("\n1. Add");
    System.out.println("2. Go Back");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.addText(contentBlockId, sectionNumber, text);
            if (success) {
                System.out.println("Text added successfully.");
            } else {
                System.out.println("Failed to add text.");
            }
            break;
        case 2:
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Add Picture
private void addPicture(String contentBlockId, String sectionNumber) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter Picture URL/Path: ");
    String picturePath = scanner.nextLine();
    
    System.out.println("\n1. Add");
    System.out.println("2. Go Back");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.addPicture(contentBlockId, sectionNumber, picturePath);
            if (success) {
                System.out.println("Picture added successfully.");
            } else {
                System.out.println("Failed to add picture.");
            }
            break;
        case 2:
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}
// Hide Activity
private void hideActivity(String blockId) throws SQLException {
    System.out.println("Enter Unique Activity ID:");
    String activityId = scanner.nextLine();
    
    System.out.println("1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.hideActivity(activityId);
            if (success) {
                System.out.println("Activity hidden successfully.");
            } else {
                System.out.println("Failed to hide activity.");
            }
            break;
        case 2:
            System.out.println("Operation cancelled.");
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}

// Delete Activity
private void deleteActivity(String blockId) throws SQLException {
    System.out.println("Enter Unique Activity ID:");
    String activityId = scanner.nextLine();
    
    System.out.println("1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.deleteActivity(activityId);
            if (success) {
                System.out.println("Activity deleted successfully.");
            } else {
                System.out.println("Failed to delete activity.");
            }
            break;
        case 2:
            System.out.println("Operation cancelled.");
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}
// Add Activity
private void addActivity(String blockId, String sectionNumber) throws SQLException {
    System.out.println("Enter Unique Activity ID:");
    String activityId = scanner.nextLine();
    
    while (true) {
        System.out.println("1. Add Question");
        System.out.println("2. Go Back");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                addQuestion(activityId);
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
}

// Add Question
private void addQuestion(String activityId) throws SQLException {
    System.out.println("Enter Question Details:");
    
    System.out.print("Question ID: ");
    String questionId = scanner.nextLine();
    
    System.out.print("Question Text: ");
    String questionText = scanner.nextLine();
    
    System.out.print("Option 1: ");
    String option1 = scanner.nextLine();
    System.out.print("Option 1 Explanation: ");
    String explanation1 = scanner.nextLine();
    
    System.out.print("Option 2: ");
    String option2 = scanner.nextLine();
    System.out.print("Option 2 Explanation: ");
    String explanation2 = scanner.nextLine();
    
    System.out.print("Option 3: ");
    String option3 = scanner.nextLine();
    System.out.print("Option 3 Explanation: ");
    String explanation3 = scanner.nextLine();
    
    System.out.print("Option 4: ");
    String option4 = scanner.nextLine();
    System.out.print("Option 4 Explanation: ");
    String explanation4 = scanner.nextLine();
    
    System.out.print("Answer (1-4): ");
    int answer = Integer.parseInt(scanner.nextLine());
    
    System.out.println("\n1. Save");
    System.out.println("2. Cancel");
    
    int choice = Integer.parseInt(scanner.nextLine());
    
    switch (choice) {
        case 1:
            boolean success = facultyService.addQuestion(
                questionId, activityId, questionText,
                option1, explanation1, option2, explanation2,
                option3, explanation3, option4, explanation4, answer
            );
            if (success) {
                System.out.println("Question added successfully.");
            } else {
                System.out.println("Failed to add question.");
            }
            break;
        case 2:
            System.out.println("Operation cancelled.");
            return;
        default:
            System.out.println("Invalid choice! Please try again.");
    }
}
}
