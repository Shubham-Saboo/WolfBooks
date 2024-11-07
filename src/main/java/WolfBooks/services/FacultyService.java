package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.dao.*;
import src.main.java.WolfBooks.models.*;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class FacultyService {
    private final UserDAO userDAO = new UserDAO();
    //private final BlockDAO blockDAO = new BlockDAO();
    private final SectionDAO sectionDAO = new SectionDAO();
    private final StudentActivityDAO studentActivityDAO = new StudentActivityDAO();
    private final StudentGradesDAO studentGradesDAO = new StudentGradesDAO();
   // private final QuestionDAO questionDAO = new QuestionDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
  //  private final ActivityDAO activityDAO = new ActivityDAO();
    private final TextbookDAO textbookDAO = new TextbookDAO();
    private final ChapterDAO chapterDAO = new ChapterDAO();
    private final TeachingAssistantDAO taDAO = new TeachingAssistantDAO();
    private static final ActivityDAO activityDAO = new ActivityDAO();
    private static final QuestionDAO questionDAO = new QuestionDAO();
    private static final BlockDAO blockDAO = new BlockDAO();

//    public FacultyService(CourseDAO courseDAO, EnrollmentDAO enrollmentDAO,
//                         ChapterDAO chapterDAO, SectionDAO sectionDAO,
//                         BlockDAO blockDAO, ActivityDAO activityDAO,
//                         UserDAO userDAO) {
//        this.courseDAO = courseDAO;
//        this.enrollmentDAO = enrollmentDAO;
//        this.chapterDAO = chapterDAO;
//        this.sectionDAO = sectionDAO;
//        this.blockDAO = blockDAO;
//        this.activityDAO = activityDAO;
//        this.userDAO = userDAO;
//    }

    public FacultyService(){

    }

    private String getTextbookIdForBlock(String blockId) throws SQLException {
    String sql = "SELECT textbook_id FROM blocks WHERE block_id = ?";
    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, blockId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("textbook_id");
        }
    }
    return null;
}

private String getChapterIdForBlock(String blockId) throws SQLException {
    String sql = "SELECT chapter_id FROM blocks WHERE block_id = ?";
    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, blockId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("chapter_id");
        }
    }
    return null;
}

private String getSectionIdForBlock(String blockId) throws SQLException {
    String sql = "SELECT section_id FROM blocks WHERE block_id = ?";
    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, blockId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("section_id");
        }
    }
    return null;
}

private String getTextbookIdForSection(String sectionId) throws SQLException {
    String sql = "SELECT textbook_id FROM Sections WHERE section_id = ?";
    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, sectionId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("textbook_id");
        }
    }
    return null;
}

private String getChapterIdForSection(String sectionId) throws SQLException {
    String sql = "SELECT chapter_id FROM Sections WHERE section_id = ?";
    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, sectionId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("chapter_id");
        }
    }
    return null;
}
private String getTextbookIdForChapter(String chapterId) throws SQLException {
    String sql = "SELECT textbook_id FROM Chapters WHERE chapter_id = ?";
    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, chapterId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("textbook_id");
        }
    }
    return null;
}
    // Authentication
    public boolean validateLogin(String userId, String password) throws SQLException {
        UserModel user = userDAO.authenticateUser(userId, password);
        return user != null && "faculty".equalsIgnoreCase(user.getRole());
    }

    // Course Management
    public List<CourseModel> getCoursesByFaculty(String facultyId) throws SQLException {
        return courseDAO.getCoursesByFaculty(facultyId);
    }

    // Enrollment Management
    public List<UserModel> getWorklistForCourse(String courseId) throws SQLException {
        return enrollmentDAO.getWorklistForCourse(courseId);
    }

    public List<UserModel> getStudentsByCourse(String courseId) throws SQLException {
        return enrollmentDAO.getEnrolledStudents(courseId);
    }

    public boolean approveEnrollment(String courseId, String studentId) throws SQLException {
        return enrollmentDAO.updateEnrollmentStatus(studentId, courseId, "Approved");
    }
//
//    // Chapter Management
//    public boolean addNewChapter(String courseId, String chapterId, String title) throws SQLException {
////        ChapterModel chapter = new ChapterModel(chapterId, title, courseId, false, "faculty");
////        return chapterDAO.createChapter(chapter);
//
//    }
//
//    public boolean hideChapter(String chapterId) throws SQLException {
//        String textbookId = getTextbookIdForChapter(chapterId);
//
//        if (textbookId == null) {
//            return false;
//        }
//
//        ChapterModel chapter = ChapterDAO.getChapterById(textbookId, chapterId);
//        if (chapter != null) {
//            chapter.setHidden(true);
//            return chapterDAO.updateChapter(chapter);
//        }
//        return false;
//    }
//
//    public boolean deleteChapter(String chapterId) throws SQLException {
//        return chapterDAO.deleteChapter(chapterId);
//    }
//
//    // Section Management
//    public boolean addNewSection(String chapterId, String sectionNumber, String title) throws SQLException {
//        SectionModel section = new SectionModel(sectionNumber, null, chapterId, title, false, "faculty");
//        return sectionDAO.createSection(section);
//    }
//
//    public boolean hideSection(String sectionId) throws SQLException {
//        String textbookId = getTextbookIdForSection(sectionId);
//        String chapterId = getChapterIdForSection(sectionId);
//
//        if (textbookId == null || chapterId == null) {
//            return false;
//        }
//
//        SectionModel section = SectionDAO.getSectionById(textbookId, chapterId, sectionId);
//        if (section != null) {
//            section.setHidden(true);
//            return sectionDAO.updateSection(section);
//        }
//        return false;
//    }
//
//    public boolean deleteSection(String sectionId) throws SQLException {
//        return sectionDAO.deleteSection(sectionId);
//    }
//
//    // Content Block Management
//    public boolean addText(String contentBlockId, String sectionId, String text) throws SQLException {
//        BlockModel block = new BlockModel(sectionId, null, contentBlockId, null, "Text", text, false, "faculty");
//        return blockDAO.createBlock(block);
//    }
//
//    public boolean addPicture(String contentBlockId, String sectionId, String picturePath) throws SQLException {
//        BlockModel block = new BlockModel(sectionId, null, contentBlockId, null, "Image", picturePath, false, "faculty");
//        return blockDAO.createBlock(block);
//    }
//
//    public boolean hideContentBlock(String blockId) throws SQLException {
//        String textbookId = getTextbookIdForBlock(blockId);
//        String chapterId = getChapterIdForBlock(blockId);
//        String sectionId = getSectionIdForBlock(blockId);
//
//        if (textbookId == null || chapterId == null || sectionId == null) {
//            return false;
//        }
//
//        BlockModel block = BlockDAO.findBlock(textbookId, chapterId, sectionId, blockId);
//        if (block != null) {
//            try {
//                block.setHidden(true);
//                return blockDAO.modifyBlock(block);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Error updating block: " + e.getMessage());
//                return false;
//            }
//        }
//        return false;
//    }
//
//    // Update the deleteContentBlock method
//    public boolean deleteContentBlock(String blockId) throws SQLException {
//        String textbookId = getTextbookIdForBlock(blockId);
//        String chapterId = getChapterIdForBlock(blockId);
//        String sectionId = getSectionIdForBlock(blockId);
//
//        if (textbookId == null || chapterId == null || sectionId == null) {
//            return false;
//        }
//
//        BlockModel block = BlockDAO.findBlock(textbookId, chapterId, sectionId, blockId);
//        if (block != null) {
//            try {
//                return blockDAO.deleteBlock(block);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Error deleting block: " + e.getMessage());
//                return false;
//            }
//        }
//        return false;
//    }
//    // Activity Management
//    public boolean hideActivity(String activityId) throws SQLException {
//        return activityDAO.hideActivity(activityId);
//    }
//
//    public boolean deleteActivity(String activityId) throws SQLException {
//        return activityDAO.deleteActivity(activityId);
//    }
//
//    public boolean addQuestion(String questionId, String activityId, String questionText,
//                             String option1, String explanation1,
//                             String option2, String explanation2,
//                             String option3, String explanation3,
//                             String option4, String explanation4,
//                             int answer) throws SQLException {
//        return activityDAO.addQuestion(questionId, activityId, questionText,
//                                     option1, explanation1, option2, explanation2,
//                                     option3, explanation3, option4, explanation4, answer);
//    }


    // ==================== Chapter Management ====================
    public boolean addNewChapter(String textbookId, String chapterId, String chapterTitle, String createdBy) {
        try {
            validateChapterInput(chapterId, chapterTitle);
            //List<String> textbookId= getAssignedTextbooksForCourse(createdBy,courseId);

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
            return taDAO.hideChapterforfaculty(textbookId, chapterId, taId, courseId);
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


    public boolean hideActivity(String textbookId, String chapterId, String sectionId, String blockId, String activityId, String taId, String courseId) {
        try {
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(sectionId, "Section ID");
            validateId(blockId, "Block ID");
            validateId(activityId, "Activity ID");
            validateId(taId, "TA ID");
            validateId(courseId, "Course ID");
            return taDAO.hideActivity(textbookId, chapterId, sectionId, blockId, activityId, taId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to hide activity: " + e.getMessage());
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
    public boolean deleteChapter(String chapterId, String taId,String textboookId, String courseId) {
        try {
            validateId(chapterId, "Chapter ID");
            validateId(taId, "TA ID");
            return taDAO.deleteChapter(chapterId, taId,textboookId,courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete chapter: " + e.getMessage());
        }
    }

    // Delete Section
    public boolean deleteSection(String textbookId,String sectionId, String taId,String chapterId) {
        try {
            validateId(sectionId, "Section ID");
            validateId(taId, "TA ID");
            return taDAO.deleteSection(textbookId,sectionId, taId,chapterId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete section: " + e.getMessage());
        }
    }

    // Delete Block
    public boolean deleteBlock(String textbookId, String chapterId, String sectionId, String blockId, String taId) {
        try {
            validateId(textbookId, "Textbook ID");
            validateId(chapterId, "Chapter ID");
            validateId(sectionId, "Section ID");
            validateId(blockId, "Block ID");
            validateId(taId, "TA ID");
            return taDAO.deleteContentBlock(textbookId, chapterId, sectionId, blockId, taId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete block: " + e.getMessage());
        }
    }


    // ==================== Password Management ====================
//    public boolean changePassword(String userId, String currentPassword, String newPassword) {
//        try {
//            validatePasswordRequirements(newPassword);
//            if (!taDAO.updatePassword(userId, currentPassword, newPassword)) {
//                System.out.println("Password update failed. Please check the current password.");
//                return false;
//            }
//            System.out.println("Password successfully updated.");
//            return true;
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to update password: " + e.getMessage());
//        }
//    }


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
            List<String> textbooks = taDAO.getAssignedTextbooksforfaculty(taId, courseId);
            System.out.println(textbooks.get(0));
            return textbooks;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve textbooks: " + e.getMessage());
        }
    }

    public List<String> getAssignedCourseIds(String taId, String courseId) {
        try {
            List<String> textbooks = taDAO.getAssignedTextbooksforfaculty(taId, courseId);

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
























    // TA Management
    public boolean addTA(String firstName, String lastName, String email, String defaultPassword, String courseId, String FacultyId) throws SQLException {
        String taId = "TA" + System.currentTimeMillis();
        UserModel ta = new UserModel(taId, firstName, lastName, email, defaultPassword, "teaching_assistant", true);
        return userDAO.createTAAccount(ta, courseId, FacultyId);
    }

    // Password Management
    public boolean changePassword(String facultyId, String currentPassword, String newPassword) throws SQLException {
        return userDAO.changePassword(facultyId, currentPassword, newPassword);
    }
}
