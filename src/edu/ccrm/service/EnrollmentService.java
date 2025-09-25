package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;

import java.util.List;
import java.util.Map;

public interface EnrollmentService {
    void enrollStudent(Student student, Course course) throws Exception;
    void unenrollStudent(Student student, Course course);
    void setGrade(Enrollment enrollment, Grade grade);
    List<Enrollment> getEnrollmentsByStudent(Student student);
    List<Enrollment> getEnrollmentsByCourse(Course course);
    double calculateGpa(Student student);
    List<Student> getTopStudents(int count);
    Map<String, Long> getGpaDistribution();
}
