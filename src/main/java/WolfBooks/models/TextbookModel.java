package src.main.java.WolfBooks.models;

public class TextbookModel {

    private String textbookID;
    private String textbookTitle;

    public TextbookModel(String textbookID, String textbookTitle) {
        setTextbookID(textbookID);
        setTextbookTitle(textbookTitle);
    }

    public String getTextbookID() {
        return textbookID;
    }

    public String getTextbookTitle() {
        return textbookTitle;
    }

    public void setTextbookID(String textbookID) {
        this.textbookID = textbookID;
    }

    public void setTextbookTitle(String textbookTitle) {
        this.textbookTitle = textbookTitle;
    }
}
