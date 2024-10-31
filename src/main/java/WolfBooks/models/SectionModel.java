package src.main.java.WolfBooks.models;

public class SectionModel {

    private int sectionId;
    private int textbookId;
    private int chapterId;

    private String sectionTitle;
    private int sectionNumber;
    private boolean isHidden;
    private String createdBy;

    public SectionModel(int sectionId, int textbookId, int chapterId, String sectionTitle, int sectionNumber, boolean isHidden, String createdBy) {
        setSectionId(sectionId);
        setTextbookId(textbookId);
        setChapterId(chapterId);
        setSectionTitle(sectionTitle);
        setSectionNumber(sectionNumber);
        setHidden(isHidden);
        setCreatedBy(createdBy);
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(int textbookId) {
        this.textbookId = textbookId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
