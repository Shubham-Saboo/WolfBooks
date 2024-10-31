package src.main.java.WolfBooks.models;

public class StudentModel extends UserModel {


    // Maybe keep a list of textbooks?
    public StudentModel(String username) {
        super(username, "student");
    }
}
