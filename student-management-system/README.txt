====================================================
   STUDENT MANAGEMENT SYSTEM - Java Swing Project
====================================================

PROJECT STRUCTURE:
------------------
student-management-system/
├── Main.java                  - Entry point (main method)
├── Student.java               - Model class (JavaBean/POJO)
├── DatabaseConnection.java    - JDBC + SQLite connection handler
├── StudentService.java        - Service/DAO layer (CRUD operations)
├── StudentManagementUI.java   - Swing GUI with all event listeners
├── sqlite-jdbc.jar            - SQLite JDBC driver (included)
├── students.db                - SQLite database file (auto-created on first run)
└── README.txt                 - This file

PREREQUISITES:
--------------
- Java JDK 8 or higher installed on your Mac
- Check by running: java -version
- If not installed, download from: https://www.oracle.com/java/technologies/downloads/

HOW TO COMPILE AND RUN:
-----------------------
1. Open Terminal on your Mac
2. Navigate to the project folder:
   cd path/to/student-management-system

3. Compile all Java files (include the SQLite JDBC jar in classpath):
   javac -cp .:sqlite-jdbc.jar *.java

4. Run the application:
   java -cp .:sqlite-jdbc.jar Main

NOTE: On Windows, replace the colon (:) with semicolon (;):
   javac -cp .;sqlite-jdbc.jar *.java
   java -cp .;sqlite-jdbc.jar Main

FEATURES:
---------
1. Add Student - Fill in the form and click "Add Student"
2. View Students - All students are shown in the table automatically
3. Update Student - Click a row in the table, edit the form, click "Update Student"
4. Delete Student - Click a row in the table, click "Delete Student"
5. Filter by Department - Use the "Filter Dept" dropdown and click "Apply"
6. Filter by Semester - Use the "Filter Sem" dropdown and click "Apply"
7. Sort by Name - Select "Name" in "Sort By" dropdown and click "Apply"
8. Sort by Roll Number - Select "Roll Number" in "Sort By" dropdown and click "Apply"
9. Search by Name - Type in the search field for real-time filtering
10. Reset - Click "Reset" to clear all filters

SWING EVENT LISTENERS USED (5 types):
--------------------------------------
1. ActionListener   - Button clicks (Add, Update, Delete, Clear, Apply, Reset)
2. MouseListener    - Table row click to select a student for editing
3. KeyListener      - Real-time search as user types in search field
4. ItemListener     - Department combo box selection change detection
5. WindowListener   - Welcome message on open, exit confirmation on close

OOP CONCEPTS USED:
------------------
1. Encapsulation    - Private fields with public getters/setters in Student.java
2. Abstraction      - StudentService hides database complexity from the UI
3. Separation       - Each class has a single responsibility (SRP)
4. Constructor      - Multiple constructors in Student.java (overloading)

DATABASE:
---------
- Uses SQLite (local file-based database, no server needed)
- Database file (students.db) is automatically created on first run
- Table: students (id, name, roll_number, department, semester, email)
- Uses JDBC PreparedStatement to prevent SQL injection

VIVA TIPS:
----------
- The project follows MVC-like pattern:
  Model = Student.java
  View = StudentManagementUI.java
  Controller = StudentService.java
- JDBC stands for Java Database Connectivity
- SQLite is a serverless, self-contained database engine
- JTable uses DefaultTableModel to manage table data
- JOptionPane is used for dialog boxes (messages, confirmations)
- SwingUtilities.invokeLater() ensures GUI runs on the Event Dispatch Thread
