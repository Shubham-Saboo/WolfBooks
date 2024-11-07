package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.dao.TeachingAssistantDAO;
import src.main.java.WolfBooks.models.TeachingAssistantModel;
import src.main.java.WolfBooks.models.UserModel;
import src.main.java.WolfBooks.dao.*;
import src.main.java.WolfBooks.models.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeachingAssistantService {
    private final TeachingAssistantDAO taDAO;
    private static final ActivityDAO activityDAO = new ActivityDAO();
    private static final QuestionDAO questionDAO = new QuestionDAO();
    private static final BlockDAO blockDAO = new BlockDAO();

    public TeachingAssistantService(TeachingAssistantDAO taDAO) {



        this.taDAO = taDAO;

    }

    // ==================== Authentication ====================
    public TeachingAssistantModel authenticateTA(String userId, String password) {
        try {
            TeachingAssistantModel ta = taDAO.authenticateTA(userId, password);
            if (ta != null) {
                ta.setAssignedCourseIds(taDAO.getAssignedCourses(userId));
            }
            return ta;
        } catch (SQLException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    // ==================== Course Access Operations ====================
    public List<String> getAssignedCourses(String taId) {
        try {
            validateId(taId, "TA ID");
            return taDAO.getAssignedCourses(taId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve assigned courses: " + e.getMessage());
        }
    }

    public boolean verifyTACourseAccess(String taId, String courseId) {
        try {
            validateId(taId, "TA ID");
            validateId(courseId, "Course ID");
            return taDAO.validateTACourseAccess(taId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to verify course access: " + e.getMessage());
        }
    }

    // ==================== Student Management ====================
    public List<UserModel> viewStudentsInCourse(String courseId) {
        try {
            validateId(courseId, "Course ID");
            return taDAO.viewStudentsInCourse(courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve students: " + e.getMessage());
        }
    }

    // ==================== Chapter Management ====================
    public boolean addNewChapter(String textbookId, String chapterId, String chapterTitle, String createdBy) {
        try {
            validateChapterInput(chapterId, chapterTitle);
            //List<String> textbookId= getAssignedTextbooksForCourse(createdBy,courseId);
            if (textbookId.isEmpty()){
                System.out.println("chutuye");
            }
            else{
                System.out.println(textbookId);
            }
            validateId(textbookId, "Textbook ID");
            validateId(createdBy, "Creator ID");
            return taDAO.addChapter(textbookId, chapterId, chapterTitle, createdBy);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add chapter: " + e.getMessage());
        }
    }

    public boolean modifyChapter(String textbookId, String chapterId, String chapterTitle, String taId, String courseId) {
        try {
            validateChapterInput(chapterId, chapterTitle);
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(taId, "TA ID");
            validateId(courseId, "Course ID");
            return taDAO.modifyChapter(chapterId, chapterTitle, taId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to modify chapter: " + e.getMessage());
        }
    }

    public boolean hideChapter(String textbookId, String chapterId, String taId, String courseId) {
        try {
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(taId, "TA ID");
            validateId(courseId, "Course ID");
            return taDAO.hideChapter(textbookId, chapterId, taId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to hide chapter: " + e.getMessage());
        }
    }

    // ==================== Section Management ====================
    public boolean addNewSection(String textbookId, String chapterId, String sectionId,
                                 String sectionTitle, String taId) {
        try {
            validateSectionInput(sectionId, sectionTitle);
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(taId, "TA ID");
            return taDAO.addSection(textbookId, chapterId, sectionId, sectionTitle, taId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add section: " + e.getMessage());
        }
    }

    // ==================== Block Management ====================
    public boolean addBlock(String textbookId, String chapterId, String sectionId,
                            String blockId, String contentType, String content, String taId) {
        try {
            validateBlockInput(blockId, contentType, content);
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(sectionId, "Section ID");
            validateId(taId, "TA ID");
            return taDAO.addContentBlock(textbookId, chapterId, sectionId, blockId, contentType, content, taId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add block: " + e.getMessage());
        }
    }

    // ==================== Validation Methods ====================
    private void validateId(String id, String fieldName) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    private void validateChapterInput(String chapterId, String chapterTitle) {
        validateId(chapterId, "Chapter ID");
        if (chapterTitle == null || chapterTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Chapter title cannot be empty");
        }
    }

    private void validateSectionInput(String sectionId, String sectionTitle) {
        validateId(sectionId, "Section ID");
        if (sectionTitle == null || sectionTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Section title cannot be empty");
        }
    }

    private void validateBlockInput(String blockId, String contentType, String content) {
        validateId(blockId, "Block ID");
        if (contentType == null || contentType.trim().isEmpty()) {
            throw new IllegalArgumentException("Content type cannot be empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
    }

    // ==================== Additional Operations ====================

    // Modify Section
    public boolean modifySection(String textbookId, String chapterId, String sectionId,
                                 String sectionTitle, String taId, String courseId) {
        try {
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(sectionId, "Section ID");
            validateId(taId, "TA ID");
            validateId(courseId, "Course ID");
            return taDAO.modifySection(sectionId, sectionTitle, taId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to modify section: " + e.getMessage());
        }
    }

    // Hide Block
    public boolean hideBlock(String textbookId, String chapterId, String sectionId,
                             String blockId, String taId, String courseId) {
        try {
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(sectionId, "Section ID");
            validateId(blockId, "Block ID");
            validateId(taId, "TA ID");
            validateId(courseId, "Course ID");
            return taDAO.hideContentBlock(textbookId, chapterId, sectionId, blockId, taId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to hide block: " + e.getMessage());
        }
    }

    // Modify Block
    public boolean modifyBlock(String blockId, String content, String contentType,
                               String taId, String courseId) {
        try {
            validateId(blockId, "Block ID");
            validateId(taId, "TA ID");
            validateId(courseId, "Course ID");
            validateBlockInput(blockId, contentType, content);
            return taDAO.modifyContentBlock(blockId, content, taId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to modify block: " + e.getMessage());
        }
    }

    // Delete Chapter
    public boolean deleteChapter(String chapterId, String taId) {
        try {
            validateId(chapterId, "Chapter ID");
            validateId(taId, "TA ID");
            return taDAO.deleteChapter(chapterId, taId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete chapter: " + e.getMessage());
        }
    }

    // Delete Section
    public boolean deleteSection(String sectionId, String taId) {
        try {
            validateId(sectionId, "Section ID");
            validateId(taId, "TA ID");
            return taDAO.deleteSection(sectionId, taId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete section: " + e.getMessage());
        }
    }

    // Delete Block
    public boolean deleteBlock(String blockId, String taId) {
        try {
            validateId(blockId, "Block ID");
            validateId(taId, "TA ID");
            return taDAO.deleteContentBlock(blockId, taId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete block: " + e.getMessage());
        }
    }

    // ==================== Password Management ====================
    public boolean changePassword(String userId, String currentPassword, String newPassword) {
        try {
            validatePasswordRequirements(newPassword);
            if (!taDAO.updatePassword(userId, currentPassword, newPassword)) {
                System.out.println("Password update failed. Please check the current password.");
                return false;
            }
            System.out.println("Password successfully updated.");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update password: " + e.getMessage());
        }
    }


    // ==================== Additional Helper Methods ====================
    private void validatePasswordRequirements(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
    }

    private static boolean validateActivityInput(String activityId) {
        if (activityId == null || activityId.trim().isEmpty()) {
            System.out.println("Activity ID cannot be empty.");
            return false;
        }
        return true;
    }



    // Method to get assigned textbooks for a specific course
    public List<String> getAssignedTextbooksForCourse(String taId, String courseId) {
        try {
            List<String> textbooks = taDAO.getAssignedTextbooks(taId, courseId);
            System.out.println("Calling from function: " + textbooks.get(0));
            return textbooks;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve textbooks: " + e.getMessage());
        }
    }

    // Implement a method that updates the TA model with assigned textbooks for a course
    public void updateAssignedTextbooksForCourse(TeachingAssistantModel ta, String courseId) {
        List<String> textbooks = getAssignedTextbooksForCourse(ta.getUserId(), courseId);
        ta.setAccessibleTextbooks(textbooks);  // Assuming there's a setter in the model
    }


    public static boolean createBlock(String textbookId, String chapterId, String sectionId,
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

    public static boolean createActivity(String textbookId, String chapterId, String sectionId, String blockId,
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

    public static boolean addQuestion(String textbookId, String chapterId, String sectionId, String blockId,
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


    private static boolean validateBlockInput(String blockId, String contentType) {
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



    private static boolean validateQuestionInput(String questionId, String questionText,
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


    // ==================== Section Visibility Management ====================
    public boolean hideSection(String textbookId, String chapterId, String sectionId, String taId, String courseId) {
        try {
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(sectionId, "Section ID");
            validateId(taId, "TA ID");
            validateId(courseId, "Course ID");
            return taDAO.hideSection(textbookId, chapterId, sectionId, taId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to hide section: " + e.getMessage());
        }
    }


    // ==================== Other Operations ====================
    // Implement other methods here following similar patterns as above for activities, hiding sections, etc.

}