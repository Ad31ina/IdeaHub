package com.example.ideahub_backend.controller;

import com.example.ideahub_backend.dto.UserDto;
import com.example.ideahub_backend.model.User;
import com.example.ideahub_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        UserDto dto = new UserDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        return userService.findByEmail(user.getEmail())
                .map(existing -> {
                    if (passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
                        return ResponseEntity.ok("Вход в систему прошел успешно!");
                    } else {
                        return ResponseEntity.status(401).body("Неверный пароль");
                    }
                })
                .orElse(ResponseEntity.status(404).body("Пользователь не найден"));
    }


}
