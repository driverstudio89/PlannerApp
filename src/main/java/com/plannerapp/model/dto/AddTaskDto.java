package com.plannerapp.model.dto;

import com.plannerapp.model.entity.enums.PriorityName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AddTaskDto {

    @NotNull
    @Size(min = 2, max = 50)
    private String description;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private PriorityName priority;

    public @NotNull @Size(min = 2, max = 50) String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @Size(min = 2, max = 50) String description) {
        this.description = description;
    }

    public @NotNull LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NotNull LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public @NotNull PriorityName getPriority() {
        return priority;
    }

    public void setPriority(@NotNull PriorityName priority) {
        this.priority = priority;
    }
}
