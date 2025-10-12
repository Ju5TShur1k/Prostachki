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

    @GetMapping("/login")
    public String showLoginForm(Model model,
                                @RequestParam(value = "error", required = false) Boolean error) {
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        System.out.println("=== DEBUG LOGIN ATTEMPT ===");
        System.out.println("Username: " + username);
        System.out.println("Session ID before: " + session.getId());

        boolean isAuthenticated = authService.validateUserCredentials(username, password);

        if (isAuthenticated) {
            // Устанавливаем пользователя в сессию
            session.setAttribute("currentUser", username);
            System.out.println("Session ID after: " + session.getId());
            System.out.println("Session currentUser set to: " + session.getAttribute("currentUser"));
            System.out.println("=== LOGIN SUCCESS ===");
            return "redirect:/";
        } else {
            System.out.println("=== LOGIN FAILED ===");
            return "redirect:/auth/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        System.out.println("=== DEBUG LOGOUT ===");
        System.out.println("Session ID: " + session.getId());
        System.out.println("User before logout: " + session.getAttribute("currentUser"));

        // Удаляем пользователя из сессии
        session.removeAttribute("currentUser");
        session.invalidate();

        System.out.println("=== LOGOUT SUCCESS ===");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
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
            System.out.println("=== DEBUG REGISTRATION ===");
            System.out.println("User registered and logged in: " + registerRequest.getUsername());
            return "redirect:/?registrationSuccess=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}