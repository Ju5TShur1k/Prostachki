package com.example.demo.service;

import com.example.demo.util.SimplePasswordHasher;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public String encodePassword(String rawPassword) {
        // Временная заглушка
        return "encoded_" + rawPassword;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        // Временная заглушка
        return ("encoded_" + rawPassword).equals(encodedPassword);
    }
}