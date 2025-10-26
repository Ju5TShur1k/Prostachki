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
        String username = (String) session.getAttribute("currentUser");
        String directorate = (String) session.getAttribute("userDirectorate");

        // Проверяем, что пользователь авторизован и из ЦДУ
        if (username == null) {
            return "redirect:/auth/login";
        }

        if (!"ЦДИ".equals(directorate)) {
            return "redirect:/";
        }

        return "cdi/info-dashboard";
    }
}