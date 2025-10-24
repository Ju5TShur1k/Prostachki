package com.example.demo.controller;

import com.example.demo.model.dto.RegisterRequest;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        boolean isAuthenticated = authService.validateUserCredentials(username, password);

        if (isAuthenticated) {
            // Устанавливаем пользователя и его дирекцию в сессию
            session.setAttribute("currentUser", username);
            String directorate = authService.getUserDirectorate(username);
            session.setAttribute("userDirectorate", directorate);

            // Всегда перенаправляем на главную страницу после входа
            return "redirect:/";
        } else {
            return "redirect:/auth/login?error=true";
        }
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest registerRequest,
                               HttpSession session,
                               Model model) {

        // Проверка совпадения паролей
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            model.addAttribute("error", "Пароли не совпадают");
            return "auth/register";
        }

        try {
            authService.registerUser(registerRequest);
            // Автоматически входим после регистрации
            session.setAttribute("currentUser", registerRequest.getUsername());
            session.setAttribute("userDirectorate", registerRequest.getDirectorate());

            // Всегда перенаправляем на главную страницу после регистрации
            return "redirect:/?registrationSuccess=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}