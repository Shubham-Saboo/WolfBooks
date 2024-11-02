package src.main.java.WolfBooks.models;

public class BlockModel {

    private String sectionId;
    private String textbookId;
    private String blockId;
    private String chapterId;
    private String contentType;
    private String content;
    private boolean isHidden;
    private String createdBy;
    private int sequenceNumber;

    public BlockModel(String sectionId, String textbookId, String blockId, String chapterId, String contentType, String content, boolean isHidden, String createdBy, int sequenceNumber) {
        setSectionId(sectionId);
        setTextbookId(textbookId);
        setBlockId(blockId);
        setChapterId(chapterId);
        setContentType(contentType);
        setContent(content);
        setHidden(isHidden);
        setCreatedBy(createdBy);
        setSequenceNumber(sequenceNumber);
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

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
