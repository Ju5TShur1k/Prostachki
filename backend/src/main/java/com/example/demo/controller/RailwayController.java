package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RailwayController {

    @GetMapping("/krasnoyarsk")
    public String krasnoyarskSection(Model model) {
        model.addAttribute("sectionName", "Участок Красноярской ж/д");
        model.addAttribute("kmRange", "Км 120-125");
        model.addAttribute("description", "Основной магистральный участок центрального направления");
        model.addAttribute("hasRepairWork", true);
        model.addAttribute("repairStatus", "Запланированы");
        model.addAttribute("repairPeriod", "15 сентября 2023 - 30 сентября 2023");
        model.addAttribute("workType", "Частичная замена шпал и балласта");
        model.addAttribute("contractor", "ООО \"Желдорремонт\"");

        return "railway/krasnoyarsk-section"; // Обратите внимание на путь!
    }
}