package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.exception.DuplicateEntryException;
import edu.ccrm.exception.InstructorNotFoundException;
import edu.ccrm.exception.StudentNotFoundException;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.DataExporter;
import edu.ccrm.io.DataImporter;
import edu.ccrm.service.*;
import edu.ccrm.util.RecursiveFileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class CCRMApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentService studSrv = new StudentServiceImpl();
    private static final CourseService crsSrv = new CourseServiceImpl();
    private static final InstructorService instSrv = new InstructorServiceImpl();
    private static final EnrollmentService enrSrv = new EnrollmentServiceImpl();
    private static final BackupService bkupSrv = new BackupService();
    private static final DataExporter dataExp = new DataExporter();
    private static final DataImporter dataImp = new DataImporter(studSrv, crsSrv);

    public static void main(String[] args) {
        System.out.println("Welcome to the Campus Course & Records Manager (CCRM)");

        boolean flag = true;
        while (flag) {
            printMenu();
            int ch = getInt();
            sc.nextLine();
            switch (ch) {
                case 1 -> studMgmt();
                case 2 -> instMgmt();
                case 3 -> courseMgmt();
                case 4 -> gradeMgmt();
                case 5 -> fileUtil();
                case 6 -> flag = false; 
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Thank you for using CCRM. Goodbye!");
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Instructors");
        System.out.println("3. Manage Courses");
        System.out.println("4. Grades & Transcripts");
        System.out.println("5. File Utilities");
        System.out.println("6. Exit");
        System.out.print("Select: ");
    }

    private static void studMgmt() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Update Student");
        System.out.print("Select: ");
        int opt = getInt();
        sc.nextLine();

        try {
            switch (opt) {
                case 1:
                    System.out.print("Enter ID: ");
                    int sid = getInt();
                    sc.nextLine();
                    System.out.print("Enter Reg No: ");
                    String rg = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String nm = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String em = sc.nextLine();
                    try {
                        studSrv.addStudent(new Student(sid, rg, nm, em, LocalDate.of(2005, 5, 20)));
                        System.out.println("Student added!");
                    } catch (DuplicateEntryException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("All students:");
                    studSrv.getAllStudents().forEach(s -> System.out.println(s.getDetailedProfile()));
                    break;
                case 3:
                    System.out.print("Enter Reg No to update: ");
                    String ru = sc.nextLine();
                    System.out.print("Enter new Name: ");
                    String nn = sc.nextLine();
                    System.out.print("Enter new Email: ");
                    String ne = sc.nextLine();
                    studSrv.updateStudent(ru, nn, ne);
                    System.out.println("Updated successfully!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (StudentNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void instMgmt() {
        System.out.println("\n--- Instructor Management ---");
        System.out.println("1. Add Instructor");
        System.out.println("2. View Instructors");
        System.out.print("Select: ");
        int opt = getInt();
        sc.nextLine();

        if (opt == 1) {
            System.out.print("Enter ID: ");
            int iid = getInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String nm = sc.nextLine();
            System.out.print("Enter Email: ");
            String em = sc.nextLine();
            System.out.print("Enter Dept: ");
            String dp = sc.nextLine();
            try {
                instSrv.addInstructor(new Instructor(iid, nm, em, LocalDate.now(), dp));
                System.out.println("Instructor added!");
            } catch (DuplicateEntryException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else if (opt == 2) {
            System.out.println("All instructors:");
            instSrv.getAllInstructors().forEach(i -> System.out.println(i.getDetailedProfile()));
        } else
            System.out.println("Invalid choice.");
    }

    private static void courseMgmt() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add Course");
        System.out.println("2. View Courses");
        System.out.println("3. Assign Instructor");
        System.out.print("Select: ");
        int opt = getInt();
        sc.nextLine();

        switch (opt) {
            case 1:
                System.out.print("Enter Code: ");
                String cd = sc.nextLine();
                System.out.print("Enter Title: ");
                String tt = sc.nextLine();
                System.out.print("Enter Credits: ");
                int cr = getInt();
                sc.nextLine();
                System.out.print("Enter Dept: ");
                String dp = sc.nextLine();
                try {
                    Course c = new Course.Builder(cd, tt, cr).department(dp).build();
                    crsSrv.addCourse(c);
                    System.out.println("Course added!");
                } catch (DuplicateEntryException e) {
                    System.err.println("Error: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("All courses:");
                crsSrv.getAllCourses().forEach(System.out::println);
                break;
            case 3:
                System.out.print("Enter Course Code: ");
                String cc = sc.nextLine();
                System.out.print("Enter Instructor ID: ");
                int iid = getInt();
                sc.nextLine();
                try {
                    crsSrv.assignInstructorToCourse(cc, iid);
                } catch (CourseNotFoundException | InstructorNotFoundException e) {
                    System.err.println("Error: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void gradeMgmt() {
        System.out.println("\n--- Grades & Transcripts ---");
        System.out.println("1. Enroll Student");
        System.out.println("2. Unenroll Student");
        System.out.println("3. Assign Grade");
        System.out.println("4. View Transcript");
        System.out.println("5. Compute GPA");
        System.out.print("Select: ");
        int opt = getInt();
        sc.nextLine();

        String rg;
        String cd;
        try {
            switch (opt) {
                case 1:
                    System.out.print("Enter Student Reg No: ");
                    rg = sc.nextLine();
                    System.out.print("Enter Course Code: ");
                    cd = sc.nextLine();
                    enrSrv.enrollStudent(rg, cd);
                    break;
                case 2:
                    System.out.print("Enter Student Reg No: ");
                    rg = sc.nextLine();
                    System.out.print("Enter Course Code: ");
                    cd = sc.nextLine();
                    enrSrv.unenrollStudent(rg, cd);
                    break;
                case 3:
                    System.out.print("Enter Student Reg No: ");
                    rg = sc.nextLine();
                    System.out.print("Enter Course Code: ");
                    cd = sc.nextLine();
                    System.out.print("Enter Grade: ");
                    Grade g = Grade.valueOf(sc.nextLine().toUpperCase());
                    enrSrv.assignGrade(rg, cd, g);
                    break;
                case 4:
                    System.out.print("Enter Student Reg No: ");
                    rg = sc.nextLine();
                    enrSrv.printTranscript(rg);
                    break;
                case 5:
                    System.out.print("Enter Student Reg No: ");
                    rg = sc.nextLine();
                    double gpa = enrSrv.calculateGPA(rg);
                    System.out.printf("GPA for %s: %.2f\n", rg, gpa);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid grade.");
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void fileUtil() {
        System.out.println("\n--- File Utilities ---");
        System.out.println("1. Import Test Data");
        System.out.println("2. Export All Data");
        System.out.println("3. Perform Backup of Exported Data");
        System.out.println("4. Show Backup Directory Size");
        System.out.print("Select: ");
        int opt = getInt();
        sc.nextLine();

        Path p = Paths.get("data", "backups");

        switch (opt) {
            case 1:
                dataImp.importAll();
                break;
            case 2:
                try {
                    dataExp.exportAllData();
                } catch (IOException e) {
                    System.err.println("Export failed: " + e.getMessage());
                }
                break;
            case 3:
                try {
                    bkupSrv.performBackup();
                } catch (IOException e) {
                    System.err.println("Backup failed: " + e.getMessage());
                }
                break;
            case 4:
                try {
                    if (!Files.exists(p)) {
                        System.out.println("No backup yet.");
                        return;
                    }
                    long sz = RecursiveFileUtils.calculateDirectorySize(p);
                    System.out.printf("Backup size: %d bytes (%.2f KB)\n", sz, sz / 1024.0);
                } catch (IOException e) {
                    System.err.println("Size check failed: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static int getInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Enter number: ");
            sc.next();
        }
        return sc.nextInt();
    }
}