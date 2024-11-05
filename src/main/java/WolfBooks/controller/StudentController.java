package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.models.*;
import src.main.java.WolfBooks.services.StudentService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;
    private final Scanner scanner;
    private final SimpleDateFormat dateFormat;

    UserModel student;
    String courseId;
    List<CourseModel> courses;
    List<TextbookModel> textbooks;

    public StudentController() {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // TODO Add a way to get all courses a student is in and the names of the textbook.
    }

    public void start() {
        showStudentMenu();
    }

    public void showStudentMenu() {
        while (true) {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1. Enroll in a course");
            System.out.println("2. Sign-in");
            System.out.println("3. Go back");

            try {
                switch (this.scanner.nextInt()) {
                    case 1:
                        handleEnrollment();
                        break;
                    case 2:
                        if (signInStudent()) {
                            showLanding();
                        };
                        break;
                    case 3:
                        return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean handleEnrollment() {
        System.out.println("Enter in first name:");
        String firstName = this.scanner.next();
        System.out.println("Enter in last name:");
        String lastName = this.scanner.next();
        System.out.println("Enter in your email:");
        String email = this.scanner.next();
        System.out.println("Enter in the course token:");
        String courseToken = this.scanner.next();

        if (!studentService.enrollStudent(firstName, lastName, email, courseToken)) {
            createStudentAccount();
            studentService.enrollStudent(firstName, lastName, email, courseToken);
        }
        return true;
    }

    public boolean signInStudent() {
        System.out.println("\n=== Student Sign-in ===");
        System.out.println("Enter your username:");
        String userId = scanner.next();
        System.out.println("Enter your password:");
        String password = scanner.next();
        student = studentService.authenticateUser(userId, password, "student");
        return student != null;
    }

    public void showLanding() {
        while (true) {
            System.out.println("\n=== Landing Menu ===");
            System.out.println("1. View a section");
            System.out.println("2. View participation activity point");
            System.out.println("3. Logout");
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
                    viewSection();
                    break;
                case 2:
                    viewParticipationPoints();
                    break;
                case 3:
                    return;
            }
        }
    }

    public void viewSection() {
        while (true) {
            System.out.println("\n=== View Section ===");
            System.out.println("1. View block");
            System.out.println("2. Go back");
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("\n=== View Section ===");
                    System.out.println("Enter in the textbook id: ");
                    String textbookId = this.scanner.next();
                    System.out.println("Enter in the chapter id: ");
                    String chapterId = this.scanner.next();
                    System.out.println("Enter in the section id: ");
                    String sectionId = this.scanner.next();
                    if (textbookId.isEmpty() || chapterId.isEmpty() || sectionId.isEmpty() ||
                            textbookId == null || chapterId == null || sectionId == null) {
                        System.out.println("Invalid input.");
                        return;
                    }
                    viewBlock(textbookId, chapterId, sectionId);
                    break;
                case 2:
                    return;
            }
        }
    }

    public void viewBlock(String textbookId, String chapterId, String sectionId) {
        int blockId = 0;
        System.out.println("\n=== View Block ===");
        while (true) {
            System.out.println("1. Next/Submit");
            System.out.println("2. Go back");
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
                    blockId += 1;
                    String placeHolder = blockId < 10 ? "0" : "";
                    String blockString = "Block" + placeHolder + blockId;
                    BlockModel block = studentService.viewBlock(textbookId, chapterId, sectionId, blockString);
                    printBlock(block);
                    break;
                case 2:
                    return;
            }
        }
    }

    public void printBlock(BlockModel block) {
        if (block == null || block.isHidden()) {
            return;
        }
        if ("text".equals(block.getContentType())) {
            System.out.println(block.getContent());
        }
        else if ("picture".equals(block.getContentType())) {
            System.out.println(block.getContent());
        }
        else if ("activity".equals(block.getContentType())) {
            List<QuestionModel> questions = studentService.getActivityQuestions(block.getTextbookId(), block.getChapterId(), block.getSectionId(), block.getBlockId(), block.getContent());
            int questionNumber = 0;
            for (QuestionModel question : questions) {
                questionNumber += 1;
                System.out.println("Question: " + questionNumber);
                System.out.println(question.getQuestionText());
                System.out.println("Question choices:");
                System.out.println("Answer 1: " + question.getAnswerOne());
                System.out.println("Answer 2: " + question.getAnswerTwo());
                System.out.println("Answer 3: " + question.getAnswerThree());
                System.out.println("Answer 4: " + question.getAnswerFour());
                System.out.println("Enter in your answer:");
                int answer = this.scanner.nextInt();
                int score = 1;
                if (answer != Integer.parseInt(question.getCorrectAnswer())) {
                    System.out.println("Incorrect answer.\nAnswer explanation: ");
                    if (answer == 1) System.out.println(question.getExplanationOne());
                    if (answer == 2) System.out.println(question.getExplanationTwo());
                    if (answer == 3) System.out.println(question.getExplanationThree());
                    if (answer == 4) System.out.println(question.getExplanationFour());
                    score = 0;
                }
                studentService.addStudentActivity(new StudentActivityModel(student.getUserId(), courseId, block.getTextbookId(), block.getChapterId(),
                        block.getSectionId(), block.getBlockId(), question.getQuestionId(), block.getContent(), score, dateFormat.toString()));
            }
        }
    }

    public void viewParticipationPoints() {
        System.out.println("\n=== View Participation Points ===");
        List<StudentActivityModel> studentActivities = studentService.viewStudentActivities(student.getUserId());
        for (StudentActivityModel student : studentActivities) {
            System.out.println();
        }
        int option = 0;
        while (option != 1) {
            System.out.println("1. Go back");
            option = this.scanner.nextInt();
        }
    }

    public UserModel createStudentAccount() {
        System.out.println("\n=== Creating Student Account Menu ===");
        while (true) {
            String firstName, lastName, email, password;
            System.out.println("Enter your first name:");
            firstName = this.scanner.next();
            System.out.println("Enter your last name:");
            lastName = this.scanner.next();
            System.out.println("Enter your email:");
            email = this.scanner.next();
            System.out.println("Enter your password:");
            password = this.scanner.next();
            try {
                validateUserInput(firstName, lastName, email, password);
                student = studentService.createStudentAccount(firstName, lastName, email, password);
                return student;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateUserInput(String firstName, String lastName, String email, String password) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
    }
}
