package src.main.java.WolfBooks.dao;

import src.main.java.WolfBooks.models.QuestionModel;
import src.main.java.WolfBooks.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    // Create a new question
    public boolean createQuestion(QuestionModel question) {
        String sql = "INSERT INTO Questions (textbook_id, chapter_id, section_id, block_id, activity_id, question_id, " +
                "question, explanation_one, explanation_two, explanation_three, explanation_four, " +
                "answer_one, answer_two, answer_three, answer_four, answer_correct) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, question.getTextbookId());
            pstmt.setString(2, question.getChapterId());
            pstmt.setString(3, question.getSectionId());
            pstmt.setString(4, question.getBlockId());
            pstmt.setString(5, question.getActivityId());
            pstmt.setString(6, question.getQuestionId());
            pstmt.setString(7, question.getQuestionText());
            pstmt.setString(8, question.getExplanationOne());
            pstmt.setString(9, question.getExplanationTwo());
            pstmt.setString(10, question.getExplanationThree());
            pstmt.setString(11, question.getExplanationFour());
            pstmt.setString(12, question.getAnswerOne());
            pstmt.setString(13, question.getAnswerTwo());
            pstmt.setString(14, question.getAnswerThree());
            pstmt.setString(15, question.getAnswerFour());
            pstmt.setString(16, question.getCorrectAnswer());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a question by ID
    public QuestionModel getQuestionById(String textbookId, String chapterId, String sectionId,
                                         String blockId, String activityId, String questionId) {
        String sql = "SELECT * FROM Questions WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND activity_id = ? AND question_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            pstmt.setString(4, blockId);
            pstmt.setString(5, activityId);
            pstmt.setString(6, questionId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractQuestionFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all questions for a specific activity
    public List<QuestionModel> getQuestionsByActivity(String textbookId,
                                                      String chapterId,
                                                      String sectionId,
                                                      String blockId,
                                                      String activityId) {
        List<QuestionModel> questions = new ArrayList<>();
        String sql = "SELECT * FROM Questions WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND activity_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            pstmt.setString(4, blockId);
            pstmt.setString(5, activityId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                questions.add(extractQuestionFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    // Update a question
    public boolean updateQuestion(QuestionModel question) {
        String sql = "UPDATE Questions SET question = ?, explanation_one = ?, explanation_two = ?, explanation_three = ?, explanation_four = ?, " +
                "answer_one = ?, answer_two = ?, answer_three = ?, answer_four = ?, answer_correct = ? " +
                "WHERE textbook_id = ? AND chapter_id = ? AND section_id = ? AND block_id = ? AND activity_id = ? AND question_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set updated fields
            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getExplanationOne());
            pstmt.setString(3, question.getExplanationTwo());
            pstmt.setString(4, question.getExplanationThree());
            pstmt.setString(5, question.getExplanationFour());

            // Set updated answers
            pstmt.setString(6, question.getAnswerOne());
            pstmt.setString(7, question.getAnswerTwo());
            pstmt.setString(8, question.getAnswerThree());
            pstmt.setString(9, question.getAnswerFour());

            // Set correct answer
            pstmt.setString(10, String.valueOf(question.getCorrectAnswer()));

            // Set identifiers
            pstmt.setString(11, question.getTextbookId());
            pstmt.setString(12, question.getChapterId());
            pstmt.setString(13, question.getSectionId());
            pstmt.setString(14, question.getBlockId());
            pstmt.setString(15, question.getActivityId());
            pstmt.setString(16, question.getQuestionId());

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a specific Question by ID
    public boolean deleteQuestion(String textbookId, String chapterId, String sectionId, String blockId, String activityId, String questionId) {
        String sql = "DELETE FROM Questions WHERE textbook_id=? AND chapter_id=? AND section_id=? AND block_id=? AND activity_id=? AND question_id=?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textbookId);
            pstmt.setString(2, chapterId);
            pstmt.setString(3, sectionId);
            pstmt.setString(4, blockId);
            pstmt.setString(5, activityId);
            pstmt.setString(6, questionId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Utility method to extract QuestionModel from ResultSet
    private QuestionModel extractQuestionFromResultSet(ResultSet rs) throws SQLException {
        return new QuestionModel(
                rs.getString("textbook_id"),
                rs.getString("chapter_id"),
                rs.getString("section_id"),
                rs.getString("block_id"),
                rs.getString("activity_id"),
                rs.getString("question_id"),
                rs.getString("question"),
                rs.getString("explanation_one"),
                rs.getString("explanation_two"),
                rs.getString("explanation_three"),
                rs.getString("explanation_four"),
                rs.getString("answer_one"),
                rs.getString("answer_two"),
                rs.getString("answer_three"),
                rs.getString("answer_four"),
                rs.getString("answer_correct")
        );
    }
}
