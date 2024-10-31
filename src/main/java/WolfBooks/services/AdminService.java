package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.dao.UserDAO;
import src.main.java.WolfBooks.models.UserModel;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AdminService {
    private final UserDAO userDAO;
    //TODO: Add other DAOs when implemented
    // private final TextbookDAO textbookDAO;
    // private final CourseDAO courseDAO;
    // private final ChapterDAO chapterDAO;

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

            String facultyId = generateUniqueFacultyId();
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

    // E-textbook Management
    public boolean createETextbook(String title, String textbookId) {
        validateTextbookInput(title, textbookId);
        // TODO: Implement when TextbookDAO is available
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean addChapter(String textbookId, String chapterId, String chapterTitle) {
        validateChapterInput(chapterId, chapterTitle);
        // TODO: Implement when ChapterDAO is available
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean addSection(String textbookId, String chapterId, String sectionId,
                              int sectionNumber, String sectionTitle) {
        validateSectionInput(sectionId, sectionNumber, sectionTitle);
        // TODO: Implement when appropriate DAO is available
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean addContentBlock(String textbookId, String chapterId, String sectionId,
                                   String blockId, String contentType) {
        validateContentBlockInput(blockId, contentType);
        // TODO: Implement when appropriate DAO is available
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean addActivity(String blockId, String activityId) {
        validateActivityInput(activityId);
        // TODO: Implement when appropriate DAO is available
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean addQuestion(String activityId, String questionId, String questionText,
                               List<String> options, List<String> explanations, char correctAnswer) {
        validateQuestionInput(questionId, questionText, options, explanations, correctAnswer);
        // TODO: Implement when appropriate DAO is available
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Course Management
    public boolean createActiveCourse(String courseId, String courseName, String textbookId,
                                      String facultyId, Date startDate, Date endDate,
                                      String token, int capacity) {
        try {
            validateCourseInput(courseId, courseName, textbookId, facultyId, startDate, endDate);
            validateActiveTokenAndCapacity(token, capacity);

            if (userDAO.findById(facultyId) == null) {
                throw new RuntimeException("Faculty member not found");
            }

            // TODO: Implement when CourseDAO is available
            throw new UnsupportedOperationException("Not implemented yet");
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    public boolean createEvaluationCourse(String courseId, String courseName, String textbookId,
                                          String facultyId, Date startDate, Date endDate) {
        try {
            validateCourseInput(courseId, courseName, textbookId, facultyId, startDate, endDate);

            if (userDAO.findById(facultyId) == null) {
                throw new RuntimeException("Faculty member not found");
            }

            // TODO: Implement when CourseDAO is available
            throw new UnsupportedOperationException("Not implemented yet");
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

    private void validateTextbookInput(String title, String textbookId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Textbook title cannot be empty");
        }
        if (textbookId == null || textbookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Textbook ID cannot be empty");
        }
    }

    private void validateChapterInput(String chapterId, String chapterTitle) {
        if (chapterId == null || chapterId.trim().isEmpty()) {
            throw new IllegalArgumentException("Chapter ID cannot be empty");
        }
        if (chapterTitle == null || chapterTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Chapter title cannot be empty");
        }
    }

    private void validateSectionInput(String sectionId, int sectionNumber, String sectionTitle) {
        if (sectionId == null || sectionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Section ID cannot be empty");
        }
        if (sectionNumber <= 0) {
            throw new IllegalArgumentException("Invalid section number");
        }
        if (sectionTitle == null || sectionTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Section title cannot be empty");
        }
    }

    private void validateContentBlockInput(String blockId, String contentType) {
        if (blockId == null || blockId.trim().isEmpty()) {
            throw new IllegalArgumentException("Block ID cannot be empty");
        }
        if (contentType == null || !Arrays.asList("text", "picture", "activity").contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Invalid content type");
        }
    }

    private void validateActivityInput(String activityId) {
        if (activityId == null || activityId.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity ID cannot be empty");
        }
    }

    private void validateQuestionInput(String questionId, String questionText,
                                       List<String> options, List<String> explanations, char correctAnswer) {
        if (questionId == null || questionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Question ID cannot be empty");
        }
        if (questionText == null || questionText.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty");
        }
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException("Must provide exactly 4 options");
        }
        if (explanations == null || explanations.size() != 4) {
            throw new IllegalArgumentException("Must provide exactly 4 explanations");
        }
        if (!Arrays.asList('A', 'B', 'C', 'D').contains(correctAnswer)) {
            throw new IllegalArgumentException("Invalid correct answer option");
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

    private String generateUniqueFacultyId() {
        return "F" + System.currentTimeMillis();
    }
}