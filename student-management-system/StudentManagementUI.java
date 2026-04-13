// StudentManagementUI.java - Main GUI class using Java Swing
// This class creates the entire user interface and handles all events
// It demonstrates 5 different types of Swing event listeners:
// 1. ActionListener - for button clicks and combo box selections
// 2. MouseListener - for table row clicks
// 3. KeyListener - for keyboard input in search field
// 4. ItemListener - for combo box item state changes
// 5. WindowListener - for window open/close events

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementUI extends JFrame {

    // Service object to perform database operations
    private StudentService studentService;

    // Input fields for student data
    private JTextField nameField, rollNumberField, emailField, searchField;
    private JComboBox<String> departmentCombo, semesterCombo;

    // Table to display student records
    private JTable studentTable;
    private DefaultTableModel tableModel;

    // Buttons for CRUD operations
    private JButton addButton, updateButton, deleteButton, clearButton;

    // Filter and sort combo boxes
    private JComboBox<String> filterDeptCombo, filterSemCombo, sortCombo;

    // Stores the currently selected student's ID (for update/delete)
    private int selectedStudentId = -1;

    // Constructor - sets up the entire GUI
    public StudentManagementUI() {
        studentService = new StudentService(); // Initialize the service layer
        initializeUI(); // Build the user interface
        loadStudentData(); // Load existing data from database into the table
    }

    // Method to initialize all UI components
    private void initializeUI() {
        // ===== Window Setup =====
        setTitle("Student Management System"); // Window title
        setSize(950, 650); // Window dimensions
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // We handle closing manually
        setLocationRelativeTo(null); // Center the window on screen
        setLayout(new BorderLayout(10, 10)); // Main layout with gaps

        // ===== EVENT 5: WindowListener =====
        // Handles window events like opening and closing
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                // Show a welcome message when the application starts
                JOptionPane.showMessageDialog(StudentManagementUI.this,
                        "Welcome to Student Management System!\nUse the form to add, update, or delete students.",
                        "Welcome", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                // Ask for confirmation before closing the application
                int choice = JOptionPane.showConfirmDialog(StudentManagementUI.this,
                        "Are you sure you want to exit?", "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0); // Exit the application
                }
            }

            // These methods are required by WindowListener but not used here
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        // ===== Title Panel (Top) =====
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(44, 62, 80)); // Dark blue background
        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH); // Add to the top of the window

        // ===== Input Form Panel (Left Side) =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // GridBagLayout for flexible form layout
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));
        formPanel.setPreferredSize(new Dimension(320, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around each component
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        // Roll Number field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Roll Number:"), gbc);
        gbc.gridx = 1;
        rollNumberField = new JTextField(15);
        formPanel.add(rollNumberField, gbc);

        // Department dropdown
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        String[] departments = {"CSE", "ECE", "ME", "CE", "EE"};
        departmentCombo = new JComboBox<>(departments);
        formPanel.add(departmentCombo, gbc);

        // ===== EVENT 4: ItemListener =====
        // Triggered when a combo box item selection changes
        departmentCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Only respond to SELECTED events (not DESELECTED)
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Department selected: " + e.getItem());
                }
            }
        });

        // Semester dropdown
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Semester:"), gbc);
        gbc.gridx = 1;
        String[] semesters = {"1", "2", "3", "4", "5", "6", "7", "8"};
        semesterCombo = new JComboBox<>(semesters);
        formPanel.add(semesterCombo, gbc);

        // Email field
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        formPanel.add(emailField, gbc);

        // ===== Buttons Panel =====
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        addButton = new JButton("Add Student");
        updateButton = new JButton("Update Student");
        deleteButton = new JButton("Delete Student");
        clearButton = new JButton("Clear Form");

        // Style the buttons with colors
        addButton.setBackground(new Color(39, 174, 96));
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(41, 128, 185));
        updateButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(149, 165, 166));
        clearButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // ===== Search Field =====
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Search by Name (type to search):"), gbc);
        gbc.gridy = 7;
        searchField = new JTextField(15);
        formPanel.add(searchField, gbc);

        // ===== EVENT 3: KeyListener =====
        // Triggered when the user types in the search field
        // Implements real-time search as the user types
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used but required by KeyListener interface
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Not used but required by KeyListener interface
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Called after a key is released - we search here for real-time filtering
                String searchText = searchField.getText().trim().toLowerCase();
                filterTableByName(searchText);
            }
        });

        add(formPanel, BorderLayout.WEST); // Add form to the left side

        // ===== Table Panel (Center) =====
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Student Records"));

        // Create table with column headers (non-editable)
        String[] columns = {"ID", "Name", "Roll No", "Department", "Semester", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one row selection
        studentTable.setRowHeight(25); // Set row height for better readability
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // ===== EVENT 2: MouseListener =====
        // When user clicks on a table row, populate the form fields for editing
        studentTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Get values from the selected row and fill the form
                    selectedStudentId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    rollNumberField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    departmentCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
                    semesterCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
                    emailField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                }
            }

            // These methods are required by MouseListener but not used here
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });

        JScrollPane scrollPane = new JScrollPane(studentTable); // Add scrollbar to the table
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // ===== Filter and Sort Panel (below the table) =====
        JPanel filterSortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterSortPanel.setBorder(BorderFactory.createTitledBorder("Filter & Sort"));

        // Filter by Department
        filterSortPanel.add(new JLabel("Filter Dept:"));
        String[] filterDepts = {"All", "CSE", "ECE", "ME", "CE", "EE"};
        filterDeptCombo = new JComboBox<>(filterDepts);
        filterSortPanel.add(filterDeptCombo);

        // Filter by Semester
        filterSortPanel.add(new JLabel("Filter Sem:"));
        String[] filterSems = {"All", "1", "2", "3", "4", "5", "6", "7", "8"};
        filterSemCombo = new JComboBox<>(filterSems);
        filterSortPanel.add(filterSemCombo);

        // Sort options
        filterSortPanel.add(new JLabel("Sort By:"));
        String[] sortOptions = {"Default", "Name", "Roll Number"};
        sortCombo = new JComboBox<>(sortOptions);
        filterSortPanel.add(sortCombo);

        // Apply Filter/Sort button
        JButton applyFilterButton = new JButton("Apply");
        applyFilterButton.setBackground(new Color(155, 89, 182));
        applyFilterButton.setForeground(Color.WHITE);
        filterSortPanel.add(applyFilterButton);

        // Reset button to clear all filters
        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(52, 73, 94));
        resetButton.setForeground(Color.WHITE);
        filterSortPanel.add(resetButton);

        tablePanel.add(filterSortPanel, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.CENTER); // Add table to the center

        // ===== EVENT 1: ActionListener =====
        // Handles button click events

        // Add Student button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        // Update Student button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        // Delete Student button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        // Clear Form button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Apply Filter/Sort button
        applyFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilterAndSort();
            }
        });

        // Reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterDeptCombo.setSelectedIndex(0);
                filterSemCombo.setSelectedIndex(0);
                sortCombo.setSelectedIndex(0);
                searchField.setText("");
                loadStudentData(); // Reload all data
            }
        });

        // Make the window visible
        setVisible(true);
    }

    // ===== CRUD Operation Methods =====

    // Add a new student to the database
    private void addStudent() {
        // Validate input fields
        if (!validateInput()) return;

        // Create a new Student object from form data
        Student student = new Student(
                nameField.getText().trim(),
                Integer.parseInt(rollNumberField.getText().trim()),
                departmentCombo.getSelectedItem().toString(),
                Integer.parseInt(semesterCombo.getSelectedItem().toString()),
                emailField.getText().trim()
        );

        // Call the service to add the student
        if (studentService.addStudent(student)) {
            JOptionPane.showMessageDialog(this, "Student added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadStudentData(); // Refresh the table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add student. Roll number may already exist.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update an existing student's details
    private void updateStudent() {
        // Check if a student is selected
        if (selectedStudentId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateInput()) return;

        // Create updated Student object
        Student student = new Student(
                selectedStudentId,
                nameField.getText().trim(),
                Integer.parseInt(rollNumberField.getText().trim()),
                departmentCombo.getSelectedItem().toString(),
                Integer.parseInt(semesterCombo.getSelectedItem().toString()),
                emailField.getText().trim()
        );

        // Call the service to update
        if (studentService.updateStudent(student)) {
            JOptionPane.showMessageDialog(this, "Student updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadStudentData();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update student.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete a student from the database
    private void deleteStudent() {
        if (selectedStudentId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirm deletion with the user
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this student?",
                "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            if (studentService.deleteStudent(selectedStudentId)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===== Helper Methods =====

    // Validate form input fields
    private boolean validateInput() {
        String name = nameField.getText().trim();
        String rollStr = rollNumberField.getText().trim();
        String email = emailField.getText().trim();

        // Check for empty fields
        if (name.isEmpty() || rollStr.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Check if roll number is a valid integer
        try {
            Integer.parseInt(rollStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Roll Number must be a valid number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Basic email validation
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // Clear all form fields and reset selection
    private void clearForm() {
        nameField.setText("");
        rollNumberField.setText("");
        emailField.setText("");
        departmentCombo.setSelectedIndex(0);
        semesterCombo.setSelectedIndex(0);
        selectedStudentId = -1; // Reset selected student
        studentTable.clearSelection(); // Deselect table row
    }

    // Load all students from database into the table
    private void loadStudentData() {
        List<Student> students = studentService.getAllStudents();
        populateTable(students);
    }

    // Populate the JTable with a list of students
    private void populateTable(List<Student> students) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Student s : students) {
            Object[] row = {
                    s.getId(),
                    s.getName(),
                    s.getRollNumber(),
                    s.getDepartment(),
                    s.getSemester(),
                    s.getEmail()
            };
            tableModel.addRow(row); // Add each student as a row
        }
    }

    // Filter the table by name (real-time search)
    private void filterTableByName(String searchText) {
        List<Student> allStudents = studentService.getAllStudents();
        tableModel.setRowCount(0);

        for (Student s : allStudents) {
            // Check if the student's name contains the search text (case-insensitive)
            if (s.getName().toLowerCase().contains(searchText)) {
                Object[] row = {
                        s.getId(), s.getName(), s.getRollNumber(),
                        s.getDepartment(), s.getSemester(), s.getEmail()
                };
                tableModel.addRow(row);
            }
        }
    }

    // Apply filter and sort based on combo box selections
    private void applyFilterAndSort() {
        String selectedDept = filterDeptCombo.getSelectedItem().toString();
        String selectedSem = filterSemCombo.getSelectedItem().toString();
        String selectedSort = sortCombo.getSelectedItem().toString();

        List<Student> students;

        // Apply sorting first
        if (selectedSort.equals("Name")) {
            students = studentService.getStudentsSortedByName();
        } else if (selectedSort.equals("Roll Number")) {
            students = studentService.getStudentsSortedByRollNumber();
        } else {
            students = studentService.getAllStudents();
        }

        // Apply filters
        tableModel.setRowCount(0);
        for (Student s : students) {
            boolean matchesDept = selectedDept.equals("All") || s.getDepartment().equals(selectedDept);
            boolean matchesSem = selectedSem.equals("All") || s.getSemester() == Integer.parseInt(selectedSem);

            if (matchesDept && matchesSem) {
                Object[] row = {
                        s.getId(), s.getName(), s.getRollNumber(),
                        s.getDepartment(), s.getSemester(), s.getEmail()
                };
                tableModel.addRow(row);
            }
        }
    }
}
