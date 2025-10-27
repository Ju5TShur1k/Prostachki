package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultsController {

    @GetMapping("/results")
    public String resultsPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");
        String directorate = (String) session.getAttribute("userDirectorate");

        if (username == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("currentUsername", username);
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("userDirectorate", directorate);

        return "results";
    }
}