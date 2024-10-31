package src.main.java.WolfBooks.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserModel {
    private static final Set<String> VALID_ROLES = new HashSet<>(
            Arrays.asList("student", "faculty", "teaching_assistant", "admin")
    );

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private boolean firstLogin;

    public UserModel(String userId, String firstName, String lastName,
                     String email, String password, String role,
                     boolean firstLogin) {
        setUserId(userId);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        setRole(role);
        setFirstLogin(firstLogin);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (userId.length() > 50) {
            throw new IllegalArgumentException("User ID cannot exceed 50 characters");
        }
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (firstName.length() > 100) {
            throw new IllegalArgumentException("First name cannot exceed 100 characters");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (lastName.length() > 100) {
            throw new IllegalArgumentException("Last name cannot exceed 100 characters");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (email.length() > 255) {
            throw new IllegalArgumentException("Email cannot exceed 255 characters");
        }
        // Basic email validation
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (password.length() > 255) {
            throw new IllegalArgumentException("Password cannot exceed 255 characters");
        }
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        if (!VALID_ROLES.contains(role.toLowerCase())) {
            throw new IllegalArgumentException("Invalid role. Must be one of: "
                    + String.join(", ", VALID_ROLES));
        }
        this.role = role.toLowerCase();
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", firstLogin=" + firstLogin +
                '}';
    }
}