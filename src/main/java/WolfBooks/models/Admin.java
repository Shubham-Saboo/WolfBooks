package src.main.java.WolfBooks.models;

public class Admin extends User {

        public Admin(String username) {
            super(username, "admin");
        }

        @Override
        public String[] getOptions() {
            return null;
        }
}
