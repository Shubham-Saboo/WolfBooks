package src.main.java.WolfBooks.models;

public class QuestionModel {

    private String textbookId;
    private String chapterId;
    private String sectionId;
    private String blockId;
    private String activityId;
    private String questionId;
    private String questionText;
    private String explanationOne;
    private String explanationTwo;
    private String explanationThree;
    private String explanationFour;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerFour;
    private String correctAnswer;

    // Constructor
    public QuestionModel(String textbookId, String chapterId, String sectionId, String blockId,
                         String activityId, String questionId, String questionText,
                         String explanationOne, String explanationTwo, String explanationThree,
                         String explanationFour, String answerOne, String answerTwo,
                         String answerThree, String answerFour, String correctAnswer) {
        this.textbookId = textbookId;
        this.chapterId = chapterId;
        this.sectionId = sectionId;
        this.blockId = blockId;
        this.activityId = activityId;
        this.questionId = questionId;
        this.questionText = questionText;
        this.explanationOne = explanationOne;
        this.explanationTwo = explanationTwo;
        this.explanationThree = explanationThree;
        this.explanationFour = explanationFour;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.correctAnswer = correctAnswer;
    }

    // Getters and Setters
    public String getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(String textbookId) {
        this.textbookId = textbookId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getExplanationOne() {
        return explanationOne;
    }

    public void setExplanationOne(String explanationOne) {
        this.explanationOne = explanationOne;
    }

    public String getExplanationTwo() {
        return explanationTwo;
    }

    public void setExplanationTwo(String explanationTwo) {
        this.explanationTwo = explanationTwo;
    }

    public String getExplanationThree() {
        return explanationThree;
    }

    public void setExplanationThree(String explanationThree) {
        this.explanationThree = explanationThree;
    }

    public String getExplanationFour() {
        return explanationFour;
    }

    public void setExplanationFour(String explanationFour) {
        this.explanationFour = explanationFour;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }


    // Getter and Setter for Correct Answer
    // Correct Answer is expected to be a single character ('A', 'B', 'C', 'D')

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
