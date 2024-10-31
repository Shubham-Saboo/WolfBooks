package src.main.java.WolfBooks.models;

public class AdminModel extends UserModel {

        public AdminModel(String username) {
            super(username, "admin");
        }

        @Override
        public String[] getOptions() {
            return null;
        }

        public void insertTextbook(String textbookName) {

        }

        public void insertChapter() {

        }

        public void insertSection() {

        }

        public void insertBlock() {

        }
}
