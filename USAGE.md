# CCRM Usage Guide

This guide provides sample commands and data file format specifications for the Campus Course & Records Manager (CCRM).

---

## 1. Running the Application

First, compile the application from the project root directory:
```bash
javac -d out --source-path src src/edu/ccrm/cli/CcrnCli.java
```

Then, run the main CLI:
```bash
java -cp out edu.ccrm.cli.CcrnCli
```

You will be greeted with the main menu:
```
--- Main Menu ---
1. Manage Students
2. Manage Courses
3. Manage Enrollment & Grading
4. File Operations
5. Reports
6. Demonstrations
0. Exit
Enter your choice:
```

---

## 2. Data File Formats (CSV)

The application uses simple CSV (Comma-Separated Values) files for data import and export. These files should be placed in the `test-data` directory for easy access.

### `students.csv`

This file contains student records.

- **Format:** `id,regNo,firstName,lastName,email,status`
- **`id`:** A unique UUID (optional during import, as it's auto-generated).
- **`status`:** Must be one of the `StudentStatus` enum values (e.g., `ACTIVE`, `INACTIVE`).

**Example (`test-data/students.csv`):**
```csv
id,regNo,firstName,lastName,email,status
f47ac10b-58cc-4372-a567-0e02b2c3d479,S001,John,Doe,john.doe@example.com,ACTIVE
f47ac10b-58cc-4372-a567-0e02b2c3d480,S002,Jane,Smith,jane.smith@example.com,ACTIVE
```

### `courses.csv`

This file contains course details.

- **Format:** `code,title,credits,instructorId,semester,department`
- **`instructorId`:** A UUID linking to an instructor. Can be left blank.
- **`semester`:** Must be one of the `Semester` enum values (e.g., `SPRING`, `FALL`).

**Example (`test-data/courses.csv`):**
```csv
code,title,credits,instructorId,semester,department
CS101,Introduction to Computer Science,3,,SPRING,Computer Science
MATH201,Calculus I,4,,FALL,Mathematics
```

---

## 3. Sample Commands (CLI Workflow)

### Importing Data

1.  Navigate to **4. File Operations** from the main menu.
2.  Select **1. Import Students from CSV**.
3.  When prompted, you can either press Enter to use the default path (`data/students.csv`) or provide the path to your file, for example: `test-data/students.csv`.
4.  Select **2. Import Courses from CSV** and provide the path `test-data/courses.csv`.

### Managing Students

1.  Navigate to **1. Manage Students**.
2.  Select **2. List Students** to see the newly imported students.
3.  Select **1. Add Student** and follow the prompts to add a new student to the system.

### Enrolling a Student

1.  Navigate to **3. Manage Enrollment & Grading**.
2.  Select **1. Enroll Student in Course**.
3.  You will be prompted for a Student ID and a Course Code. You can get these from the "List Students" and "List Courses" menus.

### Exporting Data

1.  Navigate to **4. File Operations**.
2.  Select **3. Export Students to CSV**.
3.  Press Enter to export to the default file path (`data/students.csv`). This will create a new file with all current student data in the system.

### Backing Up Data

1.  Navigate to **4. File Operations**.
2.  Select **6. Backup Data**.
3.  Press Enter twice to accept the default source (`data`) and backup (`backup`) directories.
4.  A new timestamped folder will be created in the `backup` directory containing copies of the files from the `data` directory.