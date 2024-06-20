package com.plannerapp.service;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.UserLoginDto;
import com.plannerapp.model.dto.UserRegisterDto;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
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
}
