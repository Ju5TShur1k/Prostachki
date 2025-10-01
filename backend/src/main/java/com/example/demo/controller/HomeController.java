package com.example.demo.controller;

import com.example.demo.service.railway.RailwayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final RailwayService railwayService;

    public HomeController(RailwayService railwayService) {
        this.railwayService = railwayService;
    }

    // Главная страница
    @GetMapping("/")
    public String home(HttpSession session,
                       @RequestParam(value = "registrationSuccess", required = false) Boolean registrationSuccess,
                       Model model) {
        String username = (String) session.getAttribute("currentUser");
        model.addAttribute("username", username);
        model.addAttribute("isAuthenticated", username != null);

        // Показываем сообщение об успешной регистрации
        if (Boolean.TRUE.equals(registrationSuccess)) {
            model.addAttribute("showRegistrationMessage", true);
        }

        // Загружаем данные для меню
        model.addAttribute("railwaySections", railwayService.getAllSections());

        return "index";
    }
}