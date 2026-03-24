package edu.ccrm.service;

import edu.ccrm.domain.Grade;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.StudentNotFoundException;

public interface EnrollmentService {

    void enrollStudent(String reg, String course) throws StudentNotFoundException, CourseNotFoundException;

    void assignGrade(String reg, String course, Grade grade) throws StudentNotFoundException, CourseNotFoundException;

    void printTranscript(String reg) throws StudentNotFoundException;

    double calculateGPA(String reg) throws StudentNotFoundException;

    void unenrollStudent(String reg, String course) throws StudentNotFoundException, CourseNotFoundException;
}
