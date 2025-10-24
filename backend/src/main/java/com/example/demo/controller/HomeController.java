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

    @GetMapping("/")
    public String home(HttpSession session,
                       @RequestParam(value = "registrationSuccess", required = false) Boolean registrationSuccess,
                       Model model) {

        String username = (String) session.getAttribute("currentUser");
        String directorate = (String) session.getAttribute("userDirectorate");

        model.addAttribute("currentUsername", username);
        model.addAttribute("isAuthenticated", username != null);
        model.addAttribute("userDirectorate", directorate);

        if (Boolean.TRUE.equals(registrationSuccess)) {
            model.addAttribute("showRegistrationMessage", true);
        }

        model.addAttribute("railwaySections", railwayService.getAllSections());

        return "index";
    }
}