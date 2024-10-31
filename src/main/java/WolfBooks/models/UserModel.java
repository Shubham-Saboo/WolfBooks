package src.main.java.WolfBooks.models;

public abstract class UserModel {

    String username;
    String role;
    String[] options;

    public UserModel(String username) {
        setUsername(username);
    }

    public UserModel(String username, String role) {
        this(username);
        setRole(role);
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String[] getOptions() {
        return options;
    }

    public void useOption(int option) {

    }
}
