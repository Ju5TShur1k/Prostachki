package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public String encodePassword(String rawPassword) {
        // Простое шифрование для теста
        return "encoded_" + rawPassword;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        // Простая проверка для теста
        return ("encoded_" + rawPassword).equals(encodedPassword);
    }
}