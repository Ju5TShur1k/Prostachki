package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.dto.RegisterRequest;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;


    public AuthService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    // Регистрация пользователя
    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Имя пользователя уже занято!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email уже используется!");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Пароли не совпадают!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordService.encodePassword(registerRequest.getPassword()));
        user.setVerificationToken(UUID.randomUUID().toString());
        user.setRegistrationDate(LocalDateTime.now());
        user.setEnabled(false);

        return userRepository.save(user);
    }

    // Подтверждение пользователя
    public boolean verifyUser(String token) {
        Optional<User> userOptional = userRepository.findByVerificationToken(token);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Неверный токен подтверждения");
        }

        User user = userOptional.get();
        user.setEnabled(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return true;
    }

    // Поиск пользователя по имени
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Проверка учетных данных
    public boolean validateUserCredentials(String username, String password) {
        // Временная упрощенная проверка
        return "admin".equals(username) && "admin".equals(password);
    }

}