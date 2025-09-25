package edu.ccrm.util;

import edu.ccrm.domain.Person;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Name;

import java.util.Arrays;

public class LoopDemonstrator {

    public static void demonstrateLoops() {
        System.out.println("--- Demonstrating Loops ---");

        // for loop
        System.out.println("For loop:");
        for (int i = 0; i < 3; i++) {
            System.out.println("i = " + i);
        }

        // while loop
        System.out.println("\nWhile loop:");
        int j = 0;
        while (j < 3) {
            System.out.println("j = " + j);
            j++;
        }

        // do-while loop
        System.out.println("\nDo-while loop:");
        int k = 0;
        do {
            System.out.println("k = " + k);
            k++;
        } while (k < 3);

        // enhanced for loop
        System.out.println("\nEnhanced for loop:");
        int[] numbers = {1, 2, 3};
        for (int number : numbers) {
            System.out.println("number = " + number);
        }

        // break and continue
        System.out.println("\nBreak and continue:");
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                System.out.println("Breaking at i = 3");
                break;
            }
            if (i == 1) {
                System.out.println("Continuing at i = 1");
                continue;
            }
            System.out.println("i = " + i);
        }

        // labeled jump
        System.out.println("\nLabeled jump:");
        outer:
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 3; l++) {
                if (i == 1 && l == 1) {
                    System.out.println("Breaking from outer loop at i=1, l=1");
                    break outer;
                }
                System.out.println("i=" + i + ", l=" + l);
            }
        }
    }

    public static void demonstrateArrayUtilities() {
        System.out.println("\n--- Demonstrating Array Utilities ---");
        String[] courseCodes = {"CS101", "MA202", "PH101", "CS201"};
        System.out.println("Original array: " + Arrays.toString(courseCodes));

        // Sorting
        Arrays.sort(courseCodes);
        System.out.println("Sorted array: " + Arrays.toString(courseCodes));

        // Searching
        int index = Arrays.binarySearch(courseCodes, "PH101");
        System.out.println("Index of PH101 is: " + index);
    }

    public static void demonstrateOperatorPrecedence() {
        System.out.println("\n--- Demonstrating Operator Precedence ---");
        int a = 5, b = 10, c = 15;
        // In Java, * and / have higher precedence than + and -.
        // So, b * c is evaluated first.
        int result = a + b * c; // Equivalent to a + (b * c)
        System.out.println("Result of a + b * c is: " + result);
    }

    public static void demonstrateCastingAndInstanceof() {
        System.out.println("\n--- Demonstrating Upcasting, Downcasting, and instanceof ---");

        // Upcasting: Assigning a subclass object to a superclass reference
        Person person1 = new Student(new Name("Alice", "Smith"), "alice@example.com", "S001");
        Person person2 = new Instructor(new Name("Bob", "Johnson"), "bob@example.com", "Computer Science");

        System.out.println("Person 1 (upcasted Student): " + person1.getFullName());
        System.out.println("Person 2 (upcasted Instructor): " + person2.getFullName());

        // instanceof check and Downcasting
        // Justification: Downcasting is necessary when you need to access
        // members (methods or fields) that are specific to the subclass
        // but are not available in the superclass.
        // instanceof is crucial before downcasting to prevent ClassCastException.
        if (person1 instanceof Student) {
            System.out.println("person1 is an instance of Student.");
            Student student = (Student) person1; // Downcasting
            System.out.println("Student's Registration No: " + student.getRegNo());
        }

        if (person2 instanceof Instructor) {
            System.out.println("person2 is an instance of Instructor.");
            Instructor instructor = (Instructor) person2; // Downcasting
            System.out.println("Instructor's Department: " + instructor.getDepartment());
        }

        // Attempting invalid downcast (will not execute due to instanceof)
        if (person1 instanceof Instructor) {
            System.out.println("This will not be printed.");
            // Instructor invalidInstructor = (Instructor) person1; // This would cause ClassCastException
        }
    }

    public static void demonstrateFinallyBlock() {
        System.out.println("\n--- Demonstrating finally block ---");
        try {
            System.out.println("Inside try block.");
            int result = 10 / 0; // This will cause an ArithmeticException
            System.out.println("Result: " + result); // This line will not be executed
        } catch (ArithmeticException e) {
            System.out.println("Inside catch block: " + e.getMessage());
        } finally {
            System.out.println("Inside finally block. This always executes.");
        }
    }

    public static void demonstrateAssertions() {
        System.out.println("\n--- Demonstrating Assertions ---");
        int value = 10;
        // Assertions are typically used for internal self-checks in development.
        // They should be enabled with -ea JVM argument.
        assert value > 0 : "Value must be positive";
        System.out.println("Assertion passed: value is " + value);

        // Example of a failing assertion (uncomment to see it fail if assertions are enabled)
        // int negativeValue = -5;
        // assert negativeValue > 0 : "Negative value assertion failed";
        // System.out.println("Assertion passed: negativeValue is " + negativeValue);
    }
}
