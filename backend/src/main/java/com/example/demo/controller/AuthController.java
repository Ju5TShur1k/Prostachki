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

        boolean isAuthenticated = authService.validateUserCredentials(username, password);

        if (isAuthenticated) {
            // Устанавливаем пользователя и его дирекцию в сессию
            session.setAttribute("currentUser", username);
            String directorate = authService.getUserDirectorate(username);
            session.setAttribute("userDirectorate", directorate);

            // Перенаправляем в зависимости от дирекции
            if ("ЦДУ".equals(directorate)) {
                return "redirect:/cdu/dashboard";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/auth/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.removeAttribute("currentUser");
        session.removeAttribute("userDirectorate");
        session.invalidate();
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
            session.setAttribute("userDirectorate", registerRequest.getDirectorate());

            // Перенаправляем в зависимости от дирекции
            if ("ЦДУ".equals(registerRequest.getDirectorate())) {
                return "redirect:/cdu/dashboard";
            } else {
                return "redirect:/?registrationSuccess=true";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}