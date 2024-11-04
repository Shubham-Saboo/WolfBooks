package src.main.java.WolfBooks.models;

import java.util.ArrayList;
import java.util.List;

public class SectionModel {
    private String sectionId;
    private String textbookId;
    private String chapterId;
    private String title;
    private List<BlockModel> blocks;
    private boolean isHidden;
    private String createdBy;

    // Constructor
    public SectionModel(String sectionId, String textbookId, String chapterId, String title, boolean isHidden, String createdBy) {
        this.sectionId = sectionId;
        this.textbookId = textbookId;
        this.chapterId = chapterId;
        this.title = title;
        this.isHidden = isHidden;
        this.createdBy = createdBy;
        this.blocks = new ArrayList<>();
    }

    // Getters and setters
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

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BlockModel> getBlocks() {
        return blocks;
    }

    public void addBlock(BlockModel block) {
        this.blocks.add(block);
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

    // Additional methods
    public void removeBlock(String blockId) {
        blocks.removeIf(block -> block.getBlockId().equals(blockId));
    }

    public BlockModel getBlockById(String blockId) {
        return blocks.stream()
                .filter(block -> block.getBlockId().equals(blockId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "SectionModel{" +
                "sectionId='" + sectionId + '\'' +
                ", textbookId='" + textbookId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", title='" + title + '\'' +
                ", blocksCount=" + blocks.size() +
                ", isHidden=" + isHidden +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}