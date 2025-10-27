package com.example.demo.controller;

import com.example.demo.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultsController {

    private final ReportService reportService;

    public ResultsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/results")
    public String showResults(Model model) {
        model.addAttribute("reports", reportService.getApprovedReports());
        return "results";
    }
}