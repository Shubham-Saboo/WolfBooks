package src.main.java.WolfBooks.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeachingAssistantModel extends UserModel {
    private String facultyId;
    private List<String> assignedCourseIds;
    private List<String> accessibleTextbooks;
    private List<String> assignedTextbookIds;

    // Constructor
    public TeachingAssistantModel(String userId,
                                  String firstName,
                                  String lastName,
                                  String email,
                                  String password,
                                  boolean firstLogin,
                                  String facultyId) {
        super(userId, firstName, lastName, email, password, "teaching_assistant", firstLogin);
        validateFacultyId(facultyId);
        this.facultyId = facultyId;
        this.assignedCourseIds = new ArrayList<>();
        this.accessibleTextbooks = new ArrayList<>();
    }

    // Course Assignment Methods
    public void addCourseAssignment(String courseId) {
        validateId(courseId, "Course ID");
        if (!assignedCourseIds.contains(courseId)) {
            assignedCourseIds.add(courseId);
        }
    }




    public void removeCourseAssignment(String courseId) {
        validateId(courseId, "Course ID");
        assignedCourseIds.remove(courseId);
    }

    public boolean isAssignedToCourse(String courseId) {
        validateId(courseId, "Course ID");
        return assignedCourseIds.contains(courseId);
    }

    // Textbook Access Methods
    public void addTextbookAccess(String textbookId) {
        validateId(textbookId, "Textbook ID");
        if (!accessibleTextbooks.contains(textbookId)) {
            accessibleTextbooks.add(textbookId);
        }
    }

    public void removeTextbookAccess(String textbookId) {
        validateId(textbookId, "Textbook ID");
        accessibleTextbooks.remove(textbookId);
    }

    public boolean hasTextbookAccess(String textbookId) {
        validateId(textbookId, "Textbook ID");
        return accessibleTextbooks.contains(textbookId);
    }

    // Permission Methods
    public boolean hasPermissionForCourse(String courseId) {
        return isAssignedToCourse(courseId);
    }

    public List<String> getAssignedTextbooks(String courseId) {
        // This method will be populated by service logic
        //List<String> textbookId= taService.getAssignedTextbooksForCourse(createdBy,courseId);
        return new ArrayList<>(); // Placeholder to be filled by service logic
    }

    // Getters and Setters
    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        validateFacultyId(facultyId);
        this.facultyId = facultyId;
    }

    public List<String> getAssignedCourseIds() {
        return new ArrayList<>(assignedCourseIds);
    }

    public void setAssignedCourseIds(List<String> courseIds) {
        if (courseIds == null) {
            throw new IllegalArgumentException("Course IDs list cannot be null");
        }
        this.assignedCourseIds = new ArrayList<>(courseIds);
    }

    public List<String> getAccessibleTextbooks() {
        return new ArrayList<>(accessibleTextbooks);
    }

    public void setAccessibleTextbooks(List<String> textbookIds) {
        if (textbookIds == null) {
            throw new IllegalArgumentException("Textbook IDs list cannot be null");
        }
        this.accessibleTextbooks = new ArrayList<>(textbookIds);
    }

    // Validation Methods
    private void validateFacultyId(String facultyId) {
        if (facultyId == null || facultyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Faculty ID cannot be null or empty");
        }
    }

    private void validateId(String id, String fieldName) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    @Override
    public void setRole(String role) {
        if (!"teaching_assistant".equals(role.toLowerCase())) {
            throw new IllegalArgumentException("Role must be 'teaching_assistant'");
        }
        super.setRole(role);
    }

    // Object Methods
    @Override
    public String toString() {
        return "TeachingAssistantModel{" +
                "userId='" + getUserId() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + getRole() + '\'' +
                ", facultyId='" + facultyId + '\'' +
                ", assignedCourses=" + assignedCourseIds +
                ", accessibleTextbooks=" + accessibleTextbooks +
                ", firstLogin=" + isFirstLogin() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeachingAssistantModel)) return false;
        if (!super.equals(o)) return false;
        TeachingAssistantModel that = (TeachingAssistantModel) o;
        return Objects.equals(facultyId, that.facultyId) &&
                Objects.equals(assignedCourseIds, that.assignedCourseIds) &&
                Objects.equals(accessibleTextbooks, that.accessibleTextbooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), facultyId, assignedCourseIds, accessibleTextbooks);
    }
}