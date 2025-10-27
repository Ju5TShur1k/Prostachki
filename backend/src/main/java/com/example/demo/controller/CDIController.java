package com.example.demo.controller;

import com.example.demo.model.dto.CreateReportRequest;
import com.example.demo.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cdi")
public class CDIController {

    private final ReportService reportService;

    public CDIController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/info-dashboard")
    public String cdiDashboard(HttpSession session, Model model) {
        return checkAuthAndReturnView(session, model, "cdi/info-dashboard");
    }

    @GetMapping("/document")
    public String documentManagement(HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");
        if (username != null) {
            model.addAttribute("userReports", reportService.getUserReports(username));
        }
        model.addAttribute("createReportRequest", new CreateReportRequest());
        return checkAuthAndReturnView(session, model, "cdi/document");
    }

    @PostMapping("/document")
    public String createReport(@ModelAttribute CreateReportRequest createReportRequest,
                               HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username != null) {
            reportService.createReport(createReportRequest, username);
        }
        return "redirect:/cdi/document?success=true";
    }

    @GetMapping("/results")
    public String reports(HttpSession session, Model model) {
        model.addAttribute("reports", reportService.getUserReports(
                (String) session.getAttribute("currentUser")
        ));
        return checkAuthAndReturnView(session, model, "cdi/results");
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