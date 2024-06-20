package com.plannerapp.service;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.TaskInfoDto;
import com.plannerapp.model.dto.UserLoginDto;
import com.plannerapp.model.dto.UserRegisterDto;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.TaskRepository;
import com.plannerapp.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;
    private final TaskRepository taskRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
        this.taskRepository = taskRepository;
    }


    public boolean register(UserRegisterDto userRegisterDto) {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(userRegisterDto.getUsername(), userRegisterDto.getEmail());
        if (optionalUser.isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean login(UserLoginDto userLoginDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userLoginDto.getUsername());
        if (optionalUser.isEmpty()) {
            return false;
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), optionalUser.get().getPassword())) {
            return false;
        }

        if (userSession.isLoggedIn()) {
            return false;
        }

        userSession.login(optionalUser.get().getUsername(), optionalUser.get().getId());
        System.out.println();
        return true;
    }

    public boolean assignToUser(long userId, long taskId) {
        User user = userRepository.findById(userId);
        Task task = taskRepository.findById(taskId);
        user.getAssignedTasks().add(task);
        task.setUser(user);
        userRepository.saveAndFlush(user);
        return true;
    }

    public List<TaskInfoDto> getAssignedTasks(long userId) {
        User user = userRepository.findById(userId);
        return user.getAssignedTasks().stream().map(TaskInfoDto::new).toList();
    }

    @Transactional
    public void returnTask(long id) {
        User user = userRepository.findById(userSession.getId());
        Task task = taskRepository.findById(id);
        task.setUser(null);
        Set<Task> assignedTasks = user.getAssignedTasks();
        assignedTasks.remove(task);
        System.out.println();
        userRepository.save(user);
        taskRepository.save(task);
    }

    public void logout() {
        userSession.logout();
    }
}
