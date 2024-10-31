package src.main.java.WolfBooks.models;

public class ChapterModel {

    private int chapterId;
    private String chapterTitle;
    private int textbookId;
    private boolean isHidden;
    private String createdBy;

    public ChapterModel(int chapterId, String chapterTitle, int textbookId, boolean isHidden, String createdBy) {
        setChapterId(chapterId);
        setChapterTitle(chapterTitle);
        setTextbookId(textbookId);
        setHidden(isHidden);
        setCreatedBy(createdBy);
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public int getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(int textbookId) {
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
