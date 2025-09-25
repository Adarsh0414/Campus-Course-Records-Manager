package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class EnrollmentMenu implements Menu {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final Scanner scanner;

    public EnrollmentMenu(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService, Scanner scanner) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.scanner = scanner;
    }

    public void handle() {
        while (true) {
            printEnrollmentMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    enrollStudent();
                    break;
                case 2:
                    unenrollStudent();
                    break;
                case 3:
                    recordGrade();
                    break;
                case 4:
                    printTranscript();
                    break;
                case 5:
                    calculateGpa();
                    break;
                case 0:
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printEnrollmentMenu() {
        System.out.println("\n--- Enrollment & Grading ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Unenroll Student from Course");
        System.out.println("3. Record Grade");
        System.out.println("4. Print Student Transcript");
        System.out.println("5. Calculate Student GPA");
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

    private void enrollStudent() {
        System.out.println("\n--- Enroll Student in Course ---");
        System.out.print("Enter Student ID: ");
        UUID studentId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        studentService.getStudentById(studentId).ifPresentOrElse(student -> {
            courseService.getCourseByCode(courseCode).ifPresentOrElse(course -> {
                try {
                    enrollmentService.enrollStudent(student, course);
                    System.out.println("Student enrolled successfully.");
                } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
            }, () -> System.out.println("Course not found with code: " + courseCode));
        }, () -> System.out.println("Student not found with ID: " + studentId));
    }

    private void unenrollStudent() {
        System.out.println("\n--- Unenroll Student from Course ---");
        System.out.print("Enter Student ID: ");
        UUID studentId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        studentService.getStudentById(studentId).ifPresentOrElse(student -> {
            courseService.getCourseByCode(courseCode).ifPresentOrElse(course -> {
                enrollmentService.unenrollStudent(student, course);
                System.out.println("Student unenrolled successfully.");
            }, () -> System.out.println("Course not found with code: " + courseCode));
        }, () -> System.out.println("Student not found with ID: " + studentId));
    }

    private void recordGrade() {
        System.out.println("\n--- Record Grade ---");
        System.out.print("Enter Student ID: ");
        UUID studentId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        studentService.getStudentById(studentId).ifPresentOrElse(student -> {
            courseService.getCourseByCode(courseCode).ifPresentOrElse(course -> {
                enrollmentService.getEnrollmentsByStudent(student).stream()
                        .filter(e -> e.getCourse().equals(course))
                        .findFirst()
                        .ifPresentOrElse(enrollment -> {
                            System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
                            Grade grade = Grade.valueOf(scanner.nextLine().toUpperCase());
                            enrollmentService.setGrade(enrollment, grade);
                            System.out.println("Grade recorded successfully.");
                        }, () -> System.out.println("Student is not enrolled in this course."));
            }, () -> System.out.println("Course not found with code: " + courseCode));
        }, () -> System.out.println("Student not found with ID: " + studentId));
    }

    private void printTranscript() {
        System.out.println("\n--- Student Transcript ---");
        System.out.print("Enter Student ID: ");
        UUID studentId = UUID.fromString(scanner.nextLine());

        studentService.getStudentById(studentId).ifPresentOrElse(student -> {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student);
            Student.Transcript transcript = student.new Transcript();
            System.out.println(transcript.generate(enrollments));
            System.out.println("GPA: " + enrollmentService.calculateGpa(student));
        }, () -> System.out.println("Student not found with ID: " + studentId));
    }

    private void calculateGpa() {
        System.out.println("\n--- Calculate Student GPA ---");
        System.out.print("Enter Student ID: ");
        UUID studentId = UUID.fromString(scanner.nextLine());

        studentService.getStudentById(studentId).ifPresentOrElse(student -> {
            double gpa = enrollmentService.calculateGpa(student);
            System.out.println("GPA for " + student.getFullName() + " is: " + gpa);
        }, () -> System.out.println("Student not found with ID: " + studentId));
    }
}
