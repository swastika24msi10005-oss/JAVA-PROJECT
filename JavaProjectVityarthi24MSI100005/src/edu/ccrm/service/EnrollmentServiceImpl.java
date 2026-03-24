package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.exception.StudentNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentServiceImpl implements EnrollmentService {

    private static final int MAX_CREDITS = 18;

    private final DataStore dataStore = DataStore.getInstance();
    private final StudentService studentService = new StudentServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public void enrollStudent(String reg, String courseCode) throws StudentNotFoundException, CourseNotFoundException {
        Student student = studentService.findStudentByRegNo(reg);
        Course course = courseService.findCourseByCode(courseCode);

        boolean alreadyEnrolled = dataStore.getEnrollments().stream()
                .anyMatch(e -> e.getStudent().equals(student) && e.getCourse().equals(course));

        if (alreadyEnrolled) {
            System.out.println("Warning: Student already enrolled in this course.");
            return;
        }

        int currentCredits = dataStore.getEnrollments().stream()
                .filter(e -> e.getStudent().equals(student))
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        if (currentCredits + course.getCredits() > MAX_CREDITS) {
            throw new MaxCreditLimitExceededException(
                    "Cannot enroll. Exceeds max credit limit of " + MAX_CREDITS
            );
        }

        dataStore.getEnrollments().add(new Enrollment(student, course));
        System.out.println("Enrollment successful!");
    }

    @Override
    public void unenrollStudent(String reg, String courseCode) throws StudentNotFoundException, CourseNotFoundException {
        Student student = studentService.findStudentByRegNo(reg);
        Course course = courseService.findCourseByCode(courseCode);

        boolean removed = dataStore.getEnrollments().removeIf(
                e -> e.getStudent().equals(student) && e.getCourse().equals(course)
        );

        if (removed) {
            System.out.println("Student successfully unenrolled from " + course.getCode());
        } else {
            System.out.println("Warning: Student was not enrolled in this course.");
        }
    }

    @Override
    public void assignGrade(String reg, String courseCode, Grade grade) throws StudentNotFoundException, CourseNotFoundException {
        Student student = studentService.findStudentByRegNo(reg);
        Course course = courseService.findCourseByCode(courseCode);

        dataStore.getEnrollments().stream()
                .filter(e -> e.getStudent().equals(student) && e.getCourse().equals(course))
                .findFirst()
                .ifPresentOrElse(
                        e -> {
                            e.setGrade(grade);
                            System.out.println("Grade assigned successfully.");
                        },
                        () -> System.out.println("Error: Enrollment not found for grade assignment.")
                );
    }

    @Override
    public double calculateGPA(String reg) throws StudentNotFoundException {
        Student student = studentService.findStudentByRegNo(reg);

        List<Enrollment> enrollments = dataStore.getEnrollments().stream()
                .filter(e -> e.getStudent().equals(student))
                .collect(Collectors.toList());

        if (enrollments.isEmpty()) return 0.0;

        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment e : enrollments) {
            Grade g = e.getGrade();
            if (g != null) {
                totalPoints += g.getGradePoint() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    @Override
    public void printTranscript(String reg) throws StudentNotFoundException {
        Student student = studentService.findStudentByRegNo(reg);

        System.out.println("\n--- Academic Transcript ---");
        System.out.println(student.getDetailedProfile());
        System.out.println("---------------------------");

        List<Enrollment> enrollments = dataStore.getEnrollments().stream()
                .filter(e -> e.getStudent().equals(student))
                .collect(Collectors.toList());

        if (enrollments.isEmpty()) {
            System.out.println("No courses enrolled.");
            return;
        }

        System.out.printf("%-10s %-30s %-8s %-10s%n", "Course", "Title", "Credits", "Grade");
        System.out.println("---------------------------------------------------------------");

        for (Enrollment e : enrollments) {
            Course c = e.getCourse();
            String gradeStr = e.getGrade() != null ? e.getGrade().name() : "IP";
            System.out.printf("%-10s %-30s %-8d %-10s%n", c.getCode(), c.getTitle(), c.getCredits(), gradeStr);
        }

        System.out.println("---------------------------------------------------------------");

        double gpa = calculateGPA(reg);
        if (gpa > 0.0) {
            System.out.printf("Cumulative GPA: %.2f%n", gpa);
        } else {
            System.out.println("Cumulative GPA: N/A (No graded courses)");
        }
    }
}
