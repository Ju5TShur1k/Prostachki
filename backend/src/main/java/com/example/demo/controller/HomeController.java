package com.example.demo.controller;

import com.example.demo.service.railway.RailwayService;
import com.example.demo.service.WindowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final RailwayService railwayService;
    private final WindowService windowService;

    public HomeController(RailwayService railwayService, WindowService windowService) {
        this.railwayService = railwayService;
        this.windowService = windowService;
    }

    @GetMapping("/")
    public String home(HttpSession session,
                       @RequestParam(value = "registrationSuccess", required = false) Boolean registrationSuccess,
                       Model model) {

        String username = (String) session.getAttribute("currentUser");
        String directorate = (String) session.getAttribute("userDirectorate");

        model.addAttribute("currentUsername", username);
        model.addAttribute("isAuthenticated", username != null);
        model.addAttribute("userDirectorate", directorate);

        // Добавляем активные окна для отображения на главной
        model.addAttribute("activeWindows", windowService.getAllWindows());

        if (Boolean.TRUE.equals(registrationSuccess)) {
            model.addAttribute("showRegistrationMessage", true);
        }

        model.addAttribute("railwaySections", railwayService.getAllSections());

        return "index";
    }
}