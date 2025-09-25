package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.StudentStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class StudentServiceImpl implements StudentService {

    private final Map<UUID, Student> studentStore = new ConcurrentHashMap<>();

    @Override
    public Student addStudent(Student student) {
        assert student.getId() != null : "Student ID cannot be null";
        if (student == null) {
            throw new IllegalArgumentException("Student must not be null");
        }
        studentStore.put(student.getId(), student);
        return student;
    }

    @Override
    public Optional<Student> getStudentById(UUID id) {
        return Optional.ofNullable(studentStore.get(id));
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentStore.values());
    }

    @Override
    public Student updateStudent(Student student) {
        if (student == null || student.getId() == null) {
            throw new IllegalArgumentException("Student and student ID must not be null");
        }
        if (!studentStore.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student with ID " + student.getId() + " not found.");
        }
        student.setUpdatedAt(LocalDateTime.now());
        studentStore.put(student.getId(), student);
        return student;
    }

    @Override
    public void deactivateStudent(UUID id) {
        getStudentById(id).ifPresent(student -> {
            student.setStatus(StudentStatus.INACTIVE);
            student.setUpdatedAt(LocalDateTime.now());
            studentStore.put(id, student);
        });
    }
}
