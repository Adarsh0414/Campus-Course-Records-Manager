# CCRM Project Requirements

## 1. Deliverables

1.  **Source Code**: A fully functional Java project organized in packages.
2.  **`README.md`**: Comprehensive project documentation.
3.  **`USAGE.md`**: A guide with sample commands and data file formats.
4.  **`test-data` folder**: Containing small CSV files for data import.

---

## 2. Functional Requirements

### 1. Student Management
- Add/list/update/deactivate students.
- Each student has: id, regNo, fullName, email, status, enrolledCourses, and date fields using Java Date/Time API.
- Print a student profile and transcript.

### 2. Course Management
- Add/list/update/deactivate courses.
- Each course has: code, title, credits, instructor, semester, and department.
- Provide search & filter (by instructor, department, semester) using the Stream API.

### 3. Enrollment & Grading
- Enroll/unenroll students to courses (with business rules, e.g., max credits per semester).
- Record marks & compute letter grades and GPA.
- Enum for Semester (e.g., SPRING, SUMMER, FALL) and Grade (S, A, B, ..., F with grade points).
- Generate a transcript view that uses `toString()` overrides and polymorphism.

### 4. File Operations (NIO.2 + Streams)
- Import students/courses from simple CSV-like text files.
- Export current data (students, courses, enrollments) to files.
- Backup command that copies exported files to a timestamped folder (use Path, Files, walk, copy, move, exists).
- A recursive utility (e.g., recursively compute and print total size of the backup directory, or recursively list files by depth).

### 5. CLI Workflow
- Menu-driven console with options for all operations.
- Use `switch` (classic or enhanced) and all decision/loop constructs (while/do-while/for/enhanced-for; demonstrate break, continue, and a labeled jump once).

---

## 3. Documentation Requirements

### `README.md` Content
- Project overview & how to run (JDK version, commands).
- Evolution of Java (short timeline bullet points).
- Differentiate Java ME vs Java SE vs Java EE (table or bullets).
- Java architecture: JDK, JRE, JVM (what they are & how they interact).
- Install & configure Java on Windows (steps + screenshots).
- Using Eclipse IDE: new project creation, run configs (screenshots).
- Mapping table: syllabus topic → file/class/method where it’s demonstrated.
- Notes on enabling assertions and sample commands.

### `USAGE.md` Content
- A short guide with sample commands & data files.

---

## 4. Mandatory Technical Requirements

### Setup & Platform
- Code organized with packages (e.g., `edu.ccrm.domain`, `edu.ccrm.service`, etc.).
- Show Java syntax & structure with a clearly defined `main` class.

### Language & Core
- Demonstrate:
  - Primitive variables, objects, operators (arithmetic, relational, logical, bitwise), operator precedence.
  - Decision structures: `if` / `if-else` / `nested if-else`, and a `switch` menu.
  - Loops: `while`, `do-while`, `for`, `enhanced for`; include at least one jump control (`break`, `continue`).
  - Arrays and `Arrays` class utilities: sorting/searching.
  - Strings & common methods (`substring`, `split`, `join`, `equals`, `compareTo`, etc.).

### OOP & Type System
- **Four pillars**:
  - Encapsulation: `private` fields + getters/setters.
  - Inheritance: e.g., `Person` (abstract) → `Student` and `Instructor`.
  - Abstraction: `abstract` class `Person` with `abstract` methods.
  - Polymorphism: common interface or base-class references to varied subtypes.
- Access levels: use `private`, `protected`, `default`, `public` meaningfully.
- Types of inheritance and constructors in inheritance; demonstrate `super`.
- Immutability: one immutable value class (e.g., `Name`).
- Top-level & nested classes: include one `static` nested class and one `inner` class.
- **Interfaces**:
  - Define at least one interface (e.g., `DataService`).
  - Show diamond problem resolution via `default` methods and explicit override.
  - Justify interface vs. class inheritance choice in `README`.
- Functional interfaces & lambdas: e.g., `Comparator` lambdas, `Predicate`s for filtering.
- Anonymous inner classes: use once.
- Enums with constructors & fields: `Semester`, `Grade`.

### Advanced Concepts
- Upcast & downcast, and `instanceof` checks.
- Overriding & overloading methods; override `toString()` in domain classes.
- **Design patterns**:
  - Singleton: `AppConfig` or `DataStore`.
  - Builder: `Course.Builder`.
- **Exceptions**:
  - Clarify Errors vs Exceptions in `README`.
  - Use checked & unchecked exceptions; `try/catch/multi-catch/finally/throw/throws`.
  - Create at least two custom exceptions (e.g., `DuplicateEnrollmentException`).
  - Use assertions for invariants.
- **File I/O (NIO.2)**:
  - `Path` & `Files` APIs for check/delete/copy/move.
  - Use Streams to read/write and process lines.
  - Demonstrate a small Stream pipeline that aggregates reports.
- **Date/Time API** for timestamps.