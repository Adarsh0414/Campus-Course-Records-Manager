package edu.ccrm.service;

import edu.ccrm.domain.Instructor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstructorService {
    Instructor addInstructor(Instructor instructor);
    Optional<Instructor> getInstructorById(UUID id);
    List<Instructor> getAllInstructors();
}
