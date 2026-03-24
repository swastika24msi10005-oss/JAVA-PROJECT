package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEntryException;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

public class DataImporter {
    // The importer needs services to add the data it reads.
    private final StudentService studentService;
    private final CourseService courseService;
    
    private static final Path TEST_DATA_DIR = Paths.get("test-data");

    public DataImporter(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public void importAll() {
        System.out.println("Starting data import from " + TEST_DATA_DIR.toAbsolutePath() + "...");
        importStudents();
        importCourses();
        System.out.println("Data import finished.");
    }

    private void importStudents() {
        Path studentFile = TEST_DATA_DIR.resolve("students.csv");
        if (!Files.exists(studentFile)) {
            System.out.println("Warning: students.csv not found, skipping.");
            return;
        }

        try (Stream<String> lines = Files.lines(studentFile)) {
            lines.skip(1) // Skip the header row
                 .map(line -> line.split(","))
                 .forEach(data -> {
                     try {
                         int id = Integer.parseInt(data[0].trim());
                         String regNo = data[1].trim();
                         String fullName = data[2].trim();
                         String email = data[3].trim();
                         LocalDate dob = LocalDate.parse(data[4].trim());
                         
                         studentService.addStudent(new Student(id, regNo, fullName, email, dob));
                         System.out.println("Imported student: " + fullName);
                     } catch (DuplicateEntryException e) {
                         // Gracefully handle duplicates instead of crashing
                         System.out.println("Warning: Skipping duplicate student - " + e.getMessage());
                     } catch (Exception e) {
                         System.err.println("Error parsing a student record: " + e.getMessage());
                     }
                 });
        } catch (IOException e) {
            System.err.println("Failed to read student data file: " + e.getMessage());
        }
    }

    private void importCourses() {
        Path courseFile = TEST_DATA_DIR.resolve("courses.csv");
        if (!Files.exists(courseFile)) {
            System.out.println("Warning: courses.csv not found, skipping.");
            return;
        }
        
        try (Stream<String> lines = Files.lines(courseFile)) {
            lines.skip(1) // Skip the header row
                 .map(line -> line.split(","))
                 .forEach(data -> {
                     try {
                         String code = data[0].trim();
                         String title = data[1].trim();
                         int credits = Integer.parseInt(data[2].trim());
                         String department = data[3].trim();
                         Semester semester = Semester.valueOf(data[4].trim().toUpperCase());

                         Course course = new Course.Builder(code, title, credits)
                                                   .department(department)
                                                   .semester(semester)
                                                   .build();
                         courseService.addCourse(course);
                         System.out.println("Imported course: " + title);
                     } catch (DuplicateEntryException e) {
                         System.out.println("Warning: Skipping duplicate course - " + e.getMessage());
                     } catch (Exception e) {
                         System.err.println("Error parsing a course record: " + e.getMessage());
                     }
                 });
        } catch (IOException e) {
            System.err.println("Failed to read course data file: " + e.getMessage());
        }
    }
}