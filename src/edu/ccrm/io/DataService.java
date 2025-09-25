package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface DataService {
    void importStudents(Path path) throws IOException;
    void importCourses(Path path) throws IOException;
    void exportStudents(Path path, List<Student> students) throws IOException;
    void exportCourses(Path path, List<Course> courses) throws IOException;
    void exportEnrollments(Path path, List<Enrollment> enrollments) throws IOException;
    void backupData(Path sourceDir, Path backupDir) throws IOException;
    long getDirectorySize(Path path) throws IOException;
}
