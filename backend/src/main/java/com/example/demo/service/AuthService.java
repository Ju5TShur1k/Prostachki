package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.dto.RegisterRequest;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public AuthService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public User registerUser(RegisterRequest registerRequest) {
        // Проверка существования пользователя
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Имя пользователя уже занято!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email уже используется!");
        }

        // Проверка выбора дирекции
        if (registerRequest.getDirectorate() == null || registerRequest.getDirectorate().isEmpty()) {
            throw new RuntimeException("Необходимо выбрать дирекцию!");
        }

        // Создание нового пользователя
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordService.encodePassword(registerRequest.getPassword()));
        user.setDirectorate(registerRequest.getDirectorate());
        user.setRegistrationDate(LocalDateTime.now());
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public boolean validateUserCredentials(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        return passwordService.matches(password, user.getPassword());
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getUserDirectorate(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getDirectorate).orElse(null);
    }
}