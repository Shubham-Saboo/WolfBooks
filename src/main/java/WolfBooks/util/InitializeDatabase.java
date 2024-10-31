package src.main.java.WolfBooks.util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * initalizeDatabase is used to create the schema if needed and to populate the tables.
 */
public class InitializeDatabase {

    /**
     * Creates the schema for the database.
     * @param conn The connection to the database.
     */
    public void createSchema(final Connection conn) {
        String schemaPath = "src/main/java/resources/sql/create_schema.sql";
        runSQLFile(schemaPath, conn);
    }

    /**
     * Runs the given path to the SQL file.
     * @param filePath The path to the SQL file.
     * @param conn The connection to the database.
     */
    public void runSQLFile(final String filePath, final Connection conn) {
        try {
            String sqlFile = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] sqlStatements = sqlFile.split(";");

            for (String sqlStatement : sqlStatements) {
                if (!sqlStatement.trim().isEmpty()) {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlStatement);
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in running SQL file: " + e.getMessage());
        }
    }
}
