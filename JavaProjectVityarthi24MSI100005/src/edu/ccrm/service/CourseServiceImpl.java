package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.InstructorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {

    private final DataStore dataStore = DataStore.getInstance();
    private final InstructorService instructorService = new InstructorServiceImpl();

    @Override
    public void addCourse(Course course) {
        dataStore.getCourses().add(course);
    }

    @Override
    public Course findCourseByCode(String code) throws CourseNotFoundException {
        return dataStore.getCourses().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(
                        "No course found with code: " + code
                ));
    }

    @Override
    public List<Course> getAllCourses() {
        return dataStore.getCourses();
    }

    @Override
    public List<Course> findCoursesByDepartment(String department) {
        return dataStore.getCourses().stream()
                .filter(course -> course.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    @Override
    public void assignInstructorToCourse(String courseCode, int instructorId)
            throws CourseNotFoundException, InstructorNotFoundException {

        Course course = findCourseByCode(courseCode);
        Instructor instructor = instructorService.findInstructorById(instructorId);

        course.assignInstructor(instructor);

        System.out.printf(
                "Instructor %s has been assigned to the course: %s%n",
                instructor.getFullName(),
                course.getTitle()
        );
    }
}
