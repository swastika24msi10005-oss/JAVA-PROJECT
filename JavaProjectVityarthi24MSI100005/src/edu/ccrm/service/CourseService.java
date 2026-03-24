package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.InstructorNotFoundException;

import java.util.List;

public interface CourseService 
{
 void addCourse(Course c);

 Course findCourseByCode(String cd) throws CourseNotFoundException;

 List<Course> getAllCourses();

 List<Course> findCoursesByDepartment(String dept);

 void assignInstructorToCourse(String cCode,int instId) throws CourseNotFoundException, InstructorNotFoundException;
}
