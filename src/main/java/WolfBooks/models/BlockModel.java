package src.main.java.WolfBooks.models;

public class BlockModel {

    private String textbookId;
    private String chapterId;
    private String sectionId;
    private String blockId;
    private String contentType;
    private String content;
    private boolean isHidden;
    private String createdBy;

    public BlockModel( String textbookId, String chapterId, String sectionId, String blockId, String contentType, String content, boolean isHidden, String createdBy) {

        setTextbookId(textbookId);
        setChapterId(chapterId);
        setSectionId(sectionId);
        setBlockId(blockId);
        setContentType(contentType);
        setContent(content);
        setHidden(isHidden);
        setCreatedBy(createdBy);
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(String textbookId) {
        this.textbookId = textbookId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
