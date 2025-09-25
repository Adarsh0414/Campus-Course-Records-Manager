package edu.ccrm.cli;

import edu.ccrm.domain.Student;
import edu.ccrm.service.EnrollmentService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReportsMenu implements Menu {

    private final EnrollmentService enrollmentService;
    private final Scanner scanner;

    public ReportsMenu(EnrollmentService enrollmentService, Scanner scanner) {
        this.enrollmentService = enrollmentService;
        this.scanner = scanner;
    }

    public void handle() {
        while (true) {
            printReportsMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    showTopStudents();
                    break;
                case 2:
                    showGpaDistribution();
                    break;
                case 0:
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printReportsMenu() {
        System.out.println("\n--- Reports ---");
        System.out.println("1. Show Top Students");
        System.out.println("2. Show GPA Distribution");
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

    private void showTopStudents() {
        System.out.print("Enter number of top students to show: ");
        int count = Integer.parseInt(scanner.nextLine());
        List<Student> topStudents = enrollmentService.getTopStudents(count);
        System.out.println("\n--- Top " + count + " Students ---");
        topStudents.forEach(student -> {
            System.out.println(student.getFullName() + " - GPA: " + enrollmentService.calculateGpa(student));
        });
    }

    private void showGpaDistribution() {
        Map<String, Long> gpaDistribution = enrollmentService.getGpaDistribution();
        System.out.println("\n--- GPA Distribution ---");
        gpaDistribution.forEach((range, count) -> {
            System.out.println(range + ": " + count + " students");
        });
    }
}
