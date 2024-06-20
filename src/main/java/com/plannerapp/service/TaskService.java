package com.plannerapp.service;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.AddTaskDto;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.PriorityRepository;
import com.plannerapp.repo.TaskRepository;
import com.plannerapp.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final UserSession userSession;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, PriorityRepository priorityRepository, UserSession userSession, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
        this.userSession = userSession;
        this.userRepository = userRepository;
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

    public List<Task> findAllUnassignedTasks() {

        List<Task> byUserIsNull = taskRepository.findByUserIsNull();
        return byUserIsNull;
    }

    @Transactional
    public void removeTask(long id) {
        User user = userRepository.findById(userSession.getId());
        Task task = taskRepository.findById(id);
        task.setUser(null);
        Set<Task> assignedTasks = user.getAssignedTasks();
        assignedTasks.remove(task);
        userRepository.save(user);
        taskRepository.save(task);
        taskRepository.delete(task);

    }

}
