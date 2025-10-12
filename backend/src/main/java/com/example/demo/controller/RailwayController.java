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
        model.addAttribute("repairPeriod", "15 сентября 2025 - 30 сентября 2025");
        model.addAttribute("workType", "Частичная замена шпал и балласта");
        model.addAttribute("contractor", "ООО \"Желдорремонт\"");

        return "railway/krasnoyarsk-section";
    }

    @GetMapping("/east-siberia")
    public String eastSiberiaSection(Model model) {
        model.addAttribute("sectionName", "Участок Восточно-Сибирской ж/д");
        model.addAttribute("kmRange", "Км 125-230");
        model.addAttribute("description", "Горный участок со сложным рельефом");
        model.addAttribute("hasRepairWork", true);
        model.addAttribute("repairStatus", "В процессе");
        model.addAttribute("repairPeriod", "10 октября 2025 - 25 октября 2025");
        model.addAttribute("workType", "Укрепление склонов и ремонт тоннелей");
        model.addAttribute("contractor", "ООО \"Гордорстрой\"");

        return "railway/East-Siberia";
    }

    @GetMapping("/zabaikalye")
    public String zabaikalyeSection(Model model) {
        model.addAttribute("sectionName", "Участок Забайкальской ж/д");
        model.addAttribute("kmRange", "Км 230-335");
        model.addAttribute("description", "Развинный участок, текущие работы по обслуживанию");
        model.addAttribute("hasRepairWork", false);
        model.addAttribute("repairStatus", "Не запланированы");
        model.addAttribute("repairPeriod", "Работы не запланированы");
        model.addAttribute("workType", "Регулярное техническое обслуживание");
        model.addAttribute("contractor", "Служба пути Забайкальской ж/д");

        return "railway/Zabaikalye";
    }
}