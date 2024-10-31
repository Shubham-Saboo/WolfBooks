package src.main.java.WolfBooks.models;

public class TextbookModel {

    private int textbookID;
    private String textbookName;

    public TextbookModel(int textbookID, String textbookName) {
        setTextbookID(textbookID);
        setTextbookName(textbookName);
    }

    public int getTextbookID() {
        return textbookID;
    }

    public void setTextbookID(int textbookID) {
        this.textbookID = textbookID;
    }

    public String getTextbookName() {
        return textbookName;
    }

    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName;
    }
}
