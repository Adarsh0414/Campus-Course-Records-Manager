# Campus Course & Records Manager (CCRM)

This project is a console-based Java application for managing students, courses, and academic records, built to demonstrate a wide range of core Java features from basic syntax to advanced concepts like NIO.2, Streams, and design patterns.

## How to Run

- **JDK Version:** Java 21 (or higher)
- **Compile:**
  ```bash
  javac -d out --source-path src src/edu/ccrm/cli/CcrnCli.java
  ```
- **Run:**
  ```bash
  java -cp out edu.ccrm.cli.CcrnCli
  ```
- **Enable Assertions:**
  To run with assertions enabled (for internal invariant checks):
  ```bash
  java -ea -cp out edu.ccrm.cli.CcrnCli
  ```

---

## 1. The Evolution of Java

- **JDK 1.0 (1996):** The initial release. "Write Once, Run Anywhere."
- **J2SE 1.2 (1998):** Introduced Swing, Collections Framework, and JIT compilation.
- **J2SE 5.0 (2004):** A major release that introduced Generics, Enums, Annotations, and the `for-each` loop.
- **Java SE 7 (2011):** Added `try-with-resources`, the Diamond Operator (`<>`), and NIO.2 (New I/O).
- **Java SE 8 (2014):** A landmark release that brought Lambda Expressions, the Stream API, and a new Date/Time API.
- **Java SE 9 (2017):** Introduced the Java Platform Module System (JPMS).
- **Java SE 11 (2018):** The first Long-Term Support (LTS) release after the new six-month release cadence.
- **Java SE 17 (2021):** The second LTS release, bringing Sealed Classes and Pattern Matching for `instanceof`.
- **Java SE 21 (2023):** The latest LTS release, introducing Virtual Threads, Record Patterns, and Sequenced Collections.

---

## 2. Java Platforms: ME, SE, and EE

| Platform              | Full Name              | Target                                                 | Key Features                                                              |
| --------------------- | ---------------------- | ------------------------------------------------------ | ------------------------------------------------------------------------- |
| **Java ME**           | Micro Edition          | Resource-constrained devices (e.g., mobile, embedded)  | Small footprint, subset of Java SE APIs, specific libraries for mobile.   |
| **Java SE (This App)**| Standard Edition       | Desktop, servers, and general-purpose applications     | Core Java language, JVM, Collections, Swing, Networking, I/O, Concurrency.|
| **Java EE**           | Enterprise Edition     | Large-scale, distributed, transactional applications   | Built on Java SE; adds APIs for servlets, web services, messaging (JMS).  |

---

## 3. The Java Architecture: JDK, JRE, JVM

- **JVM (Java Virtual Machine):**
  - An abstract computing machine that enables a computer to run a Java program.
  - It interprets with the compiled Java bytecode and translates it into the native machine code of the underlying operating system.
  - This is the core component that makes Java "platform-independent."

- **JRE (Java Runtime Environment):**
  - The software environment in which Java programs run.
  - It contains the JVM, the Java Class Library (core classes like `String`, `List`, etc.), and other supporting files.
  - You need the JRE to *run* a Java application, but not to develop one.

- **JDK (Java Development Kit):**
  - The full-featured software development kit for creating Java applications.
  - It contains everything in the JRE, plus development tools like the **compiler (`javac`)**, **archiver (`jar`)**, and **documentation generator (`javadoc`)**.
  - You need the JDK to *develop* Java applications.

**Interaction:** A developer writes Java code (`.java` files), uses the **JDK** to compile it into bytecode (`.class` files), and then the **JRE** (specifically the **JVM**) runs that bytecode.

---

## 4. Setup & Configuration

### Installing Java on Windows

1.  **Download the JDK:** Go to the official Oracle Java Downloads page and get the "x64 Installer" for the latest LTS version (e.g., Java 21).
2.  **Run the Installer:** Execute the downloaded `.exe` file and follow the on-screen prompts. It will typically install to `C:\Program Files\Java\jdk-21`.
3.  **Set the `JAVA_HOME` Environment Variable:**
    - Search for "Edit the system environment variables" in the Start Menu.
    - Click the "Environment Variables..." button.
    - Under "System variables," click "New...".
    - Variable name: `JAVA_HOME`
    - Variable value: `C:\Program Files\Java\jdk-21` (or your installation path).
4.  **Update the `Path` Variable:**
    - Under "System variables," find and select the `Path` variable, then click "Edit...".
    - Click "New" and add `%JAVA_HOME%\bin`.
    - Move this new entry to the top of the list to ensure it takes precedence.
5.  **Verify the Installation:**
    - Open a new Command Prompt or PowerShell window.
    - Run `java -version` and `javac -version`. Both should display the version you just installed.


**(USER ACTION: Add Screenshot Here)**

*After running `java -version` and `javac -version`, take a screenshot of your terminal showing the output for both commands. Replace this text block with the screenshot.*


### Using an IDE (Eclipse Example)

1.  **Create a New Java Project:**
    - Go to `File > New > Java Project`.
    - Give it a project name (e.g., "CCRM").
    - Select the appropriate JRE (e.g., Java 21).
    - Click "Finish."
2.  **Create Packages and Classes:**
    - Right-click the `src` folder in the Project Explorer.
    - Select `New > Package` (e.g., `edu.ccrm.cli`).
    - Right-click the new package and select `New > Class` (e.g., `CcrnCli`).
3.  **Create a Run Configuration:**
    - Right-click the file containing your `main` method (`CcrnCli.java`).
    - Select `Run As > Java Application`. This automatically creates a run configuration.
    - To view or edit it, go to `Run > Run Configurations...`.


**(USER ACTION: Add Screenshot Here)**

*Inside Eclipse, with your CCRM project open, go to `Run > Run Configurations...`. Select your "CcrnCli" configuration on the left. Take a screenshot of the entire Eclipse window, showing the project explorer on the left and the run configuration dialog on the right. Replace this text block with the screenshot.*


---

## 5. Core Java Concepts Demonstrated

### Errors vs. Exceptions

- **`Error`:** Represents serious problems that a reasonable application should not try to catch. These are typically unrecoverable issues related to the JVM itself, like `OutOfMemoryError` or `StackOverflowError`.
- **`Exception`:** Represents conditions that a reasonable application *might* want to catch. They are categorized into:
  - **Checked Exceptions:** Exceptions that the compiler forces you to handle (with `try-catch` or `throws`). They represent predictable, recoverable problems (e.g., `IOException`, `SQLException`). This project uses them for file operations.
  - **Unchecked (Runtime) Exceptions:** Exceptions that you are not required to handle, though you can. They often indicate programming errors (e.g., `NullPointerException`, `IllegalArgumentException`). This project uses them for business rule violations like `DuplicateEnrollmentException`.

### Assertions

Assertions are used to define invariants—conditions that should always be true at a certain point in the code. They are a debugging tool to catch logical errors. In this project, an assertion might be used to ensure a student ID is not null before an operation.

- **Example:** `assert studentId != null : "Student ID cannot be null";`
- **Enabling:** Assertions are disabled by default. You must enable them at runtime with the `-ea` flag: `java -ea edu.ccrm.cli.CcrnCli`.

### Interface vs. Abstract Class Inheritance

- **Abstract Class (e.g., `Person`):** Used when there is a strong "is-a" relationship and a desire to share common code and state. A `Student` *is a* `Person`, and an `Instructor` *is a* `Person`. They share common attributes like `name` and `email`, so an abstract class is a natural fit to avoid code duplication.
- **Interface (e.g., `DataService`):** Used to define a "can-do" relationship or a contract. It specifies *what* a class can do, but not *how*. The `DataServiceImpl` class implements the `DataService` interface, promising to provide data import/export functionality. This decouples the high-level CLI from the low-level implementation details, allowing for different data sources (e.g., file, database) to be swapped in the future without changing the client code.

---

## 6. Syllabus Mapping

| Topic / Concept | File / Class / Method | Description |
| --- | --- | --- |
| **Packages** | `edu.ccrm.*` | Code is organized into `cli`, `domain`, `service`, `io`, `config`, `util` packages. |
| **Primitives, Operators** | `LoopDemonstrator.java` | Demonstrates various operators and primitive types. |
| **Decision Structures** | `CcrnCli.java`, `*Menu.java` | `if-else` and `switch` statements are used for menu navigation and logic. |
| **Loops & Jumps** | `LoopDemonstrator.java` | Demonstrates `while`, `do-while`, `for`, `enhanced-for`, `break`, `continue`. |
| **Arrays & `Arrays` class** | `LoopDemonstrator.java` | Demonstrates array creation, sorting, and searching. |
| **Encapsulation** | `Student.java`, `Course.java` | Domain classes use `private` fields with `public` getters/setters. |
| **Inheritance** | `Student.java` extends `Person.java` | `Student` and `Instructor` inherit common properties from the `Person` class. |
| **Abstraction** | `Person.java` | `Person` is an `abstract` class with an `abstract` method `getDetails()`. |
| **Polymorphism** | `CcrnCli.java`, `Student.java` | `Person` references hold `Student` objects; `getDetails()` is invoked polymorphically. |
| **Immutability** | `Name.java` | A `final` class with `final` fields and no setters. |
| **Static Nested Class** | `Course.Builder` in `Course.java` | The `Builder` is a `static` nested class used to construct `Course` objects. |
| **Inner Class** | `Student.Transcript` in `Student.java` | The `Transcript` class is an inner class that has access to `Student` fields. |
| **Interfaces** | `DataService.java`, `Menu.java` | Define contracts for data operations and CLI menu handling. |
| **Lambdas & Func. Interfaces** | `EnrollmentServiceImpl.java` | Lambdas are used with the Stream API for sorting (`Comparator`) and filtering (`Predicate`). |
| **Anonymous Inner Class** | `CcrnCli.java` | A `Runnable` is implemented as an anonymous inner class for a welcome message. |
| **Enums with Constructors** | `Grade.java`, `Semester.java` | Enums define constants with associated data (grade points, display names). |
| **Overriding** | `Student.java` | `getDetails()` and `toString()` are overridden. |
| **Singleton Pattern** | `AppConfig.java` | `AppConfig.getInstance()` ensures only one instance of the configuration class. |
| **Builder Pattern** | `Course.Builder` in `Course.java` | Provides a fluent API for creating complex `Course` objects. |
| **Custom Exceptions** | `DuplicateEnrollmentException.java` | Custom checked exceptions are thrown and caught for specific business rule violations. |
| **try-catch-finally** | `EnrollmentMenu.java` | `try-catch` blocks handle exceptions during user operations. |
| **File I/O (NIO.2)** | `DataServiceImpl.java` | Uses `Path`, `Files`, `Paths` for all file operations. |
| **Stream API** | `DataServiceImpl.java` | `Files.lines()` and `Files.walk()` are used to process files as streams. |
| **Date/Time API** | `Person.java`, `Enrollment.java` | `LocalDateTime` is used for timestamps. |

