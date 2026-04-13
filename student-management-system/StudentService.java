// StudentService.java - Service/DAO layer for CRUD operations on the Student table
// This class contains all the database operations (Create, Read, Update, Delete)
// It uses JDBC PreparedStatement to prevent SQL injection attacks

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    // CREATE - Add a new student to the database
    // Returns true if the student was added successfully, false otherwise
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, roll_number, department, semester, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the values for each placeholder (?) in the SQL statement
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getRollNumber());
            pstmt.setString(3, student.getDepartment());
            pstmt.setInt(4, student.getSemester());
            pstmt.setString(5, student.getEmail());

            int rowsAffected = pstmt.executeUpdate(); // Execute the INSERT
            return rowsAffected > 0; // Return true if at least one row was inserted

        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // READ - Get all students from the database
    // Returns a List of Student objects
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Iterate through each row in the result set
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("roll_number"),
                        rs.getString("department"),
                        rs.getInt("semester"),
                        rs.getString("email")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }

        return students;
    }

    // UPDATE - Update an existing student's details
    // Uses the student's ID to find and update the record
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, roll_number = ?, department = ?, semester = ?, email = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getRollNumber());
            pstmt.setString(3, student.getDepartment());
            pstmt.setInt(4, student.getSemester());
            pstmt.setString(5, student.getEmail());
            pstmt.setInt(6, student.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // DELETE - Remove a student from the database by ID
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    // FILTER - Get students filtered by department
    public List<Student> getStudentsByDepartment(String department) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE department = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("roll_number"),
                        rs.getString("department"),
                        rs.getInt("semester"),
                        rs.getString("email")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error filtering by department: " + e.getMessage());
        }

        return students;
    }

    // FILTER - Get students filtered by semester
    public List<Student> getStudentsBySemester(int semester) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE semester = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, semester);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("roll_number"),
                        rs.getString("department"),
                        rs.getInt("semester"),
                        rs.getString("email")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error filtering by semester: " + e.getMessage());
        }

        return students;
    }

    // SORT - Get all students sorted by name (alphabetical order)
    public List<Student> getStudentsSortedByName() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY name ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("roll_number"),
                        rs.getString("department"),
                        rs.getInt("semester"),
                        rs.getString("email")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error sorting by name: " + e.getMessage());
        }

        return students;
    }

    // SORT - Get all students sorted by roll number
    public List<Student> getStudentsSortedByRollNumber() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY roll_number ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("roll_number"),
                        rs.getString("department"),
                        rs.getInt("semester"),
                        rs.getString("email")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error sorting by roll number: " + e.getMessage());
        }

        return students;
    }
}
