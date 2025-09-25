package edu.ccrm.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class Student extends Person {
    private final UUID id;
    private String regNo;
    private StudentStatus status;
    private List<Course> enrolledCourses;

    public Student(Name fullName, String email, String regNo) {
        super(fullName, email);
        this.id = UUID.randomUUID();
        this.regNo = regNo;
        this.status = StudentStatus.ACTIVE;
        this.enrolledCourses = new ArrayList<>();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public List<Course> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses); // Defensive copy
    }

    public void enrollInCourse(Course course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void unenrollFromCourse(Course course) {
        enrolledCourses.remove(course);
    }

        @Override
    public String getDetails() {
        return "Student{"
                + "id=" + id
                + ", regNo='" + regNo + "'"
                + ", fullName=" + getFullName()
                + ", email='" + getEmail() + "'"
                + ", status=" + status
                + ", createdAt=" + getCreatedAt()
                + ", updatedAt=" + getUpdatedAt()
                + "}";
    }

    public class Transcript {
        public String generate(List<Enrollment> enrollments) {
            StringBuilder sb = new StringBuilder();
            sb.append("Transcript for: ").append(getFullName()).append("\n");
            sb.append("Registration No: ").append(regNo).append("\n");
            sb.append("----------------------------------------\n");
            for (Enrollment enrollment : enrollments) {
                sb.append("Course: ").append(enrollment.getCourse().getTitle());
                sb.append(", Credits: ").append(enrollment.getCourse().getCredits());
                sb.append(", Grade: ").append(enrollment.getGrade() != null ? enrollment.getGrade() : "N/A");
                sb.append("\n");
            }
            sb.append("----------------------------------------\n");
            return sb.toString();
        }
    }
}
