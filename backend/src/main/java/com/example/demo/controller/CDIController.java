package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cdi")
public class CDIController {

    @GetMapping("/info-dashboard")
    public String cdiDashboard(HttpSession session, Model model) {
        return checkAuthAndReturnView(session, model, "cdi/info-dashboard");
    }

    @GetMapping("/document")
    public String documentManagement(HttpSession session, Model model) {
        return checkAuthAndReturnView(session, model, "cdi/document");
    }

    private String checkAuthAndReturnView(HttpSession session, Model model, String viewName) {
        String username = (String) session.getAttribute("currentUser");
        String directorate = (String) session.getAttribute("userDirectorate");

        if (username == null) {
            return "redirect:/auth/login";
        }

        if (!"ЦДИ".equals(directorate)) {
            return "redirect:/";
        }

        model.addAttribute("currentUsername", username);
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("userDirectorate", directorate);

        return viewName;
    }
}