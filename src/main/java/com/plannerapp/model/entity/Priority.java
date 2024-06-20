package com.plannerapp.model.entity;

import com.plannerapp.model.entity.enums.PriorityName;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private PriorityName priorityName;

    @Column(nullable = false)
    private String description;

    @OneToMany(targetEntity = Task.class, mappedBy = "priority")
    private Set<Task> tasks;

    public Priority() {
    }

    public Priority(PriorityName priorityName, String description) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PriorityName getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(PriorityName priorityName) {
        this.priorityName = priorityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
