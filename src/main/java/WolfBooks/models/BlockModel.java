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
    private int sequenceNumber;  // Added for sequence management

    // Original constructor
    public BlockModel(String sectionId, String textbookId, String blockId, String chapterId, 
                     String contentType, String content, boolean isHidden, String createdBy) {
        setSectionId(sectionId);
        setTextbookId(textbookId);
        setBlockId(blockId);
        setChapterId(chapterId);
        setContentType(contentType);
        setContent(content);
        setHidden(isHidden);
        setCreatedBy(createdBy);
        this.sequenceNumber = 0;
    }

    // New constructor for creating text/image blocks (used in FacultyService)
    public BlockModel(String sectionId, String contentType, String content, 
                     int sequenceNumber, boolean isHidden, String createdBy) {
        this.sectionId = sectionId;
        this.contentType = contentType;
        this.content = content;
        this.sequenceNumber = sequenceNumber;
        this.isHidden = isHidden;
        this.createdBy = createdBy;
        this.blockId = "BLK" + System.currentTimeMillis(); // Generate unique block ID
    }

    // Existing getters and setters

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        if (sectionId == null || sectionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Section ID cannot be null or empty");
        }
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
        if (blockId == null || blockId.trim().isEmpty()) {
            throw new IllegalArgumentException("Block ID cannot be null or empty");
        }
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
        if (contentType == null || contentType.trim().isEmpty()) {
            throw new IllegalArgumentException("Content type cannot be null or empty");
        }
        if (!contentType.equals("Text") && !contentType.equals("Image") && !contentType.equals("Activity")) {
            throw new IllegalArgumentException("Invalid content type. Must be Text, Image, or Activity");
        }
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
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
        if (createdBy == null || createdBy.trim().isEmpty()) {
            throw new IllegalArgumentException("Created by cannot be null or empty");
        }
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "BlockModel{" +
                "blockId='" + blockId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content='" + (content != null && content.length() > 50 ? 
                               content.substring(0, 47) + "..." : content) + '\'' +
                ", isHidden=" + isHidden +
                ", sequenceNumber=" + sequenceNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockModel that = (BlockModel) o;
        return blockId != null ? blockId.equals(that.blockId) : that.blockId == null;
    }

    @Override
    public int hashCode() {
        return blockId != null ? blockId.hashCode() : 0;
    }
}
