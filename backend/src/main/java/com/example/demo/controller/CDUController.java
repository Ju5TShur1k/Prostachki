package com.example.demo.controller;

import com.example.demo.model.WindowConfiguration;
import com.example.demo.service.WindowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cdu")
public class CDUController {

    private final WindowService windowService;

    public CDUController(WindowService windowService) {
        this.windowService = windowService;
    }

    @GetMapping("/dashboard")
    public String cduDashboard(HttpSession session, Model model) {
        return checkAuthAndReturnView(session, model, "cdu/dashboard");
    }

    @GetMapping("/windows")
    public String windowsManagement(HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");
        String directorate = (String) session.getAttribute("userDirectorate");

        if (username == null || !"ЦДУ".equals(directorate)) {
            return "redirect:/auth/login";
        }

        model.addAttribute("currentUsername", username);
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("userDirectorate", directorate);
        model.addAttribute("windows", windowService.getAllWindows());
        model.addAttribute("newWindow", new WindowConfiguration());

        return "cdu/windows";
    }

    @PostMapping("/windows/create")
    public String createWindow(@ModelAttribute WindowConfiguration windowConfig,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("currentUser");

        if (username == null) {
            return "redirect:/auth/login";
        }

        windowConfig.setCreatedBy(username);
        windowService.createWindow(windowConfig);
        redirectAttributes.addFlashAttribute("success", "Окно успешно создано!");

        return "redirect:/cdu/windows";
    }

    @PostMapping("/windows/delete/{id}")
    public String deleteWindow(@PathVariable Long id,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("currentUser");

        if (username == null) {
            return "redirect:/auth/login";
        }

        windowService.deleteWindow(id);
        redirectAttributes.addFlashAttribute("success", "Окно успешно удалено!");

        return "redirect:/cdu/windows";
    }

    @GetMapping("/results")
    public String analytics(HttpSession session, Model model) {
        return checkAuthAndReturnView(session, model, "cdu/results");
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