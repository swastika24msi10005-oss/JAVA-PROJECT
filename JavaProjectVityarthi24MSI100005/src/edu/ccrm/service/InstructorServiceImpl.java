package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Instructor;
import edu.ccrm.exception.InstructorNotFoundException;
import java.util.List;

public class InstructorServiceImpl implements InstructorService {

    private final DataStore store = DataStore.getInstance();

    @Override
    public void addInstructor(Instructor inst) {
        store.getInstructors().add(inst);
    }

    @Override
    public Instructor findInstructorById(int instId) throws InstructorNotFoundException {
        return store.getInstructors().stream()
                .filter(i -> i.getId() == instId)
                .findFirst()
                .orElseThrow(() -> new InstructorNotFoundException(
                        "Instructor with ID '" + instId + "' not found."
                ));
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return store.getInstructors();
    }
}
