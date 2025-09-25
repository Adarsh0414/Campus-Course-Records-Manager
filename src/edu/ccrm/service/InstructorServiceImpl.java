package edu.ccrm.service;

import edu.ccrm.domain.Instructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InstructorServiceImpl implements InstructorService {

    private final Map<UUID, Instructor> instructorStore = new ConcurrentHashMap<>();

    @Override
    public Instructor addInstructor(Instructor instructor) {
        if (instructor == null || instructor.getId() == null) {
            throw new IllegalArgumentException("Instructor and instructor ID must not be null");
        }
        instructorStore.put(instructor.getId(), instructor);
        return instructor;
    }

    @Override
    public Optional<Instructor> getInstructorById(UUID id) {
        return Optional.ofNullable(instructorStore.get(id));
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructorStore.values());
    }
}
