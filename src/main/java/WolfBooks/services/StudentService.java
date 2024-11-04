package src.main.java.WolfBooks.services;

import src.main.java.WolfBooks.dao.BlockDAO;
import src.main.java.WolfBooks.dao.StudentActivityDAO;
import src.main.java.WolfBooks.dao.StudentGradesDAO;
import src.main.java.WolfBooks.dao.UserDAO;
import src.main.java.WolfBooks.models.BlockModel;
import src.main.java.WolfBooks.models.SectionModel;
import src.main.java.WolfBooks.models.StudentActivityModel;
import src.main.java.WolfBooks.models.UserModel;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class StudentService {
    private final UserDAO userDAO;
//    private final BlockDAO blockDAO;
//    private final SectionDAO sectionDAO;
    private final StudentActivityDAO studentActivityDAO;
    private final StudentGradesDAO studentGradesDAO;

    public StudentService(UserDAO userDAO, StudentActivityDAO studentActivityDAO, StudentGradesDAO studentGradesDAO) {
        this.userDAO = userDAO;
        this.studentActivityDAO = studentActivityDAO;
        this.studentGradesDAO = studentGradesDAO;
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

    public boolean createStudentAccount(String userId, String firstName, String lastName, String email, String password) {
        if (authenticateUser(userId, password, "student") != null) {
            return false;
        }
        try {
            return userDAO.createUser(new UserModel(userId, firstName, lastName, email, password, "student", false));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // TODO Add in a new method to get all student activities
    public StudentActivityModel viewStudentActivities(String studentId) {
//        try {
//            return studentActivityDAO.getStudentActivities();
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return null;
//        }

        return null;
    }

    // TODO Finish this with SectionDAO
    public SectionModel viewSection(String chapterId, String sectionId) {
//        try {
//            return
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return null;
//        }

        return null;
    }

    public BlockModel viewBlock(String blockId) {
        return null;
    }

    public boolean answerQuestion(int answer) {
        return false;
    }

    public boolean enrollStudent() {
        return false;
    }
}
