package com.example.demo.service.railway;

import com.example.demo.model.railway.RailwaySection;
import com.example.demo.repository.railway.RailwaySectionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RailwayService {

    private final RailwaySectionRepository railwaySectionRepository;

    public RailwayService(RailwaySectionRepository railwaySectionRepository) {
        this.railwaySectionRepository = railwaySectionRepository;

        // Инициализация тестовых данных (можно удалить после добавления реальных)
        initializeTestData();
    }

    public List<RailwaySection> getAllSections() {
        return railwaySectionRepository.findAllByOrderByKilometerAsc();
    }

    public RailwaySection getSectionData(String kilometer) {
        return railwaySectionRepository.findByKilometer(kilometer)
                .orElseThrow(() -> new RuntimeException("Section not found"));
    }

    private void initializeTestData() {
        if (railwaySectionRepository.count() == 0) {
            // Тестовые данные для демонстрации
            RailwaySection section1 = new RailwaySection("km 0-3000", "Участок Москва-Ярославль", "Основной магистральный участок");
            section1.setRegion("Центральный");
            section1.setStatus("active");

            RailwaySection section2 = new RailwaySection("km 3000-6000", "Участок Ярославль-Хабаровск", "Лесной участок");
            section2.setRegion("Сибирский");
            section2.setStatus("active");

            RailwaySection section3 = new RailwaySection("km 6000-9000", "Участок Хабаровск-Владивосток", "Равнинный участок");
            section3.setRegion("Восточный");
            section3.setStatus("maintenance");

            railwaySectionRepository.saveAll(List.of(section1, section2, section3));
        }
    }
}