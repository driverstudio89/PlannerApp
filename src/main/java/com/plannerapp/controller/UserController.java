package com.plannerapp.controller;

import com.plannerapp.model.dto.UserRegisterDto;
import com.plannerapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @GetMapping("/users/register")
    public String viewLogin() {
        return "register";
    }

    @PostMapping("/users/register")
    public String doRegister(
            @Valid UserRegisterDto userRegisterDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/users/register";
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("passwordMismatch", true);
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);

            return "redirect:/users/register";
        }

        if (!userService.register(userRegisterDto)) {
            redirectAttributes.addFlashAttribute("alreadyInUse", true);
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);

            return "redirect:/users/register";
        }

        return "redirect:/users/login";

    }




}