package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final Student student;
    private final Course course;
    private Grade grade;
    private final LocalDateTime enrollmentDate;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student.getFullName() +
                ", course=" + course.getTitle() +
                ", grade=" + grade +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
