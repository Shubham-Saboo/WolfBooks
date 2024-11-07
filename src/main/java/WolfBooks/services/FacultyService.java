package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.dao.*;
import src.main.java.WolfBooks.models.*;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class FacultyService {
    private final UserDAO userDAO = new UserDAO();
    private final BlockDAO blockDAO = new BlockDAO();
    private final SectionDAO sectionDAO = new SectionDAO();
    private final StudentActivityDAO studentActivityDAO = new StudentActivityDAO();
    private final StudentGradesDAO studentGradesDAO = new StudentGradesDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final ActivityDAO activityDAO = new ActivityDAO();
    private final TextbookDAO textbookDAO = new TextbookDAO();
    private final ChapterDAO chapterDAO = new ChapterDAO();

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

    // Chapter Management
    public boolean addNewChapter(String courseId, String chapterId, String title) throws SQLException {
        ChapterModel chapter = new ChapterModel(chapterId, title, courseId, false, "faculty");
        return chapterDAO.createChapter(chapter);
    }

    public boolean hideChapter(String chapterId) throws SQLException {
        String textbookId = getTextbookIdForChapter(chapterId);
        
        if (textbookId == null) {
            return false;
        }
        
        ChapterModel chapter = ChapterDAO.getChapterById(textbookId, chapterId);
        if (chapter != null) {
            chapter.setHidden(true);
            return chapterDAO.updateChapter(chapter);
        }
        return false;
    }

    public boolean deleteChapter(String chapterId) throws SQLException {
        return chapterDAO.deleteChapter(chapterId);
    }

    // Section Management
    public boolean addNewSection(String chapterId, String sectionNumber, String title) throws SQLException {
        SectionModel section = new SectionModel(sectionNumber, null, chapterId, title, false, "faculty");
        return sectionDAO.createSection(section);
    }

    public boolean hideSection(String sectionId) throws SQLException {
        String textbookId = getTextbookIdForSection(sectionId);
        String chapterId = getChapterIdForSection(sectionId);
        
        if (textbookId == null || chapterId == null) {
            return false;
        }
        
        SectionModel section = SectionDAO.getSectionById(textbookId, chapterId, sectionId);
        if (section != null) {
            section.setHidden(true);
            return sectionDAO.updateSection(section);
        }
        return false;
    }

    public boolean deleteSection(String sectionId) throws SQLException {
        return sectionDAO.deleteSection(sectionId);
    }

    // Content Block Management
    public boolean addText(String contentBlockId, String sectionId, String text) throws SQLException {
        BlockModel block = new BlockModel(sectionId, null, contentBlockId, null, "Text", text, false, "faculty");
        return blockDAO.createBlock(block);
    }

    public boolean addPicture(String contentBlockId, String sectionId, String picturePath) throws SQLException {
        BlockModel block = new BlockModel(sectionId, null, contentBlockId, null, "Image", picturePath, false, "faculty");
        return blockDAO.createBlock(block);
    }

    public boolean hideContentBlock(String blockId) throws SQLException {
        String textbookId = getTextbookIdForBlock(blockId);
        String chapterId = getChapterIdForBlock(blockId);
        String sectionId = getSectionIdForBlock(blockId);
        
        if (textbookId == null || chapterId == null || sectionId == null) {
            return false;
        }
        
        BlockModel block = BlockDAO.findBlock(textbookId, chapterId, sectionId, blockId);
        if (block != null) {
            try {
                block.setHidden(true);
                return blockDAO.modifyBlock(block);
            } catch (IllegalArgumentException e) {
                System.out.println("Error updating block: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
    
    // Update the deleteContentBlock method
    public boolean deleteContentBlock(String blockId) throws SQLException {
        String textbookId = getTextbookIdForBlock(blockId);
        String chapterId = getChapterIdForBlock(blockId);
        String sectionId = getSectionIdForBlock(blockId);
        
        if (textbookId == null || chapterId == null || sectionId == null) {
            return false;
        }
        
        BlockModel block = BlockDAO.findBlock(textbookId, chapterId, sectionId, blockId);
        if (block != null) {
            try {
                return blockDAO.deleteBlock(block);
            } catch (IllegalArgumentException e) {
                System.out.println("Error deleting block: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
    // Activity Management
    public boolean hideActivity(String activityId) throws SQLException {
        return activityDAO.hideActivity(activityId);
    }

    public boolean deleteActivity(String activityId) throws SQLException {
        return activityDAO.deleteActivity(activityId);
    }

    public boolean addQuestion(String questionId, String activityId, String questionText,
                             String option1, String explanation1,
                             String option2, String explanation2,
                             String option3, String explanation3,
                             String option4, String explanation4,
                             int answer) throws SQLException {
        return activityDAO.addQuestion(questionId, activityId, questionText,
                                     option1, explanation1, option2, explanation2,
                                     option3, explanation3, option4, explanation4, answer);
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
