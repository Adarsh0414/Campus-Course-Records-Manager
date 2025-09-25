package edu.ccrm.service;

import edu.ccrm.domain.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnrollmentServiceImpl implements EnrollmentService {

    private static final int MAX_CREDITS_PER_SEMESTER = 20;
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final StudentService studentService;

    public EnrollmentServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void enrollStudent(Student student, Course course) throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        // Check for duplicate enrollment
        if (enrollments.stream().anyMatch(e -> e.getStudent().equals(student) && e.getCourse().equals(course))) {
            throw new DuplicateEnrollmentException("Student is already enrolled in this course.");
        }

        // Check for max credits per semester
        int currentCredits = getEnrollmentsByStudent(student).stream()
                .filter(e -> e.getCourse().getSemester() == course.getSemester())
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException("Cannot enroll. Maximum credits for the semester will be exceeded.");
        }

        enrollments.add(new Enrollment(student, course));
        student.enrollInCourse(course);
    }

    @Override
    public void unenrollStudent(Student student, Course course) {
        enrollments.removeIf(e -> e.getStudent().equals(student) && e.getCourse().equals(course));
        student.unenrollFromCourse(course);
    }

    @Override
    public void setGrade(Enrollment enrollment, Grade grade) {
        enrollment.setGrade(grade);
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudent(Student student) {
        return enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> getEnrollmentsByCourse(Course course) {
        return enrollments.stream()
                .filter(e -> e.getCourse().equals(course))
                .collect(Collectors.toList());
    }

    @Override
    public double calculateGpa(Student student) {
        List<Enrollment> studentEnrollments = getEnrollmentsByStudent(student);
        if (studentEnrollments.isEmpty()) {
            return 0.0;
        }

        double totalGradePoints = studentEnrollments.stream()
                .filter(e -> e.getGrade() != null)
                .mapToDouble(e -> e.getGrade().getGradePoint() * e.getCourse().getCredits())
                .sum();

        int totalCredits = studentEnrollments.stream()
                .filter(e -> e.getGrade() != null)
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        return totalCredits == 0 ? 0.0 : totalGradePoints / totalCredits;
    }

    @Override
    public List<Student> getTopStudents(int count) {
        return studentService.getAllStudents().stream()
                .sorted(Comparator.comparingDouble(this::calculateGpa).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> getGpaDistribution() {
        return studentService.getAllStudents().stream()
                .map(this::calculateGpa)
                .collect(Collectors.groupingBy(gpa -> {
                    if (gpa >= 9) return "9.0 - 10.0";
                    if (gpa >= 8) return "8.0 - 8.9";
                    if (gpa >= 7) return "7.0 - 7.9";
                    if (gpa >= 6) return "6.0 - 6.9";
                    if (gpa >= 5) return "5.0 - 5.9";
                    return "Below 5.0";
                }, Collectors.counting()));
    }
}
