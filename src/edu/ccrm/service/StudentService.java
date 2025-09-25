package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {
    Student addStudent(Student student);
    Optional<Student> getStudentById(UUID id);
    List<Student> getAllStudents();
    Student updateStudent(Student student);
    void deactivateStudent(UUID id);
}
