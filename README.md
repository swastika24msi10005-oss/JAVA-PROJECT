🎓 Campus Course & Records Manager (CCRM)
Project Overview
A Java console application called the Campus Course & Records Manager (CCRM) was created to manage student and course records for educational institutions. It provides a hands-on example of fundamental Java SE concepts such as Streams API, modern file I/O with NIO.2, Object-Oriented Programming (OOP), and fundamental design patterns.

Using a command-line interface, the application enables an administrator to carry out the following tasks:

Manage Students: List, add, and update students.

Manage Courses: List, add, and search courses.

Manage Instructors: Assign instructors to courses after adding them.

Manage Enrolment: Apply business regulations, such as credit limits, when enrolling and unenrolling students in classes.

Transcripts & Grades: Print student transcripts, calculate GPA, and assign grades.

File Utilities: Make time-stamped backups and import/export data from/to CSV-like files.

How to Run
Prerequisites
Java Development Kit (JDK): Version 17 or higher.
Command-Line Instructions
Clone the Repository
git clone https://github.com/AYUSHMISHRAOFFICIAL/JavaProjectVityarthi24BCE10224.git
cd CCRM
Compile the Project (From the root CCRM directory)
javac -d bin src/edu/ccrm/cli/CCRMApp.java
Run the Application
java -cp bin edu.ccrm.cli.CCRMApp
☕ The Evolution of Java
1995: Java 1.0 is released by Sun Microsystems, introducing the "Write Once, Run Anywhere" philosophy.
2004: Java 5 (Tiger) is a major release, adding significant language features like Generics, Enums, and Annotations.
2014: Java 8 marks a revolutionary change, introducing Lambda Expressions, the Stream API, and a new Date/Time API (java.time).
2021: Java 17 is released as the current primary LTS version, bringing further refinements like sealed classes and pattern matching.
⚖️ Java ME vs. SE vs. EE
Feature	Java ME (Micro Edition)	Java SE (Standard Edition)	Java EE (Enterprise Edition) -> Jakarta EE
Purpose	Small, resource-constrained devices (e.g., embedded systems).	General-purpose desktop, server, and console applications.	Large-scale, distributed, web-based enterprise applications.
Core API	A small subset of the Java SE API.	The core Java platform (java.lang, java.util, etc.).	Extends Java SE with APIs for the web (Servlets, JPA, etc.).
Example Use	SIM cards, early mobile phone apps.	This CCRM project, desktop apps like IntelliJ IDEA.	E-commerce websites, banking systems.
🏗️ Java Architecture: JDK, JRE, & JVM
The Java platform consists of three core components that work together.

Java Virtual Machine (JVM): An abstract machine that offers the environment needed to run Java bytecode. It converts native machine code from bytecode.
JRE (Java Runtime Environment): A software package containing everything needed to run a compiled Java application. It comes with the Java Class Library and the JVM. The full software development kit for developers is the Java Development Kit (JDK). The JRE, a compiler (javac), a debugger (jdb), and additional tools are included.
Simply put: To write code, you utilise the JDK. A computer requires the JRE in order to execute that code. The code is then run by the JRE using the JVM.

💻 Installation and Setup
Windows JDK Installation
Download: Get the JDK installer from Oracle or Adoptium (OpenJDK).
Install: Run the installer.
Set Environment Variables: Set the JAVA_HOME system variable to your JDK path (e.g., C:\Program Files\Java\jdk-17) and add %JAVA_HOME%\bin to your Path variable.
Verify: Open a new Command Prompt and run java -version.
Eclipse IDE Setup
Download: Get the "Eclipse Installer" and choose "Eclipse IDE for Java Developers".
Create Project: In Eclipse, go to File -> New -> Java Project.
Run Application: Right-click on CCRMApp.java -> Run As -> Java Application.
🗺️ Syllabus Topic Mapping
This table maps key Java concepts to their implementation within the CCRM project.

Concept/Topic	File(s) / Package	Example / Method / Line Reference
Packages	edu.ccrm.*	Project organized into logical packages (cli, domain, etc.).
OOP: Encapsulation	Person.java	private fields with public getters and setters.
OOP: Inheritance	Student.java	public class Student extends Person { ... }
OOP: Abstraction	Person.java, StudentService.java	public abstract class Person, public interface StudentService
OOP: Polymorphism	CCRMApp.java	A Person reference can hold a Student or Instructor object.
Design Pattern: Singleton	config/DataStore.java	private static instance;, private DataStore(), getInstance()
Design Pattern: Builder	domain/Course.java	Static nested Builder class for Course object creation.
Control Flow (if, switch)	cli/CCRMApp.java	if (choice == 1), switch (choice) in all menu methods.
Loops (while, for-each)	cli/CCRMApp.java	while (running) main loop, forEach used for printing lists.
Enums with Constructors	domain/Grade.java	Grade(double gradePoint) constructor and gradePoint field.
Interfaces	service/CourseService.java	Defines the contract for course-related business logic.
Exception Handling	cli/CCRMApp.java	Service calls in menus are wrapped in try-catch blocks.
Custom Exceptions	exception/StudentNotFoundException.java	Custom exception for specific business errors.
File Input / Parsing	io/DataImporter.java	Files.lines().skip(1).map(line -> line.split(",")) to read CSV.
NIO.2 (File Output)	io/DataExporter.java	Use of Path, Paths, Files.write(), and Files.copy().
Streams API	service/EnrollmentServiceImpl.java	.stream().filter(...).collect(...) for data processing.
Date/Time API	domain/Person.java	Use of LocalDate for birth dates and LocalDateTime for timestamps.
Recursion	util/RecursiveFileUtils.java	calculateDirectorySize() method calls itself for subdirectories.
💡 Notes
Enabling Assertions
Assertions are used to verify program invariants. To enable them, use the -ea flag when running from the command line.

# Example of running with assertions enabled
java -cp bin -ea edu.ccrm.cli.CCRMApp
