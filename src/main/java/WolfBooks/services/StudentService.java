package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.dao.*;
import src.main.java.WolfBooks.models.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
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
    private final NotificationDAO notificationDAO = new NotificationDAO();

//    public StudentService(UserDAO userDAO, StudentActivityDAO studentActivityDAO,
//                          StudentGradesDAO studentGradesDAO, SectionDAO sectionDAO,
//                          BlockDAO blockDAO, QuestionDAO questionDAO) {
//        this.userDAO = userDAO;
//        this.studentActivityDAO = studentActivityDAO;
//        this.studentGradesDAO = studentGradesDAO;
//        this.sectionDAO = sectionDAO;
//        this.blockDAO = blockDAO;
//        this.questionDAO = questionDAO;
//    }

    public StudentService() {

    }

    public UserModel authenticateUser(String userId, String password, String role) {
        try {
            UserModel user = userDAO.authenticateUser(userId, password);
            if (user != null && user.getRole().equals(role)) {
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public UserModel createStudentAccount(String firstName, String lastName, String email, String password) {
        String userId = makeStudentId(firstName, lastName);
        if (userDAO.findByEmailAndRole(email, "student") != null) {
            return null;
        }
        try {
            UserModel student = new UserModel(userId, firstName, lastName, email, password, "student", false);
            userDAO.createUser(student);
            return student;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // TODO Add in a new method to get all student activities
    public List<StudentActivityModel> getStudentActivities(String studentId) {
        return studentActivityDAO.getStudentActivities(studentId);
    }

    // TODO Finish this with SectionDAO
    public SectionModel viewSection(String textbookId, String chapterId, String sectionId) {
        return sectionDAO.getSectionById(textbookId, chapterId, sectionId);
    }

    public BlockModel getBlock(String textbookId, String chapterId, String sectionId, String blockId) {
        return blockDAO.findBlock(textbookId, chapterId, sectionId, blockId);
    }

//    public boolean answerQuestion(String courseId, String textbookId, String chapterId, String sectionId,
//                                  String blockId, String activityId, String questionId,
//                                  String studentId, String answer) {
//        QuestionModel questionModel = questionDAO.getQuestionById(textbookId, chapterId, sectionId, blockId, activityId, questionId);
//        if (questionModel == null) {
//            return false;
//        }
//        int score = answer.equals(questionModel.getCorrectAnswer()) ? 1 : 0;
//        return studentActivityDAO.addStudentActivity(
//                new StudentActivityModel(studentId, courseId, textbookId, chapterId, sectionId, blockId,
//                        questionId, questionModel.getActivityId(), score, LocalDateTime.now().toString()));
//    }

    public boolean enrollStudent(String firstName, String lastName, String email, String courseToken) {
        UserModel student = userDAO.findByEmailAndRole(email, "student");
        if (student == null) {
            return false;
        }
        CourseModel courseModel = courseDAO.getCourseByToken(courseToken);
        if (courseModel == null) {
            return false;
        }
        EnrollmentModel enrollmentModel = enrollmentDAO.getEnrollmentById(student.getUserId(), courseModel.getCourseId());
        if (enrollmentModel != null) {
            return false;
        }
        enrollmentModel = new EnrollmentModel(student.getUserId(), courseModel.getCourseId(), "pending");
        enrollmentDAO.createEnrollment(enrollmentModel);
        return true;
    }

    public ActivityModel getActivity(String textbookId, String chapterId, String sectionId, String blockId, String activityId) {
        return activityDAO.getActivityById(textbookId, chapterId, sectionId, blockId, activityId);
    }

    public void addStudentActivity(StudentActivityModel newActivity) {
        StudentActivityModel returnedActivity = studentActivityDAO.getStudentActivity(newActivity.getStudentId(), newActivity.getCourseId(), newActivity.getTextbookId(), newActivity.getChapterId(), newActivity.getSectionId(), newActivity.getBlockId(), newActivity.getQuestionId(), newActivity.getUniqueActivityId());
        if (returnedActivity == null) {
            studentActivityDAO.addStudentActivity(newActivity);
        }
        else {
            returnedActivity.setScore(newActivity.getScore());
            studentActivityDAO.modifyStudentActivity(returnedActivity);
        }
    }

    public List<QuestionModel> getActivityQuestions(String textbookId, String chapterId, String sectionId, String blockId, String activityId) {
        return questionDAO.getQuestionsByActivity(textbookId, chapterId, sectionId, blockId, activityId);
    }

    public TextbookModel getTextbookById(String textbookId) {
        return textbookDAO.getTextbookById(textbookId);
    }

    public List<ChapterModel> getVisibleChapters(String textbookId) {
        List<ChapterModel> visibleChapters = chapterDAO.getChaptersByTextbook(textbookId);
        for (int i = 0; i < visibleChapters.size(); i++) {
            if (visibleChapters.get(i).isHidden()) {
                visibleChapters.remove(i);
                i--;
            }
        }
        return visibleChapters;
    }

    public List<SectionModel> getVisibleSections(String textbookId, String chapterId) {
        List<SectionModel> visibleSections = sectionDAO.getSectionsByChapter(textbookId, chapterId);
        for (int i = 0; i < visibleSections.size(); i++) {
            if (visibleSections.get(i).isHidden()) {
                visibleSections.remove(i);
                i--;
            }
        }
        return visibleSections;
    }

    public List<BlockModel> getVisibleBlocks(String textbookId, String chapterId, String sectionId) {
        List<BlockModel> visibleBlocks = blockDAO.getBlocksBySection(textbookId, chapterId, sectionId);
        for (int i = 0; i < visibleBlocks.size(); i++) {
            if (visibleBlocks.get(i).isHidden()) {
                visibleBlocks.remove(i);
                i--;
            }
        }
        return visibleBlocks;
    }

    public SectionModel getSectionById(String textbookId, String chapterId, String sectionId) {
        return sectionDAO.getSectionById(textbookId, chapterId, sectionId);
    }

    public List<CourseModel> getStudentCourses(UserModel student) {
        List<EnrollmentModel> enrollments = enrollmentDAO.getStudentEnrollments(student);
        List<CourseModel> courses = new ArrayList<>();
        for (EnrollmentModel enrollment : enrollments) {
            courses.add(courseDAO.getCourseById(enrollment.getCourseId()));
        }
        return courses;
    }

    public List<NotificationModel> getUserNotifications(String userId) {
        List<NotificationModel> notifications = notificationDAO.getNotificationsByUser(userId);
//        for (int i = 0; i < notifications.size(); i++) {
//            if (notifications.get(i).) {
//
//            }
//        }
        for (NotificationModel notification : notifications) {
            notificationDAO.deleteNotification(userId, notification.getNotificationId());
        }
        return notifications;
    }

    // Helper Methods
    public String makeStudentId(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return "";
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return "";
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        String twoDigitYear = now.format(formatter);
        return capitalize(firstName).substring(0, 2) + capitalize(lastName).substring(0, 2) + now.getMonthValue() + twoDigitYear;
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
