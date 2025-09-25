package edu.ccrm.cli;

import edu.ccrm.domain.Name;
import edu.ccrm.domain.Student;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class StudentMenu implements Menu {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final Scanner scanner;

    public StudentMenu(StudentService studentService, EnrollmentService enrollmentService, Scanner scanner) {
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
        this.scanner = scanner;
    }

    public void handle() {
        while (true) {
            printStudentMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    listStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deactivateStudent();
                    break;
                case 5:
                    generateAndPrintTranscript();
                    break;
                case 0:
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printStudentMenu() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Update Student");
        System.out.println("4. Deactivate Student");
        System.out.println("5. Generate Student Transcript");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }

    private void addStudent() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine();

        Name name = new Name(firstName, lastName);
        Student student = new Student(name, email, regNo);
        studentService.addStudent(student);

        System.out.println("Student added successfully!");
        printStudentProfile(student);
    }

    private void listStudents() {
        System.out.println("\n--- List of All Students ---");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(this::printStudentProfile);
        }
    }

    private void updateStudent() {
        System.out.println("\n--- Update Student ---");
        System.out.print("Enter Student ID to update: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            studentService.getStudentById(id).ifPresentOrElse(student -> {
                System.out.print("Enter new Email (or press Enter to keep current: " + student.getEmail() + "): ");
                String email = scanner.nextLine();
                if (!email.isBlank()) {
                    student.setEmail(email);
                }
                // Add more fields to update as needed
                studentService.updateStudent(student);
                System.out.println("Student updated successfully!");
            }, () -> System.out.println("Student not found with ID: " + id));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
        }
    }

    private void deactivateStudent() {
        System.out.println("\n--- Deactivate Student ---");
        System.out.print("Enter Student ID to deactivate: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            studentService.getStudentById(id).ifPresentOrElse(student -> {
                studentService.deactivateStudent(id);
                System.out.println("Student deactivated successfully.");
            }, () -> System.out.println("Student not found with ID: " + id));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
        }
    }

    private void printStudentProfile(Student student) {
        System.out.println("----------------------------------------");
        System.out.println("Student ID: " + student.getId());
        System.out.println("Registration No: " + student.getRegNo());
        System.out.println("Full Name: " + student.getFullName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Status: " + student.getStatus());
        System.out.println("Created At: " + student.getCreatedAt());
        System.out.println("Last Updated At: " + student.getUpdatedAt());
        System.out.println("----------------------------------------");
    }

    private void generateAndPrintTranscript() {
        System.out.println("\n--- Generate Student Transcript ---");
        System.out.print("Enter Student ID: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            studentService.getStudentById(id).ifPresentOrElse(student -> {
                                List<edu.ccrm.domain.Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student);
                Student.Transcript transcriptGenerator = student.new Transcript();
                String transcript = transcriptGenerator.generate(enrollments);
                System.out.println(transcript);
            }, () -> System.out.println("Student not found with ID: " + id));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
        }
    }
}
