package com.example.ideahub_backend.controller;

import com.example.ideahub_backend.dto.UserDto;
import com.example.ideahub_backend.dto.UpdateProfileRequest;
import com.example.ideahub_backend.model.User;
import com.example.ideahub_backend.security.JwtUtils;
import com.example.ideahub_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private JwtUtils jwtUtils;

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
                        String token = jwtUtils.generateToken(existing.getId(), existing.getUsername());
                        return ResponseEntity.ok(token);
                    } else {
                        return ResponseEntity.status(401).body("Неверный пароль");
                    }
                })
                .orElse(ResponseEntity.status(404).body("Пользователь не найден"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) { //getPrincipal()-авторизован
            return ResponseEntity.status(401).build();
        }
        
        Long userId = (Long) authentication.getPrincipal();
        return userService.getById(userId)
                .map(user -> {
                    UserDto dto = new UserDto(user.getId(), user.getUsername(), user.getEmail());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.status(404).build());
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UpdateProfileRequest request, Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }

        Long userId = (Long) authentication.getPrincipal();
        return userService.getById(userId)
                .map(existing -> {
                    try {
                        User updated = userService.updateProfile(userId, request.getUsername(), request.getEmail());
                        UserDto dto = new UserDto(updated.getId(), updated.getUsername(), updated.getEmail());
                        return ResponseEntity.ok(dto);
                    } catch (RuntimeException ex) {
                        String message = ex.getMessage();
                        if ("Имя пользователя уже занято".equals(message) || "Email уже используется".equals(message)) {
                            return ResponseEntity.status(409).body(message);
                        }
                        if ("Пользователь не найден".equals(message)) {
                            return ResponseEntity.status(404).body(message);
                        }
                        return ResponseEntity.badRequest().body(message);
                    }
                })
                .orElse(ResponseEntity.status(404).body("Пользователь не найден"));
    }

}
