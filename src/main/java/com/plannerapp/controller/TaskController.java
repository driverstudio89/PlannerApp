package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.AddTaskDto;
import com.plannerapp.service.TaskService;
import com.plannerapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class TaskController {

    private final UserSession userSession;
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(UserSession userSession, TaskService taskService, UserService userService) {
        this.userSession = userSession;
        this.taskService = taskService;
        this.userService = userService;
    }

    @ModelAttribute("addTaskData")
    public AddTaskDto addTaskDto() {
        return new AddTaskDto();
    }

    @GetMapping("/task-add")
    public String viewAddTask() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        return "task-add";
    }

    @PostMapping("task-add")
    public String doAddTask(
            @Valid AddTaskDto addTaskDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addTaskData", addTaskDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addTaskData", bindingResult);
            return "redirect:/task-add";
        }

        if (addTaskDto.getDueDate().isBefore(LocalDate.now())) {
            redirectAttributes.addFlashAttribute("wrongDate", true);
            redirectAttributes.addFlashAttribute("addTaskData", addTaskDto);
            return "redirect:/task-add";
        }

        if (!taskService.addTask(addTaskDto)) {
            return "redirect:/";
        }
        return "redirect:home";
    }

    @Transactional
    @PostMapping("/assign-to-me/{taskId}")
    public String assignTaskToMe(@PathVariable long taskId) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        userService.assignToUser(userSession.getId(), taskId);

        return "redirect:/";
    }

    @Transactional
    @PostMapping("/done-remove-task/{id}")
    public String removeTask(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        taskService.removeTask(id);
        return "redirect:/home";
    }

    @PostMapping("/return-task/{id}")
    public String returnTask(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        userService.returnTask(id);
        return "redirect:/home";
    }

}
