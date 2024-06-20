package com.plannerapp.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserLoginDto {

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Size(min = 3, max = 20)
    private String password;

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
}
