package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.TaskInfoDto;
import com.plannerapp.model.entity.Task;
import com.plannerapp.service.TaskService;
import com.plannerapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserSession userSession;
    private final TaskService taskService;
    private final UserService userService;

    public HomeController(UserSession userSession, TaskService taskService, UserService userService) {
        this.userSession = userSession;
        this.taskService = taskService;
        this.userService = userService;
    }



    @GetMapping("/")
    public String nonLoggedIndex() {
        if (!userSession.isLoggedIn()) {
            return "index";
        }
        return "redirect:/home";
    }

    @Transactional
    @GetMapping("/home")
    public String loggedInIndex(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        List<Task> allUnassignedTasks = taskService.findAllUnassignedTasks();

        List<TaskInfoDto> assignedTasks = userService.getAssignedTasks(userSession.getId());


        model.addAttribute("allUnassignedTasks", allUnassignedTasks);
        model.addAttribute("allAssignedTasks", assignedTasks);


        return "home";
    }

}
