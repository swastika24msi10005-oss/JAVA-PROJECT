package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEntryException;
import edu.ccrm.exception.StudentNotFoundException;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final DataStore store = DataStore.getInstance();

    @Override
    public void addStudent(Student stu) {
        boolean regExists = store.getStudents().stream()
                .anyMatch(s -> s.getRegNo().equalsIgnoreCase(stu.getRegNo()));
        if (regExists) {
            throw new DuplicateEntryException("Student with Registration No '" + stu.getRegNo() + "' already exists.");
        }

        boolean idExists = store.getStudents().stream()
                .anyMatch(s -> s.getId() == stu.getId());
        if (idExists) {
            throw new DuplicateEntryException("Student with ID '" + stu.getId() + "' already exists.");
        }

        store.getStudents().add(stu);
    }

    @Override
    public Student findStudentByRegNo(String regNo) throws StudentNotFoundException {
        return store.getStudents().stream()
                .filter(s -> s.getRegNo().equalsIgnoreCase(regNo))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Student with RegNo '" + regNo + "' not found."));
    }

    @Override
    public List<Student> getAllStudents() {
        return store.getStudents();
    }

    @Override
    public void updateStudent(String regNo, String newName, String newEmail) throws StudentNotFoundException {
        Student stu = findStudentByRegNo(regNo);
        stu.setFullName(newName);
        stu.setEmail(newEmail);
    }
}
