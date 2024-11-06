package src.main.java.WolfBooks.controller;

import src.main.java.WolfBooks.models.*;
import src.main.java.WolfBooks.services.StudentService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;
    private final Scanner scanner;

    UserModel student;
    List<CourseModel> courses;
    List<TextbookModel> textbooks;

    public StudentController() {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);
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
            System.out.print("Choose an option (1-3): ");
            try {
                switch (this.scanner.nextInt()) {
                    case 1:
                        handleEnrollment();
                        break;
                    case 2:
                        if (signInStudent()) {
                            populateCourses();
                            System.out.println("\nWelcome back " + student.getFirstName() + "!");
                            viewLanding();
                        } else {
                            System.out.println("Invalid credentials!");
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void viewLanding() {
        while (true) {
            System.out.println("\n=== Landing Menu ===");
            printTextbooks();
            System.out.println("1. View a section");
            System.out.println("2. View participation activity point");
            System.out.println("3. Logout");
            System.out.print("Choose an option (1-3): ");
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Enter in the course number: ");
                    int courseNumber = scanner.nextInt();
                    System.out.print("Enter in the chapter id: ");
                    int chapterNumber = scanner.nextInt();
                    System.out.print("Enter in the section id: ");
                    int sectionNumber = scanner.nextInt();
                    viewSection(courseNumber, chapterNumber, sectionNumber);
                    break;
                case 2:
                    viewParticipationPoints();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public void viewSection(int courseNumber, int chapterNumber, int sectionNumber) {
        // Check the course
        if (courseNumber < 1 || courseNumber > courses.size() + 1) return;
        CourseModel course = courses.get(courseNumber - 1);
        String textbookId = course.getTextbookId();

        // Check and get chapters
        List<ChapterModel> chapters = studentService.getVisibleChapters(textbookId);
        if (chapterNumber < 1 || chapterNumber > chapters.size() + 1) return;
        String chapterId = chapters.get(chapterNumber - 1).getChapterId();

        // Check and get sections
        List<SectionModel> sections = studentService.getVisibleSections(textbookId, chapterId);
        if (sectionNumber < 1 || sectionNumber > sections.size() + 1) return;
        String sectionId = sections.get(sectionNumber - 1).getSectionId();

        // Get the blocks
        List<BlockModel> blocks = studentService.getVisibleBlocks(textbookId, chapterId, sectionId);
        while (true) {
            System.out.println("\n=== View Section ===");
            System.out.println("1. View block");
            System.out.println("2. Go back");
            System.out.print("Choose an option (1-2): ");
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
                    if (viewBlock(course, blocks) == 1) {
                        return;
                    };
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public int viewBlock(CourseModel course, List<BlockModel> blocks) {
        System.out.println("\n=== View Block ===");
        int blockId = 0;
        BlockModel block = blocks.get(blockId);
        printBlock(course, block);
        blockId++;
        while (true) {
            if (blockId >= blocks.size()) {
                System.out.println("There are no more blocks left in the section!\nGoing back to view section.");
                return 0;
            }
            System.out.println("1. Next/Submit");
            System.out.println("2. Go back");
            System.out.print("Choose an option (1-2): ");
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
                    block = blocks.get(blockId);
                    printBlock(course, block);
                    blockId++;
                    break;
                case 2:
                    return 1;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void populateCourses() {
        courses = studentService.getStudentCourses(student);
    }

    public boolean handleEnrollment() {
        System.out.print("Enter in first name: ");
        String firstName = this.scanner.next();
        System.out.print("Enter in last name: ");
        String lastName = this.scanner.next();
        System.out.print("Enter in your email: ");
        String email = this.scanner.next();
        System.out.print("Enter in the course token: ");
        String courseToken = this.scanner.next();

        if (!studentService.enrollStudent(firstName, lastName, email, courseToken)) {
            createStudentAccount();
            studentService.enrollStudent(firstName, lastName, email, courseToken);
        }
        return true;
    }

    public boolean signInStudent() {
        System.out.println("\n=== Student Sign-in ===");
        System.out.print("Enter your username: ");
        String userId = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();
        student = studentService.authenticateUser(userId, password, "student");
        return student != null;
    }

    private void printTextbooks() {
        for (int i = 0; i < courses.size(); i++) {
            CourseModel course = courses.get(i);
            TextbookModel textbook = studentService.getTextbookById(course.getTextbookId());
            System.out.println(course.getCourseTitle() + " (#" + (i + 1) + ")");
            System.out.println(textbook.getTextbookTitle() + " (ID: " + textbook.getTextbookID() + ")");
            List<ChapterModel> chapters = studentService.getVisibleChapters(textbook.getTextbookID());
            int chapterNumber = 0;
            for (ChapterModel chapter : chapters) {
                chapterNumber++;
                System.out.println("\tChapter " + chapterNumber + ": " + chapter.getChapterTitle());
                List<SectionModel> sections = studentService.getVisibleSections(chapter.getTextbookId(), chapter.getChapterId());
                int sectionNumber = 0;
                for (SectionModel section : sections) {
                    sectionNumber++;
                    System.out.println("\t\tSection " + sectionNumber + ": " + section.getTitle());
                    List<BlockModel> blocks = studentService.getVisibleBlocks(section.getTextbookId(), section.getChapterId(), section.getSectionId());
                    int blockNumber = 0;
                    for (BlockModel block : blocks) {
                        blockNumber++;
                        System.out.println("\t\t\tBlock " + blockNumber + ": " + block.getContentType());
                    }
                }
            }
            System.out.println("\n");
        }
    }

    public void printBlock(CourseModel course, BlockModel block) {
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
                System.out.println("(Q" + questionNumber + ") " + question.getQuestionText() + "\n");
//                System.out.println(question.getQuestionText());
//                System.out.println("Question choices:");
                System.out.println("1. " + question.getAnswerOne());
                System.out.println("2. " + question.getAnswerTwo());
                System.out.println("3. " + question.getAnswerThree());
                System.out.println("4. " + question.getAnswerFour());
                System.out.print("Enter in your answer (1-4): ");
                int answer = 0;
                int score = 1;
                while (true) {
                    answer = this.scanner.nextInt();
                    if (answer < 1 || answer > 4) {
                        System.out.println("Invalid selection.");
                        continue;
                    }
                    if (answer != Integer.parseInt(question.getCorrectAnswer())) {
                        System.out.println("Incorrect answer. Here is why: ");
                        if (answer == 1) System.out.println(question.getExplanationOne());
                        if (answer == 2) System.out.println(question.getExplanationTwo());
                        if (answer == 3) System.out.println(question.getExplanationThree());
                        if (answer == 4) System.out.println(question.getExplanationFour());
                        score = 0;
                    }
                    System.out.println();
                    break;
                }
                LocalDateTime local = LocalDateTime.now();
                Timestamp sqlTimestamp = Timestamp.valueOf(local);

                studentService.addStudentActivity(new StudentActivityModel(student.getUserId(), course.getCourseId(), block.getTextbookId(), block.getChapterId(),
                        block.getSectionId(), block.getBlockId(), question.getQuestionId(), block.getContent(), score, sqlTimestamp));


                System.out.println("1. Next question");
                System.out.println("2. Leave activity");
                int option = scanner.nextInt();
                while (option < 1 || option > 2) {
                    System.out.println("Invalid option!");
                    option = scanner.nextInt();
                }
                if (option == 2) return;
            }
//            studentService.addStudentActivity(new StudentActivityModel(student.getUserId(), course.getCourseId(), block.getTextbookId(), block.getChapterId(),
//                        block.getSectionId(), block.getBlockId(), question.getQuestionId(), block.getContent(), score, sqlTimestamp));
        }
    }

    public void viewParticipationPoints() {
        System.out.println("\n=== View Participation Points ===");
        List<StudentActivityModel> studentActivities = studentService.getStudentActivities(student.getUserId());
        for (StudentActivityModel studentActivity : studentActivities) {
            System.out.println("Activity for course: " + studentActivity.getCourseId());
            System.out.println("Timestamp: " + studentActivity.getTimestamp());
            System.out.println("Score: " + studentActivity.getScore());
            System.out.println();
        }
        int option = 0;
        while (option != 1) {
            System.out.println("1. Go back");
            System.out.print("Choose an option (1): ");
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
