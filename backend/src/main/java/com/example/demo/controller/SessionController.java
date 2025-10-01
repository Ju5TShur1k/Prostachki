package com.example.demo.controller;

import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

    private final AuthService authService;

    public SessionController(AuthService authService) {
        this.authService = authService;
    }

    // Форма входа
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "auth/login";
    }

    // Обработка входа
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        if (authService.validateUserCredentials(username, password)) {
            session.setAttribute("currentUser", username);
            return "redirect:/"; // Перенаправляем на главную страницу
        } else {
            return "redirect:/login?error";
        }
    }

    // Выход
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    // Личный кабинет
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");

        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);
        return "auth/dashboard";
    }
}