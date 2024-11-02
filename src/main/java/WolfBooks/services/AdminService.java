package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.dao.UserDAO;
import src.main.java.WolfBooks.models.UserModel;
import src.main.java.WolfBooks.dao.TextbookDAO;
import src.main.java.WolfBooks.models.TextbookModel;
import src.main.java.WolfBooks.dao.ChapterDAO;
import src.main.java.WolfBooks.models.ChapterModel;
import src.main.java.WolfBooks.dao.SectionDAO;
import src.main.java.WolfBooks.models.SectionModel;
import src.main.java.WolfBooks.dao.BlockDAO;
import src.main.java.WolfBooks.models.BlockModel;

import java.sql.SQLException;
import java.util.*;


public class AdminService {

    private final UserDAO userDAO;
    private final TextbookDAO textbookDAO = new TextbookDAO();
    private final SectionDAO sectionDAO = new SectionDAO();
    private final ChapterDAO chapterDAO = new ChapterDAO();
    private final BlockDAO blockDAO = new BlockDAO();

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

        ChapterModel chapter = new ChapterModel(chapterId, textbookId, chapterTitle, isHidden, createdBy);
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
                               String blockId,String contentType,
                               String content,
                               boolean isHidden,
                               String createdBy) {

        BlockModel block=   new BlockModel(blockId ,sectionId ,textbookId ,chapterId ,contentType ,content ,isHidden ,createdBy);

        return blockDAO.createBlock(block);

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
