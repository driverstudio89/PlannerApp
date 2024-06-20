package com.plannerapp.service;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.AddTaskDto;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import com.plannerapp.repo.PriorityRepository;
import com.plannerapp.repo.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final UserSession userSession;

    public TaskService(TaskRepository taskRepository, PriorityRepository priorityRepository, UserSession userSession) {
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
        this.userSession = userSession;
    }

    public boolean addTask(AddTaskDto addTaskDto) {

        if (!userSession.isLoggedIn()) {
            return false;
        }

        Task task = new Task();
        Priority priority = priorityRepository.findByPriorityName(addTaskDto.getPriority());
        task.setDescription(addTaskDto.getDescription());
        task.setDueDate(addTaskDto.getDueDate());
        task.setPriority(priority);
        taskRepository.save(task);
        return true;
    }
}
