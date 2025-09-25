package edu.ccrm.cli;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Name;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.InstructorService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CourseMenu implements Menu {

    private final CourseService courseService;
    private final InstructorService instructorService;
    private final Scanner scanner;

    public CourseMenu(CourseService courseService, InstructorService instructorService, Scanner scanner) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.scanner = scanner;
    }

    public void handle() {
        while (true) {
            printCourseMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    listCourses();
                    break;
                case 3:
                    updateCourse();
                    break;
                case 4:
                    assignInstructorToCourse();
                    break;
                case 5:
                    searchCoursesMenu();
                    break;
                case 0:
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printCourseMenu() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add Course");
        System.out.println("2. List Courses");
        System.out.println("3. Update Course");
        System.out.println("4. Assign Instructor to Course");
        System.out.println("5. Search Courses");
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

    private void addCourse() {
        System.out.println("\n--- Add New Course ---");
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Semester (SPRING, SUMMER, FALL, WINTER): ");
        Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());

        Course course = new Course.Builder(code, title)
                .credits(credits)
                .department(department)
                .semester(semester)
                .build();
        courseService.addCourse(course);

        System.out.println("Course added successfully!");
        System.out.println(course);
    }

    private void listCourses() {
        System.out.println("\n--- List of All Courses ---");
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    private void updateCourse() {
        System.out.println("\n--- Update Course ---");
        System.out.print("Enter Course Code to update: ");
        String code = scanner.nextLine();
        courseService.getCourseByCode(code).ifPresentOrElse(course -> {
            System.out.print("Enter new Title (or press Enter to keep current: " + course.getTitle() + "): ");
            String title = scanner.nextLine();
            if (!title.isBlank()) {
                // As Course is immutable, we need to create a new one
                Course updatedCourse = new Course.Builder(course.getCode(), title)
                        .credits(course.getCredits())
                        .department(course.getDepartment())
                        .semester(course.getSemester())
                        .instructor(course.getInstructor())
                        .build();
                courseService.updateCourse(updatedCourse);
                System.out.println("Course updated successfully!");
            }
        }, () -> System.out.println("Course not found with code: " + code));
    }

    private void assignInstructorToCourse() {
        System.out.println("\n--- Assign Instructor to Course ---");
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        courseService.getCourseByCode(courseCode).ifPresentOrElse(course -> {
            System.out.println("--- Select an Instructor ---");
            List<Instructor> instructors = instructorService.getAllInstructors(); // Assumed method
            if (instructors.isEmpty()) {
                System.out.println("No instructors available.");
                return;
            }
            for (int i = 0; i < instructors.size(); i++) {
                System.out.println((i + 1) + ". " + instructors.get(i));
            }
            System.out.print("Enter instructor number: ");
            int instructorNum = Integer.parseInt(scanner.nextLine());
            if (instructorNum > 0 && instructorNum <= instructors.size()) {
                Instructor selectedInstructor = instructors.get(instructorNum - 1);
                courseService.assignInstructorToCourse(selectedInstructor, courseCode);
                System.out.println("Instructor assigned successfully.");
            } else {
                System.out.println("Invalid instructor selection.");
            }
        }, () -> System.out.println("Course not found with code: " + courseCode));
    }

    private void searchCoursesMenu() {
        while (true) {
            System.out.println("\n--- Search Courses ---");
            System.out.println("1. By Instructor");
            System.out.println("2. By Department");
            System.out.println("3. By Semester");
            System.out.println("0. Back to Course Menu");
            System.out.print("Enter your choice: ");
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    searchByInstructor();
                    break;
                case 2:
                    searchByDepartment();
                    break;
                case 3:
                    searchBySemester();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchByInstructor() {
        System.out.println("\n--- Search by Instructor ---");
        System.out.print("Enter Instructor ID: ");
        UUID instructorId = UUID.fromString(scanner.nextLine());
        List<Course> courses = courseService.findCoursesByInstructor(instructorId);
        if (courses.isEmpty()) {
            System.out.println("No courses found for this instructor.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    private void searchByDepartment() {
        System.out.println("\n--- Search by Department ---");
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        List<Course> courses = courseService.findCoursesByDepartment(department);
        if (courses.isEmpty()) {
            System.out.println("No courses found for this department.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    private void searchBySemester() {
        System.out.println("\n--- Search by Semester ---");
        System.out.print("Enter Semester (SPRING, SUMMER, FALL, WINTER): ");
        Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());
        List<Course> courses = courseService.findCoursesBySemester(semester);
        if (courses.isEmpty()) {
            System.out.println("No courses found for this semester.");
        } else {
            courses.forEach(System.out::println);
        }
    }
}
