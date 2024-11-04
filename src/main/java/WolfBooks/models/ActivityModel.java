package src.main.java.WolfBooks.models;

public class ActivityModel {

    private String activityId;
    private String blockId;
    private String sectionId;
    private String chapterId;
    private String textbookId;
    private boolean isHidden;
    private String createdBy;

    // Constructor
    public ActivityModel(String activityId, String blockId, String sectionId, String chapterId,
                         String textbookId, boolean isHidden, String createdBy) {
        this.activityId = activityId;
        this.blockId = blockId;
        this.sectionId = sectionId;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.isHidden = isHidden;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(String textbookId) {
        this.textbookId = textbookId;
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
