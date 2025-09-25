package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.InstructorService;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataServiceImpl implements DataService {

    private final StudentService studentService;
    private final CourseService courseService;
    private final InstructorService instructorService;

    public DataServiceImpl(StudentService studentService, CourseService courseService, InstructorService instructorService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    @Override
    public void importStudents(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                Name name = new Name(parts[2], parts[3]);
                Student student = new Student(name, parts[4], parts[1]);
                studentService.addStudent(student);
            });
        }
    }

    @Override
    public void importCourses(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                Course.Builder builder = new Course.Builder(parts[0], parts[1])
                        .credits(Integer.parseInt(parts[2]))
                        .semester(Semester.valueOf(parts[4]))
                        .department(parts[5]);
                if (!parts[3].isBlank()) {
                    instructorService.getInstructorById(UUID.fromString(parts[3]))
                            .ifPresent(builder::instructor);
                }
                courseService.addCourse(builder.build());
            });
        }
    }

    @Override
    public void exportStudents(Path path, List<Student> students) throws IOException {
        List<String> lines = students.stream()
                .map(s -> String.join(",",
                        s.getId().toString(),
                        s.getRegNo(),
                        s.getFullName().getFirstName(),
                        s.getFullName().getLastName(),
                        s.getEmail(),
                        s.getStatus().toString()))
                .collect(Collectors.toList());
        lines.add(0, "id,regNo,firstName,lastName,email,status");
        Files.write(path, lines);
    }

    @Override
    public void exportCourses(Path path, List<Course> courses) throws IOException {
        List<String> lines = courses.stream()
                .map(c -> String.join(",",
                        c.getCode(),
                        c.getTitle(),
                        String.valueOf(c.getCredits()),
                        c.getInstructor() != null ? c.getInstructor().getId().toString() : "",
                        c.getSemester().toString(),
                        c.getDepartment()))
                .collect(Collectors.toList());
        lines.add(0, "code,title,credits,instructorId,semester,department");
        Files.write(path, lines);
    }

    @Override
    public void exportEnrollments(Path path, List<Enrollment> enrollments) throws IOException {
        List<String> lines = enrollments.stream()
                .map(e -> String.join(",",
                        e.getStudent().getId().toString(),
                        e.getCourse().getCode(),
                        e.getGrade() != null ? e.getGrade().toString() : ""))
                .collect(Collectors.toList());
        lines.add(0, "studentId,courseCode,grade");
        Files.write(path, lines);
    }

    @Override
    public void backupData(Path sourceDir, Path backupDir) throws IOException {
        Path timestampedDir = backupDir.resolve("backup-" + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
        Files.createDirectories(timestampedDir);
        try (Stream<Path> stream = Files.walk(sourceDir)) {
            stream.forEach(source -> {
                try {
                    Files.copy(source, timestampedDir.resolve(sourceDir.relativize(source)), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public long getDirectorySize(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path)) {
            return stream
                    .filter(p -> p.toFile().isFile())
                    .mapToLong(p -> p.toFile().length())
                    .sum();
        }
    }
}
