// Student.java - Model class representing a Student entity
// This class follows the JavaBean pattern with private fields and public getters/setters
// It is used to pass student data between the UI, service, and database layers

public class Student {
    // Private fields (Encapsulation - OOP concept)
    private int id;            // Unique identifier from the database
    private String name;       // Full name of the student
    private int rollNumber;    // Roll number (unique per student)
    private String department; // Department (e.g., CSE, ECE, ME, CE, EE)
    private int semester;      // Current semester (1-8)
    private String email;      // Email address of the student

    // Default constructor (needed for creating empty Student objects)
    public Student() {
    }

    // Parameterized constructor (used when creating a Student with all details)
    public Student(int id, String name, int rollNumber, String department, int semester, String email) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.semester = semester;
        this.email = email;
    }

    // Constructor without ID (used when adding a new student, ID is auto-generated)
    public Student(String name, int rollNumber, String department, int semester, String email) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.semester = semester;
        this.email = email;
    }

    // Getter and Setter methods (Encapsulation)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString method (useful for debugging and printing student info)
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rollNumber=" + rollNumber +
                ", department='" + department + '\'' +
                ", semester=" + semester +
                ", email='" + email + '\'' +
                '}';
    }
}
