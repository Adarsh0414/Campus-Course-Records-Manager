package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.io.DataService;
import edu.ccrm.io.DataServiceImpl;
import edu.ccrm.service.*;
import edu.ccrm.util.LoopDemonstrator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CcrnCli {

    private static final AppConfig appConfig = AppConfig.getInstance();
    private static final StudentService studentService = new StudentServiceImpl();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final InstructorService instructorService = new InstructorServiceImpl();
    private static final EnrollmentService enrollmentService = new EnrollmentServiceImpl(studentService);
    private static final DataService dataService = new DataServiceImpl(studentService, courseService, instructorService);
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, Menu> menus = new HashMap<>();

    public static void main(String[] args) {
        printPlatformNote();

        // Anonymous inner class demonstration
        Runnable welcomeMessagePrinter = new Runnable() {
            @Override
            public void run() {
                System.out.println("\nInitializing CCRM application...");
            }
        };
        welcomeMessagePrinter.run();


        System.out.println("Loading configuration...");
        System.out.println("Data folder: " + appConfig.getDataFolderPath());
        System.out.println("Backup folder: " + appConfig.getBackupFolderPath());

        // Initialize menus
        menus.put(1, new StudentMenu(studentService, enrollmentService, scanner));
        menus.put(2, new CourseMenu(courseService, instructorService, scanner));
        menus.put(3, new EnrollmentMenu(studentService, courseService, enrollmentService, scanner));
        menus.put(4, new FileMenu(dataService, studentService, courseService, enrollmentService, scanner, appConfig));
        menus.put(5, new ReportsMenu(enrollmentService, scanner));


        System.out.println("\nWelcome to the Campus Course & Records Manager (CCRM)");

        while (true) {
            printMainMenu();
            int choice = getUserChoice();

            if (choice == 0) {
                System.out.println("Exiting application. Goodbye!");
                break;
            }

            Menu menu = menus.get(choice);
            if (menu != null) {
                menu.handle();
            } else if (choice == 6) {
                LoopDemonstrator.demonstrateLoops();
                LoopDemonstrator.demonstrateArrayUtilities();
                LoopDemonstrator.demonstrateOperatorPrecedence();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollment & Grading");
        System.out.println("4. File Operations");
        System.out.println("5. Reports");
        System.out.println("6. Demonstrations");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }

    private static void printPlatformNote() {
        System.out.println("----------------------------------------------------\n");
        System.out.println("Java Platform Summary:");
        System.out.println("- Java SE (Standard Edition): For desktop and server applications.");
        System.out.println("- Java ME (Micro Edition): For mobile and embedded devices.");
        System.out.println("- Java EE (Enterprise Edition): For large-scale, distributed applications.");
        System.out.println("----------------------------------------------------\n");
    }
}
