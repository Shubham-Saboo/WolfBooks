package src.main.java.WolfBooks.helpers;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class initializeDatabase {

    public void createSchema(final Connection conn) {
        String schemaPath = "../../resources/create_schema.sql";

    }

    public void runSQLFile(final String path, final Connection conn) {
        String sql = new String(Files.readAllBytes(Paths.get(sqlFile)));
    }
}
