// Main.java - Entry point of the Student Management System
// This is the starting class that launches the application

public class Main {
    public static void main(String[] args) {
        // Initialize the database (create table if not exists)
        DatabaseConnection.initialize();

        // Launch the GUI on the Event Dispatch Thread (EDT)
        // Swing requires GUI updates to happen on the EDT for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentManagementUI(); // Create and show the main window
            }
        });
    }
}
