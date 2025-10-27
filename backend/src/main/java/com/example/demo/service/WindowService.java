package com.example.demo.service;

import com.example.demo.model.WindowConfiguration;
import com.example.demo.repository.WindowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindowService {

    private final WindowRepository windowRepository;

    public WindowService(WindowRepository windowRepository) {
        this.windowRepository = windowRepository;
    }

    public List<WindowConfiguration> getWindowsByRailwaySection(String section) {
        return windowRepository.findByRailwaySectionAndIsActiveTrue(section);
    }
    public List<WindowConfiguration> getAllWindows() {
        return windowRepository.findByIsActiveTrue();
    }

    public List<WindowConfiguration> getUserWindows(String username) {
        return windowRepository.findByCreatedBy(username);
    }

    public WindowConfiguration createWindow(WindowConfiguration windowConfig) {
        return windowRepository.save(windowConfig);
    }

    public void deleteWindow(Long id) {
        windowRepository.deleteById(id);
    }

    public WindowConfiguration getWindowById(Long id) {
        return windowRepository.findById(id).orElse(null);
    }
}