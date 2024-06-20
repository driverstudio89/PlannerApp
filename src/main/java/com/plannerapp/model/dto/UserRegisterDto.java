package com.plannerapp.model.dto;

import jakarta.validation.constraints.*;

public class UserRegisterDto {

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 20)
    private String password;

    @NotNull
    @Size(min = 3, max = 20)
    private String confirmPassword;

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull @Size(min = 3, max = 20) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @Size(min = 3, max = 20) String username) {
        this.username = username;
    }

    public @NotNull @Size(min = 3, max = 20) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = 3, max = 20) String password) {
        this.password = password;
    }

    public @NotNull @Size(min = 3, max = 20) String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotNull @Size(min = 3, max = 20) String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
