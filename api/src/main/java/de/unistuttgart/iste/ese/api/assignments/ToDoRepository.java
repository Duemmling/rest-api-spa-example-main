package de.unistuttgart.iste.ese.api.assignments;

import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, Long> {
    ToDo findByTitle(String title);

    ToDo findById(long id);
}
