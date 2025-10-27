package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cdu")
public class CDUController {

    @GetMapping("/dashboard")
    public String cduDashboard(HttpSession session, Model model) {
        return checkAuthAndReturnView(session, model, "cdu/dashboard");
    }

    @GetMapping("/windows")
    public String windowsManagement(HttpSession session, Model model) {
        return checkAuthAndReturnView(session, model, "cdu/windows");
    }

    @GetMapping("/users")
    public String usersManagement(HttpSession session, Model model) {
        return checkAuthAndReturnView(session, model, "cdu/users");
    }

    private String checkAuthAndReturnView(HttpSession session, Model model, String viewName) {
        String username = (String) session.getAttribute("currentUser");
        String directorate = (String) session.getAttribute("userDirectorate");

        if (username == null) {
            return "redirect:/auth/login";
        }

        if (!"ЦДУ".equals(directorate)) {
            return "redirect:/";
        }

        model.addAttribute("currentUsername", username);
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("userDirectorate", directorate);

        return viewName;
    }
}