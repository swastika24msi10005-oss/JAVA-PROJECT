package edu.ccrm.io;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataExporter {
 private static final Path EXP=Paths.get("data","exports");
 private final DataStore ds=DataStore.getInstance();

 public void exportAllData() throws IOException {
  Files.createDirectories(EXP);
  exportStuds();
  exportCrss();
  exportEnrs();
  System.out.println("Data exported -> "+EXP.toAbsolutePath());
 }

 private void exportStuds() throws IOException {
  Path f=EXP.resolve("students.csv");
  List<String> l=ds.getStudents().stream()
   .map(s->String.join(",",
    String.valueOf(s.getId()),
    s.getRegNo(),
    s.getFullName(),
    s.getEmail(),
    s.getDateOfBirth().toString()))
   .collect(Collectors.toList());
  l.add(0,"id,regNo,fullName,email,dob");
  Files.write(f,l);
 }

 private void exportCrss() throws IOException {
  Path f=EXP.resolve("courses.csv");
  List<String> l=ds.getCourses().stream()
   .map(c->String.join(",",
    c.getCode(),
    c.getTitle(),
    String.valueOf(c.getCredits()),
    c.getDepartment(),
    c.getSemester().name()))
   .collect(Collectors.toList());
  l.add(0,"code,title,credits,department,semester");
  Files.write(f,l);
 }

 private void exportEnrs() throws IOException {
  Path f=EXP.resolve("enrollments.csv");
  List<String> l=ds.getEnrollments().stream()
   .map(e->String.join(",",
    e.getStudent().getRegNo(),
    e.getCourse().getCode(),
    e.getGrade()!=null?e.getGrade().name():"N/A"))
   .collect(Collectors.toList());
  l.add(0,"studentRegNo,courseCode,grade");
  Files.write(f,l);
 }
}
