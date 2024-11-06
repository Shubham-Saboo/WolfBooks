package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.dao.*;
import src.main.java.WolfBooks.models.*;


import java.sql.SQLException;
import java.util.*;


public class AdminService {

    private final UserDAO userDAO;
    private final TextbookDAO textbookDAO = new TextbookDAO();
    private final SectionDAO sectionDAO = new SectionDAO();
    private final ChapterDAO chapterDAO = new ChapterDAO();
    private final BlockDAO blockDAO = new BlockDAO();
    private final ActivityDAO activityDAO = new ActivityDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();
    private final CourseDAO courseDAO = new CourseDAO();

    public AdminService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Authentication
    public UserModel authenticateUser(String userId, String password, String role) throws SQLException {
        UserModel user = userDAO.authenticateUser(userId, password);
        if (user != null && user.getRole().equals(role)) {
            return user;
        }
        return null;
    }


    // Faculty Account Management
    public boolean createFacultyAccount(String firstName, String lastName, String email, String password) {
        try {
            validateUserInput(firstName, lastName, email, password);

            if (userDAO.findByEmail(email) != null) {
                throw new RuntimeException("Email already exists");
            }

            String facultyId = generateUniqueFacultyId(firstName, lastName);
            UserModel faculty = new UserModel(
                    facultyId,
                    firstName,
                    lastName,
                    email,
                    password,
                    "faculty",
                    true
            );

            return userDAO.createFacultyAccount(faculty);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    // Textbook Management
    public boolean createTextbook(String title, String textbookId) {
        if (!validateTextbookInput(title, textbookId)) {
            return false;
        }

        TextbookModel newTextbook = new TextbookModel(textbookId, title);
        return textbookDAO.createTextbook(newTextbook);
    }

    // Chapter Management with isHidden and createdBy
    public boolean createChapter(String textbookId, String chapterId, String chapterTitle, boolean isHidden, String createdBy) {
        if (!validateChapterInput(chapterId, chapterTitle)) {
            return false;
        }

        ChapterModel chapter = new ChapterModel(chapterId, chapterTitle, textbookId, isHidden, createdBy);
        return chapterDAO.createChapter(chapter);
    }

    // Section Management with isHidden and createdBy
    public boolean createSection(String textbookId, String chapterId, String sectionId, String sectionTitle, boolean isHidden, String createdBy) {
        if (!validateSectionInput(sectionId, sectionTitle)) {
            return false;
        }

        SectionModel section = new SectionModel(sectionId, textbookId, chapterId, sectionTitle, isHidden /* is_hidden */, createdBy);
        return sectionDAO.createSection(section);
    }

    // Block Management with isHidden and createdBy
    public boolean createBlock(String textbookId, String chapterId, String sectionId,
                               String blockId, String contentType,
                               String content,
                               boolean isHidden,
                               String createdBy) {
        if (!validateBlockInput(blockId, contentType)) {
            return false;
        }
        BlockModel block = new BlockModel(textbookId, chapterId, sectionId, blockId, contentType, content, isHidden, createdBy);

        return blockDAO.createBlock(block);

    }

    public boolean createActivity(String textbookId, String chapterId, String sectionId, String blockId,
                                  String activityId, boolean isHidden, String createdBy) {
        // Validate input
        if (!validateActivityInput(activityId)) {
            return false;
        }

        // Create a new ActivityModel object without activityDescription
        ActivityModel activity = new ActivityModel(activityId, blockId, sectionId, chapterId, textbookId, isHidden, createdBy); // No activityDescription

        // Call DAO to insert the activity into the database
        return activityDAO.createActivity(activity);
    }

    public boolean addQuestion(String textbookId, String chapterId, String sectionId, String blockId,
                               String activityId, String questionId, String questionText,
                               String explanationOne, String explanationTwo, String explanationThree,
                               String explanationFour, String optionOne, String optionTwo, String optionThree, String optionFour,
                               String correctAnswer) {
        if (!validateQuestionInput(questionId,questionText,
                optionOne, optionTwo,
                optionThree, optionFour,
                explanationOne, explanationTwo,
                explanationThree, explanationFour,
                correctAnswer)) {
            return false;
        }
        QuestionModel question = new QuestionModel(textbookId, chapterId, sectionId, blockId, activityId,
                questionId, questionText, explanationOne, explanationTwo, explanationThree, explanationFour,
                optionOne, optionTwo, optionThree, optionFour, correctAnswer);
        return questionDAO.createQuestion(question);
    }


    // Validation Methods
    private boolean validateTextbookInput(String title, String textbookId) {
        if (title == null || title.isEmpty() || textbookId == null || textbookId.isEmpty()) {
            System.out.println("Invalid input for creating a textbook.");
            return false;
        }
        return true;
    }

    private boolean validateChapterInput(String chapterId, String chapterTitle) {
        if (chapterId == null || chapterId.trim().isEmpty()) {
            System.out.println("Chapter ID cannot be empty.");
            return false;
        }
        if (chapterTitle == null || chapterTitle.trim().isEmpty()) {
            System.out.println("Chapter title cannot be empty.");
            return false;
        }
        return true;
    }

    private boolean validateSectionInput(String sectionId, String sectionTitle) {
        if (sectionId == null || sectionId.trim().isEmpty()) {
            System.out.println("Section ID cannot be empty.");
            return false;
        }
        if (sectionTitle == null || sectionTitle.trim().isEmpty()) {
            System.out.println("Section title cannot be empty.");
            return false;
        }
        return true;
    }

    private boolean validateBlockInput(String blockId, String contentType) {
        if (blockId == null || blockId.trim().isEmpty()) {
            System.out.println("Block ID cannot be empty.");
            return false;
        }

        // Assuming valid content types are "text", "picture", "activity"
        if (contentType == null || !Arrays.asList("text", "picture", "activity").contains(contentType.toLowerCase())) {
            System.out.println("Invalid content type.");
            return false;
        }

        return true;
    }

    private boolean validateActivityInput(String activityId) {
        if (activityId == null || activityId.trim().isEmpty()) {
            System.out.println("Activity ID cannot be empty.");
            return false;
        }
        return true;
    }

    private boolean validateQuestionInput(String questionId, String questionText,
                                          String optionOne, String optionTwo,
                                          String optionThree, String optionFour,
                                          String explanationOne, String explanationTwo,
                                          String explanationThree, String explanationFour,
                                          String correctAnswer) {
        // Validate Question ID
        if (questionId == null || questionId.trim().isEmpty()) {
            System.out.println("Question ID cannot be empty.");
            return false;
        }

        // Validate Question Text
        if (questionText == null || questionText.trim().isEmpty()) {
            System.out.println("Question text cannot be empty.");
            return false;
        }

        // Validate Options
        if (optionOne == null || optionOne.trim().isEmpty()) {
            System.out.println("Option 1 cannot be empty.");
            return false;
        }
        if (optionTwo == null || optionTwo.trim().isEmpty()) {
            System.out.println("Option 2 cannot be empty.");
            return false;
        }
        if (optionThree == null || optionThree.trim().isEmpty()) {
            System.out.println("Option 3 cannot be empty.");
            return false;
        }
        if (optionFour == null || optionFour.trim().isEmpty()) {
            System.out.println("Option 4 cannot be empty.");
            return false;
        }

        // Validate Explanations
        if (explanationOne == null || explanationOne.trim().isEmpty()) {
            System.out.println("Explanation for Option 1 cannot be empty.");
            return false;
        }
        if (explanationTwo == null || explanationTwo.trim().isEmpty()) {
            System.out.println("Explanation for Option 2 cannot be empty.");
            return false;
        }
        if (explanationThree == null || explanationThree.trim().isEmpty()) {
            System.out.println("Explanation for Option 3 cannot be empty.");
            return false;
        }
        if (explanationFour == null || explanationFour.trim().isEmpty()) {
            System.out.println("Explanation for Option 4 cannot be empty.");
            return false;
        }

        // Validate Correct Answer
        if (correctAnswer == null || !correctAnswer.matches("[1-4]")) {
            System.out.println("Correct answer must be one of the options (1/2/3/4).");
            return false;
        }

        // If all validations pass, return true
        return true;
    }

    // Course Management
    public boolean createActiveCourse(String courseId, String courseName, String textbookId,
                                      String facultyId, Date startDate, Date endDate,
                                      String token, int capacity) {
        try {
            // Step 1: Validate input
            validateCourseInput(courseId, courseName, textbookId, facultyId, startDate, endDate);
            validateActiveTokenAndCapacity(token, capacity);

            // Step 2: Check if faculty member exists
            if (userDAO.findById(facultyId) == null) {
                throw new RuntimeException("Faculty member not found");
            }

            // Step 3: Check if textbook exists
            if (textbookDAO.getTextbookById(textbookId) == null) {
                throw new RuntimeException("Textbook not found");
            }

            // Step 4: Create a new CourseModel object
            CourseModel newCourse = new CourseModel(
                    courseId,
                    courseName,
                    facultyId,
                    startDate,
                    endDate,
                    "Active",  // Active course type
                    capacity,
                    token,
                    textbookId
            );

            // Step 5: Save the course using CourseDAO
            return courseDAO.createCourse(newCourse);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    public boolean createEvaluationCourse(String courseId, String courseName, String textbookId,
                                          String facultyId, Date startDate, Date endDate) {
        try {
            // Step 1: Validate input
            validateCourseInput(courseId, courseName, textbookId, facultyId, startDate, endDate);

            // Step 2: Check if faculty member exists
            if (userDAO.findById(facultyId) == null) {
                throw new RuntimeException("Faculty member not found");
            }

            // Step 3: Check if textbook exists
            if (textbookDAO.getTextbookById(textbookId) == null) {
                throw new RuntimeException("Textbook not found");
            }

            // Step 4: Create a new CourseModel object
            CourseModel newCourse = new CourseModel(
                    courseId,
                    courseName,
                    facultyId,
                    (java.sql.Date) startDate,
                    (java.sql.Date) endDate,
                    "Evaluation",  // Evaluation course type
                    0,  // Capacity is 0 for evaluation courses by default
                    null,  // No token needed for evaluation courses
                    textbookId
            );

            // Step 5: Save the course using CourseDAO
            return courseDAO.createCourse(newCourse);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    // Validation Methods
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


    private void validateCourseInput(String courseId, String courseName, String textbookId,
                                     String facultyId, Date startDate, Date endDate) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be empty");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (textbookId == null || textbookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Textbook ID cannot be empty");
        }
        if (facultyId == null || facultyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Faculty ID cannot be empty");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Course dates cannot be empty");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    private void validateActiveTokenAndCapacity(String token, int capacity) {
        if (token == null || token.length() != 7) {
            throw new IllegalArgumentException("Token must be exactly 7 characters");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
    }

    private String generateUniqueFacultyId(String firstName, String lastName) {
        // Extract the first two letters from firstName and lastName
        String firstTwoLettersFirstName = firstName.length() >= 2 ? firstName.substring(0, 2) : firstName;
        String firstTwoLettersLastName = lastName.length() >= 2 ? lastName.substring(0, 2) : lastName;

        // Concatenate with '1024' to form the unique faculty ID
        return firstTwoLettersFirstName + firstTwoLettersLastName + "1124";
    }
}