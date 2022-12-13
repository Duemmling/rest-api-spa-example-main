package de.unistuttgart.iste.ese.api.assignments;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class AssignmentController {

    @Autowired
    private AssigneeRepository AssigneeRepository;
    @Autowired
    private ToDoRepository ToDoRepository;

    // get all to Dos
    @GetMapping("/todos")
    public List<ToDo> getToDos() {
        List<ToDo> allTasks = (List<ToDo>) ToDoRepository.findAll();
        return allTasks;
    }

    // get a single ToDo
    @GetMapping("/todos/{id}")
    public ToDo getToDo(@PathVariable("id") long id) {

        ToDo searchedTask = ToDoRepository.findById(id);
        if (searchedTask != null) {
            return searchedTask;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("ToDo with ID %s not found!", id));
    }

    // get all Assignees
    @GetMapping("/assignees")
    public List<Assignee> getAssignees() {
        List<Assignee> assigneeList = (List<Assignee>) AssigneeRepository.findAll();
        return assigneeList;
    }

    // get a single Assignee
    @GetMapping("/assignees/{id}")
    public Assignee getAssignee(@PathVariable("id") long id) {
        Assignee searchedAssignee = AssigneeRepository.findById(id);
        if (searchedAssignee != null) {
            return searchedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Assignee with ID %s not found!", id));
    }

    // create a task
    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo createTask(@Valid @RequestBody ToDo requestToDo) {
        if(requestToDo.getAssigneeIdList().length>0){
        long[] now = requestToDo.getAssigneeIdList();
        for(int i = 0; i< now.length; i++){
            long newId = now[i];
            Assignee old = AssigneeRepository.findById(newId);
            if(AssigneeRepository.findById(newId)!=null){
                requestToDo.setAssignee(old);
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("the specific Assignees are not in the System"));
            }
        }
        ToDo savedToDo = ToDoRepository.save(requestToDo);
        return savedToDo;
        }
        else{
            ToDo savedToDo = ToDoRepository.save(requestToDo);
            return savedToDo;
        }
    }

    // create a Assginee
    @PostMapping("/assignees")
    @ResponseStatus(HttpStatus.CREATED)
    public Assignee createAssignee(@Valid @RequestBody Assignee requestAssignee) {
        Assignee request = new Assignee(requestAssignee.getName(), requestAssignee.getPrename(), requestAssignee.getEmail());
        Assignee savedAssignee = AssigneeRepository.save(request);
        return savedAssignee;
    }
    // update a task
    @PutMapping("/todos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ToDo updateToDo(@PathVariable("id") long id, @Valid @RequestBody ToDo requestBody) {
        ToDo taskToUpdate = ToDoRepository.findById(id);
        if (taskToUpdate != null) {
            requestBody.setId(id);
            ToDo savedTask = ToDoRepository.save(requestBody);
            return savedTask;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Task with ID %s not found!", id));
    }

    // update a Assignee
    @PutMapping("/assignees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Assignee updateAssignee(@PathVariable("id") long id, @Valid @RequestBody Assignee requestBody) {
        Assignee assigneeToUpdate = AssigneeRepository.findById(id);
        if (assigneeToUpdate != null) {
            requestBody.setId(id);
            Assignee savedAssignee = AssigneeRepository.save(requestBody);
            return savedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Assignee with ID %s not found!", id));
    }
    // delete an Assignee
    @DeleteMapping("/assignees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Assignee deleteAssignee(@PathVariable("id") long id) {
        if(id<1||id>AssigneeRepository.count()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
        }
        Assignee assigneeToDelete = AssigneeRepository.findById(id);
            for(ToDo tasks: ToDoRepository.findAll()){
                if(tasks.getAssigneeList().contains(assigneeToDelete)){
                    tasks.deleteAssignee(assigneeToDelete);
                }
            }
            AssigneeRepository.deleteById(id);
            return assigneeToDelete;
        }
        

    // delete a task
    @DeleteMapping("/todos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ToDo deleteToDo(@PathVariable("id") long id) {
        ToDo taskToDelete = ToDoRepository.findById(id);
        if (taskToDelete != null) {
            if(taskToDelete.getAssigneeList().size() > 0){
                for(Assignee assigneeDelte: taskToDelete.getAssigneeList()){
                    assigneeDelte.deleteToDo(id);
                }
            }
            ToDoRepository.deleteById(id);
            return taskToDelete;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Task with ID %s not found!", id));
    }
}
