package de.unistuttgart.iste.ese.api.assignments;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface AssigneeRepository extends CrudRepository<Assignee, Long> {
    Assignee findByName(String name);

    Assignee findById(long id);
}
