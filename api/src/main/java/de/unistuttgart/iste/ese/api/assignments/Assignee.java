package de.unistuttgart.iste.ese.api.assignments;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "assignees")
public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 1)
    @Column(name= "prename")
    private String prename;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9!#$%&'*+-/=?^_`{|}~][A-Za-z0-9.!#$%&'*+-/=?^_`{|}~]*@[a-z]*"+ ".uni-stuttgart.de$")
    private String email;
    
    //Set with all the ids of the to dos the assignee has to do
    @ManyToMany(targetEntity = de.unistuttgart.iste.ese.api.assignments.ToDo.class)
    private Set<ToDo> toDoList;


    // empty default constructor is necessary for JPA
    public Assignee() {
        this.toDoList = new HashSet<ToDo>();
    }

    public Assignee(String prename, String name, String email) {
        this.name = name;
        this.prename = prename;
        this.email= email;
        this.toDoList = new HashSet<ToDo>();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrename() {
        return this.prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ToDo> getToDoList() {
        return this.toDoList;
    }

    public boolean setToDo(ToDo toDo) {
         if(this.getToDoList().contains(toDo)){
            return false;
         }
         this.toDoList.add(toDo);
         return true;
    }
    public void deleteToDo(Long task) {
        if(task==null){
            throw new IllegalArgumentException("task not found");
        }
        Set<ToDo> tasks = this.getToDoList();
        tasks.remove(task);
        this.toDoList = tasks;
    }
    public void setToDoList(Set<ToDo> toDoList) {
        this.toDoList = toDoList;
    }
}
