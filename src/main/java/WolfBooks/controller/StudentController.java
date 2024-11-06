package src.main.java.WolfBooks.controller;

import org.w3c.dom.Text;
import src.main.java.WolfBooks.models.*;
import src.main.java.WolfBooks.services.StudentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    List<ChapterModel> visibleChapters;
    List<SectionModel> visibleSections;
    List<BlockModel> visibleBlocks;

    public StudentController() {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // TODO Add a way to get all courses a student is in and the names of the textbook.
        // First, get all of the courses of the currently logged in student. Do this after they are logged in.
        // After getting the courses, find the associated textbooks.
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
                            // populateCoursesAndTextbooks();
                            populateCourses();
                            System.out.println("\nWelcome back" + student.getFirstName() + "!");
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
                    System.out.println("Enter in the course number: ");
                    int courseNumber = scanner.nextInt();
                    System.out.println("Enter in the chapter id: ");
                    int chapterNumber = scanner.nextInt();
                    System.out.println("Enter in the section id: ");
                    int sectionNumber = scanner.nextInt();
                    if (courseNumber < 1 || courseNumber > courses.size() ||
                            chapterNumber < 1 || chapterNumber > visibleChapters.size() + 1 ||
                            sectionNumber < 1 || sectionNumber > visibleSections.size() + 1) {
                        break;
                    } else {
                        viewSection(courseNumber, chapterNumber, sectionNumber);
                    }
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
        CourseModel course = courses.get(courseNumber - 1);
        String textbookId = course.getTextbookId();
        List<ChapterModel> chapters = studentService.getVisibleChapters(textbookId);
        String chapterId = chapters.get(chapterNumber - 1).getChapterId();
        List<SectionModel> sections = studentService.getVisibleSections(textbookId, chapterId);
        String sectionId = sections.get(sectionNumber - 1).getSectionId();
        List<BlockModel> blocks = studentService.getVisibleBlocks(textbookId, chapterId, sectionId);

//        String placeHolder = chapterNumber < 10 ? "0" : "";
//        String chapterId = "chap" + placeHolder + chapterNumber;
//        placeHolder = sectionNumber < 10 ? "0" : "";
//        String sectionId = "Sec" + placeHolder + sectionNumber;
//        System.out.println(courseNumber);
//        System.out.println(chapterId);
//        System.out.println(sectionId);
        while (true) {
            System.out.println("\n=== View Section ===");
            System.out.println("1. View block");
            System.out.println("2. Go back");
            System.out.print("Choose an option (1-2): ");
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
//                    if (courseNumber < 1 || courseNumber > courses.size() + 1 ||
//                            chapterId.isEmpty() || sectionId.isEmpty()) {
//                        System.out.println("Invalid input.");
//                        break;
//                    }
                    if (viewBlock(courseNumber, chapterId, sectionId) == 1) {
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

    public int viewBlock(int courseNumber, String chapterId, String sectionId) {
        CourseModel course = courses.get(courseNumber - 1);
        TextbookModel textbook = studentService.getTextbookById(course.getTextbookId());
        int blockId = 0;
        System.out.println("\n=== View Block ===");
        while (true) {
            System.out.println("1. Next/Submit");
            System.out.println("2. Go back");
            System.out.print("Choose an option (1-2): ");
            int option = this.scanner.nextInt();
            switch (option) {
                case 1:
                    blockId += 1;
                    String placeHolder = blockId < 10 ? "0" : "";
                    String blockString = "Block" + placeHolder + blockId;
                    BlockModel block = studentService.getBlock(textbook.getTextbookID(), chapterId, sectionId, blockString);
                    printBlock(course, block);
                    break;
                case 2:
                    return 1;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

//    private void populateCoursesAndTextbooks() {
//        textbooks = new ArrayList<>();
//        courses = studentService.getStudentCourses(student);
//        for (CourseModel course : courses) {
//            textbooks.add(studentService.getTextbookById(course.getTextbookId()));
//        }
//    }

    private void populateCourses() {
        courses = studentService.getStudentCourses(student);
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
                System.out.println("Question: " + questionNumber);
                System.out.println(question.getQuestionText());
                System.out.println("Question choices:");
                System.out.println("Answer 1: " + question.getAnswerOne());
                System.out.println("Answer 2: " + question.getAnswerTwo());
                System.out.println("Answer 3: " + question.getAnswerThree());
                System.out.println("Answer 4: " + question.getAnswerFour());
                System.out.println("Enter in your answer (1-4):");
                int answer = 0;
                int score = 1;
                while (true) {
                    answer = this.scanner.nextInt();
                    if (answer < 1 || answer > 4) {
                        System.out.println("Invalid selection.");
                        continue;
                    }
                    if (answer != Integer.parseInt(question.getCorrectAnswer())) {
                        System.out.println("Incorrect answer.\nAnswer explanation: ");
                        if (answer == 1) System.out.println(question.getExplanationOne());
                        if (answer == 2) System.out.println(question.getExplanationTwo());
                        if (answer == 3) System.out.println(question.getExplanationThree());
                        if (answer == 4) System.out.println(question.getExplanationFour());
                        score = 0;
                    }
                    break;
                }

                studentService.addStudentActivity(new StudentActivityModel(student.getUserId(), course.getCourseId(), block.getTextbookId(), block.getChapterId(),
                        block.getSectionId(), block.getBlockId(), question.getQuestionId(), block.getContent(), score, dateFormat.toString()));
            }
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
