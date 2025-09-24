package com.example.demo.controller;

import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ← ВАЖНО: этот импорт!
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

    private final AuthService authService;

    public SessionController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                Model model) { // ← Model должен быть параметром
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль"); // ← теперь работает
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) { // ← Добавить Model если нужно передавать данные

        if (authService.validateUserCredentials(username, password)) {
            session.setAttribute("currentUser", username);
            return "redirect:/dashboard";
        } else {
            // Если используем redirect, то передаем через параметры URL
            return "redirect:/login?error";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");

        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username); // ← передаем имя в шаблон
        return "dashboard";
    }
}
