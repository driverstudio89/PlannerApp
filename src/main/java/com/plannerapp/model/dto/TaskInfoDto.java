package com.plannerapp.model.dto;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TaskInfoDto {

    @NotNull
    private long id;

    @NotNull
    private String description;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private Priority priority;

    public TaskInfoDto(Task task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.priority = task.getPriority();
    }

    @NotNull
    public long getId() {
        return id;
    }

    public void setId(@NotNull long id) {
        this.id = id;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public @NotNull LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NotNull LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public @NotNull Priority getPriority() {
        return priority;
    }

    public void setPriority(@NotNull Priority priority) {
        this.priority = priority;
    }
}
