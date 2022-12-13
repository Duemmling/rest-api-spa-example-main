package de.unistuttgart.iste.ese.api.assignments;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.tomcat.jni.Time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "description")
    private String description;

    @Column(name = "finished")
    private boolean finished;

    private long[] assigneeIdList;
    //List with ids of assignees who are responsible for this to do
    @Column(name = "assigneeList")
    @ManyToMany(targetEntity = de.unistuttgart.iste.ese.api.assignments.Assignee.class)
    private Set<Assignee> assigneeList;

    @Column(name = "createdDate")
    private Long createdDate;

    //@Size(min = 3)
    //@Pattern(regexp = "^[0-9]*$")
    @Column(name = "dueDate")
    private Long dueDate;

    @Column(name = "finishedDate")
    private Long finishedDate;

    // empty default constructor is necessary for JPA
    public ToDo() {
        this.assigneeList = new HashSet<Assignee>();
    }

    public ToDo(String title, String description, long[] assigneeIdList) {
        this.title = title;
        this.description = description;
        this.finished = false;
        this.createdDate = null;
        this.dueDate = null;
        this.finishedDate = null;
        this.assigneeIdList = null;
        this.assigneeList = null;
    }

    public ToDo(String title, String description, boolean finish, long[]assigneeIdList, Date dueDate, Date finsishDate, Date createdDate) {
        this.title = title;
        this.description = description;
        this.finished = finish;
        this.createdDate = createdDate.getTime();
        this.dueDate = dueDate.getTime();
        this.finishedDate = finsishDate.getTime();
        this.assigneeIdList = assigneeIdList;
        this.assigneeList = new HashSet<>();
    }

    public ToDo(String title, String description, Set<Assignee> assignees, Date dueDate, Date finsishDate, Date createdDate) {
        this.title = title;
        this.description = description;
        this.finished = false;
        this.createdDate = createdDate.getTime();
        this.dueDate = dueDate.getTime();
        this.finishedDate = finsishDate.getTime();
        this.assigneeList = assignees;
        this.assigneeIdList = null;
    }

    public ToDo(String title, String description,  boolean finish, Date finsishDate, Date createdDate, Date duDate) {
        this.title = title;
        this.description = description;
        this.finished = finish;
        this.createdDate = createdDate.getTime();
        this.dueDate = duDate.getTime();
        this.finishedDate = finsishDate.getTime();
        this.assigneeList = new HashSet<Assignee>();
        this.assigneeIdList = null;
    }

    public ToDo(String title, String description) {
        this.title = title;
        this.description = description;
        this.finished = false;
        this.createdDate = null;
        this.dueDate = null;
        this.finishedDate = null;
        this.assigneeList = new HashSet<Assignee>();
        this.assigneeIdList = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) throws IllegalArgumentException{
        if(this.getId()==id){
            throw new IllegalArgumentException("The id hasn't changed");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
     public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean getFinished() {
        return this.finished;
    }

    public void setFinished() {
        if(this.getFinished()){
            this.finished = false;
        }
        else {
            this.finished = true;
        }
    }

    public Set<Assignee> getAssigneeList() {
        return this.assigneeList;
    }

    public boolean setAssignee(Assignee assignee) {
        if(this.getAssigneeList().size()==0){
            this.assigneeList.add(assignee);
            return true;
        }
        for(Assignee oldAssignee: this.assigneeList){
            if(oldAssignee.getId() == assignee.getId()){
                return false;
            } 
         }
        this.assigneeList.add(assignee);
        return true;
    }

    public void setAssigneeList(Set<Assignee> assignee) {
        this.assigneeList = assignee;
    }

    public void deleteAssignee(Assignee assignee) {
        if(assignee==null){
            throw new IllegalArgumentException("Assingee not found");
        }
        Set<Assignee> assignees = this.getAssigneeList();
        assignees.remove(assignee);
        this.assigneeList = assignees;
    }

    public Long getCreatedDate(){
        return this.createdDate;
    }

    public void setCreatedDate(Long createdDate){
        this.createdDate = createdDate;
    }

    public Long getDueDate(){
        return this.dueDate;
    }

    public void setDueDate(Long dueDate){
        this.dueDate = dueDate;
    }

    public Long getFinishedDate(){
        return this.finishedDate;
    }

    public void setFinishedDate(Long finishedDate){
        this.finishedDate = finishedDate;
    }

    public long[] getAssigneeIdList(){
        return this.assigneeIdList;
    }

}
