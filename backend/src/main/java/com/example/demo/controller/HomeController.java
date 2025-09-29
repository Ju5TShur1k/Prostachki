package com.example.demo.controller;

import com.example.demo.service.railway.RailwayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    private final RailwayService railwayService;

    public HomeController(RailwayService railwayService) {
        this.railwayService = railwayService;
    }

    // Главная страница
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");
        model.addAttribute("username", username);
        model.addAttribute("isAuthenticated", username != null);

        // Загружаем данные для меню (километры, участки и т.д.)
        model.addAttribute("railwaySections", railwayService.getAllSections());

        return "index";
    }

    // Страница выбора километра
    @GetMapping("/railway/{kilometer}")
    public String railwaySection(@PathVariable String kilometer,
                                 HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("kilometer", kilometer);
        model.addAttribute("sectionData", railwayService.getSectionData(kilometer));
        model.addAttribute("username", username);

        return "railway/section";
    }
}