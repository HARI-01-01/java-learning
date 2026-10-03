# Student Management System (Java)

A robust, console-based **Student Management System** implemented in Java. This application provides a complete CRUD and data persistence workflow for managing student records.

## Features

- **Add Student**: Add student records with validation for name, positive roll number, and marks (0-100).
- **List All Students**: View all registered students in the database.
- **Search by Roll Number**: Quickly find student details by their roll number.
- **Delete Student**: Remove a student record by roll number.
- **Update Marks**: Update student marks dynamically.
- **File Persistence**: Save and load student records automatically using `student.txt`.
- **Analytics & Sorting**:
  - View Top 3 Toppers.
  - Calculate Average Marks of all students.
  - List Students sorted alphabetically by name.
- **Robust Error Handling**: Handles invalid input gracefully using custom exceptions and input scanners.

## How to Run

1. Compile the Java file:
   ```bash
   javac Main.java
   ```
2. Run the application:
   ```bash
   java Main
   ```
