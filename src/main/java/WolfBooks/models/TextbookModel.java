package src.main.java.WolfBooks.models;

public class TextbookModel {

    private String textbookID;
    private String textbookName;

    public TextbookModel(String textbookID, String textbookName) {
        setTextbookID(textbookID);
        setTextbookName(textbookName);
    }

    public String getTextbookID() {
        return textbookID;
    }

    public String getTextbookTitle() {
        return textbookName;
    }

    public void setTextbookID(String textbookID) {
        this.textbookID = textbookID;
    }

    public String getTextbookName() {
        return textbookName;
    }

    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName;
    }
}
