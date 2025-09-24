package com.example.demo.controller;

import com.example.demo.model.dto.RegisterRequest;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register"; // ← ссылается на register.html
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                               BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        // Дополнительная проверка совпадения паролей
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.registerRequest", "Пароли не совпадают");
            return "auth/register";
        }

        try {
            authService.registerUser(registerRequest);
            return "redirect:/auth/register-success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/register-success")
    public String showSuccessPage(Model model) {
        model.addAttribute("message", "Регистрация успешна! Проверьте вашу почту для подтверждения.");
        return "auth/success";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam String token, Model model) {
        try {
            boolean verified = authService.verifyUser(token);
            model.addAttribute("success", true);
            model.addAttribute("message", "Email успешно подтвержден! Теперь вы можете войти в систему.");
            return "auth/verified";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("success", false);
            return "auth/verification-failed";
        }
    }
}