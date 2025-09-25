package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.io.DataService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;
import edu.ccrm.domain.Enrollment;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileMenu implements Menu {

    private final DataService dataService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final Scanner scanner;
    private final AppConfig appConfig;

    public FileMenu(DataService dataService, StudentService studentService, CourseService courseService, EnrollmentService enrollmentService, Scanner scanner, AppConfig appConfig) {
        this.dataService = dataService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.scanner = scanner;
        this.appConfig = appConfig;
    }

    public void handle() {
        while (true) {
            printFileMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    importStudents();
                    break;
                case 2:
                    importCourses();
                    break;
                case 3:
                    exportStudents();
                    break;
                case 4:
                    exportCourses();
                    break;
                case 5:
                    exportEnrollments();
                    break;
                case 6:
                    backupData();
                    break;
                case 7:
                    getBackupDirectorySize();
                    break;
                case 0:
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printFileMenu() {
        System.out.println("\n--- File Operations ---");
        System.out.println("1. Import Students from CSV");
        System.out.println("2. Import Courses from CSV");
        System.out.println("3. Export Students to CSV");
        System.out.println("4. Export Courses to CSV");
        System.out.println("5. Export Enrollments to CSV");
        System.out.println("6. Backup Data");
        System.out.println("7. Get Backup Directory Size");
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

    private void importStudents() {
        System.out.print("Enter path to students CSV file (default: " + appConfig.getDataFolderPath().resolve("students.csv") + "): ");
        String pathStr = scanner.nextLine();
        Path path = pathStr.isBlank() ? appConfig.getDataFolderPath().resolve("students.csv") : Paths.get(pathStr);
        try {
            dataService.importStudents(path);
            System.out.println("Students imported successfully.");
        } catch (IOException e) {
            System.out.println("Error importing students: " + e.getMessage());
        }
    }

    private void importCourses() {
        System.out.print("Enter path to courses CSV file (default: " + appConfig.getDataFolderPath().resolve("courses.csv") + "): ");
        String pathStr = scanner.nextLine();
        Path path = pathStr.isBlank() ? appConfig.getDataFolderPath().resolve("courses.csv") : Paths.get(pathStr);
        try {
            dataService.importCourses(path);
            System.out.println("Courses imported successfully.");
        } catch (IOException e) {
            System.out.println("Error importing courses: " + e.getMessage());
        }
    }

    private void exportStudents() {
        System.out.print("Enter path to export students CSV file (default: " + appConfig.getDataFolderPath().resolve("students.csv") + "): ");
        String pathStr = scanner.nextLine();
        Path path = pathStr.isBlank() ? appConfig.getDataFolderPath().resolve("students.csv") : Paths.get(pathStr);
        try {
            dataService.exportStudents(path, studentService.getAllStudents());
            System.out.println("Students exported successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting students: " + e.getMessage());
        }
    }

    private void exportCourses() {
        System.out.print("Enter path to export courses CSV file (default: " + appConfig.getDataFolderPath().resolve("courses.csv") + "): ");
        String pathStr = scanner.nextLine();
        Path path = pathStr.isBlank() ? appConfig.getDataFolderPath().resolve("courses.csv") : Paths.get(pathStr);
        try {
            dataService.exportCourses(path, courseService.getAllCourses());
            System.out.println("Courses exported successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting courses: " + e.getMessage());
        }
    }

    private void exportEnrollments() {
        System.out.print("Enter path to export enrollments CSV file (default: " + appConfig.getDataFolderPath().resolve("enrollments.csv") + "): ");
        String pathStr = scanner.nextLine();
        Path path = pathStr.isBlank() ? appConfig.getDataFolderPath().resolve("enrollments.csv") : Paths.get(pathStr);
        try {
            List<Enrollment> allEnrollments = studentService.getAllStudents().stream()
                    .flatMap(student -> enrollmentService.getEnrollmentsByStudent(student).stream())
                    .collect(Collectors.toList());
            dataService.exportEnrollments(path, allEnrollments);
            System.out.println("Enrollments exported successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting enrollments: " + e.getMessage());
        }
    }

    private void backupData() {
        System.out.print("Enter source directory path (default: " + appConfig.getDataFolderPath() + "): ");
        String sourceDirStr = scanner.nextLine();
        Path sourceDir = sourceDirStr.isBlank() ? appConfig.getDataFolderPath() : Paths.get(sourceDirStr);

        System.out.print("Enter backup directory path (default: " + appConfig.getBackupFolderPath() + "): ");
        String backupDirStr = scanner.nextLine();
        Path backupDir = backupDirStr.isBlank() ? appConfig.getBackupFolderPath() : Paths.get(backupDirStr);

        try {
            dataService.backupData(sourceDir, backupDir);
            System.out.println("Data backed up successfully.");
        } catch (IOException e) {
            System.out.println("Error backing up data: " + e.getMessage());
        }
    }

    private void getBackupDirectorySize() {
        System.out.print("Enter directory path (default: " + appConfig.getBackupFolderPath() + "): ");
        String pathStr = scanner.nextLine();
        Path path = pathStr.isBlank() ? appConfig.getBackupFolderPath() : Paths.get(pathStr);
        try {
            long size = dataService.getDirectorySize(path);
            System.out.println("Directory size: " + size + " bytes");
        } catch (IOException e) {
            System.out.println("Error getting directory size: " + e.getMessage());
        }
    }
}
