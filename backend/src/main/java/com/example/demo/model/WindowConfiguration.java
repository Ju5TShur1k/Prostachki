package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "train_windows")
public class WindowConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String railwaySection; // Участок ж/д
    private String startTime;      // Время начала окна
    private String endTime;        // Время окончания окна
    private String date;           // Дата окна
    private String direction;      // Направление движения
    private Boolean isActive;
    private String createdBy;
    private LocalDateTime createdAt;

    // Конструкторы
    public WindowConfiguration() {
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRailwaySection() { return railwaySection; }
    public void setRailwaySection(String railwaySection) { this.railwaySection = railwaySection; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}