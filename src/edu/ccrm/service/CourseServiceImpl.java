package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {

    private final Map<String, Course> courseStore = new ConcurrentHashMap<>();

    @Override
    public Course addCourse(Course course) {
        if (course == null || course.getCode() == null) {
            throw new IllegalArgumentException("Course and course code must not be null");
        }
        courseStore.put(course.getCode(), course);
        return course;
    }

    @Override
    public Optional<Course> getCourseByCode(String code) {
        return Optional.ofNullable(courseStore.get(code));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseStore.values().stream().collect(Collectors.toList());
    }

    @Override
    public Course updateCourse(Course course) {
        if (course == null || course.getCode() == null) {
            throw new IllegalArgumentException("Course and course code must not be null");
        }
        if (!courseStore.containsKey(course.getCode())) {
            throw new IllegalArgumentException("Course with code " + course.getCode() + " not found.");
        }
        courseStore.put(course.getCode(), course);
        return course;
    }

    @Override
    public void assignInstructorToCourse(Instructor instructor, String courseCode) {
        getCourseByCode(courseCode).ifPresent(course -> {
            course.setInstructor(instructor);
            updateCourse(course);
        });
    }

    @Override
    public List<Course> findCoursesByInstructor(UUID instructorId) {
        return courseStore.values().stream()
                .filter(course -> course.getInstructor() != null && course.getInstructor().getId().equals(instructorId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findCoursesByDepartment(String department) {
        return courseStore.values().stream()
                .filter(course -> course.getDepartment() != null && course.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findCoursesBySemester(Semester semester) {
        return courseStore.values().stream()
                .filter(course -> course.getSemester() == semester)
                .collect(Collectors.toList());
    }
}
