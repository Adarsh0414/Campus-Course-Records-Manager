package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    Course addCourse(Course course);
    Optional<Course> getCourseByCode(String code);
    List<Course> getAllCourses();
    Course updateCourse(Course course);
    void assignInstructorToCourse(Instructor instructor, String courseCode);
    List<Course> findCoursesByInstructor(UUID instructorId);
    List<Course> findCoursesByDepartment(String department);
    List<Course> findCoursesBySemester(Semester semester);
}
