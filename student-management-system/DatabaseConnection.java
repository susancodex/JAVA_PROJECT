// DatabaseConnection.java - Handles SQLite database connection using JDBC
// SQLite stores data in a local file (students.db), no server installation needed
// JDBC (Java Database Connectivity) is the standard Java API for database access

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    // SQLite database file URL - the database is stored as a local file
    // jdbc:sqlite:students.db means the file "students.db" will be created in the project folder
    private static final String DB_URL = "jdbc:sqlite:students.db";

    // Method to get a connection to the SQLite database
    // Returns a Connection object that can be used to execute SQL queries
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Initialize the database - create the students table if it doesn't exist
    // This method is called once when the application starts
    public static void initialize() {
        // SQL statement to create the students table
        // IF NOT EXISTS ensures we don't get an error if the table already exists
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // Auto-incrementing primary key
                "name TEXT NOT NULL, " +                    // Student name (required)
                "roll_number INTEGER NOT NULL UNIQUE, " +   // Roll number (required, must be unique)
                "department TEXT NOT NULL, " +               // Department (required)
                "semester INTEGER NOT NULL, " +              // Semester (required)
                "email TEXT NOT NULL" +                      // Email (required)
                ")";

        // try-with-resources: automatically closes Connection and Statement when done
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL); // Execute the CREATE TABLE statement
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
