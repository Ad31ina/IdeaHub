package com.example.ideahub_backend.service;

import com.example.ideahub_backend.model.User;
import com.example.ideahub_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email уже используется");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Имя пользователя уже занято");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public User updateProfile(Long userId, String newUsername, String newEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (newUsername != null) {
            String trimmed = newUsername.trim();
            if (!trimmed.isEmpty() && !trimmed.equals(user.getUsername())) {
                if (userRepository.existsByUsernameAndIdNot(trimmed, userId)) {
                    throw new RuntimeException("Имя пользователя уже занято");
                }
                user.setUsername(trimmed);
            }
        }

        if (newEmail != null) {
            String trimmed = newEmail.trim();
            if (!trimmed.isEmpty() && !trimmed.equals(user.getEmail())) {
                if (userRepository.existsByEmailAndIdNot(trimmed, userId)) {
                    throw new RuntimeException("Email уже используется");
                }
                user.setEmail(trimmed);
            }
        }

        return userRepository.save(user);
    }
}
